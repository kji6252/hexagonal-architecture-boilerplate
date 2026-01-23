package kr.co.example.service.adapter.memory

import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(MemoryConfig::class)
annotation class EnableMemoryAdapter
