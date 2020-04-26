package ua.axiom.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] GUEST_ENDPOINTS = {"/", "/**", "/login", "/register", "/logout"};
    private static final String[] UNSECURED_ENDPOINTS ={"/userdesc"};
    private static final String[] USER_ENDPOINTS = {"/userpage", "/apiPages/neworder"};
    private static final String[] DRIVER_ENDPOINTS = {"/driverpage"};
    private static final String[] ADMIN_ENDPOINTS = {"/adminpage"};

    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    public SecurityConfig(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            AuthenticationFailureHandler failureHandler
    ) {
        this.userDetailsService = userDetailsService;
        this.failureHandler = failureHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .cors()
                .and()
                    .authorizeRequests()
                    .antMatchers(DRIVER_ENDPOINTS).hasAuthority("DRIVER")
                    .antMatchers(USER_ENDPOINTS).hasAuthority("CLIENT")
                    .antMatchers(ADMIN_ENDPOINTS).hasAuthority("ADMIN")
                    .antMatchers(UNSECURED_ENDPOINTS).permitAll()
                    .antMatchers(GUEST_ENDPOINTS).permitAll()
                    .antMatchers("/public/resources/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginProcessingUrl("/login")
                    .passwordParameter("password")
                    .usernameParameter("login")
                    .successForwardUrl("/api/plrdr")
                    .failureHandler(failureHandler)
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                .and()
                    .csrf()
                    .disable()
                    .userDetailsService(userDetailsService);

    }

    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .usersByUsernameQuery("SELECT username, password, true FROM UUSERS WHERE username=?")
                .passwordEncoder(passwordEncoder);
    }


}
