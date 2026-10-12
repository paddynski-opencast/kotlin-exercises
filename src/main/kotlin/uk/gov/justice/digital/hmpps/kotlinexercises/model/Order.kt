package uk.gov.justice.digital.hmpps.kotlinexercises.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

@Entity
class Order(var item: String, var quantity: Int) {
  @Id
  @Column(name = "id", nullable = false, unique = true)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  var id: UUID = UUID.randomUUID()
}