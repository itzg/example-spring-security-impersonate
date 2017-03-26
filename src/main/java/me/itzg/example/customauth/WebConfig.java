package me.itzg.example.customauth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Geoff Bourne
 * @since Mar 2017
 */
@Configuration
public class WebConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new ImpersonateFilter(), UsernamePasswordAuthenticationFilter.class);

        http.antMatcher("/api/**")
                .authorizeRequests()
                .anyRequest().authenticated();
    }
}
