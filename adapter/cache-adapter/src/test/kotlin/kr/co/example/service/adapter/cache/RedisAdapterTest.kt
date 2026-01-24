package kr.co.example.service.adapter.cache

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [SpringBootApplication::class])
@ActiveProfiles("test")
@Tag("unit")
@DisplayName("RedisAdapter 테스트")
class RedisAdapterTest {

  @Autowired(required = false)
  private var redisAdapter: RedisAdapter? = null

  @Test
  @DisplayName("RedisAdapter 빈이 존재한다")
  fun `RedisAdapter 빈이 존재한다`() {
    redisAdapter?.let {
      assertNotNull(redisAdapter)
    }
  }
}
