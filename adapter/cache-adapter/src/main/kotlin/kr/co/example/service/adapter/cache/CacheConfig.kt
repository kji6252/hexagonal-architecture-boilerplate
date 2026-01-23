package kr.co.example.service.adapter.cache

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.util.concurrent.TimeUnit

/**
 * 캐시 설정 클래스
 *
 * Spring Cache Abstraction을 위한 설정입니다.
 * Caffeine을 사용하여 인메모리 캐시를 구현합니다.
 */
@Configuration
@EnableCaching
class CacheConfig {

  companion object {
    /**
     * 샘플 캐시 이름
     */
    const val SAMPLE_CACHE = "samples"

    /**
     * 기본 캐시 TTL (초)
     */
    const val DEFAULT_CACHE_TTL_SECONDS = 60L

    /**
     * 캐시 최대 크기
     */
    const val DEFAULT_CACHE_MAX_SIZE = 1000L
  }

  /**
   * Caffeine 기반 캐시 매니저
   *
   * Caffeine은 고성능 Java 캐싱 라이브러리입니다.
   * - TTL: 캐시 항목의 수명 설정
   * - Maximum Size: 캐시 최대 크기 설정
   */
  @Primary
  @Bean
  fun caffeineCacheManager(): CacheManager {
    val cacheManager = CaffeineCacheManager()

    // 캐시 이름 등록
    cacheManager.setCacheNames(SAMPLE_CACHE)

    // Caffeine 설정: TTL과 최대 크기 지정
    cacheManager.setCaffeine(
      Caffeine.newBuilder()
        .expireAfterWrite(DEFAULT_CACHE_TTL_SECONDS, TimeUnit.SECONDS)
        .maximumSize(DEFAULT_CACHE_MAX_SIZE)
        .recordStats() // 캐시 통계 수집 활성화
    )

    return cacheManager
  }

  /**
   * 개별 캐시 설정이 필요한 경우 사용할 수 있는 커스텀 캐시 매니저
   *
   * 캐시별로 다른 TTL과 크기를 설정하고 싶을 때 사용합니다.
   */
  @Bean
  fun customCaffeineCacheManager(): CacheManager {
    val cacheManager = CaffeineCacheManager()

    cacheManager.setCacheNames(SAMPLE_CACHE)

    // 캐시별 설정을 위한 CaffeineSpec
    // 예: "samples" 캐시는 5분 TTL, 최대 500개
    cacheManager.setCaffeine(
      Caffeine.newBuilder()
        .expireAfterWrite(5, TimeUnit.MINUTES)
        .maximumSize(500)
        .recordStats()
    )

    return cacheManager
  }
}
