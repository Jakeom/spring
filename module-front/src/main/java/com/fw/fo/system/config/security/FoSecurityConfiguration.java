package com.fw.fo.system.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Order(1)
@Configuration
@RequiredArgsConstructor
public class FoSecurityConfiguration {

    private final FoLoginAuthSuccessHandler foLoginAuthSuccessHandler;
    private final FoLoginAuthFailureHandler foLoginAuthFailureHandler;

    @Value("${server.servlet.session.cookie.name}")
    private String FO_COOKIE_NAME;

    @Bean
    public SecurityFilterChain foFilterChain(HttpSecurity http) throws Exception {
        http.antMatcher("/fo/**")
                .authorizeRequests()
                .antMatchers("/fo/auth/**").permitAll()
                .antMatchers("/fo/token/**").permitAll()
                .antMatchers("/fo/common/**").permitAll()
                .anyRequest().hasRole("AP")

                .and()
                .formLogin()
                .loginPage("/fo/auth/login")
                .usernameParameter("loginId")
                .passwordParameter("password")
                .loginProcessingUrl("/fo/auth/login")
                .successHandler(foLoginAuthSuccessHandler)
                .failureHandler(foLoginAuthFailureHandler)

                .and()
                .logout()
                .logoutUrl("/fo/auth/logout")
                .logoutSuccessUrl("/fo/auth/login")
                .deleteCookies(FO_COOKIE_NAME)
                .invalidateHttpSession(true)

                .and()
                .csrf().disable();

        return http.build();
    }

}
