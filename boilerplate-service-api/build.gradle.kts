plugins {
  id("org.springframework.boot")


  kotlin("plugin.jpa")
  kotlin("plugin.allopen")
  kotlin("plugin.spring")
}

dependencies {
  implementation(project(":core"))
  implementation(project(":application"))
  implementation(project(":adapter:persistence-adapter"))
  implementation(project(":adapter:web-adapter"))
  implementation(project(":adapter:client-adapter"))
  implementation(project(":adapter:cache-adapter"))
  implementation(project(":adapter:memory-adapter"))
  implementation(project(":adapter:message-adapter"))
  implementation(project(":adapter:stream-adapter"))
  implementation(project(":adapter:storage-adapter"))
  implementation(project(":domain"))

  implementation(enforcedPlatform("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}"))

  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  runtimeOnly("io.micrometer:micrometer-registry-prometheus")
  runtimeOnly("com.mysql:mysql-connector-j")
  implementation("org.springframework.boot:spring-boot-starter-validation")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.boot:spring-boot-devtools")
  testImplementation("org.springframework.boot:spring-boot-testcontainers")
  testImplementation("org.testcontainers:junit-jupiter")
  testImplementation("org.testcontainers:mysql")
  testImplementation("org.testcontainers:localstack")
  testImplementation(platform("software.amazon.awssdk:bom:2.29.45"))
  testImplementation("software.amazon.awssdk:s3")
}

tasks {
  bootJar {
    enabled = true
//    archiveFileName.set("${archiveExtension.get()}.jar")

    doLast {
      copy {
        from("$buildDir/libs")
        into("${rootDir}/build/libs")
      }
    }
  }
}
