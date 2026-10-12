package uk.gov.justice.digital.hmpps.kotlinexercises.dto

data class OrderRequest(
  var item: String,
  var quantity: Int
)