package uk.gov.justice.digital.hmpps.kotlinexercises.dto

data class OrderRequest(
  var customerId: Int,
  var productId: Int,
  var quantity: Int,
  var line1: String,
  var city: String,
  var postCode: String
)