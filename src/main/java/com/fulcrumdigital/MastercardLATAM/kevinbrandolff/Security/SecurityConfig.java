package com.fulcrumdigital.MastercardLATAM.kevinbrandolff.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Profile("kevin")
@Order(2)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationService authentication;

    @Override
    protected void configure( HttpSecurity http ) throws Exception {
        http.headers().frameOptions().sameOrigin().and().cors().and()
                .exceptionHandling().authenticationEntryPoint( new CustomAuthenticationEntryPoint() ).and()
                .csrf().disable().authorizeRequests()
                .antMatchers( HttpMethod.POST, "/login" ).permitAll()
                .antMatchers( HttpMethod.POST, "/kevin/user/" ).permitAll()
                .antMatchers( HttpMethod.GET, "/kevin/employee/{id}" )
                    .hasRole("ADMIN")
                .antMatchers( HttpMethod.GET, "/kevin/project/{id}" )
                    .hasRole("USER")
                .anyRequest().authenticated().and()
                .addFilterBefore( new JWTLoginFilter( "/login", authenticationManager() ),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore( new JWTAuthenticationFilter(authentication, new CustomAccessDeniedHandler()), UsernamePasswordAuthenticationFilter.class );
    }

    @Override
    protected void configure( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService(authentication).passwordEncoder(new BCryptPasswordEncoder());
    }

}
