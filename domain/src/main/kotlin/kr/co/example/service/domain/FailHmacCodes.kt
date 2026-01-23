package kr.co.example.service.domain

enum class FailHmacCodes(
  override val status: Int,
  override val code: String,
  override val message: String,
) : FailEnum {
  INVALID_HASH(417, "HMAC_9001", "Something went wrong.[HMAC_9001]"),
  TIMEOUT_ERROR(417, "HMAC_9002", "Something went wrong.[HMAC_9002]"),
  INTERNAL_SERVER_ERROR(417, "HMAC_9003", "Something went wrong.[HMAC_9003]"),
}
