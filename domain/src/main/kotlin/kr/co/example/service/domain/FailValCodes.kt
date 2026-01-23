package kr.co.example.service.domain

import kr.co.example.service.exception.CommonRuntimeException

enum class FailValCodes(
  override val status: Int,
  override val code: String,
  override val message: String,
): FailEnum {
  MISSING_PARAMETER(400, "VAL_2001", "Something went wrong.[VAL_2001]"),
  INVALID_FORMAT(400, "VAL_2002", "Something went wrong.[VAL_2002]"),
  METHOD_NOT_ALLOWED(405, "VAL_2003", "Something went wrong.[VAL_2003]"),
  INVALID_DATE(400, "VAL_2004", "Something went wrong.[VAL_2004]"),
  CONFLICT(400, "VAL_2005", "Something went wrong.[VAL_2005]"),
  CONFLICT_409(409, "VAL_2007", "Something went wrong.[VAL_2007]"),
  PROFANITY_DETECTED(400, "VAL_2006", "Something went wrong.[VAL_2006]"),
  UNKNOWN_ERROR(500, "VAL_2100", "Something went wrong.[VAL_2100]");

  companion object {
    fun validFormat(condition: Boolean, message: String) {
      if (!condition) throw CommonRuntimeException(FailValCodes.INVALID_FORMAT, message)
    }
  }

}

