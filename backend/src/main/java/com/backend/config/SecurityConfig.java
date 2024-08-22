
package com.backend.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.backend.security.JwtAuthenticationEntryPoint;
import com.backend.security.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;

/**
 * Questa classe definisce la configurazione di sicurezza dell'applicazione.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationEntryPoint authenticationEntryPoint;
  private final JwtAuthenticationFilter authenticationFilter;

  /**
   * Configura l'AuthenticationManager.
   *
   * @param configuration L'oggetto AuthenticationConfiguration rappresenta la
   *                      configurazione dell'autenticazione
   * @return L'AuthenticationManager configurato
   * @throws Exception Se si verifica un errore durante la configurazione
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  /**
   * Configura il filtro di sicurezza.
   *
   * @param http L'oggetto HttpSecurity rappresenta la configurazione di sicurezza
   * @return Il filtro di sicurezza configurato
   * @throws Exception Se si verifica un errore durante la configurazione del
   *                   filtro
   */
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .authorizeHttpRequests(authorize -> {
          authorize.requestMatchers("/auth/**", "/accountsgrf").permitAll(); // Assicurati che il percorso sia corretto
          authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
          authorize.anyRequest().authenticated();
        })
        .httpBasic(Customizer.withDefaults());
    http.exceptionHandling(exception -> exception
        .authenticationEntryPoint(authenticationEntryPoint));
    http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  /**
   * Configura le impostazioni CORS per consentire le richieste dal frontend.
   *
   * @return La configurazione CORS
   */
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // origine frontend
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  /**
   * Configura l'PasswordEncoder per la codifica delle password.
   *
   * @return L'oggetto PasswordEncoder configurato
   */
  @Bean
  public static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
