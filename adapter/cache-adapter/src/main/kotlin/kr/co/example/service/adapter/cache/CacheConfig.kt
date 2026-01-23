package kr.co.example.service.adapter.cache

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.cache.CacheKeyPrefix
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool

@Configuration
@EnableCaching
@EnableConfigurationProperties(CacheProperties::class)
class CacheConfig(
  private val cacheProperties: CacheProperties,
) {

  @Primary
  @Bean
  fun caffeineCacheManager(): CaffeineCacheManager {
    val caffeineCacheManager = CaffeineCacheManager()
    for ((key: String, value: String) in cacheProperties.localCaches.entries) {
      val build = Caffeine.from(value).build<Any, Any>()
      caffeineCacheManager.registerCustomCache(key, build)
    }
    return caffeineCacheManager
  }

  @Bean
  fun bucket4jJedisPool(jedisConnectionFactory: JedisConnectionFactory): JedisPool {
    val poolConfig = jedisConnectionFactory.getPoolConfig<Jedis>()?.apply {
      jmxEnabled = false
    }

    return JedisPool(
      poolConfig,
    )
  }

  @Bean
  fun redisCacheManager(connectionFactory: RedisConnectionFactory, objectMapper: ObjectMapper): RedisCacheManager {

    val defaultCacheConfig = defaultCacheConfig()
      .entryTtl(java.time.Duration.ofMinutes(30))
      .computePrefixWith(CacheKeyPrefix.simple())
      .serializeKeysWith(
        RedisSerializationContext.SerializationPair.fromSerializer(
          StringRedisSerializer()
        )
      )
      .serializeValuesWith(
        RedisSerializationContext.SerializationPair.fromSerializer(
          GenericJackson2JsonRedisSerializer(customObjectMapper(objectMapper))
        )
      )

    return buildCacheManager(defaultCacheConfig, connectionFactory)
  }

  @Bean
  fun redisCacheManagerForAuth(connectionFactory: RedisConnectionFactory): RedisCacheManager {

    val defaultCacheConfig = defaultCacheConfig()
      .computePrefixWith { cacheName -> "${cacheName}_" }
      .serializeKeysWith(
        RedisSerializationContext.SerializationPair.fromSerializer(
          StringRedisSerializer()
        )
      )
      .serializeValuesWith(
        RedisSerializationContext.SerializationPair.fromSerializer(
          StringRedisSerializer()
        )
      )
    return buildCacheManager(defaultCacheConfig, connectionFactory)
  }

  private fun customObjectMapper(objectMapper: ObjectMapper): ObjectMapper =
    objectMapper.copy()
      .activateDefaultTyping(
        BasicPolymorphicTypeValidator.builder().allowIfBaseType(Any::class.java).build(),
        ObjectMapper.DefaultTyping.NON_FINAL
      )

  private fun buildCacheManager(
    defaultCacheConfig: RedisCacheConfiguration,
    connectionFactory: RedisConnectionFactory
  ): RedisCacheManager {
    val configurationMap =
      cacheProperties.redisCaches.mapValues { (_, value) -> defaultCacheConfig.entryTtl(value) }

    return RedisCacheManager.builder(connectionFactory)
      .cacheDefaults(defaultCacheConfig)
      .enableStatistics()
      .withInitialCacheConfigurations(configurationMap)
      .build()
  }

  @Bean
  fun stringRedisTemplate(connectionFactory: RedisConnectionFactory): StringRedisTemplate {
    return StringRedisTemplate(connectionFactory)
  }
}
