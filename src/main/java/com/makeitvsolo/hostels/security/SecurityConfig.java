package com.makeitvsolo.hostels.security;

import com.makeitvsolo.hostels.repository.MemberRepository;
import com.makeitvsolo.hostels.security.jwt.BaseJwtProvider;
import com.makeitvsolo.hostels.security.jwt.JwtAuthenticationFilter;
import com.makeitvsolo.hostels.security.jwt.UnauthorizedHandler;
import com.makeitvsolo.hostels.service.security.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final MemberRepository memberRepository;
    private final JwtAuthenticationFilter authFilter;
    private final UnauthorizedHandler unauthorizedHandler;

    public SecurityConfig(
            MemberRepository memberRepository,
            JwtAuthenticationFilter authFilter,
            UnauthorizedHandler unauthorizedHandler
    ) {
        this.memberRepository = memberRepository;
        this.authFilter = authFilter;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                       .formLogin(FormLoginConfigurer::disable)
                       .cors(CorsConfigurer::disable)
                       .csrf(CsrfConfigurer::disable)
                       .sessionManagement(
                               policy -> {
                                   policy.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                               }
                       )
                       .securityMatcher("/api/v1/**")
                       .authorizeHttpRequests(
                               registry -> {
                                   registry.requestMatchers("api/v1/access/**").permitAll();
                                   registry.anyRequest().authenticated();
                               }
                       )
                       .exceptionHandling(
                               exception -> {
                                   exception.authenticationEntryPoint(unauthorizedHandler);
                               }
                       )
                       .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                       .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return name -> memberRepository.findByName(name)
                               .map(member -> new MemberPrincipal(
                                       member.getId(),
                                       member.getName(),
                                       member.getPassword()
                               ))
                               .orElseThrow(() -> new UsernameNotFoundException(name));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtProvider jwtProvider() {
        return new BaseJwtProvider();
    }
}
