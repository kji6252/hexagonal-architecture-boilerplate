package kr.co.example.service.adapter.persistence.sample

import kr.co.example.service.domain.Sample
import kr.co.example.service.port.output.SampleRepositoryPort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class SampleRepositoryAdapter(
  private val sampleRepository: SampleRepository
) : SampleRepositoryPort {

  override fun findById(id: Long): Sample? {
    return sampleRepository.findById(id).map { it.toDomain() }.orElse(null)
  }

  override fun save(sample: Sample): Sample {
    val entity = if (sample.id == 0L) {
      // New entity
      SampleEntity(
        id = null,
        message = sample.message,
        createdAt = sample.createdAt,
        updatedAt = sample.updatedAt
      )
    } else {
      // Existing entity
      SampleEntity(
        id = sample.id,
        message = sample.message,
        createdAt = sample.createdAt,
        updatedAt = sample.updatedAt
      )
    }

    val savedEntity = sampleRepository.save(entity)
    return savedEntity.toDomain()
  }

  override fun findAll(): List<Sample> {
    return sampleRepository.findAll().map { it.toDomain() }
  }

  override fun deleteById(id: Long) {
    sampleRepository.deleteById(id)
  }
}

private fun SampleEntity.toDomain(): Sample {
  return Sample(
    id = this.id ?: 0,
    message = this.message,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
  )
}
