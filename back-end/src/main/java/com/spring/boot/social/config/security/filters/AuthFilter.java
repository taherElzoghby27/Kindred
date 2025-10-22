package com.spring.boot.social.config.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.social.config.security.TokenHandler;
import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.dto.ExceptionDto;
import com.spring.boot.social.exceptions.ExpiredTokenException;
import com.spring.boot.social.entity.BundleMessage;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class AuthFilter extends OncePerRequestFilter {
    private TokenHandler tokenHandler;

    @Autowired
    public void setTokenHandler(@Lazy TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            //1- get token from headers
            String token = request.getHeader("Authorization");
            //2- check token
            if (Objects.isNull(token) || !token.startsWith("Bearer ")) {
                response.reset();
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                //throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
            token = token.substring(7);
            //3- validate token
            AccountDto userValidated = null;
            userValidated = tokenHandler.validateToken(token);
            if (Objects.isNull(userValidated) || userValidated.getEnabled() == 0) {
                response.reset();
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
            //4- get roles
//            List<SimpleGrantedAuthority> roles = userValidated.getRoles().stream().map(
//                    role -> new SimpleGrantedAuthority("ROLE_" + role.getRole())
//            ).toList();
            //5- encapsulate user data , used to store details about an authenticated user after authentication is complete.
            //Stored in the SecurityContextHolder to represent the authenticated user for the duration of the request.
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userValidated, userValidated.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
            //6- The SecurityContextHolder stores UsernamePasswordAuthenticationToken to make the authenticated user’s details available throughout the request.
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            //7- Continue with the filter chain
            filterChain.doFilter(request, response);
        } catch (ExpiredTokenException ex) {
            System.out.println("1 " + ex.getMessage());
            // Handle expired token in filter
            handleExpiredToken(response);

        } catch (Exception ex) {
            System.out.println("2 " + ex.getMessage());
            // Handle other auth exceptions
            handleAuthException(response, ex);
        }
    }

    private void handleExpiredToken(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 401 for expired token
        response.setContentType("application/json");

        ExceptionDto errorResponse = new ExceptionDto(HttpStatus.UNAUTHORIZED.value(), new BundleMessage("Token has expired"), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        // Convert Java object to JSON string and write to response
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }

    private void handleAuthException(HttpServletResponse response, Exception ex) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");

        ExceptionDto errorResponse = new ExceptionDto(HttpStatus.FORBIDDEN.value(), new BundleMessage(ex.getMessage()), HttpStatus.FORBIDDEN.getReasonPhrase());

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().contains("auth/login") ||
               request.getRequestURI().contains("auth/sign-up") ||
               request.getRequestURI().contains("/v3/api-docs") ||
               request.getRequestURI().contains("/swagger-ui") ||
               request.getRequestURI().contains("/swagger-ui.html");
    }
}
