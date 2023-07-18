package com.fw.fo.system.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Order(2)
@Configuration
@RequiredArgsConstructor
public class HhSecurityConfiguration {

    private final HhLoginAuthSuccessHandler hhLoginAuthSuccessHandler;
    private final HhLoginAuthFailureHandler hhLoginAuthFailureHandler;

    @Value("${server.servlet.session.cookie.name}")
    private String FO_COOKIE_NAME;

    @Bean
    public SecurityFilterChain hhFilterChain(HttpSecurity http) throws Exception {
        http.antMatcher("/hh/**")
                .authorizeRequests()
                .antMatchers("/hh/auth/**").permitAll()
                .anyRequest().hasRole("HH")

                .and()
                .formLogin()
                .loginPage("/hh/auth/login")
                .usernameParameter("loginId")
                .passwordParameter("password")
                .loginProcessingUrl("/hh/auth/login")
                .successHandler(hhLoginAuthSuccessHandler)
                .failureHandler(hhLoginAuthFailureHandler)

                .and()
                .logout()
                .logoutUrl("/hh/auth/logout")
                .logoutSuccessUrl("/hh/auth/login")
                .deleteCookies(FO_COOKIE_NAME)
                .invalidateHttpSession(true)

                .and()
                .csrf().disable();

        return http.build();
    }

}
