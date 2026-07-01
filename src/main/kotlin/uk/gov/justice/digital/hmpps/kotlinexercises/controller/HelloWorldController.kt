package uk.gov.justice.digital.hmpps.kotlinexercises.controller

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hello", produces = ["application/json"])
class HelloWorldController {

  @GetMapping
  @PreAuthorize("permitAll()")
  fun helloWorld(): Map<String, String> = mapOf("message" to "Hello, World!")

  @GetMapping("/version")
  @PreAuthorize("permitAll()")
  fun version(): Map<String, String> = mapOf(
    "message" to "Hello from HMPPS Template Kotlin",
    "version" to "1.0.0",
  )
}
