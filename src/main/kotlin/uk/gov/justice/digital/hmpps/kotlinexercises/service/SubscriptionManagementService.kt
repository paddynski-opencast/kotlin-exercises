package uk.gov.justice.digital.hmpps.kotlinexercises.service

import org.springframework.stereotype.Service
import uk.gov.justice.digital.hmpps.kotlinexercises.dto.CustomerRequest
import uk.gov.justice.digital.hmpps.kotlinexercises.model.Customer
import uk.gov.justice.digital.hmpps.kotlinexercises.repository.CustomerDetailsRepository

@Service
class SubscriptionManagementService(private val customerDetailsRepository: CustomerDetailsRepository) {
  fun isEligibleForPremium(customer: CustomerRequest): ApprovalStatus = ApprovalStatus.APPROVED
  fun getNumApprovedCustomers(): Int = customerDetailsRepository.getAllByAccountStatusEquals(ApprovalStatus.APPROVED.name).size
  fun setCustomerDetails(customer: CustomerRequest): Customer = customerDetailsRepository.save(
    Customer(
      customer.age,
      customer.accountStatus,
      customer.riskScore,
    ),
  )

  enum class ApprovalStatus {
    APPROVED,
    REJECTED,
    MANUAL_REVIEW,
  }
}
