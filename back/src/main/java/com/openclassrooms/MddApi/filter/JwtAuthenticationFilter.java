package com.openclassrooms.MddApi.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.openclassrooms.MddApi.services.JwtService;
import com.openclassrooms.MddApi.services.UserService;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @SuppressWarnings("null")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // String currentUrl = ((HttpServletRequest)
        // request).getRequestURL().toString();

        // if (currentUrl.contains("/api/auth/profile") ||
        // currentUrl.contains("/api/user")
        // || currentUrl.contains("/api/rental") || currentUrl.contains("/api/message"))
        // {

        // String bearerToken = request.getHeader("Authorization");
        // if (bearerToken == null && !bearerToken.startsWith("Bearer ")) {
        // throw new IOException("Bearer token not found");
        // }

        String jwt = header.substring(7);
        Claims claims = jwtService.getTokenClaims(jwt);

        if (claims == null) {
            throw new IOException("Token not valid");
        }

        String email = claims.getSubject();
        var userDetails = userService.loadUserByUsername(email);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // }

        filterChain.doFilter(request, response);
    }
}
