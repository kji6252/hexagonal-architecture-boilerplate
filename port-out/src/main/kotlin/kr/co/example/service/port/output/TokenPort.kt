package kr.co.example.service.port.output

import kr.co.example.service.dto.TokenDto

interface TokenPort {
    fun requestToken(userRefreshToken: String): TokenDto
    fun requestServerToken(): TokenDto
    fun requestTokenByAuthCode(authCode: String, state: String, codeVerifier: String): TokenDto
    fun verify(token: String)
    fun revokeToken(token: String)
    fun requestAuthUrl(accessToken: String, encryptedUserId: String): String
}
