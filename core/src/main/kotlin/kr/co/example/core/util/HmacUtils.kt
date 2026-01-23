package kr.co.uplus.core.util

import java.time.Instant
import java.time.format.DateTimeParseException
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object HmacUtils {

    fun checkHmacSha512(headers: Map<String, String>, keyHmacSha512: String): HmacResult {
        return try {
            val keyXAuthTime    = "x-authorizationtime"
            val keyXAppName     = "x-app-name"
            val keyXGlbTranId   = "x-global-transaction-id"
            val keyXHeadAuth    = "x-header-authorization"

            val xAuthTime  = headers[keyXAuthTime].orEmpty()
            val xAppName   = headers[keyXAppName].orEmpty()
            val xGlbTranId = headers[keyXGlbTranId].orEmpty()
            val xHeadAuth  = headers[keyXHeadAuth].orEmpty()

            // 원본 데이터 조합 및 서명 비교
            val inData     = "$xAuthTime@$xAppName@$xGlbTranId"
            val chkHeadAuth = signHmacSHA512(inData, keyHmacSha512)
            if (chkHeadAuth != xHeadAuth) {
                return HmacResult.Err.InvalidHash
            }

            // 타임스탬프 유효성 검사
            val isoTime = convertToIso8601(xAuthTime)
            if (!isValidTime(isoTime)) {
                return HmacResult.Err.TimeoutError
            }

            HmacResult.OK
        } catch (e: Exception) {
            HmacResult.Err.InternalServerError
        }
    }

    private fun isValidTime(inboundTime: String, baseTimeMillis: Long = 600_000L): Boolean {
        return try {
            val inboundInstant = Instant.parse(inboundTime)
            val nowMillis = Instant.now().toEpochMilli()
            val inboundMillis = inboundInstant.toEpochMilli()
            nowMillis <= inboundMillis + baseTimeMillis
        } catch (e: DateTimeParseException) {
            false
        }
    }

    private fun convertToIso8601(dateString: String): String {
        val regex = Regex("""^(\d{4})(\d{2})(\d{2})T(\d{2})(\d{2})(\d{2})([+-]\d{2})(\d{2})$""")
        val match = regex.matchEntire(dateString) ?: return "Invalid Date"
        val (year, month, day, hour, minute, second, tzHour, tzMinute) = match.destructured
        return "$year-$month-$day" +
                "T$hour:$minute:$second" +
                "$tzHour:$tzMinute"
    }

    private fun signHmacSHA512(inData: String, hexSha512Key: String): String {
        val keyBytes = hexSha512Key.toByteArray(Charsets.UTF_8)
        val keySpec = SecretKeySpec(keyBytes, "HmacSHA512")
        val mac = Mac.getInstance("HmacSHA512").apply {
            init(keySpec)
        }
        val hmacBytes = mac.doFinal(inData.toByteArray(Charsets.UTF_8))
        return Base64.getEncoder().encodeToString(hmacBytes)
    }
}

sealed class HmacResult {
    data object OK : HmacResult()

    sealed class Err : HmacResult() {
        data object InvalidHash : Err()
        data object TimeoutError : Err()
        data object InternalServerError : Err()
    }
}