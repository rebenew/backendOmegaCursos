package com.cursos.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity
@Profile("dev")
public class SecurityConfig{

    // Usuario de prueba con rol ADMIN
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        manager.createUser(
                User.withUsername("admin")
                        .password("{noop}admin123")
                        .roles("ADMIN")
                        .build()
        );

        return manager;
    }

    // Configuración de seguridad para permitir acceso por roles
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF desactivado solo para desarrollo
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/courses/**").authenticated() // protege endpoints de cursos
                        .anyRequest().permitAll() // otros endpoints son públicos
                )
                .httpBasic(AbstractHttpConfigurer::disable) // auth básica desactivada
                .formLogin(AbstractHttpConfigurer::disable); //Login por formulario desactivado

        return http.build();
    }
}

