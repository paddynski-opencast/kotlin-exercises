package uk.gov.justice.digital.hmpps.kotlinexercises.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

@Entity
class CustomerOrder(
  var customerId: Int,
  var productId: Int,
  var quantity: Int,
  var line1: String,
  var city: String,
  var postCode: String,
  var trackingId: String,
) {
  @Id
  @Column(name = "id", nullable = false, unique = true)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  var id: UUID = UUID.randomUUID()
}
