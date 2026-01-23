package kr.co.example.service.port.input

/**
 * 샘플 유즈케이스 - Hello World 예제
 *
 * 이 인터페이스는 헥사고날 아키텍처의 Port-In 역할을 합니다.
 * Web Adapter에서 호출되며, Application Layer에서 구현됩니다.
 */
interface SampleUseCase {
    /**
     * Hello World 메시지를 생성합니다.
     *
     * @param command 이름 파라미터를 포함한 커맨드
     * @return 인사말 메시지
     */
    fun sayHello(command: HelloCommand): String

    /**
     * 샘플 데이터를 조회합니다.
     *
     * @param id 샘플 데이터 ID
     * @return 샘플 데이터 응답
     */
    fun getSample(id: Long): SampleDto

    /**
     * 캐시를 적용하여 샘플 데이터를 조회합니다.
     * 동일한 ID로 다시 조회 시 캐시된 결과를 반환합니다.
     *
     * @param id 샘플 데이터 ID
     * @return 샘플 데이터 응답
     */
    fun getSampleWithCache(id: Long): SampleDto

    /**
     * 샘플 데이터를 생성하고 캐시를 적용합니다.
     *
     * @param command 생성 커맨드
     * @return 생성된 샘플 데이터 응답
     */
    fun createSample(command: CreateSampleCommand): SampleDto

    /**
     * 샘플 데이터를 업데이트하고 캐시를 갱신합니다.
     *
     * @param id 샘플 데이터 ID
     * @param command 업데이트 커맨드
     * @return 업데이트된 샘플 데이터 응답
     */
    fun updateSample(id: Long, command: UpdateSampleCommand): SampleDto

    /**
     * 특정 ID의 캐시를 삭제합니다.
     *
     * @param id 샘플 데이터 ID
     */
    fun evictSampleCache(id: Long)

    /**
     * 모든 샘플 캐시를 삭제합니다.
     */
    fun evictAllSampleCache()
}

/**
 * Hello Command
 *
 * @param name 이름 (기본값: "World")
 */
data class HelloCommand(
    val name: String = "World"
)

/**
 * 샘플 생성 커맨드
 *
 * @param message 메시지
 */
data class CreateSampleCommand(
    val message: String
)

/**
 * 샘플 업데이트 커맨드
 *
 * @param message 새로운 메시지
 */
data class UpdateSampleCommand(
    val message: String
)

/**
 * 샘플 데이터 DTO
 */
data class SampleDto(
    val id: Long,
    val message: String,
    val createdAt: java.time.LocalDateTime
)
