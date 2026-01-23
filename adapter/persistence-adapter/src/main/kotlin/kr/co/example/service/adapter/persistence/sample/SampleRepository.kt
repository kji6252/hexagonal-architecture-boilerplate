package kr.co.example.service.adapter.persistence.sample

import org.springframework.data.jpa.repository.JpaRepository

interface SampleRepository : JpaRepository<SampleEntity, Long>
