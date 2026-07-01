package uk.gov.justice.digital.hmpps.kotlinexercises.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class ExampleService(
  private val webClient: WebClient,
) {
  companion object {
    private val log = LoggerFactory.getLogger(this::class.java)
  }

  fun performBackgroundWork(): Mono<String> = fetchExternalData()
    .doOnNext { data -> log.info("Background work received data: {}", data) }
    .map(::processData)
    .subscribeOn(Schedulers.boundedElastic())
    .onErrorResume { error ->
      log.error("Background work failed", error)
      Mono.just("Background work failed: ${error.message}")
    }

  private fun fetchExternalData(): Mono<String> = webClient.get()
    .uri("https://catfact.ninja/fact?max_length=1000")
    .retrieve()
    .bodyToMono<String>()
    .doOnNext { log.debug("Fetched data from external API") }
    .timeout(java.time.Duration.ofSeconds(10))

  private fun processData(data: String): String = data
}
