plugins {
  id("uk.gov.justice.hmpps.gradle-spring-boot") version "10.5.0"
  kotlin("plugin.spring") version "2.4.0"
  kotlin("plugin.jpa") version "2.4.0"
  id("org.jlleitschuh.gradle.ktlint") version "14.2.0"
}

dependencies {
  // hmpps starter includes starter-webmvc, starter-webclient, starter-webmvc-test and springdoc-openapi-starter-webmvc-ui
  implementation("uk.gov.justice.service.hmpps:hmpps-kotlin-spring-boot-starter:2.5.0")
  implementation("org.springframework.boot:spring-boot-starter-webclient") // WebClient
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("com.h2database:h2")
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.3")
  implementation("tools.jackson.module:jackson-module-kotlin:3.2.0")
  implementation("io.arrow-kt:arrow-core:2.1.2")

  testImplementation("uk.gov.justice.service.hmpps:hmpps-kotlin-spring-boot-starter-test:2.5.0")
  testImplementation("org.springframework.boot:spring-boot-starter-webflux-test") // WebTestClient
  testImplementation("org.wiremock:wiremock-standalone:3.13.2")
  testImplementation("io.swagger.parser.v3:swagger-parser:2.1.44") {
    exclude(group = "io.swagger.core.v3")
  }
  testImplementation("io.mockk:mockk:1.14.11")
  testImplementation("com.ninja-squad:springmockk:5.0.1")
  runtimeOnly("org.postgresql:postgresql")
}

kotlin {
  jvmToolchain(25)
}

tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions.jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_25
  }
}
