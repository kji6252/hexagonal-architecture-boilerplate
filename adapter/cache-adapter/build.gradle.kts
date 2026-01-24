
plugins {
    id("org.springframework.boot")
    kotlin("plugin.spring")
}

dependencies {
    api(project(":port-out"))
    implementation(project(":domain"))
    implementation(project(":core"))

    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("redis.clients:jedis")

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test") // SpringExtension을 포함한 의존성
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("com.github.ben-manes.caffeine:caffeine")
}

configurations {
    testImplementation.get().exclude(group = "io.kotest")
    testImplementation.get().exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-test")
}

tasks.test {
    useJUnitPlatform()  // JUnit5를 사용할 수 있도록 설정
}
