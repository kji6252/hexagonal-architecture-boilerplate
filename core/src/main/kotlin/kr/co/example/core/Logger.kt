package kr.co.example.core

import mu.KotlinLogging

inline fun <reified T> logger() = KotlinLogging.logger(T::class.java.name)
