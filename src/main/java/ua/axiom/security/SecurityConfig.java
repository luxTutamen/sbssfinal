package ua.axiom.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] GUEST_ENDPOINTS = {"/", "/login", "/register", "/logout"};
    private static final String[] UNSECURED_ENDPOINTS ={"/userdesc"};
    private static final String[] USER_ENDPOINTS = {"/userpage"};
    private static final String[] DRIVER_ENDPOINTS = {"/cabinet"};
    private static final String[] ADMIN_ENDPOINTS = {"/adminpage"};

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .cors()
                .and()
                    .authorizeRequests()
                    .antMatchers(DRIVER_ENDPOINTS).hasAuthority("DRIVER")
                    .antMatchers(USER_ENDPOINTS).hasAuthority("USER")
                    .antMatchers(ADMIN_ENDPOINTS).hasAuthority("ADMIN")
                    .antMatchers(UNSECURED_ENDPOINTS).permitAll()
                    .antMatchers(GUEST_ENDPOINTS).permitAll()
                    //  .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginProcessingUrl("/login")
                    .passwordParameter("password")
                    .usernameParameter("login")
                    .successForwardUrl("/misc/plrdr")
                    .failureForwardUrl("/?error=true")
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                .and()
                    .csrf()
                    .disable()
                    .userDetailsService(userDetailsService);

//                    .apply(new JwtConfigurer(jwtTokenProvider))
//                .and()
//                    .httpBasic()
//                    .realmName(getRealmName())
//                .and()
    }

    @Override
    public void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .usersByUsernameQuery("SELECT username, password, true FROM UUSERS WHERE username=?")
                .passwordEncoder(passwordEncoder);
    }


}
