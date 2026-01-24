package kr.co.example.service.adapter.cache

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
abstract class AbstractRedisTestSupport {

  companion object {

    @Container
    @JvmStatic
    val redisContainer: GenericContainer<*> = GenericContainer(DockerImageName.parse("redis:7-alpine"))
      .withExposedPorts(6379)

    @JvmStatic
    @DynamicPropertySource
    fun redisProperties(registry: DynamicPropertyRegistry) {
      val host = redisContainer.host
      val port = redisContainer.getMappedPort(6379)
      registry.add("spring.data.redis.host") { host }
      registry.add("spring.data.redis.port") { port.toString() }
    }
  }
}
