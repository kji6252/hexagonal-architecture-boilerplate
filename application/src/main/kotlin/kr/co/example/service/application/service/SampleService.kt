package kr.co.example.service.application.service

import kr.co.uplus.core.logger
import kr.co.example.service.domain.Sample
import kr.co.example.service.port.output.SampleRepositoryPort
import kr.co.example.service.port.input.SampleDto
import kr.co.example.service.port.input.SampleUseCase
import kr.co.example.service.port.input.HelloCommand
import kr.co.example.service.port.input.CreateSampleCommand
import kr.co.example.service.port.input.UpdateSampleCommand
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * 샘플 서비스 - Hello World 예제 구현
 *
 * 이 클래스는 헥사고날 아키텍처의 Application Layer 역할을 합니다.
 * Port-In 인터페이스를 구현하며, Port-Out을 통해 외부와 상호작용합니다.
 *
 * Spring Cache Abstraction(@Cacheable, @CachePut, @CacheEvict)을 활용한 캐싱 예제입니다.
 */
@Component
class SampleService(
  private val sampleRepositoryPort: SampleRepositoryPort
) : SampleUseCase {

  private val log = logger()

  companion object {
    /**
     * 샘플 캐시 이름
     */
    const val SAMPLE_CACHE = "samples"
  }

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

  /**
   * 캐시를 적용하여 샘플 데이터를 조회합니다.
   *
   * @Cacheable: 메서드 결과를 캐시합니다. 동일한 파라미터로 호출 시 캐시된 결과를 반환합니다.
   * - key: 캐시 키 (여기서는 id)
   * - unless: 결과가 null인 경우 캐시하지 않음
   */
  @Cacheable(
    cacheNames = [SAMPLE_CACHE],
    key = "#id",
    unless = "#result == null"
  )
  override fun getSampleWithCache(id: Long): SampleDto {
    log.info { "[CACHE MISS] getSampleWithCache called with id: $id - Loading from database" }

    val sample = sampleRepositoryPort.findById(id)
      ?: throw NoSuchElementException("Sample not found with id: $id")

    return SampleDto(
      id = sample.id,
      message = sample.message,
      createdAt = sample.createdAt
    )
  }

  /**
   * 샘플 데이터를 생성하고 캐시를 적용합니다.
   *
   * 생성 후 즉시 조회될 수 있도록 캐시를 미리 넣어둡니다.
   */
  @Caching(
    put = [
      CachePut(
        cacheNames = [SAMPLE_CACHE],
        key = "#result.id",
        unless = "#result == null"
      )
    ]
  )
  override fun createSample(command: CreateSampleCommand): SampleDto {
    log.info { "createSample called with message: ${command.message}" }

    // 새 ID 생성 (실제로는 DB에서 자동 생성되나, 예제를 위해 간단히 구현)
    val existingSamples = sampleRepositoryPort.findAll()
    val newId = if (existingSamples.isEmpty()) 1L else existingSamples.maxOf { it.id } + 1

    val sample = Sample.create(
      id = newId,
      message = command.message
    )

    val savedSample = sampleRepositoryPort.save(sample)

    log.info { "[CACHE PUT] Created sample with id: $newId" }

    return SampleDto(
      id = savedSample.id,
      message = savedSample.message,
      createdAt = savedSample.createdAt
    )
  }

  /**
   * 샘플 데이터를 업데이트하고 캐시를 갱신합니다.
   *
   * @CachePut: 메서드 실행 후 캐시를 갱신합니다.
   */
  @CachePut(
    cacheNames = [SAMPLE_CACHE],
    key = "#id",
    unless = "#result == null"
  )
  override fun updateSample(id: Long, command: UpdateSampleCommand): SampleDto {
    log.info { "[CACHE UPDATE] updateSample called with id: $id" }

    val sample = sampleRepositoryPort.findById(id)
      ?: throw NoSuchElementException("Sample not found with id: $id")

    val updatedSample = sample.updateMessage(command.message)
    val savedSample = sampleRepositoryPort.save(updatedSample)

    return SampleDto(
      id = savedSample.id,
      message = savedSample.message,
      createdAt = savedSample.createdAt
    )
  }

  /**
   * 특정 ID의 캐시를 삭제합니다.
   *
   * @CacheEvict: 캐시를 삭제합니다.
   * - allEntries: false로 설정하여 해당 키의 캐시만 삭제
   */
  @CacheEvict(
    cacheNames = [SAMPLE_CACHE],
    key = "#id"
  )
  override fun evictSampleCache(id: Long) {
    log.info { "[CACHE EVICT] Evicted cache for sample id: $id" }
  }

  /**
   * 모든 샘플 캐시를 삭제합니다.
   *
   * @CacheEvict: 전체 캐시를 삭제합니다.
   * - allEntries: true로 설정하여 모든 캐시 삭제
   * - beforeInvocation: false로 설정하여 메서드 실행 후 캐시 삭제
   */
  @CacheEvict(
    cacheNames = [SAMPLE_CACHE],
    allEntries = true
  )
  override fun evictAllSampleCache() {
    log.info { "[CACHE EVICT ALL] Evicted all sample cache" }
  }
}
