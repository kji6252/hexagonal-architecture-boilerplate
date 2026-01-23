package kr.co.example.service.domain

enum class FailUserCodes(
  override val status: Int,
  override val code: String,
  override val message: String,
) : FailEnum {
  USER_NOT_FOUND(404, "USER_3001", "Something went wrong.[USER_3001]"),
  UPDATE_FAILED(500, "USER_3002", "Something went wrong.[USER_3002]"),
  FORBIDDEN_SERVICE(403, "USER_3003", "Something went wrong.[USER_3003]"),
  FORBIDDEN_WITHDRAWAL(403, "USER_3004", "Something went wrong.[USER_3004]"),
  NOT_UPLUS(403, "USER_3005", "Something went wrong.[USER_3005]"),
  NOT_BUSINESS(403, "USER_3006", "Something went wrong.[USER_3006]"),
  UNDER_14(403, "USER_3007", "Something went wrong.[USER_3007]"),
  NOT_EMAIL(403, "USER_3008", "Something went wrong.[USER_3008]"),
  NOT_SUPPORTED_TYPE(403, "USER_3009", "Something went wrong.[USER_3009]"),
  ALREADY_ENTER(403, "USER_3010", "Something went wrong.[USER_3010]"),
  NOT_AGREE_EVENT(403, "USER_3012", "Something went wrong.[USER_3012]"),
  INVALID_BIRTHDATE(403, "USER_3013", "Something went wrong.[USER_3013]"),
  UNKNOWN_ERROR(500, "USER_3100", "Something went wrong.[USER_3100]"),
}
