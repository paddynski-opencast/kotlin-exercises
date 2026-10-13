package uk.gov.justice.digital.hmpps.kotlinexercises.integration.controller

import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient
import uk.gov.justice.digital.hmpps.kotlinexercises.dto.CustomerOrderRequest
import uk.gov.justice.digital.hmpps.kotlinexercises.integration.IntegrationTestBase

class OrderTest : IntegrationTestBase() {
  private fun post(): WebTestClient.ResponseSpec = webTestClient
    .post()
    .uri("/orders")
    .headers(setAuthorisation())
    .bodyValue(CustomerOrderRequest(0, 0, 0, "", "", ""))
    .exchange()

  @Test
  fun `Create order endpoint returns success response`() {
    post()
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("result").exists()
      .jsonPath("timestamp").exists()
  }

  @Test
  fun `Retrieve order endpoint returns JSON with correct structure`() {
    post().expectBody().jsonPath("result").value<String> {
      webTestClient
        .get()
        .uri("/orders/$it")
        .headers(setAuthorisation())
        .exchange()
        .expectStatus()
        .isOk
        .expectBody()
        .jsonPath("$.result").isNotEmpty
        .jsonPath("$.timestamp").isNumber
    }
  }

  @Test
  fun `Background work endpoint handles errors gracefully`() {
    webTestClient
      .get()
      .uri("/orders/1")
      .headers(setAuthorisation())
      .exchange()
      .expectStatus()
      .isOk
      .expectBody()
      .jsonPath("$.error").exists()
      .jsonPath("$.timestamp").exists()
  }
}
