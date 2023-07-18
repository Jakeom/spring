package com.fw.fo.system.config;

import com.fw.core.mapper.db1.fo.FoSimpleLoginMapper;
import com.fw.fo.login.service.FoSimpleLoginService;
import com.fw.fo.system.config.security.AES256Provider;
import com.fw.fo.system.config.security.FoAuthenticationProvider;
import com.fw.fo.system.config.security.FoLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class FoWebSecurityConfig {

    private final FoLoginService foLoginService;

    private final String[] WEB_SECURITY_IGNORE_URL = {
            "/error",
            "/error/**",
            "/m/**",
            "/static/**"
    };

    private final AES256Provider aes256Provider;
    private final FoSimpleLoginService foSimpleLoginService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.antMatcher("/").authorizeRequests().anyRequest().permitAll();
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(WEB_SECURITY_IGNORE_URL);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FoAuthenticationProvider authenticationProvider(){
        return new FoAuthenticationProvider(foLoginService, bCryptPasswordEncoder(), aes256Provider, foSimpleLoginService);
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
        return authenticationManagerBuilder.build();
    }

}