package com.marossolutions.mychatbotapp.tensorflowlite

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import com.google.android.gms.tflite.java.TfLite
import com.marossolutions.mychatbotapp.service.DispatcherProvider
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.tensorflow.lite.InterpreterApi
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.StringTokenizer
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private const val ERROR_THRESHOLD = 0.4
private const val FALLBACK_SENTENCE = "I do not understand your sentence!"
private const val INTENTS_JSON_FILE = "intents.json"
private const val WORDS_JSON_FILE = "words.json"
private const val CLASSES_JSON_FILE = "classes.json"
private const val CHATBOT_MODEL_FILE = "chatbot_model.tflite"

actual class TensorFlowLiteInputProcessorServiceImpl(
    private val context: Context,
    private val assetManager: AssetManager,
    private val dispatcherProvider: DispatcherProvider
) : TensorFlowLiteInputProcessorService {

    private lateinit var interpreter: InterpreterApi
    private lateinit var words: List<String>
    private lateinit var classes: List<String>
    private lateinit var intents: Intents

    override suspend fun loadResources() = withContext(dispatcherProvider.default) {
        val chatBotModel = getModel()
        interpreter = createInterpreter(chatBotModel)

        // Load intents.json
        val intentsStream = assetManager.open(INTENTS_JSON_FILE)
        val intentsText = intentsStream.bufferedReader().use { it.readText() }
        intents = Json.decodeFromString(intentsText)

        // Load words.json
        val wordsStream = assetManager.open(WORDS_JSON_FILE)
        val wordsText = wordsStream.bufferedReader().use { it.readText() }
        words = Json.decodeFromString(wordsText)

        // Load classes.json
        val classesStream = assetManager.open(CLASSES_JSON_FILE)
        val classesText = classesStream.bufferedReader().use { it.readText() }
        classes = Json.decodeFromString(classesText)
    }

    override fun predict(message: String): String {
        val input = processInput(message)
        val output = FloatArray(classes.size)
        interpreter.run(
            /* input = */ arrayOf(input),
            /* output = */ arrayOf(output)
        )

        // Get the intent with the highest probability
        val maxPrediction = output.max()
        if (maxPrediction < ERROR_THRESHOLD) {
            return FALLBACK_SENTENCE
        }

        val selectedClass = classes[output.indexOfFirst { it == maxPrediction }]
        val response = intents.intents.firstOrNull() { it.tag == selectedClass }
            ?.responses?.random()
            ?: FALLBACK_SENTENCE

        return response
    }

    private fun getModel(): MappedByteBuffer {
        val modelFile = assetManager.openFd(CHATBOT_MODEL_FILE)
        val inputStream = FileInputStream(modelFile.fileDescriptor)
        val fileChannel = inputStream.channel
        val modelBuffer = fileChannel.map(
            /* mode = */ FileChannel.MapMode.READ_ONLY,
            /* position = */ modelFile.startOffset,
            /* size = */ modelFile.declaredLength
        )
        return modelBuffer
    }

    private suspend fun createInterpreter(modelBuffer: MappedByteBuffer): InterpreterApi =
        suspendCoroutine { continuation ->
            TfLite.initialize(context).addOnSuccessListener {
                val interpreterOption = InterpreterApi.Options()
                    .setRuntime(InterpreterApi.Options.TfLiteRuntime.FROM_SYSTEM_ONLY)
                val interpreter = InterpreterApi.create(modelBuffer, interpreterOption)
                continuation.resume(interpreter)
            }.addOnFailureListener { exception ->
                Log.e("Interpreter", "Cannot initialize interpreter", exception)
                continuation.resumeWithException(exception)
            }
        }


    private fun processInput(sentence: String): FloatArray {
        val filteredSentence = sentence.lowercase().filter { it.isLetter() || it.isWhitespace() }
        val tokenizer = StringTokenizer(filteredSentence)
        val tokenizedWords = Array(tokenizer.countTokens()) { tokenizer.nextToken() }
        val bag = FloatArray(words.size) /*{ 0f }*/
        for (word in tokenizedWords) {
            val index = words.indexOf(word)
            if (index >= 0) bag[index] = 1f
        }
        return bag
    }
}
