package kr.co.example.service.adapter.cache

import kr.co.example.core.logger
import kr.co.example.service.port.output.RedisPort
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisAdapter(
  private val redisTemplate: StringRedisTemplate
) : RedisPort {

  private val log = logger<RedisAdapter>()

  override fun get(key: String): String? {
    return try {
      redisTemplate.opsForValue().get(key)
    } catch (e: Exception) {
      log.warn(e) { "[RedisAdapter] Redis get 실패: key=$key" }
      null
    }
  }

  override fun set(key: String, value: String, expireSeconds: Int) {
    try {
      redisTemplate.opsForValue().set(key, value, expireSeconds.toLong(), java.util.concurrent.TimeUnit.SECONDS)
    } catch (e: Exception) {
      log.warn(e) { "[RedisAdapter] Redis set 실패: key=$key, value=$value" }
    }
  }

  override fun tryLock(lockKey: String, ttlSeconds: Long): Boolean {
    val now = java.time.LocalDateTime.now().toString()
    val success = redisTemplate.opsForValue()
      .setIfAbsent(lockKey, now, ttlSeconds, java.util.concurrent.TimeUnit.SECONDS)
    return success == true
  }

  override fun evict(key: String) {
    try {
      redisTemplate.delete(key)
      log.info { "[RedisAdapter] Redis evict 완료: key=$key" }
    } catch (e: Exception) {
      log.warn(e) { "[RedisAdapter] Redis evict 실패: key=$key" }
    }
  }
}
