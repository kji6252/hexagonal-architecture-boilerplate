package kr.co.example.service.adapter.cache

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [SpringBootApplication::class])
@ActiveProfiles("test")
@Tag("unit")
@DisplayName("CacheConfig 테스트")
class CacheConfigTest {

  @Autowired
  private lateinit var cacheManager: CacheManager

  @Test
  @DisplayName("CaffeineCacheManager가 주입된다")
  fun `CaffeineCacheManager가 주입된다`() {
    assertNotNull(cacheManager)
    assertTrue(cacheManager is CaffeineCacheManager)
  }

  @Test
  @DisplayName("설정된 캐시 이름들을 반환한다")
  fun `설정된 캐시 이름들을 반환한다`() {
    val cacheNames = cacheManager.cacheNames
    assertNotNull(cacheNames)
    assertTrue(cacheNames.isNotEmpty())
  }

  @Test
  @DisplayName("캐시에 값을 저장하고 조회한다")
  fun `캐시에 값을 저장하고 조회한다`() {
    val cacheName = "samples"
    val key = "test-cache-key"
    val value = "test-cache-value"

    val cache = cacheManager.getCache(cacheName)
    assertNotNull(cache)

    cache?.put(key, value)
    val retrieved = cache?.get(key, String::class.java)
    assertEquals(value, retrieved)
  }

  @Test
  @DisplayName("캐시에서 값을 삭제한다")
  fun `캐시에서 값을 삭제한다`() {
    val cacheName = "samples"
    val key = "test-cache-key-evict"
    val value = "test-cache-value"

    val cache = cacheManager.getCache(cacheName)
    cache?.put(key, value)

    val retrievedBefore = cache?.get(key, String::class.java)
    assertEquals(value, retrievedBefore)

    cache?.evict(key)
    val retrievedAfter = cache?.get(key, String::class.java)
    assertEquals(null, retrievedAfter)
  }

  @Test
  @DisplayName("캐시를 비운다")
  fun `캐시를 비운다`() {
    val cacheName = "samples"
    val key = "test-cache-key-clear"
    val value = "test-cache-value"

    val cache = cacheManager.getCache(cacheName)
    cache?.put(key, value)

    val retrievedBefore = cache?.get(key, String::class.java)
    assertNotNull(retrievedBefore)

    cache?.clear()
    val retrievedAfter = cache?.get(key, String::class.java)
    assertEquals(null, retrievedAfter)
  }
}
