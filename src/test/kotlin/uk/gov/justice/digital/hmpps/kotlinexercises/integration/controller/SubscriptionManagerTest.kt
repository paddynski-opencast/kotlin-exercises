package uk.gov.justice.digital.hmpps.kotlinexercises.integration.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.expectBody
import uk.gov.justice.digital.hmpps.kotlinexercises.integration.IntegrationTestBase
import uk.gov.justice.digital.hmpps.kotlinexercises.model.Customer
import uk.gov.justice.digital.hmpps.kotlinexercises.repository.CustomerDetailsRepository
import uk.gov.justice.digital.hmpps.kotlinexercises.service.SubscriptionManagementService

class SubscriptionManagerTest : IntegrationTestBase() {
  @MockkBean
  lateinit var customerDetailsRepository: CustomerDetailsRepository

  @Test
  fun `always returns approved`() {
    webTestClient
      .post()
      .uri("/subscription-management/is-eligible")
      .headers(setAuthorisation())
      .bodyValue(Customer(30, "account status unknown", 0.1F))
      .exchange()
      .expectStatus()
      .isOk
      .expectBody<String>()
      .consumeWith({
        assertEquals("\"" + SubscriptionManagementService.ApprovalStatus.APPROVED.name + "\"", it.responseBody)
      })
  }

  @Test
  fun `can set customer details`() {
    every { customerDetailsRepository.save(any()) } returns Customer(100, SubscriptionManagementService.ApprovalStatus.APPROVED.name, 0.1F)

    webTestClient
      .post()
      .uri("/subscription-management/set-customer-details")
      .headers(setAuthorisation())
      .bodyValue(Customer(100, SubscriptionManagementService.ApprovalStatus.APPROVED.name, 0.1F))
      .exchange()
      .expectStatus()
      .isOk
      .expectBody<Customer>()
      .consumeWith({
        val actual: Customer? = it.responseBody
        assertEquals(100, actual?.age)
        assertEquals("APPROVED", actual?.accountStatus)
        assertEquals(0.1F, actual?.riskScore)
      })
  }

  @Test
  fun `approved customers starts at zero`() {
    every { customerDetailsRepository.getAllByAccountStatusEquals(SubscriptionManagementService.ApprovalStatus.APPROVED.name) } returns emptyList()

    webTestClient
      .get()
      .uri("/subscription-management/approved-customers")
      .headers(setAuthorisation())
      .exchange()
      .expectStatus()
      .isOk
      .expectBody<Int>()
      .consumeWith({
        val actual: Int? = it.responseBody
        assertEquals(0, actual)
      })
  }

  @Test
  fun `can get number of approved customers`() {
    every { customerDetailsRepository.getAllByAccountStatusEquals(SubscriptionManagementService.ApprovalStatus.APPROVED.name) } returns
      listOf(Customer(100, SubscriptionManagementService.ApprovalStatus.APPROVED.name, 0.1F))

    webTestClient
      .get()
      .uri("/subscription-management/approved-customers")
      .headers(setAuthorisation())
      .exchange()
      .expectStatus()
      .isOk
      .expectBody<Int>()
      .consumeWith({
        val actual: Int? = it.responseBody
        assertEquals(1, actual)
      })
  }
}
