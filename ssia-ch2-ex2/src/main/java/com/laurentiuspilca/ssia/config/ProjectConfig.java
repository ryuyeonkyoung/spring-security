package com.laurentiuspilca.ssia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/* 기본 구성 재정의
UserDetailService를 재정의 할 때는 PasswordEncoder도 같이 선언해야 함 */
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    // UserDetailService 재정의
    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        // 편의상 UserDetailsService를 직접 구현하지 않고 InMemoryUserDetailsManager 사용
        var userDetailsService = new InMemoryUserDetailsManager();

        var user = User.withUsername("john")
                .password("12345")
                .authorities("read")
                .build();

        userDetailsService.createUser(user);

        return userDetailsService;
    }

    // passwordEncoder 재정의
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // 엔드포인트 권한 부여 재정의 - WebSecurityConfigurerAdapter를 확장하여 configure 재정의
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests().anyRequest().authenticated(); // permitAll() : 인증 없이 요청
    }
}
