package kr.co.example.service

import kr.co.example.service.adapter.cache.EnableCacheAdapter
import kr.co.example.service.adapter.memory.EnableMemoryAdapter
import kr.co.example.service.adapter.persistence.EnablePersistenceAdapter
import kr.co.example.service.adapter.web.config.EnableWebAdapter
import kr.co.example.service.application.config.EnableApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableWebAdapter
@EnablePersistenceAdapter
@EnableApplication
@EnableCacheAdapter
@EnableMemoryAdapter
@EnableAsync
class BoilerplateServiceApiApplication

fun main(args: Array<String>) {
  runApplication<BoilerplateServiceApiApplication>(*args)
}
