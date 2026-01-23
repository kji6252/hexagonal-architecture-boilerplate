package kr.co.example.service.adapter.cache

import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(CacheConfig::class)
annotation class EnableCacheAdapter
