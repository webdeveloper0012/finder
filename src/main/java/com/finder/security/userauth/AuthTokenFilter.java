package com.finder.security.userauth;

import com.finder.modules.user.authentication.UserAuth;
import com.finder.modules.user.authentication.UserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private HttpServletRequest httpServletRequest;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authToken = httpServletRequest.getHeader("Finder_Auth");
        if (authToken != null) {
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserAuth userAuth = userAuthService.findByAuthToken(authToken);
                if (userAuth != null && Boolean.TRUE.equals(userAuth.getIsValid())) {
                    String username = userAuth.getUsername();
                    String phoneNumber = userAuth.getPhoneNumber();
                    log.info("User logged in {}", username);

                    // user id is kept as username for spring authentication
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userAuth.getId(), phoneNumber, null);
                    // After setting the Authentication in the context, we specify
                    // that the current user is authenticated. So it passes the
                    // Spring Security Configurations successfully.
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    //calling save will update the last login to current time
                    userAuthService.save(userAuth);
                } else {
                    logUnauthorizedRequests();
                }
            }
        } else {
            logUnauthorizedRequests();
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    public void logUnauthorizedRequests() {
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        String headers = "";
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                headers += headerName + " : " + httpServletRequest.getHeader(headerName) + " || ";
            }
        }
        log.info("User auth failed from IP {} with headers : {}", httpServletRequest.getRemoteAddr(), headers);
    }
}
