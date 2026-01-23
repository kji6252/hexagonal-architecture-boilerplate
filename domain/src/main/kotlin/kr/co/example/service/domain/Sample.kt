package kr.co.example.service.domain

import java.time.LocalDateTime

/**
 * 샘플 도메인 엔티티
 *
 * 헥사고날 아키텍처의 Domain Layer에 속하는 순수 비즈니스 도메인 객체입니다.
 * 외부 의존성 없이 비즈니스 로직을 포함합니다.
 */
data class Sample(
  val id: Long,
  val message: String,
  val createdAt: LocalDateTime,
  val updatedAt: LocalDateTime
) {
  /**
   * 메시지를 업데이트하고 수정 시간을 갱신합니다.
   *
   * @param newMessage 새로운 메시지
   * @return 업데이트된 Sample 엔티티
   */
  fun updateMessage(newMessage: String): Sample {
    return this.copy(
      message = newMessage,
      updatedAt = LocalDateTime.now()
    )
  }

  /**
   * 메시지 길이를 반환합니다.
   *
   * @return 메시지 길이
   */
  fun messageLength(): Int {
    return message.length
  }

  companion object {
    /**
     * 새로운 Sample 엔티티를 생성합니다.
     *
     * @param id 샘플 ID
     * @param message 메시지
     * @return 새 Sample 엔티티
     */
    fun create(id: Long, message: String): Sample {
      val now = LocalDateTime.now()
      return Sample(
        id = id,
        message = message,
        createdAt = now,
        updatedAt = now
      )
    }
  }
}
