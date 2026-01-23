package kr.co.example.service.domain

enum class FailBackupCodes(
  override val status: Int,
  override val code: String,
  override val message: String,
) : FailEnum {
  NOT_FOUND(400, "BKP_2001", "Something went wrong.[BKP_2001]");
}
