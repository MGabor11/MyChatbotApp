package com.marossolutions.mychatbotapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform