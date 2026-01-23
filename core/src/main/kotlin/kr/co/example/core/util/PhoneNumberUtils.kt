package kr.co.uplus.core.util

import kr.co.uplus.core.logger

object PhoneNumberUtils {

  /**
   * 전화번호를 포맷팅합니다.
   * - 12자리 전화번호의 경우, 4번째 자리의 숫자를 제거합니다.
   */
  fun formatPhoneNumber(phoneNumber: String): String {
    var cleanNumber = phoneNumber.replace("-", "")
    if (cleanNumber.length == 12) {
      cleanNumber = cleanNumber.substring(0, 3) + cleanNumber.substring(4)
    }
    return cleanNumber
  }

  fun ctnAddZeroNumber(ctn: String): String {
    return if (ctn.length >= 3 && ctn.all { it.isDigit() }) {
      ctn.substring(0, 3) + "0" + ctn.substring(3)
    } else {
      logger().warn { "CTN is not valid for adding zero number. It should be at least 3 digits and all digits."}
      ctn
    }
  }

  fun decodeAndAddHyphenPhoneNumber(phoneNumber: String): String {
    if (phoneNumber.isBlank()) {
      throw IllegalArgumentException("Input must be a string")
    }

    if (!phoneNumber.startsWith("010")) {
      throw IllegalArgumentException("Phone number must start with 010")
    }

    val normalized = if (phoneNumber.length == 12) {
      "010" + phoneNumber.substring(4)
    } else {
      phoneNumber
    }

    if (normalized.length != 11) {
      throw IllegalArgumentException("Phone number must be 11 digits after normalization")
    }

    return normalized.replace(Regex("(\\d{3})(\\d{4})(\\d{4})"), "$1-$2-$3")
  }

  fun validateAndFormatPhoneNumber(phoneNumber: String): String {
    if (phoneNumber.isBlank()) {
      throw IllegalArgumentException("Input must be a string")
    }

    if (!phoneNumber.startsWith("010")) {
      throw IllegalArgumentException("Phone number must start with 010")
    }

    if (phoneNumber.length != 11) {
      throw IllegalArgumentException("Phone number must be exactly 11 digits")
    }

    return phoneNumber
  }
}