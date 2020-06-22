package ro.iteahome.nhs.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.iteahome.nhs.backend.service.clientapp.ClientAppService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

// DEPENDENCIES: -------------------------------------------------------------------------------------------------------

    @Autowired
    private ClientAppService clientAppService;

    @Autowired
    private PasswordEncoder passwordEncoder;

// AUTHENTICATION MANAGEMENT: ------------------------------------------------------------------------------------------

// SECURE VERSION: // TODO: Make this work.
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .userDetailsService(clientAppService)
//                .passwordEncoder(passwordEncoder);
//    }

// TESTING VERSION: // TODO: Delete this when the secure version works.

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin")
                .password("secret")
                .roles("ADMIN");
    }

// AUTHORIZATION MANAGEMENT: -------------------------------------------------------------------------------------------

// SECURE VERSION: // TODO: Make this work.
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .httpBasic()
//                .and()
//                .authorizeRequests()
//                .anyRequest().authenticated(); // TODO: Add conditions based on client app privileges (Admin UI, Medicom)
//    }

// TESTING VERSION: // TODO: Delete this when the secure version works.

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().anyRequest().permitAll()
                .and()
                .csrf().disable()
                .headers().frameOptions().disable();
    }
}
