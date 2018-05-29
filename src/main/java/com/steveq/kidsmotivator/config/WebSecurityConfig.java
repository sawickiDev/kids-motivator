package com.steveq.kidsmotivator.config;

import com.steveq.kidsmotivator.app.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Password Encoder only for POSTGRESQL
        // auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
        auth.userDetailsService(userService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String[] resources = new String[]{
                "/css/**"
        };

        http
            .authorizeRequests()
                .antMatchers(resources).permitAll()
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .permitAll()
                .loginPage("/km-login")
                .loginProcessingUrl("/km-auth")
                .failureUrl("/km-login?error=true")
                .defaultSuccessUrl("/dashboard", true)
            .and()
            .logout()
                .permitAll()
                .logoutUrl("/logout-page")
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID");
    }
}
