package com.example.security.config;

import com.example.security.utils.JwtUtil;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsServiceImpl;
     @Autowired
     JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, java.io.IOException {
        final String header = request.getHeader("Authorization");
        String token=null;
        String username=null;
        String path =request.getRequestURI();
        if(header!=null&&header.startsWith("Bearer "))
        {
            token=header.substring(7);
            username=JwtUtil.extractUsername(token);
        }
        if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
            if(JwtUtil.validateToken(token))
            {
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null, Collections.EMPTY_LIST);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }

//        if(!path.equals("/api/auth/login") && !path.equals("/api/auth/register"))
//        {
//            if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
//                String tokens = header.substring(7);
//                try {
//                    Jws<Claims> claimsJws = Jwts.parserBuilder()
//                            .setSigningKey(JwtUtil.SECRET_KEY)
//                            .build()
//                            .parseClaimsJws(token);
//
//                    Claims claims = claimsJws.getBody();
//                    String username = claims.getSubject();
//
//                    UsernamePasswordAuthenticationToken authenticationToken =
//                            new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
//                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                } catch (Exception e) {
//                    // Token validation failed
//                }
//            }
//
//        }

        filterChain.doFilter(request, response);
    }
}