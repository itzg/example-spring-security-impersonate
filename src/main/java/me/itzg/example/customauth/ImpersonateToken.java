package me.itzg.example.customauth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Geoff Bourne
 * @since Mar 2017
 */
public class ImpersonateToken extends AbstractAuthenticationToken {
    private final String username;

    public ImpersonateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        setAuthenticated(true);
        this.username = username;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }
}
