package com.openclassrooms.mddapi.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthenticationFilter is a filter class that handles JWT token authentication.
 * It extends OncePerRequestFilter to ensure that it is executed once per request.
 * It checks the Authorization header of the request for a JWT token
 * and authenticates the user if the token is valid.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDatailsService;

    /**
     * Constructs a new JwtAuthenticationFilter with the specified JwtService and UserDetailsService.
     *
     * @param jwtService         the JwtService to be used for JWT token operations
     * @param userDatailsService the UserDetailsService to be used for user operations
     */
    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDatailsService) {
        this.jwtService = jwtService;
        this.userDatailsService = userDatailsService;
    }

    /**
     * Checks the Authorization header of the request for a JWT token and authenticates the user if the token is valid.
     * It is executed once per request.
     *
     * @param request the HttpServletRequest to be filtered
     * @param response the HttpServletResponse to be filtered
     * @param filterChain the FilterChain to be used for the filtering
     * @throws ServletException if a servlet-specific error occurs during the filtering
     * @throws IOException if an I/O error occurs during the filtering
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authorizationHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDatailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
