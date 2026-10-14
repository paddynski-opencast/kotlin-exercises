package uk.gov.justice.digital.hmpps.kotlinexercises.dto

data class CustomerOrderResponse(
  var orderId: String,
  var trackingUrl: String,
)
