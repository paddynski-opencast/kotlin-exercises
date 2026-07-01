package uk.gov.justice.digital.hmpps.kotlinexercises.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import uk.gov.justice.digital.hmpps.kotlinexercises.service.ExampleService

@RestController
@RequestMapping("/example", produces = ["application/json"])
class ExampleController(
  private val exampleService: ExampleService,
) {

  @GetMapping
  @PreAuthorize("permitAll()")
  fun performBackgroundWork(): Mono<Map<String, Any>> = exampleService
    .performBackgroundWork()
    .map(::buildSuccessResponse)
    .onErrorResume { error -> Mono.just(buildErrorResponse(error)) }

  private fun buildSuccessResponse(result: String): Map<String, Any> = mapOf<String, Any>(
    "result" to result,
    "timestamp" to System.currentTimeMillis(),
  )

  private fun buildErrorResponse(error: Throwable): Map<String, Any> = mapOf<String, Any>(
    "error" to (error.message ?: "Unknown error"),
    "timestamp" to System.currentTimeMillis(),
  )
}
