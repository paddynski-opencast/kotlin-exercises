package uk.gov.justice.digital.hmpps.kotlinexercises.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import uk.gov.justice.digital.hmpps.kotlinexercises.model.CustomerOrder
import java.util.*

@Repository
interface OrdersRepository : JpaRepository<CustomerOrder, UUID>
