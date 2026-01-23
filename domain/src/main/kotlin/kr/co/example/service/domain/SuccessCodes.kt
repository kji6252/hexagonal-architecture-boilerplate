package kr.co.example.service.domain

enum class SuccessCodes(
  override val status: Int,
  override val code: String,
  override val message: String,
): SuccessEnum {
  AOS_AUTO_LOGIN_OK(200, "SUCCESS", "요청이 성공적으로 처리되었습니다."),
  OK(200, "SUCCESS_0001", "요청이 성공적으로 처리되었습니다."),
  CREATED(201, "SUCCESS_0002", "리소스가 성공적으로 생성되었습니다."),
  ACCEPTED(202, "SUCCESS_0003", "요청이 접수되었으며 처리 중입니다."),
  NO_CONTENT(200, "SUCCESS_0004", "요청이 성공적으로 처리되었지만 반환할 내용이 없습니다."),
}
