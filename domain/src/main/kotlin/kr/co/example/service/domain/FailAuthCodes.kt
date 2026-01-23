package kr.co.example.service.domain

enum class FailAuthCodes(
  override val status: Int,
  override val code: String,
  override val message: String,
): FailEnum {
  INVALID_TOKEN(401, "AUTH_1001", "Something went wrong.[AUTH_1001]"),
  EXPIRED_TOKEN(401, "AUTH_1002", "Something went wrong.[AUTH_1002]"),
  INFO_NOT_MATCH(403, "AUTH_1003", "Something went wrong.[AUTH_1003]"),
  FORBIDDEN_ACCESS(403, "AUTH_1004", "Something went wrong.[AUTH_1004]"),
  UNKNOWN_ERROR(500, "AUTH_1100", "Something went wrong.[AUTH_1100]"),
}
