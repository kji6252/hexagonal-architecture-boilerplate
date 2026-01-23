package kr.co.example.service.port.output

interface RedisPort {
  fun get(key: String): String?
  fun set(key: String, value: String, expireSeconds: Int)
  fun evict(key: String)
  fun tryLock(lockKey: String, ttlSeconds: Long): Boolean
}
