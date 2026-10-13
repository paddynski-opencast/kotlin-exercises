package uk.gov.justice.digital.hmpps.kotlinexercises.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import uk.gov.justice.digital.hmpps.kotlinexercises.dto.OrderRequest
import uk.gov.justice.digital.hmpps.kotlinexercises.model.Order
import uk.gov.justice.digital.hmpps.kotlinexercises.repository.OrdersRepository
import java.util.*
import kotlin.Int

@Service
class OrdersService(
  val ordersRepository: OrdersRepository
) {
    fun save(order: OrderRequest): Mono<String> {
        return try {
            val dto = Order(
              customerId = order.customerId,
              productId = order.productId,
              quantity = order.quantity,
              line1 = order.line1,
              city = order.city,
              postCode = order.postCode,
              trackingId = ""
            )
            Mono.just(ordersRepository.save(dto).id.toString())
        } catch (e: Exception) {
            Mono.error(e)
        }
    }

    fun get(id: String): Mono<OrderRequest> {
        return try {
            val dto = ordersRepository.getReferenceById(UUID.fromString(id))
            Mono.just(OrderRequest(
              customerId = dto.customerId,
              productId = dto.productId,
              quantity = dto.quantity,
              line1 = dto.line1,
              city = dto.city,
              postCode = dto.postCode
            ))
        } catch (e: Exception) {
            Mono.error(e)
        }
    }
}