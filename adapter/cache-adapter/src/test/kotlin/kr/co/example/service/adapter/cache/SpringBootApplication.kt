package kr.co.example.service.adapter.cache

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

@SpringBootApplication(
  scanBasePackages = ["kr.co.example.service.adapter.cache", "kr.co.example.core"]
)
@EnableConfigurationProperties(CacheProperties::class)
class SpringBootApplication
