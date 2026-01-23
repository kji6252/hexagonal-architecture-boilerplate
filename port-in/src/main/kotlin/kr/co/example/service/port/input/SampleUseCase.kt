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
 * 샘플 데이터 DTO
 */
data class SampleDto(
    val id: Long,
    val message: String,
    val createdAt: java.time.LocalDateTime
)
