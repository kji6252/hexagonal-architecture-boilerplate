package kr.co.example.service.application.service

import kr.co.uplus.core.logger
import kr.co.example.service.domain.Sample
import kr.co.example.service.port.output.SampleRepositoryPort
import kr.co.example.service.port.input.SampleDto
import kr.co.example.service.port.input.SampleUseCase
import kr.co.example.service.port.input.HelloCommand
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * 샘플 서비스 - Hello World 예제 구현
 *
 * 이 클래스는 헥사고날 아키텍처의 Application Layer 역할을 합니다.
 * Port-In 인터페이스를 구현하며, Port-Out을 통해 외부와 상호작용합니다.
 */
@Component
class SampleService(
  private val sampleRepositoryPort: SampleRepositoryPort
) : SampleUseCase {

  private val log = logger()

  override fun sayHello(command: HelloCommand): String {
    log.info { "sayHello called with name: ${command.name}" }
    return "Hello, ${command.name}!"
  }

  override fun getSample(id: Long): SampleDto {
    log.info { "getSample called with id: $id" }

    val sample = sampleRepositoryPort.findById(id)
      ?: throw NoSuchElementException("Sample not found with id: $id")

    return SampleDto(
      id = sample.id,
      message = sample.message,
      createdAt = sample.createdAt
    )
  }
}
