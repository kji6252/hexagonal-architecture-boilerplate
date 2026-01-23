plugins {
  id("org.springframework.boot")
  id("org.openapi.generator")
  kotlin("plugin.spring")
  id("io.qameta.allure-adapter")
}

dependencies {
  implementation(project(":core"))
  implementation(project(":application"))
  implementation(project(":port-in"))
  implementation(project(":port-out"))
  implementation(project(":domain"))

  implementation("org.springframework.boot:spring-boot-starter-aop")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")

  implementation("org.semver4j:semver4j:5.8.0")

  // https://mvnrepository.com/artifact/com.giffing.bucket4j.spring.boot.starter/bucket4j-spring-boot-starter
  implementation("com.giffing.bucket4j.spring.boot.starter:bucket4j-spring-boot-starter:0.12.8")
  implementation("com.bucket4j:bucket4j-redis:8.10.1")
  implementation("io.opentracing:opentracing-api:0.33.0")
  implementation("io.opentracing:opentracing-util:0.33.0")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("com.ninja-squad:springmockk:${property("springMockkVersion")}")
  testImplementation("org.springframework.security:spring-security-test")
}

val buildDir = layout.buildDirectory.get().asFile

sourceSets {
  main {
    java.srcDirs("$buildDir/generated/src/main/kotlin", "$projectDir/src/api-specs/specs")
    resources.srcDir("${openApiGenerate.outputDir.get()}/src/main/resources")
  }
}

tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    dependsOn("openApiGenerate", "openApiInternalJsonGenerator")
  }
}

val apiSpecsRoot = "$projectDir/src/api-specs"
val specPath = "$apiSpecsRoot/specs/internal-openapi.yaml"

openApiGenerate {
  generatorName.set("kotlin-spring") //kotlin-spring 기반 코드 생성
  inputSpec.set(specPath) //OpenApi 3.0문서의 위치
  outputDir.set("${buildDir}/generated") //문서를 기반으로 생성될 코드의 위치
  configFile.set("$apiSpecsRoot/specs/config.json")
}

tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("openApiInternalJsonGenerator") {
  dependsOn("openApiGenerate")
  generatorName.set("openapi")
  inputSpec.set(specPath) //OpenApi 3.0문서의 위치
  outputDir.set("${buildDir}/resources/main/docs/openapi/internal") //문서를 기반으로 생성될 코드의 위치
  doLast {
    copy {
      from("$apiSpecsRoot/docs")
      into("${buildDir}/resources/main/docs")
    }
  }
}


allure {
  adapter {
    frameworks {
      junit5
    }
  }
}
