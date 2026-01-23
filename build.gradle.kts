plugins {
  val kotlinVersion = "1.9.25"
  kotlin("jvm") version kotlinVersion
  // kotlin("kapt") version kotlinVersion // kapt 제외 - Hello World 예제에서는 불필요
  kotlin("plugin.spring") version kotlinVersion
  kotlin("plugin.jpa") version kotlinVersion

  id("org.springframework.boot") version "3.4.4" apply false
  id("io.spring.dependency-management") version "1.1.7" apply false
  id("org.openapi.generator") version "7.10.0" apply false
  id("io.qameta.allure") version "2.12.0" apply false
  id("io.qameta.allure-aggregate-report") version "2.12.0"
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}


allprojects {
  apply(plugin = "kotlin")
  // apply(plugin = "kotlin-kapt") // kapt 제외 - Hello World 예제에서는 불필요
  apply(plugin = "io.spring.dependency-management")

  group = "kr.co.uplus"
  version = "1.0.0-SNAPSHOT"

  tasks.getByName<Test>("test") {
    useJUnitPlatform {
      excludeTags = setOf("integration")
    }
    reports {
      html.required.set(false)
      junitXml.required.set(false)
    }
    systemProperty("allure.link.issue.pattern", "https://lgu-cto.atlassian.net/browse/{}")
  }
  tasks.register<Test>("integrationTest") {
    useJUnitPlatform {
      includeTags = setOf("integration")
    }
  }

  tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs = listOf("-Xjsr305=strict")
    }
  }

  tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    enabled = false
  }

  repositories {
    mavenCentral()
  }
}

subprojects {
  dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("net.logstash.logback:logstash-logback-encoder:${property("logstashLogbackEncoder")}")

    // MapStruct 제외 - Hello World 예제에서는 불필요
    // implementation("org.mapstruct:mapstruct:${property("mapstructVersion")}")
    // kapt("org.mapstruct:mapstruct-processor:${property("mapstructVersion")}")
    // kaptTest("org.mapstruct:mapstruct-processor:${property("mapstructVersion")}")

    testImplementation("io.kotest:kotest-runner-junit5:${property("kotestVersion")}")
    testImplementation("io.kotest:kotest-assertions-core:${property("kotestVersion")}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${property("kotlinCoroutinesVersion")}")
    testImplementation("io.mockk:mockk:${property("mockkVersion")}")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:${property("kotestExtensionsSpringVersion")}")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${property("mockitoVersion")}")
  }

  // @ConfigurationProperties 사용 시 IDE 자동완성 및 메타데이터 생성을 위한 Configuration Processor
  // Spring Boot 플러그인이 적용된 모듈에만 자동으로 추가됨
  // Hello World 예제에서는 불필요하므로 주석 처리
  /*
  pluginManager.withPlugin("org.springframework.boot") {
    dependencies {
      kapt("org.springframework.boot:spring-boot-configuration-processor")
    }
  }
  */
}

defaultTasks(":boilerplate-service-api:bootTestRun")
tasks {
  jar {
    enabled=false
  }
}

configurations.allureAggregateReport.get().dependencies.clear()
dependencies {
  allureAggregateReport(project(":application"))
  allureAggregateReport(project(":adapter:web-adapter"))
  allureAggregateReport(project(":domain"))
}

allure {
  report {
    singleFile.set(false)
  }
}
