package uk.gov.justice.digital.hmpps.kotlinexercises.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import uk.gov.justice.digital.hmpps.kotlinexercises.dto.OrderRequest
import uk.gov.justice.digital.hmpps.kotlinexercises.model.Order
import uk.gov.justice.digital.hmpps.kotlinexercises.repository.OrdersRepository
import java.util.Optional
import java.util.UUID

@Service
class OrdersService(
    val ordersRepository: OrdersRepository
) {
    fun save(order: OrderRequest): Mono<String> {
        return try {
            val dto = Order(order.item, order.quantity)
            Mono.just(ordersRepository.save(dto).id.toString())
        } catch (e: Exception) {
            Mono.error(e)
        }
    }

    fun get(id: String): Mono<OrderRequest> {
        return try {
            val dto = ordersRepository.getReferenceById(UUID.fromString(id))
            Mono.just(OrderRequest(dto.item, dto.quantity))
        } catch (e: Exception) {
            Mono.error(e)
        }
    }
}