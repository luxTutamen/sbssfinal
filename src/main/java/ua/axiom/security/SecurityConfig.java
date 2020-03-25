package ua.axiom.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import ua.axiom.security.jwt.JwtConfigurer;
import ua.axiom.security.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] GUEST_ENDPOINTS = {"/", "/login", "/register"};
    private static final String[] USER_ENDPOINTS = {"/", "/login", "/register", "/userpage", "/logout"};
    private static final String[] DRIVER_ENDPOINTS = {"/", "/login", "/register", "/cabinet", "/logout"};
    private static final String[] ADMIN_ENDPOINTS = {"/", "/login", "/adminpage", "/cabinet", "/logout"};

    @Autowired
    private JwtTokenProvider jwtTokenProvide;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .httpBasic().disable()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                    .antMatchers(ADMIN_ENDPOINTS).hasRole("ADMIN")
                    .antMatchers(DRIVER_ENDPOINTS).hasRole("DRIVER")
                    .antMatchers(USER_ENDPOINTS).hasRole("USER")
                    .antMatchers(GUEST_ENDPOINTS).permitAll()
                    .anyRequest().authenticated()
                .and()
                    .apply(new JwtConfigurer(jwtTokenProvide));
    }
}
