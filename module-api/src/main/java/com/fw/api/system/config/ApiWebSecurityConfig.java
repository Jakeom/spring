package com.fw.api.system.config;

import com.fw.api.system.filter.JwtAuthenticationFilter;
import com.fw.api.system.security.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * 스프링 시큐리티 설정
 * @author SENGJOON PAIK
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApiWebSecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().disable();          // 교차 출처 리소스 공유 설정 (CORS)
        http.csrf().disable();          // 사이트간 요청 위조 설정 (CSRF)

        http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/api/v1/member/login").permitAll()
                .antMatchers("/api/v1/member/logout").permitAll()
                .antMatchers("/api/v1/sign/**").permitAll()
                .antMatchers("/api/v1/test/**").permitAll()
                .antMatchers("/api/v1/support/**").permitAll()
                .antMatchers("/api/v1/cert/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                //.anyRequest().authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin().disable().headers().frameOptions().disable();

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
