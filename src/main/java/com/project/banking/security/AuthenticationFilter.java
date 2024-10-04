package com.project.banking.security;

import com.project.banking.dto.response.security.JwtClaims;
import com.project.banking.entity.User;
import com.project.banking.service.JwtService;
import com.project.banking.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {
    final String AUTH_HEADER = "Authorization";
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    )
            throws ServletException, IOException
    {
        try {
            String bearerToken = request.getHeader(AUTH_HEADER);
            if (bearerToken != null && jwtService.verifyToken(bearerToken)) {
                JwtClaims decodeJwt = jwtService.claimToken(bearerToken);
                User accountBySub = userService.findId(decodeJwt.getUserId());
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        accountBySub.getUsername(),
                        null,
                        accountBySub.getAuthorities()
                );
                token.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
        } finally {
            filterChain.doFilter(request, response);
        }
    }
}
