plugins {
  id("org.springframework.boot")
  kotlin("plugin.spring")
  kotlin("plugin.jpa")
  id("java-test-fixtures")
}

allOpen {
  annotation("jakarta.persistence.Entity")
  annotation("jakarta.persistence.MappedSuperclass")
  annotation("jakarta.persistence.Embeddable")
}

dependencies {
  api(project(":port-out"))
  implementation(project(":domain"))
  implementation(project(":core"))

  implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework:spring-web")
  implementation("com.fasterxml.uuid:java-uuid-generator:${property("javaUuidGeneratorVersion")}")
  implementation("io.hypersistence:hypersistence-utils-hibernate-63:3.8.2")
  implementation("org.flywaydb:flyway-core:9.1.6")
  implementation("org.flywaydb:flyway-mysql:8.4.4")

  // QueryDSL 제외 - Hello World 예제에서는 불필요
  // implementation(group = "com.querydsl", name = "querydsl-jpa", classifier = "jakarta")
  // kapt(group = "com.querydsl", name = "querydsl-apt", classifier = "jakarta")

  runtimeOnly("com.mysql:mysql-connector-j")
  testImplementation(testFixtures(project(":domain")))
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.boot:spring-boot-testcontainers")
  testImplementation("org.testcontainers:junit-jupiter")
  testImplementation("org.testcontainers:mysql")
}

