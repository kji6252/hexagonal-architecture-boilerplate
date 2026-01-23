plugins {
  id("org.springframework.boot")
  id("io.qameta.allure-adapter")
  kotlin("plugin.spring")
}

dependencies {
  api(project(":port-out"))
  implementation(project(":domain"))
  implementation(project(":core"))

  // Spring Boot
  implementation("org.springframework.boot:spring-boot-starter")

  // AWS SDK v2
  implementation(platform("software.amazon.awssdk:bom:2.29.45"))
  implementation("software.amazon.awssdk:s3")
  implementation("software.amazon.awssdk:sts") // IRSA 인증용

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.boot:spring-boot-testcontainers")
  testImplementation("org.testcontainers:junit-jupiter")
  testImplementation("org.testcontainers:localstack")
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
