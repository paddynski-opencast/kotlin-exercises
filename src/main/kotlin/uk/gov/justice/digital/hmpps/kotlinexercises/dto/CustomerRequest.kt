package uk.gov.justice.digital.hmpps.kotlinexercises.dto

data class CustomerRequest(
  var age: Int,
  var accountStatus: String,
  var riskScore: Float,
)
