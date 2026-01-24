package kr.co.example.service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MySQLContainer

@Import(BoilerplateServiceApiApplication::class)
@SpringBootApplication
class TestBoilerplateServiceApiApplication

fun main(args: Array<String>) {
  // TestContainers 시작
  val redisContainer: GenericContainer<*> = GenericContainer("redis:7-alpine").apply {
    withExposedPorts(6379)
    start()
  }

  val mySqlContainer: MySQLContainer<*> = MySQLContainer("mysql:8.0.36").apply {
    withDatabaseName("SampleSchema")
    withUsername("root")
    withPassword("test")
    start()
  }

  println("Redis container started on port: ${redisContainer.firstMappedPort}")
  println("MySQL container started on port: ${mySqlContainer.firstMappedPort}")

  // 시스템 속성 설정 (runApplication 전에)
  System.setProperty("spring.data.redis.host", redisContainer.host)
  System.setProperty("spring.data.redis.port", redisContainer.getMappedPort(6379).toString())
  System.setProperty("spring.datasource.url", mySqlContainer.jdbcUrl)
  System.setProperty("spring.datasource.username", mySqlContainer.username)
  System.setProperty("spring.datasource.password", mySqlContainer.password)

  // Spring Boot 실행
  runApplication<TestBoilerplateServiceApiApplication>(*args)
}
