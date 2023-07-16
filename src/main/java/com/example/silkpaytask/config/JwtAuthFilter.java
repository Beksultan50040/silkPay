package com.example.silkpaytask.config;

import com.example.silkpaytask.exceptions.ApiError;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserAuthProvider userAuthProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        String PREFIX = "Bearer ";
        if (hasText(header) && header.startsWith(PREFIX)) {
            try {

                SecurityContextHolder.getContext().setAuthentication(userAuthProvider.validateToken(header.replace(PREFIX, "")));

            } catch (RuntimeException e) {

                SecurityContextHolder.clearContext();
                throw new ServletException(new ApiError(HttpStatus.UNAUTHORIZED, "USER UNAUTHORIZED", new ArrayList<>()));
            }
        }

        filterChain.doFilter(request, response);

    }
}
