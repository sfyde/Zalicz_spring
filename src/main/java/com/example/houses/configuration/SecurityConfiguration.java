package com.example.houses.configuration;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headersConfigurer ->headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers(HttpMethod.GET, "/houses/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/houses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/houses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/houses/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                        .logout(LogoutConfigurer::permitAll)
                        .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .password(passwordEncoder.encode("user"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build());
        return manager;
    }
}
