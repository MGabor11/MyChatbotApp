package com.marossolutions.mychatbotapp.di

import com.marossolutions.mychatbotapp.di.qualifier.openAIApiKey
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single {
        val openAIApiKey: String = get(qualifier = openAIApiKey)

        HttpClient {
            install(plugin = ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        explicitNulls = false
                    },
                    contentType = ContentType.Any
                )
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
            }
            defaultRequest {
                url("https://api.openai.com/v1/")
                header("Content-Type", "application/json")
                header("Authorization", "Bearer $openAIApiKey")
            }
        }
    }
}