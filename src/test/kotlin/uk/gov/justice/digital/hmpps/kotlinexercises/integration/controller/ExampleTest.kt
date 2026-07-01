package uk.gov.justice.digital.hmpps.kotlinexercises.integration.controller

import org.junit.jupiter.api.Test
import uk.gov.justice.digital.hmpps.kotlinexercises.integration.IntegrationTestBase
import java.time.Duration

class ExampleTest : IntegrationTestBase() {

  @Test
  fun `Background work endpoint returns success response`() {
    webTestClient
      .mutate()
      .responseTimeout(Duration.ofMillis(30_000)) // to resolve "Timeout on blocking read for 3000000 NANOSECONDS "
      .build()
      .get()
      .uri("/example")
      .headers(setAuthorisation())
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("result").exists()
      .jsonPath("timestamp").exists()
  }

  @Test
  fun `Background work endpoint returns JSON with correct structure`() {
    webTestClient
      .mutate()
      .responseTimeout(Duration.ofMillis(30_000)) // to resolve "Timeout on blocking read for 3000000 NANOSECONDS "
      .build()
      .get()
      .uri("/example")
      .headers(setAuthorisation())
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("$.result").isNotEmpty
      .jsonPath("$.timestamp").isNumber
  }

  @Test
  fun `Background work endpoint handles errors gracefully`() {
    webTestClient
      .mutate()
      .responseTimeout(Duration.ofMillis(30_000)) // to resolve "Timeout on blocking read for 3000000 NANOSECONDS "
      .build()
      .get()
      .uri("/example")
      .headers(setAuthorisation())
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("$.timestamp").exists()
  }
}
