package kr.co.example.service.domain

import java.time.LocalDateTime

/**
 * S3 프리사인드 URL 정보
 * 
 * @property url 프리사인드 URL
 * @property expiresAt URL 만료 시각
 */
data class PresignedUrl(
  val url: String,
  val expiresAt: LocalDateTime
)
