plugins {
  id("org.springframework.boot")
  id("io.qameta.allure-adapter")
  kotlin("plugin.spring")
}

dependencies {
  api(project(":port-out"))
  implementation(project(":domain"))
  implementation(project(":core"))
  implementation("org.springframework:spring-webmvc")
  implementation(enforcedPlatform("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}"))
  implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
  implementation("io.github.openfeign:feign-okhttp")
  implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-resilience4j")

  testImplementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.boot:spring-boot-starter-data-redis")
  testImplementation("org.springframework.boot:spring-boot-starter-cache")
  testImplementation("org.springframework.boot:spring-boot-testcontainers")
  testImplementation("org.testcontainers:junit-jupiter")
  testImplementation(project(":adapter:cache-adapter"))
}

tasks.test {
  useJUnitPlatform()
}


allure {
  adapter {
    frameworks {
      junit5
    }
  }
}
