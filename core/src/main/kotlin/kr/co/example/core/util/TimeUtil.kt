package kr.co.uplus.core.util

import java.time.*
import java.time.format.DateTimeFormatter

object TimeUtil {

  fun convertToYYYYMMDD(yyyyMMddHHmmSS: String): String{
    val formatterInput = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
    val formatterOutput = DateTimeFormatter.ofPattern("yyyyMMdd")

    val dateTime = LocalDateTime.parse(yyyyMMddHHmmSS, formatterInput)
    return dateTime.format(formatterOutput)
  }

  fun convertToYYYYMMDD(epochMill: Long): String{
    val instant = Instant.ofEpochMilli(epochMill)
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    val zonedDateTime = instant.atZone(ZoneId.of("Asia/Seoul"))
    return zonedDateTime.format(formatter)
  }

  fun convertToYYYYmmDDHHmmSS(instant: Instant): String {
    val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
    val offsetDateTime = OffsetDateTime.ofInstant(instant, ZoneId.of("+0000"))
    return offsetDateTime.format(formatter)
  }

  fun convertToYYYYmmDD_hhmmss(localDateTime: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
    return localDateTime.format(formatter)
  }

  fun convertVersionFormat(localDate: LocalDate): String {
    return localDate.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일"))
  }

  fun reverseVersionFormat(string: String): LocalDate {
    return LocalDate.parse(string, DateTimeFormatter.ofPattern("yyyy년 M월 d일"))
  }

  fun convertDateFormat(localDate: LocalDate): String = localDate.atStartOfDay(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"))

}
