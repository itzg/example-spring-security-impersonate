package me.itzg.example.customauth;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Geoff Bourne
 * @since Mar 2017
 */
public class ImpersonateFilter extends GenericFilterBean {
    public static final String ATTR_IMPERSONATED = ImpersonateFilter.class.getName() + ".IMPERSONATED";
    public static final String HEADER_IMPERSONATE_USERNAME = "X-Impersonate";
    public static final String HEADER_IMPERSONATE_ROLE = "X-Impersonate-Role";
    public static final String DEFAULT_ROLE = "ROLE_USER";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest) {
            final HttpServletRequest req = (HttpServletRequest) servletRequest;

            final String value = req.getHeader(HEADER_IMPERSONATE_USERNAME);
            if (StringUtils.hasText(value)) {
                final String username = value.trim();

                final String role = Optional.ofNullable(req.getHeader(HEADER_IMPERSONATE_ROLE)).orElse(DEFAULT_ROLE);

                final ImpersonateToken auth = new ImpersonateToken(username, Collections.singletonList(new SimpleGrantedAuthority(role)));

                req.setAttribute(ATTR_IMPERSONATED, true);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(servletRequest, servletResponse);
    }
}
