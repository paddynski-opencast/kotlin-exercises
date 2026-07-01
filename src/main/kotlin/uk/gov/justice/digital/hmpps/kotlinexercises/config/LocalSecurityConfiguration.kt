package uk.gov.justice.digital.hmpps.kotlinexercises.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

/**
 * Dev mode security configuration that disables JWT validation for easier local development.
 * Allows public access to hello and health endpoints without authentication.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Profile("local")
class LocalSecurityConfiguration {

  @Bean
  fun localSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http
      .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
      .authorizeHttpRequests { authz ->
        authz
          // Allow public endpoints without auth
          .requestMatchers("/hello/**", "/health", "/health/**", "/info", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/error").permitAll()
          // Allow all other requests in dev mode for easier testing
          .anyRequest().permitAll()
      }
      .httpBasic { }
      .cors { it.disable() }
      .csrf { it.disable() }
    return http.build()
  }
}
