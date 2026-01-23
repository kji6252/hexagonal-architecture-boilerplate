package kr.co.example.service.domain

enum class FailRsrcCodes(
  override val status: Int,
  override val code: String,
  override val message: String,
) : FailEnum {
  NOT_FOUND(404, "RSRC_4001", "Something went wrong.[RSRC_4001]"),
  FORBIDDEN_DELETE(403, "RSRC_4002", "Something went wrong.[RSRC_4002]"),
  UNSUPPORTED_DEVICE(403, "RSRC_4003", "Something went wrong.[RSRC_4003]"),
  NOT_WITHIN_PERIOD(403, "RSRC_4004", "Something went wrong.[RSRC_4004]"),
  UNKNOWN_ERROR(500, "RSRC_4100", "Something went wrong.[RSRC_4100]"),
}
