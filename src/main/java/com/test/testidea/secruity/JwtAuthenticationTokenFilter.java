package com.test.testidea.secruity;

import com.google.common.base.Strings;
import com.test.testidea.domain.user.User;
import com.test.testidea.domain.user.UserRepository;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JwtAuthenticationTokenFilter
 *
 * @author
 * @date 2018/9/3 15:27
 */

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(
        HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        String header = req.getHeader(JwtUtils.TOKEN_HEADER);
        if (Strings.isNullOrEmpty(header)) {
            header = req.getParameter(JwtUtils.TOKEN_HEADER);
        }

        if (header != null && header.startsWith(JwtUtils.TOKEN_PREFIX)) {
            String authToken = header.substring(JwtUtils.TOKEN_PREFIX.length());
            String username = JwtUtils.getUsernameFromToken(authToken);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userRepository.findByUsername(username);
                if (user != null) {
                    JwtUser userDetails = user.toUserDetails();
                    if (JwtUtils.validateToken(authToken,userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }

        chain.doFilter(req, res);
    }

}
