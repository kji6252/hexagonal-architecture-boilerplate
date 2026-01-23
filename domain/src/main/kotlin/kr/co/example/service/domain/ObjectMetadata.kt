package kr.co.example.service.domain

import java.time.LocalDateTime

/**
 * S3 오브젝트 메타데이터
 *
 * @property key 오브젝트 키
 * @property size 오브젝트 크기 (bytes)
 * @property lastModified 마지막 수정 시각
 * @property contentType 콘텐츠 타입 (MIME type)
 */
data class ObjectMetadata(
  val key: String,
  val size: Long,
  val lastModified: LocalDateTime,
  val contentType: String?,
  val expiration: LocalDateTime,
)
