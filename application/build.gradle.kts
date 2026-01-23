plugins {
  id("org.springframework.boot")
  id("io.qameta.allure-adapter")
  kotlin("plugin.spring")
}

dependencies {
  implementation(project(":core"))
  api(project(":domain"))
  implementation(project(":port-in"))
  implementation(project(":port-out"))

  implementation("org.springframework.boot:spring-boot-starter-logging")
  implementation("org.springframework:spring-context")
  implementation("org.springframework:spring-tx")
  implementation("org.springframework.security:spring-security-crypto")
  implementation("com.auth0:java-jwt:4.5.0")

  implementation("com.google.zxing:core:${property("zxingVersion")}")
  implementation("com.google.zxing:javase:${property("zxingVersion")}")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation(testFixtures(project(":domain")))
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
