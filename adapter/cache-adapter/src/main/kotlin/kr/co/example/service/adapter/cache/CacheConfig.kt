package kr.co.example.service.adapter.cache

import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
@EnableCaching
class CacheConfig {

  @Primary
  @Bean
  fun caffeineCacheManager(): CaffeineCacheManager {
    return CaffeineCacheManager()
  }
}
