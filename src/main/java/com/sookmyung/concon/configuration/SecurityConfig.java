package com.sookmyung.concon.configuration;

import com.sookmyung.concon.User.Jwt.JwtFilter;
import com.sookmyung.concon.User.Jwt.JwtUtil;
import com.sookmyung.concon.User.Jwt.LoginFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration,
                          JwtUtil jwtUtil, CorsConfigurationSource corsConfigurationSource) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public static AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors((auth) -> auth.configurationSource(corsConfigurationSource));

        http
                .csrf((auth) -> auth.disable());

        http
                .formLogin((auth) -> auth.disable());

        http
                .httpBasic((auth) -> auth.disable());


        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/api/auth/**", "/swagger-ui/**", "/api-docs/**",
                                "/swagger-ui.html", "/api/test", "/ping", "/api/import-csv",
                                "/kakao", "/api/kakao/*", "/loginForm").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/users/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated());



        // 현재 disable 되어있는 UsernamePasswordAuthenticationFilter 대신에 filter를 넣어줄 것이기 때문에 addFilterAt 사용
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration),
                        jwtUtil, "/api/auth/login"),
                        UsernamePasswordAuthenticationFilter.class);
        http
                .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);


        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .clearAuthentication(true)
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        }));

        return http.build();
    }
}