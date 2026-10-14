package uk.gov.justice.digital.hmpps.kotlinexercises.controller

import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import uk.gov.justice.digital.hmpps.kotlinexercises.dto.CustomerOrderRequest
import uk.gov.justice.digital.hmpps.kotlinexercises.service.OrdersService

@RestController
@RequestMapping("/orders", produces = ["application/json"])
class OrdersController(
  val ordersService: OrdersService,
) : AbstractController() {

  @PostMapping
  @PreAuthorize("permitAll()")
  fun createOrder(@RequestBody @Valid order: CustomerOrderRequest): Mono<Map<String, Any>> = ordersService
    .createOrder(order)
    .map(::buildSuccessResponse)
    .onErrorResume { error -> Mono.just(buildErrorResponse(error)) }

  @GetMapping("/{id}")
  @PreAuthorize("permitAll()")
  fun retrieveOrder(@PathVariable id: String): Mono<Map<String, Any>> = ordersService
    .retrieveOrder(id)
    .map(::buildSuccessResponse)
    .onErrorResume { error -> Mono.just(buildErrorResponse(error)) }
}
