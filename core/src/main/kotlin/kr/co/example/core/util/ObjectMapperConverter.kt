package kr.co.uplus.core.util

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

object ObjectMapperConverter {

    private val objectMapper = ObjectMapper()
        .registerModule(kotlinModule())
        .registerModule(JavaTimeModule())
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    private val objectMapperWithSnakeCase = ObjectMapper()
        .registerModule(kotlinModule())
        .registerModule(JavaTimeModule())
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .apply {
            propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
        }

    @Throws(Exception::class)
    fun <T> fromJsonString(jsonString: String, targetType: Class<T>): T {
        return objectMapper.readValue(jsonString, targetType)
    }

    @Throws(Exception::class)
    fun toJsonStringWithSnakeCase(obj: Any): String {
        return objectMapperWithSnakeCase.writeValueAsString(obj)
    }

    @Throws(Exception::class)
    fun <T> fromJsonStringWithSnakeCase(jsonString: String, targetType: Class<T>): T {
        return objectMapperWithSnakeCase.readValue(jsonString, targetType)
    }
}