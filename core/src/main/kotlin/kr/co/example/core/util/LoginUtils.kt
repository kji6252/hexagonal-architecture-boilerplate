package kr.co.uplus.core.util

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object LoginUtils {
  fun generateLoginHint(
    loginType: String,
    ctn: String?,
    secret: String,
    add: String
  ): String {
    val rawLoginHint = mutableMapOf(
      "loginType" to loginType,
      "encryptedAt" to System.currentTimeMillis()
    )

    if (!ctn.isNullOrBlank()) {
      rawLoginHint["mobile"] = ctn
    }

    return EncryptHelper.encryptWithAddMap(rawLoginHint, secret, add)
  }

  fun encodeParams(params: Map<String, String>): String {
    return params.entries.joinToString("&") { (key, value) ->
      "${URLEncoder.encode(key, StandardCharsets.UTF_8)}=${URLEncoder.encode(value, StandardCharsets.UTF_8)}"
    }
  }

  fun buildLoginUrl(
    baseUrl: String,
    authUri: String,
    params: String
  ): String {
    return "$baseUrl$authUri?$params"
  }
}