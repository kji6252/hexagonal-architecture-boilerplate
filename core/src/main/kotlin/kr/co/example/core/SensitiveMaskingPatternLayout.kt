package kr.co.uplus.core

import com.fasterxml.jackson.core.JsonStreamContext
import net.logstash.logback.mask.ValueMasker
import java.util.regex.Pattern
import java.util.stream.IntStream

class SensitiveMaskingPatternLayout : ValueMasker {
  val multilinePattern: Pattern?

  init {
    val maskPatterns = listOf(
      "phoneNumber=(\\S[^\\,]*)",
      "mobile=(\\S[^\\,]*)",
      "name=(\\S[^\\,]*)",
      "picture=(\\S[^\\,]*)",
      "nickname=(\\S[^\\,]*)",
      "email=(\\S*)",
      "birthDate=(\\S*)",
      "entrId=(\\S*)",
      "encnTlno=(\\S*)",
      "realUserName=(\\S*)",
      "realUserBirthdate=(\\S*)",
      "ctn=(\\S[^\\,]*)",
      "accessToken=(\\S[^\\,]*)",
      "refreshToken=(\\S[^\\,]*)",
      "idToken=(\\S[^\\,]*)",
      "userAccessToken=(\\S[^\\,]*)",
      "authCode=(\\S[^\\,]*)",
      "state=(\\S[^\\,]*)",
      "codeVerifier=(\\S[^\\,]*)",
      "carrierToken=(\\S[^\\,]*)",
      "authorization=(\\S[^\\,]*)",
      "x-ibm-client-secret=(\\S[^\\,]*)",
      "client-secret=(\\S[^\\,]*)",
      "client_secret=([^&\\s]*)",
      "encnTlno=(\\S[^\\,]*)",
      "entrId=(\\S[^\\,]*)",
      "\\\"name\\\"\\s*:\\s*\\\"(.*?)\\\"",
      "\\\"picture\\\"\\s*:\\s*\\\"(.*?)\\\"",
      "\\\"nickname\\\"\\s*:\\s*\\\"(.*?)\\\"",
      "\\\"phone_number\\\"\\s*:\\s*\\\"(.*?)\\\"",
      "\\\"email\\\"\\s*:\\s*\\\"(.*?)\\\"",
      "\\\"birthdate\\\"\\s*:\\s*\\\"(.*?)\\\"",
      "\\\\\"realUserName\\\\\":\\\\\"(.*?)\\\\\"",
      "\\\\\"realUserBirthdate\\\\\":\\\\\"(.*?)\\\\\"",
      "\\\"ctn\\\"\\s*:\\s*\\\"(.*?)\\\"",
      "\\\"ctn\\\"\\s*:\\s*\\[\\s*\\\"(.*?)\\\"\\s*\\]",
      "\\\"accessToken\\\"\\s*:\\s*\\\"(.*?)\\\"",
      "\\\"access_token\\\"\\s*:\\s*\\\"(.*?)\\\"",
      "\\\"refreshToken\\\"\\s*:\\s*\\\"(.*?)\\\"",
      "\\\"idToken\\\"\\s*:\\s*\\\"(.*?)\\\"",
      "\\\"refresh_token\\\"\\s*:\\s*\\\"(.*?)\\\"",
      "\\\"id_token\\\"\\s*:\\s*\\\"(.*?)\\\"",
      "\\\"userAccessToken\\\"\\s*:\\s*\\\"(.*?)\\\"",
      "\\\"authCode\\\"\\s*:\\s*\\\"(.*?)\\\"",
      "\\\"state\\\"\\s*:\\s*\\\"(.*?)\\\"",
      "\\\"codeVerifier\\\"\\s*:\\s*\\\"(.*?)\\\"",
      "\\\"carrierToken\\\"\\s*:\\s*\\\"(.*?)\\\"",
      "\\\"authorization\\\"\\s*:\\s*\\\"(.*?)\\\"",
      "\\\"x-ibm-client-secret\\\"\\s*:\\s*\\\"(.*?)\\\"",
      "\\\"client-secret\\\"\\s*:\\s*\\\"(.*?)\\\"",
      "\\\"deviceToken\\\"\\s*:\\s*\\\"(.*?)\\\"",
      "\\\"presignedUrl\\\"\\s*:\\s*\\\"(.*?)\\\"",
    )
    multilinePattern = Pattern.compile(java.lang.String.join("|", maskPatterns), Pattern.MULTILINE)
  }

  fun maskMessage(message: String): String {
    if (multilinePattern == null) {
      return message
    }
    val sb = StringBuilder(message)
    val matcher = multilinePattern.matcher(sb)
    while (matcher.find()) {
      IntStream.rangeClosed(1, matcher.groupCount()).forEach { group: Int ->
        if (matcher.group(group) != null) {
          IntStream.range(matcher.start(group), matcher.end(group)).forEach { i: Int -> sb.setCharAt(i, '*') }
        }
      }
    }
    return sb.toString()
  }

  override fun mask(context: JsonStreamContext?, value: Any): Any {
    return if (value is CharSequence) {
      maskMessage(value as String)
    } else value
  }
}
