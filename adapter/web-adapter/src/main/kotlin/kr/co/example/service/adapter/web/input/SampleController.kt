package kr.co.example.service.adapter.web.input

import kr.co.uplus.core.logger
import kr.co.example.service.domain.CommonResponse
import kr.co.example.service.domain.SuccessCodes
import kr.co.example.service.port.input.HelloCommand
import kr.co.example.service.port.input.SampleDto
import kr.co.example.service.port.input.SampleUseCase
import kr.co.example.service.port.input.CreateSampleCommand
import kr.co.example.service.port.input.UpdateSampleCommand
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 샘플 컨트롤러 - Hello World 예제
 *
 * 헥사고날 아키텍처의 Web Adapter 역할을 합니다.
 * HTTP 요청을 받아 Port-In 유즈케이스를 호출합니다.
 *
 * Spring Cache Abstraction을 활용한 캐싱 예제 엔드포인트를 포함합니다.
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
   * 샘플 데이터 조회 엔드포인트 (캐시 미적용)
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

  // ========== 캐시 예제 엔드포인트 ==========

  /**
   * 캐시 적용 샘플 데이터 조회 엔드포인트
   *
   * GET /api/v1/sample/cache/{id}
   *
   * 첫 번째 요청 시 DB에서 조회하고 캐시에 저장합니다.
   * 이후 동일한 ID로 요청 시 캐시된 결과를 반환합니다.
   *
   * @param id 샘플 ID
   * @return 샘플 데이터
   */
  @GetMapping("/cache/{id}")
  fun getSampleWithCache(@PathVariable id: Long): ResponseEntity<CommonResponse<SampleDto>> {
    log.info { "getSampleWithCache called with id: $id" }

    val result = sampleUseCase.getSampleWithCache(id)

    return ResponseEntity.ok(
      CommonResponse(
        code = SuccessCodes.OK.code,
        message = "캐시 적용 조회 성공",
        data = result
      )
    )
  }

  /**
   * 샘플 데이터 생성 엔드포인트
   *
   * POST /api/v1/sample
   *
   * @param command 생성 커맨드
   * @return 생성된 샘플 데이터
   */
  @PostMapping
  fun createSample(@RequestBody command: CreateSampleCommand): ResponseEntity<CommonResponse<SampleDto>> {
    log.info { "createSample called with message: ${command.message}" }

    val result = sampleUseCase.createSample(command)

    return ResponseEntity.status(HttpStatus.CREATED).body(
      CommonResponse(
        code = SuccessCodes.CREATED.code,
        message = "샘플 데이터 생성 성공 (캐시 적용)",
        data = result
      )
    )
  }

  /**
   * 샘플 데이터 업데이트 엔드포인트
   *
   * PUT /api/v1/sample/{id}
   *
   * 업데이트 후 해당 ID의 캐시가 갱신됩니다.
   *
   * @param id 샘플 ID
   * @param command 업데이트 커맨드
   * @return 업데이트된 샘플 데이터
   */
  @PutMapping("/{id}")
  fun updateSample(
    @PathVariable id: Long,
    @RequestBody command: UpdateSampleCommand
  ): ResponseEntity<CommonResponse<SampleDto>> {
    log.info { "updateSample called with id: $id" }

    val result = sampleUseCase.updateSample(id, command)

    return ResponseEntity.ok(
      CommonResponse(
        code = SuccessCodes.OK.code,
        message = "샘플 데이터 업데이트 성공 (캐시 갱신)",
        data = result
      )
    )
  }

  /**
   * 특정 ID의 캐시 삭제 엔드포인트
   *
   * DELETE /api/v1/sample/cache/{id}
   *
   * 해당 ID의 캐시만 삭제합니다.
   *
   * @param id 샘플 ID
   * @return 성공 메시지
   */
  @DeleteMapping("/cache/{id}")
  fun evictSampleCache(@PathVariable id: Long): ResponseEntity<CommonResponse<String>> {
    log.info { "evictSampleCache called with id: $id" }

    sampleUseCase.evictSampleCache(id)

    return ResponseEntity.ok(
      CommonResponse(
        code = SuccessCodes.OK.code,
        message = "캐시 삭제 성공 (ID: $id)",
        data = "evicted"
      )
    )
  }

  /**
   * 전체 캐시 삭제 엔드포인트
   *
   * DELETE /api/v1/sample/cache/all
   *
   * 모든 샘플 캐시를 삭제합니다.
   *
   * @return 성공 메시지
   */
  @DeleteMapping("/cache/all")
  fun evictAllSampleCache(): ResponseEntity<CommonResponse<String>> {
    log.info { "evictAllSampleCache called" }

    sampleUseCase.evictAllSampleCache()

    return ResponseEntity.ok(
      CommonResponse(
        code = SuccessCodes.OK.code,
        message = "전체 캐시 삭제 성공",
        data = "all evicted"
      )
    )
  }
}
