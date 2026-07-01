package uk.gov.justice.digital.hmpps.kotlinexercises.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfiguration(buildProperties: BuildProperties) {
  private val version: String = buildProperties.version!!

  @Bean
  fun customOpenAPI(): OpenAPI = OpenAPI()
    .servers(
      listOf(
        Server().url("https://template-kotlin-dev.hmpps.service.justice.gov.uk").description("Development"),
        Server().url("https://template-kotlin-preprod.hmpps.service.justice.gov.uk").description("Pre-Production"),
        Server().url("https://template-kotlin.hmpps.service.justice.gov.uk").description("Production"),
        Server().url("http://localhost:8080").description("Local"),
      ),
    )
    .tags(
      listOf(),
    )
    .info(
      Info().title("HMPPS Template Kotlin").version(version)
        .contact(Contact().name("HMPPS Digital Studio").email("feedback@digital.justice.gov.uk")),
    )
  // TODO Add security schema and roles in `.components()` and `.addSecurityItem()`

  @Bean
  fun sortServersByProfile(
    @Value("\${spring.profiles.active:local}") profile: String,
  ): OpenApiCustomizer = OpenApiCustomizer { openApi ->
    // Create your server instances
    val devServer = Server().url("https://template-kotlin-dev.hmpps.service.justice.gov.uk").description("Development")
    val preprodServer = Server().url("https://template-kotlin-preprod.hmpps.service.justice.gov.uk").description("Pre-Production")
    val prodServer = Server().url("https://template-kotlin.hmpps.service.justice.gov.uk").description("Production")
    val localServer = Server().url("http://localhost:8080").description("Local")

    val allServers = listOf(devServer, preprodServer, prodServer, localServer)

    // Dynamically move the server matching the active profile to the front
    val orderedServers = allServers.sortedByDescending { server ->
      server.description.equals(profile, ignoreCase = true)
    }

    openApi.servers = orderedServers
  }
}

private fun SecurityScheme.addBearerJwtRequirement(role: String): SecurityScheme = type(SecurityScheme.Type.HTTP)
  .scheme("bearer")
  .bearerFormat("JWT")
  .`in`(SecurityScheme.In.HEADER)
  .name("Authorization")
  .description("A HMPPS Auth access token with the `$role` role.")
