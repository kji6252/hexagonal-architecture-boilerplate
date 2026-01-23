package kr.co.example.service.domain

enum class FailRedisCodes(
  override val status: Int,
  override val code: String,
  override val message: String,
) : FailEnum {
  INTERNAL_REDIS_ERROR(500, "RED_5001", "Something went wrong.[RED_5001]"),
}
