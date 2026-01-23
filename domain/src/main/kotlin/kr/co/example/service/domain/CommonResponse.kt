package kr.co.example.service.domain

data class CommonResponse<T>(
  val code: String = "SUCCESS_0001",
  val message: String = "요청이 성공적으로 처리되었습니다.",
  val data: T? = null,
) {
  constructor(commonEnum: CommonEnum, data: T?) : this(code = commonEnum.code, message = commonEnum.message, data = data)
}

sealed interface SuccessEnum : CommonEnum
sealed interface FailEnum : CommonEnum

sealed interface CommonEnum {
  val status: Int
  val code: String
  val message: String
}
