package com.abn.recipe.security.config;

import com.abn.recipe.repository.user.UserEntity;
import com.abn.recipe.security.utility.CryptoToken;
import com.abn.recipe.security.service.JwtUserDetailsService;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Jwt request filter class
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */
@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    public static final int BEGIN_INDEX = 7;
    public static final String BEARER_ = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    /**
     * @param request - Request
     * @param response - Response
     * @param chain - Chain
     * @throws ServletException - Service Exception
     * @throws IOException - IO Exception
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader(AUTHORIZATION);

        String username = null;
        String jwtToken;

        if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith(BEARER_)) {
            jwtToken = requestTokenHeader.substring(BEGIN_INDEX);
            username = CryptoToken.getUsername(jwtToken);
        }

        if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {

            UserEntity userEntity = this.jwtUserDetailsService.loadUserEntityByUsername(username);
            if (userEntity != null && userEntity.getTokenExpireDate().isAfter(LocalDateTime.now())) {

                UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

}