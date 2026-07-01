package uk.gov.justice.digital.hmpps.kotlinexercises.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import uk.gov.justice.digital.hmpps.kotlinexercises.dto.CustomerRequest
import uk.gov.justice.digital.hmpps.kotlinexercises.model.Customer
import uk.gov.justice.digital.hmpps.kotlinexercises.service.SubscriptionManagementService

@RestController
@RequestMapping("/subscription-management", produces = ["application/json"])
class SubscriptionManagementController(
  private val subscriptionManagementService: SubscriptionManagementService,
) {

  @PostMapping("/is-eligible")
  @PreAuthorize("permitAll()")
  fun isEligibleForPremium(@RequestBody @Valid customer: CustomerRequest): ResponseEntity<SubscriptionManagementService.ApprovalStatus> = ResponseEntity.ok(subscriptionManagementService.isEligibleForPremium(customer))

  @PostMapping("/set-customer-details")
  @PreAuthorize("permitAll()")
  fun setCustomerDetails(@RequestBody @Valid customer: CustomerRequest): ResponseEntity<Customer> = ResponseEntity.ok(subscriptionManagementService.setCustomerDetails(customer))

  @GetMapping("/approved-customers")
  @PreAuthorize("permitAll()")
  fun getNumApprovedCustomers(): ResponseEntity<Int> = ResponseEntity.ok(subscriptionManagementService.getNumApprovedCustomers())
}
