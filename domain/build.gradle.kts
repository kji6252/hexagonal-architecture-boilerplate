plugins {
  `java-test-fixtures`
  id("io.qameta.allure-adapter")
}

dependencies {
  implementation(project(":core"))
}


allure {
  adapter {
    frameworks {
      junit5
    }
  }
}

