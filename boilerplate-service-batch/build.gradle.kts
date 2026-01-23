plugins {
  id("org.springframework.boot")
  id("io.spring.dependency-management")
  kotlin("plugin.jpa")
  kotlin("plugin.allopen")
  kotlin("plugin.spring")
}

dependencies {
  implementation(project(":core"))
  implementation(project(":domain"))
  implementation(project(":adapter:memory-adapter"))
  implementation(project(":adapter:persistence-adapter"))

  implementation("org.springframework.boot:spring-boot-starter-batch")
  runtimeOnly("com.h2database:h2")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.flywaydb:flyway-core")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.batch:spring-batch-test")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.boot:spring-boot-testcontainers")
  testImplementation("org.springframework.boot:spring-boot-devtools")
  testImplementation("org.testcontainers:junit-jupiter")
  testImplementation("org.testcontainers:mysql")

}

tasks {
  bootJar {
    enabled = false
  }
  jar {
    enabled = true
    archiveFileName.set("boilerplate-service-batch.jar")
  }
}
