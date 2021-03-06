package com.steveq.kidsmotivator.config;

import com.steveq.kidsmotivator.app.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    @Autowired
    public WebSecurityConfig(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(11);

        //only for mysql
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String[] resources = new String[]{
                "/css/**"
        };

        http
            .authorizeRequests()
                .antMatchers(resources).permitAll()
                .antMatchers("/dashboard").hasAuthority("PARENT")
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .permitAll()
                .loginPage("/km-login")
                .loginProcessingUrl("/km-auth")
                .failureUrl("/km-login?error=true")
                .successHandler(new CustomAuthenticationSuccessHandler())
            .and()
            .logout()
                .permitAll()
                .logoutUrl("/logout-page")
                .logoutSuccessUrl("/km-login")
                .deleteCookies("JSESSIONID");
    }
}
