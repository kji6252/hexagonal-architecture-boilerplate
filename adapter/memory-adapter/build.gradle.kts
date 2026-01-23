plugins {
  id("org.springframework.boot")
  kotlin("plugin.spring")
}

dependencies {
  api(project(":port-out"))
  implementation(project(":core"))
  implementation(project(":domain"))
  implementation("org.springframework.boot:spring-boot-starter-data-redis")
  implementation("redis.clients:jedis")

  testImplementation("org.springframework.boot:spring-boot-testcontainers")
  testImplementation("org.testcontainers:junit-jupiter")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
}
