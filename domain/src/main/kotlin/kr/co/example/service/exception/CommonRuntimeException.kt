package kr.co.example.service.exception

import kr.co.example.service.domain.FailEnum

class CommonRuntimeException(
  val failEnum: FailEnum,
  override val message: String? = failEnum.message,
  override val cause: Throwable? = null,
) : RuntimeException()
