package kr.co.example.service.adapter.persistence

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

private const val BASE_PACKAGES = "kr.co.example.service.adapter.persistence.**"

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  basePackages = [BASE_PACKAGES]
)
@EntityScan(basePackages = ["kr.co.example.service.domain", BASE_PACKAGES])
@ComponentScan(basePackageClasses = [DbConfig::class])
class DbConfig

