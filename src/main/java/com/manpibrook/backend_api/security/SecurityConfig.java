package com.manpibrook.backend_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//		http.csrf(customizer -> customizer.disable());
//		http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
//		http.formLogin(Customizer.withDefaults());
//		http.httpBasic(Customizer.withDefaults());
//		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http
			.csrf(customizer -> customizer.disable())
			.authorizeHttpRequests(request -> request.anyRequest().permitAll()) //authenticated()
			.httpBasic(Customizer.withDefaults())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.build();
//		return http.build();
	}
}
//
//package com.manpibrook.backend_api.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer; // IMPORT NÀY LÀ QUAN TRỌNG NHẤT
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//            .csrf(csrf -> csrf.disable()) // Vô hiệu hóa CSRF cho API
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/guest/**").permitAll() // Cho phép các API guest không cần login
//                .anyRequest().authenticated() // Các request khác phải login
//            )
//            .httpBasic(Customizer.withDefaults()) // Sử dụng xác thực Basic (User/Pass)
//            .sessionManagement(session -> 
//                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Không dùng Session (cho JWT)
//            )
//            .build();
//    }
//}