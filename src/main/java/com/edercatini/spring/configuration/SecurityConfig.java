package com.edercatini.spring.configuration;

import com.edercatini.spring.security.filter.JWTAuthenticationFilter;
import com.edercatini.spring.security.filter.JWTAuthorizationFilter;
import com.edercatini.spring.security.handler.authentication.AuthenticationHandler;
import com.edercatini.spring.security.handler.authorization.AuthorizationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private Environment environment;
    private UserDetailsService userDetailsService;
    private AuthenticationHandler authenticationHandler;
    private AuthorizationHandler authorizationHandler;

    private static final String[] SWAGGER_MATCHERS = {
            "/swagger-ui.html**",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/api-docs**"
    };

    private static final String[] PUBLIC_MATCHERS = {
            "/h2-console/**"
    };

    private static final String[] PUBLIC_MATCHERS_GET = {
            "/product/**",
            "/category/**",
            "/customer/**"
    };

    @Autowired
    public SecurityConfig(
            Environment environment,
            UserDetailsService userDetailsService,
            AuthenticationHandler authenticationHandler,
            AuthorizationHandler authorizationHandler)
    {
        this.environment = environment;
        this.userDetailsService = userDetailsService;
        this.authenticationHandler = authenticationHandler;
        this.authorizationHandler = authorizationHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (Arrays.asList(environment.getActiveProfiles()).contains("dev")) {
            http.headers().frameOptions().disable();
        }

        http.addFilter(new JWTAuthenticationFilter(this.authenticationHandler, authenticationManager()));
        http.addFilter(new JWTAuthorizationFilter(this.authorizationHandler, authenticationManager()));

        http.cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
                .antMatchers(PUBLIC_MATCHERS).permitAll()
                .antMatchers(SWAGGER_MATCHERS).permitAll()
                .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}