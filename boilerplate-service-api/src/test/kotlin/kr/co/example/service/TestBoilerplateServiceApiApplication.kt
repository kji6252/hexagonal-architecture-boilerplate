package kr.co.example.service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import

@Import(BoilerplateServiceApiApplication::class)
@SpringBootApplication
@TestConfiguration(proxyBeanMethods = false)
class TestBoilerplateServiceApiApplication {

//  @Bean(destroyMethod = "stop")
//  @ServiceConnection
//  fun mySqlContainer() = org.testcontainers.containers.MySQLContainer("mysql:8.0.36").apply {
//    withDatabaseName("SampleSchema")
//    withUsername("root")
//    withPassword("test")
//    start()
//    println("MySQL container started on port: $firstMappedPort")
//  }
}

fun main(args: Array<String>) {
  runApplication<TestBoilerplateServiceApiApplication>(*args)
}
