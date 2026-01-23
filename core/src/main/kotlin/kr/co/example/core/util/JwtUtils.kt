package kr.co.uplus.core.util

import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.interfaces.DecodedJWT
import kr.co.uplus.core.logger

object JwtUtils {
    
    /**
     * JWT 토큰을 검증하고 userId를 추출합니다.
     * @param token JWT 토큰
     * @return userId (subject)
     * @throws IllegalArgumentException 토큰이 유효하지 않을 경우
     */
    fun decodeAndValidateToken(token: String): String {
        try {
            val decodedToken = JWT.decode(token)
            val subject = decodedToken.subject
            if (subject.isNullOrBlank()) {
                throw IllegalArgumentException("Invalid token: subject(sub) is missing.")
            }
            logger().info { "Extract userId: $subject" }
            return subject
        } catch (e: JWTDecodeException) {
            throw IllegalArgumentException("Invalid token: cannot decode JWT.", e)
        }
    }
    
    /**
     * JWT 토큰의 유효성만 검증합니다 (userId 반환 안함)
     * @param token JWT 토큰
     * @throws IllegalArgumentException 토큰이 유효하지 않을 경우
     */
    fun validateToken(token: String) {
        decodeAndValidateToken(token)
    }
    
    /**
     * JWT 토큰을 디코딩합니다 (검증 없이)
     * @param token JWT 토큰
     * @return DecodedJWT 객체
     * @throws IllegalArgumentException 토큰이 유효하지 않을 경우
     */
    fun decodeTokenOnly(token: String): DecodedJWT {
        try {
            return JWT.decode(token)
        } catch (e: JWTDecodeException) {
            throw IllegalArgumentException("Invalid token: cannot decode JWT.", e)
        }
    }
    
    /**
     * JWT 토큰에서 userId만 추출 (로깅 없이)
     * @param token JWT 토큰
     * @return userId (subject)
     * @throws IllegalArgumentException 토큰이 유효하지 않을 경우
     */
    fun extractUserId(token: String): String {
        val decodedToken = decodeTokenOnly(token)
        val subject = decodedToken.subject
        if (subject.isNullOrBlank()) {
            throw IllegalArgumentException("Invalid token: subject(sub) is missing.")
        }
        return subject
    }
    
    /**
     * JWT 토큰에서 특정 claim 값을 추출합니다.
     * @param token JWT 토큰
     * @param claimName claim 이름
     * @return claim 값 (String)
     */
    fun extractClaim(token: String, claimName: String): String? {
        val decodedToken = decodeTokenOnly(token)
        return decodedToken.getClaim(claimName)?.asString()
    }
} 