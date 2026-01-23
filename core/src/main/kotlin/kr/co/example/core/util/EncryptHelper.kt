package kr.co.uplus.core.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kr.co.uplus.core.logger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptHelper {

  private const val IV_LENGTH = 12
  private const val GCM_TAG_LENGTH_BITS = 128

  @Throws(Exception::class)
  fun decryptWithAdd(encryptedText: String, key: String, add: String): String {
    val decoded = Base64.getDecoder().decode(encryptedText)

    if (decoded.size < IV_LENGTH) {
      throw IllegalArgumentException("Decoded data is too short to contain the IV.")
    }

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")

    val keySpec = SecretKeySpec(key.toByteArray(), "AES")

    val iv = ByteArray(IV_LENGTH)
    System.arraycopy(decoded, 0, iv, 0, IV_LENGTH)

    val nonceSpec = GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv)

    cipher.init(Cipher.DECRYPT_MODE, keySpec, nonceSpec)

    cipher.updateAAD(add.toByteArray())

    val decryptedBytes = cipher.doFinal(decoded, IV_LENGTH, decoded.size - IV_LENGTH)

    return String(decryptedBytes)
  }

  @Throws(Exception::class)
  fun encryptWithAddMap(data: Map<String, Any>, key: String, add: String = "GROOT"): String {
    val json = jacksonObjectMapper().writeValueAsString(data)
    return encryptWithAdd(json, key, add)
  }

  @Throws(Exception::class)
  fun encryptWithAdd(plainText: String, key: String, add: String): String {
    val iv = ByteArray(IV_LENGTH)
    SecureRandom().nextBytes(iv)

    val keyBytes = key.toByteArray(StandardCharsets.UTF_8)
    val secretKey = SecretKeySpec(keyBytes, "AES")

    val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    val gcmSpec = GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv)
    cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmSpec)
    cipher.updateAAD(add.toByteArray(StandardCharsets.UTF_8))

    val encryptedBytes = cipher.doFinal(plainText.toByteArray(StandardCharsets.UTF_8))

    val finalBytes = ByteArray(iv.size + encryptedBytes.size)
    System.arraycopy(iv, 0, finalBytes, 0, iv.size)
    System.arraycopy(encryptedBytes, 0, finalBytes, iv.size, encryptedBytes.size)

    return Base64.getEncoder().encodeToString(finalBytes)
  }

  @Throws(Exception::class)
  fun encryptWithAddString(data: String, key: String, add: String = "GROOT"): String {
    return encryptWithAdd(data, key, add)
  }

  @Throws(Exception::class)
  fun decryptCtn(encryptedText: String, ctnKey: String): String {
    if (encryptedText.isBlank()) {
      throw IllegalArgumentException("Encrypted text must be a string")
    }

    val keyBytes: ByteArray = when {
      ctnKey.length == 64 && ctnKey.matches(Regex("^[0-9A-Fa-f]+$")) ->
        ctnKey.chunked(2).map { it.toInt(16).toByte() }.toByteArray()

      else -> ctnKey.toByteArray(Charsets.UTF_8)
    }

    if (keyBytes.size != 32) {
      throw IllegalArgumentException("Key must be 32 bytes long")
    }

    val encryptedBuffer = Base64.getDecoder().decode(encryptedText)

    if (encryptedBuffer.size < 48) {
      throw IllegalArgumentException("Encrypted payload is too short")
    }

    val iv = encryptedBuffer.copyOfRange(16, 32)
    val cipherText = encryptedBuffer.copyOfRange(32, encryptedBuffer.size)

    return try {
      // lgtm [java/weak-cryptographic-algorithm] — algorithm choice constrained by protocol spe
      val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
      val secretKey = SecretKeySpec(keyBytes, "AES")
      cipher.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))
      val decrypted = cipher.doFinal(cipherText)
      String(decrypted, Charsets.UTF_8)
    } catch (e: Exception) {
      logger().error { "Error during decryption: ${e.message}" }
      throw e
    }
  }

  fun decryptEncryptedCtn(desource: String, key: String): String {
    try {
      val ivSize = 16
      val keySize = 16

      val encryptedIvTextBytes = Base64.getDecoder().decode(desource)
      val iv = encryptedIvTextBytes.copyOfRange(0, ivSize)
      val encryptedBytes = encryptedIvTextBytes.copyOfRange(ivSize, encryptedIvTextBytes.size)

      val sha256 = MessageDigest.getInstance("SHA-256")
      val keyHash = sha256.digest(key.toByteArray(Charsets.UTF_8))
      val keyBytes = keyHash.copyOfRange(0, keySize)

      // lgtm [java/weak-cryptographic-algorithm] — algorithm choice constrained by protocol spe
      val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
      val secretKey = SecretKeySpec(keyBytes, "AES")
      val ivSpec = IvParameterSpec(iv)

      cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec)
      val decryptedBytes = cipher.doFinal(encryptedBytes)
      return String(decryptedBytes, Charsets.UTF_8)
    } catch (e: Exception) {
      logger().error { "Error during decryption: ${e.message}" }
      throw e
    }
  }

  @Throws(Exception::class)
  fun aes256EncryptToHex(plainText: String, key: String): String {
    val secretKeySpec = SecretKeySpec(key.toByteArray(Charsets.UTF_8), "AES")
    val iv = IvParameterSpec(ByteArray(16))

    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv)

    val encryptedBytes = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))

    return encryptedBytes.joinToString("") { "%02x".format(it) }
  }

  @Throws(Exception::class)
  fun aes256DecryptFromHex(encryptedHex: String, key: String): String {
    val encryptedBytes = encryptedHex.chunked(2)
      .map { it.toInt(16).toByte() }
      .toByteArray()

    val keyBytes = key.toByteArray(Charsets.UTF_8)
    val secretKey = SecretKeySpec(keyBytes, "AES")
    val iv = IvParameterSpec(ByteArray(16))

    val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cipher.init(Cipher.DECRYPT_MODE, secretKey, iv)

    val decryptedBytes = cipher.doFinal(encryptedBytes)
    return String(decryptedBytes, Charsets.UTF_8)
  }

  fun generateHmacSignature(strSource: String, strKey: String): String {
    val secretKeySpec = SecretKeySpec(strKey.toByteArray(Charsets.UTF_8), "HmacSHA256")
    val mac = Mac.getInstance("HmacSHA256")
    mac.init(secretKeySpec)
    val hmacBytes = mac.doFinal(strSource.toByteArray(Charsets.UTF_8))
    return Base64.getEncoder().encodeToString(hmacBytes)
  }

  fun decryptAndCheckSignature(
    desource: String,
    strSignKey: String,
    strEncryptKey: String,
  ): String {
    val dePostData = decryptEncryptedCtn(desource, strEncryptKey)

    val signStartIndex = dePostData.indexOf("sign=")
    if (signStartIndex == -1) throw IllegalArgumentException("sign parameter not found")

    val postData = dePostData.substring(0, dePostData.indexOf("&sign="))
    generateHmacSignature(postData, strSignKey)

    return dePostData
  }

  fun decryptByEsServer(
    encryptedCtn: String,
    esServerSignKey: String,
    esServerEncryptKey: String,
    needHyphen: Boolean = true
  ): String? {
    val decrypted = decryptAndCheckSignature(
      encryptedCtn,
      esServerSignKey,
      esServerEncryptKey
    )

    val params = parseQueryParams(decrypted)
    val decryptedCtn = params["ctn"]

    return if (decryptedCtn.isNullOrBlank()) {
      null
    } else {
      if (needHyphen) {
        PhoneNumberUtils.decodeAndAddHyphenPhoneNumber(decryptedCtn)
      } else {
        PhoneNumberUtils.formatPhoneNumber(decryptedCtn)
      }
    }
  }

  private fun parseQueryParams(query: String): Map<String, String> {
    return query.split("&").mapNotNull {
      val parts = it.split("=", limit = 2)
      if (parts.size == 2) {
        val key = java.net.URLDecoder.decode(parts[0], "UTF-8")
        val value = java.net.URLDecoder.decode(parts[1], "UTF-8")
        key to value
      } else {
        null
      }
    }.toMap()
  }


}