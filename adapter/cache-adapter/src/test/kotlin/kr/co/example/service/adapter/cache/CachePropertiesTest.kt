package kr.co.example.service.adapter.cache

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [SpringBootApplication::class])
@ActiveProfiles("test")
@Tag("unit")
@DisplayName("CacheProperties 테스트")
class CachePropertiesTest {

  @Autowired(required = false)
  private var cacheProperties: CacheProperties? = null

  @Test
  @DisplayName("캐시 속성이 로드된다")
  fun `캐시 속성이 로드된다`() {
    cacheProperties?.let {
      assertNotNull(it)
    }
  }

  @Test
  @DisplayName("로컬 캐시 설정이 로드된다")
  fun `로컬 캐시 설정이 로드된다`() {
    cacheProperties?.let {
      val localCaches = it.localCaches
      assertNotNull(localCaches)
      assertTrue(localCaches.isNotEmpty())
    }
  }
}
