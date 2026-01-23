package kr.co.example.service.domain

enum class FailApiCodes(
  override val status: Int,
  override val code: String,
  override val message: String,
) : FailEnum {
  RATE_LIMIT_EXCEEDED(429, "API_6001", "Something went wrong.[API_6001]"),
  TOO_MANY_REQUESTS(429, "API_6002", "Something went wrong.[API_6002]"),
  EXTERNAL_API_ERROR(500, "API_6003", "Something went wrong.[API_6003]"),
  DEPRECATED(410, "API_6004", "Something went wrong.[API_6004]"),
  UNKNOWN_ERROR(500, "API_6100", "Something went wrong.[API_6100]"),
}
