package uk.gov.justice.digital.hmpps.kotlinexercises.controller

import org.springframework.http.ResponseEntity
import uk.gov.justice.digital.hmpps.kotlinexercises.model.Error

abstract class AbstractController {
  fun buildSuccessResponse(result: Any): ResponseEntity<Map<String, Any>> = ResponseEntity.ok(
    mapOf(
      "result" to result,
      "timestamp" to System.currentTimeMillis(),
    ),
  )

  fun buildErrorResponse(error: Error): ResponseEntity<Map<String, Any>> = ResponseEntity.status(error.code).body(
    mapOf(
      "error" to (error.message ?: "Unknown error"),
      "timestamp" to System.currentTimeMillis(),
    ),
  )
}
