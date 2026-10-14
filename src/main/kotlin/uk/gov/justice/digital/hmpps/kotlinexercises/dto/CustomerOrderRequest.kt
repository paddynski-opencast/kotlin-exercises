package uk.gov.justice.digital.hmpps.kotlinexercises.dto

data class CustomerOrderRequest(
  var customerId: Int,
  var productId: Int,
  var quantity: Int,
  var line: String,
  var city: String,
  var post: String,
)
