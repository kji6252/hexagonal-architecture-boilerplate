package kr.co.example.service.adapter.web.input

import kr.co.uplus.core.logger
import kr.co.example.service.domain.CommonResponse
import kr.co.example.service.domain.SuccessCodes
import kr.co.example.service.port.input.HelloCommand
import kr.co.example.service.port.input.SampleDto
import kr.co.example.service.port.input.SampleUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 샘플 컨트롤러 - Hello World 예제
 *
 * 헥사고날 아키텍처의 Web Adapter 역할을 합니다.
 * HTTP 요청을 받아 Port-In 유즈케이스를 호출합니다.
 */
@RestController
@RequestMapping("/api/v1/sample")
class SampleController(
  private val sampleUseCase: SampleUseCase
) {

  private val log = logger()

  /**
   * Hello World 엔드포인트
   *
   * GET /api/v1/sample/hello?name=World
   *
   * @param name 이름 (기본값: "World")
   * @return 인사말 메시지
   */
  @GetMapping("/hello")
  fun hello(@RequestParam(defaultValue = "World") name: String): ResponseEntity<CommonResponse<String>> {
    log.info { "hello called with name: $name" }

    val result = sampleUseCase.sayHello(HelloCommand(name))

    return ResponseEntity.ok(
      CommonResponse(
        code = SuccessCodes.OK.code,
        message = SuccessCodes.OK.message,
        data = result
      )
    )
  }

  /**
   * 샘플 데이터 조회 엔드포인트
   *
   * GET /api/v1/sample/{id}
   *
   * @param id 샘플 ID
   * @return 샘플 데이터
   */
  @GetMapping("/{id}")
  fun getSample(@PathVariable id: Long): ResponseEntity<CommonResponse<SampleDto>> {
    log.info { "getSample called with id: $id" }

    val result = sampleUseCase.getSample(id)

    return ResponseEntity.ok(
      CommonResponse(
        code = SuccessCodes.OK.code,
        message = SuccessCodes.OK.message,
        data = result
      )
    )
  }
}
