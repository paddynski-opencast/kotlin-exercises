package uk.gov.justice.digital.hmpps.kotlinexercises.integration.controller

import org.junit.jupiter.api.Test
import uk.gov.justice.digital.hmpps.kotlinexercises.integration.IntegrationTestBase

class HelloWorldTest : IntegrationTestBase() {

  @Test
  fun `Hello World endpoint returns greeting`() {
    webTestClient.get()
      .uri("/hello")
      .headers(setAuthorisation())
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("message").isEqualTo("Hello, World!")
  }

  @Test
  fun `Version endpoint returns version info`() {
    webTestClient.get()
      .uri("/hello/version")
      .headers(setAuthorisation())
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("message").isEqualTo("Hello from HMPPS Template Kotlin")
      .jsonPath("version").isEqualTo("1.0.0")
  }
}
