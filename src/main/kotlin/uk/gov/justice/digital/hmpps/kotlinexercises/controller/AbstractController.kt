package uk.gov.justice.digital.hmpps.kotlinexercises.controller

abstract class AbstractController {
  fun buildSuccessResponse(result: Any): Map<String, Any> = mapOf<String, Any>(
    "result" to result,
    "timestamp" to System.currentTimeMillis(),
  )

  fun buildErrorResponse(error: Throwable): Map<String, Any> = mapOf<String, Any>(
    "error" to (error.message ?: "Unknown error"),
    "timestamp" to System.currentTimeMillis(),
  )
}
