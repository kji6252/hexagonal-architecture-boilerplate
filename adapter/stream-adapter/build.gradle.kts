plugins {
  id("org.springframework.boot")
  kotlin("plugin.spring")
  id("io.spring.dependency-management")
}

dependencies {
  api(project(":port-in"))
  implementation(project(":domain"))
  implementation(project(":core"))
  implementation("org.springframework.cloud:spring-cloud-starter-stream-kafka")
  implementation(enforcedPlatform("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}"))
  implementation("io.micrometer:micrometer-registry-prometheus")

  testImplementation("org.junit.jupiter:junit-jupiter-api")
  testImplementation("org.junit.jupiter:junit-jupiter-engine")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.boot:spring-boot-testcontainers")
  testImplementation("org.testcontainers:junit-jupiter")
}

tasks.test {
  useJUnitPlatform()  // JUnit5를 사용할 수 있도록 설정
}
