package kr.co.example.service.port.output

import kr.co.example.service.domain.Sample

/**
 * 샘플 리파지토리 포트
 *
 * 이 인터페이스는 헥사고날 아키텍처의 Port-Out 역할을 합니다.
 * Application Layer에서 호출되며, Persistence Adapter에서 구현됩니다.
 */
interface SampleRepositoryPort {
    /**
     * ID로 샘플 데이터를 조회합니다.
     *
     * @param id 샘플 ID
     * @return 샘플 엔티티, 없으면 null
     */
    fun findById(id: Long): Sample?

    /**
     * 샘플 데이터를 저장합니다.
     *
     * @param sample 샘플 엔티티
     * @return 저장된 샘플 엔티티
     */
    fun save(sample: Sample): Sample

    /**
     * 모든 샘플 데이터를 조회합니다.
     *
     * @return 샘플 엔티티 목록
     */
    fun findAll(): List<Sample>

    /**
     * ID로 샘플 데이터를 삭제합니다.
     *
     * @param id 샘플 ID
     */
    fun deleteById(id: Long)
}
