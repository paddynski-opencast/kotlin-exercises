package uk.gov.justice.digital.hmpps.kotlinexercises.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import uk.gov.justice.digital.hmpps.kotlinexercises.dto.CustomerOrderRequest
import uk.gov.justice.digital.hmpps.kotlinexercises.dto.CustomerOrderResponse
import uk.gov.justice.digital.hmpps.kotlinexercises.model.CustomerOrder
import uk.gov.justice.digital.hmpps.kotlinexercises.repository.OrdersRepository
import java.util.*

@Service
class OrdersService(
  val ordersRepository: OrdersRepository,
) {
  fun createOrder(order: CustomerOrderRequest): Mono<String> = try {
    val dto = CustomerOrder(
      customerId = order.customerId,
      productId = order.productId,
      quantity = order.quantity,
      line1 = order.line,
      city = order.city,
      postCode = order.post,
      trackingId = null,
    )
    Mono.just(ordersRepository.save(dto).id.toString())
  } catch (e: Exception) {
    Mono.error(e)
  }

  fun retrieveOrder(id: String): Mono<CustomerOrderResponse> = try {
    val dto = ordersRepository.getReferenceById(UUID.fromString(id))
    Mono.just(
      CustomerOrderResponse(
        orderId = dto.id.toString(),
        trackingUrl = if (dto.trackingId == null) "" else "/orders/tracking/${dto.trackingId}",
      ),
    )
  } catch (e: Exception) {
    Mono.error(e)
  }
}
