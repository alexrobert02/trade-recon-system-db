package com.onuryilmazer.tradereconsystemdb.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@Profile("h2")
public class SecurityJpaConfig {

    private final JpaUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers("/webjars/**", "/resources/**", "/h2-console/**").permitAll()
                        .requestMatchers("/demo/admin/**").hasRole("ADMIN")
                        //.requestMatchers("/demo/guest/**").hasRole("GUEST")
                        .anyRequest().permitAll()
                )
                .userDetailsService(userDetailsService)
                .formLogin(Customizer.withDefaults())
                .exceptionHandling(ex -> ex.accessDeniedPage("/access_denied"))
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
