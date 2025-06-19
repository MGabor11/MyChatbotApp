//
//  NativeResponse.swift
//  iosApp
//
//  Created by Marosfalvi Gábor on 2025. 06. 19..
//  Copyright © 2025 orgName. All rights reserved.
//
import ComposeApp

class IOSNativeResponseProvider: NativeResponseProvider {
    
    private var words: [String] = []
    private var classes: [String] = []
    
    func loadResources() async throws {
        try await withCheckedThrowingContinuation { continuation in
            DispatchQueue.global(qos: .userInitiated).async {
                do {
                    print("Loading JSONs")
                    // Load words.json
                    let wordsURL = Bundle.main.url(forResource: "words", withExtension: "json")!
                    let wordsData = try Data(contentsOf: wordsURL)
                    self.words = try JSONDecoder().decode([String].self, from: wordsData)
                    
                    // Load classes.json
                    let classesURL = Bundle.main.url(forResource: "classes", withExtension: "json")!
                    let classesData = try Data(contentsOf: classesURL)
                    self.classes = try JSONDecoder().decode([String].self, from: classesData)
                    
                    continuation.resume()
                } catch {
                    continuation.resume(throwing: error)
                }
            }
        }
    }
    
    func predict(message: String) -> String {
        if words.contains(message) {
            return "The given word is available in words array: "+message.capitalized
        } else if classes.contains(message) {
            return "The given word is available in classes array: "+message.capitalized
        } else {
            return "Error: unknown word or class"
        }
    }
}


