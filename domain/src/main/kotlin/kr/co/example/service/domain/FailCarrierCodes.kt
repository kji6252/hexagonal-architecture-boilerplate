package kr.co.example.service.domain

enum class FailCarrierCodes(
  override val status: Int,
  override val code: String,
  override val message: String,
) : FailEnum {
  TOKEN_EXPIRED(401, "CARRIER_10001", "Something went wrong.[CARRIER_10001]"),
  TOKEN_NOT_RECOGNIZED(401, "CARRIER_10002", "Something went wrong.[CARRIER_10002]"),
  SYSTEM_FAILURE(500, "CARRIER_10003", "Something went wrong.[CARRIER_10003]"),
  UNKNOWN_ERROR(500, "CARRIER_10004", "Something went wrong.[CARRIER_10004]"),
}
