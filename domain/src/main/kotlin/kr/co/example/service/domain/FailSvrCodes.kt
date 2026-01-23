package kr.co.example.service.domain

enum class FailSvrCodes(
  override val status: Int,
  override val code: String,
  override val message: String,
) : FailEnum {
  INTERNAL_SERVER_ERROR(500, "SVR_5001", "Something went wrong.[SVR_5001]"),
  TIMEOUT_ERROR(408, "SVR_5002", "Something went wrong.[SVR_5002]"),
  UNKNOWN_ERROR(500, "SVR_5100", "Something went wrong.[SVR_5100]"),
}
