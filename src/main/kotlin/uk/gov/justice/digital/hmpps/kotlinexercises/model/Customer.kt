package uk.gov.justice.digital.hmpps.kotlinexercises.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity
class Customer(var age: Int, var accountStatus: String, var riskScore: Float) {
  @Id
  @Column(name = "id", nullable = false, unique = true)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  var id: UUID = UUID.randomUUID()
}
