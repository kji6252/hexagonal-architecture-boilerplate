package kr.co.example.service.adapter.cache

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties("app.cache")
class CacheProperties(
  val localCaches: Map<String, String>,
  val redisCaches: Map<String, Duration>
)
