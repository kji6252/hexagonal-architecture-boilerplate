package kr.co.example.service.domain

enum class FailUcubeCodes(
  override val status: Int,
  override val code: String,
  override val message: String,
) : FailEnum {
  UNKNOWN_ERROR(500, "UCUBE_8100", "Something went wrong.[UCUBE_8100]"),
  TOKEN_ISSUANCE_ERROR(500, "UCUBE_8101", "Something went wrong.[UCUBE_8101]"),
  USER_REGI_INFO_RETRIEVAL_ERROR(500, "UCUBE_8102", "Something went wrong.[UCUBE_8102]"),
  USER_DEVICE_INFO_RETRIEVAL_ERROR(500, "UCUBE_8103", "Something went wrong.[UCUBE_8103]"),
  USER_SERVICE_JOIN_UNAVAILABLE(500, "UCUBE_8104", "Something went wrong.[UCUBE_8104]"),
  USER_SERVICE_WITHDRAWAL_UNAVAILABLE(500, "UCUBE_8105", "Something went wrong.[UCUBE_8105]"),
  USER_MVOIP_ONOFF_FAILURE(500, "UCUBE_8106", "Something went wrong.[UCUBE_8106]"),
  USER_SERVICE_JOIN_FAILURE(500, "UCUBE_8107", "Something went wrong.[UCUBE_8107]"),
  USER_SERVICE_WITHDRAWAL_FAILURE(500, "UCUBE_8108", "Something went wrong.[UCUBE_8108]"),
  DATAPLAN_RETRIEVAL_ERROR(500, "UCUBE_8109", "Something went wrong.[UCUBE_8109]"),
  USER_STATUS_UNACCEPTABLE(403, "UCUBE_8110", "Something went wrong.[UCUBE_8110]"),
  USER_INVALID_MARKET_ID(500, "UCUBE_8111", "Something went wrong.[UCUBE_8111]"),
}
