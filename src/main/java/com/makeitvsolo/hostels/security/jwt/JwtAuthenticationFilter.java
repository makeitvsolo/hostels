package com.makeitvsolo.hostels.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public final class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final UserDetailsService detailsService;

    public JwtAuthenticationFilter(JwtProvider jwtProvider, UserDetailsService detailsService) {
        this.jwtProvider = jwtProvider;
        this.detailsService = detailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            var token = authHeader.substring(7);

            jwtProvider.decodeSubject(token)
                    .ifPresent(memberName -> {
                        var memberDetails = detailsService.loadUserByUsername(memberName);
                        var authToken = new UsernamePasswordAuthenticationToken(
                                memberDetails,
                                null,
                                memberDetails.getAuthorities()
                        );

                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    });
        }

        filterChain.doFilter(request, response);
    }
}
