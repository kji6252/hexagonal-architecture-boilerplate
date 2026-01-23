package kr.co.example.service.dto

import kr.co.uplus.core.util.ObjectMapperConverter

data class TokenDto(
    val accessToken: String,
    val refreshToken: String?,
    val idToken: String?,
    val tokenType: String,
    val marketId: String? = null,
) {
    fun toJson(): String {
        return ObjectMapperConverter.toJsonStringWithSnakeCase(this)
    }

    companion object {
        fun fromJson(json: String): TokenDto {
            return ObjectMapperConverter.fromJsonStringWithSnakeCase(json, TokenDto::class.java)
        }
    }
}