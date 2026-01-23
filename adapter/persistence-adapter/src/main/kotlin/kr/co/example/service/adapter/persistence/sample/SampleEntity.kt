package kr.co.example.service.adapter.persistence.sample

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "sample", indexes = [Index(name = "idx_created_at", columnList = "created_at")])
@EntityListeners(AuditingEntityListener::class)
class SampleEntity(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @Column(nullable = false, length = 255)
  var message: String,

  @CreatedDate
  @Column(nullable = false, updatable = false)
  var createdAt: LocalDateTime = LocalDateTime.now(),

  @LastModifiedDate
  @Column(nullable = false)
  var updatedAt: LocalDateTime = LocalDateTime.now()
)
