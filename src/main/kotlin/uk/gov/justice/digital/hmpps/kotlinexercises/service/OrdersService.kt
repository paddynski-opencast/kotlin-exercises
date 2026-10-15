package uk.gov.justice.digital.hmpps.kotlinexercises.service

import arrow.core.Either
import org.springframework.stereotype.Service
import uk.gov.justice.digital.hmpps.kotlinexercises.dto.CustomerOrderRequest
import uk.gov.justice.digital.hmpps.kotlinexercises.dto.CustomerOrderResponse
import uk.gov.justice.digital.hmpps.kotlinexercises.model.CustomerOrder
import uk.gov.justice.digital.hmpps.kotlinexercises.model.Error
import uk.gov.justice.digital.hmpps.kotlinexercises.repository.OrdersRepository
import java.util.*

@Service
class OrdersService(
  val ordersRepository: OrdersRepository,
) {
  fun createOrder(order: CustomerOrderRequest): Either<Error, String> = try {
    val dto = CustomerOrder(
      customerId = order.customerId,
      productId = order.productId,
      quantity = order.quantity,
      line = order.line,
      city = order.city,
      post = order.post,
      trackingIds = listOf(),
    )
    Either.Right(ordersRepository.save(dto).id.toString())
  } catch (e: Exception) {
    Either.Left(Error(500, e.message))
  }

  fun retrieveOrder(id: String): Either<Error, CustomerOrderResponse> = try {
    val uuid = UUID.fromString(id)
    if (ordersRepository.existsById(uuid)) {
      val dto = ordersRepository.getReferenceById(uuid)
      val trackingId = dto.trackingIds.getOrNull(dto.trackingIds.size - 1)
      Either.Right(
        CustomerOrderResponse(
          orderId = dto.id.toString(),
          trackingUrl = if (trackingId == null) "" else "/orders/tracking/$trackingId",
        ),
      )
    } else {
      Either.Left(Error(404, "No orders found"))
    }
  } catch (e: IllegalArgumentException) {
    Either.Left(Error(400, e.message))
  } catch (e: Exception) {
    Either.Left(Error(500, e.message))
  }
}
