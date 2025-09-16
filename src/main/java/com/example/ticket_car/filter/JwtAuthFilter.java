package com.example.ticket_car.filter;

import com.example.ticket_car.Dto.baseResponseDto.BaseResponseDto;
import com.example.ticket_car.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    private ObjectMapper mapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        Long userId = null;
        String jwtToken = null;
        try {
            // Lấy token từ header Authorization: Bearer xxx
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwtToken = authHeader.substring(7);
                System.out.println("jwtToken");
                System.out.println(jwtToken);
                System.out.println(jwtUtil.validateToken(jwtToken));
                if (jwtUtil.validateToken(jwtToken)) {
                    userId = jwtUtil.extractUserId(jwtToken);
                    System.out.println(userId);
                }
            }

            // Nếu token hợp lệ và chưa có authentication
            System.out.println(userId != null && SecurityContextHolder.getContext().getAuthentication() == null);
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                List<GrantedAuthority> authorities =
                        Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userId, null, authorities);

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            filterChain.doFilter(request, response);
        } catch (AccessDeniedException ex) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter()
                    .write( mapper.writeValueAsString(
                            BaseResponseDto.error(HttpServletResponse.SC_FORBIDDEN, ex.getMessage())));
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter()
                    .write( mapper.writeValueAsString(
                            BaseResponseDto.error(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage())));
        }
    }
}
