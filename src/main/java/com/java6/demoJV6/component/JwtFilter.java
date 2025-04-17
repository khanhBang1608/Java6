package com.java6.demoJV6.component;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;

    // Constructor để inject JwtUtil
    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            res.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        String uri = req.getRequestURI();
        String token = null;

        // Lấy token từ Header Authorization
        String authHeader = req.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        try {
            if (token != null) {
                Claims claims = jwtUtil.validateToken(token);
                int role = (int) claims.get("role");

                // Phân quyền
                if (uri.startsWith("/admin") && role != 0) {
                    res.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập");
                    return;
                } else if (uri.startsWith("/user") && role != 1) {
                    res.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập");
                    return;
                }
            } else if (uri.startsWith("/admin") || uri.startsWith("/user")) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Thiếu token xác thực");
                return;
            }

            chain.doFilter(request, response);

        } catch (Exception e) {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token không hợp lệ");
        }
    }
}
