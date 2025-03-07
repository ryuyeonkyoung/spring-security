package com.laurentiuspilca.ssia.config;

import com.laurentiuspilca.ssia.security.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;

import javax.swing.*;

/*
    WebSecurityConfigurerAdapter 상속의 문제점
    1. configure 메소드를 오버라이딩하기 때문에 유연성이 부족하고, 필요 없는 기능까지 포함됨
    2. 인증과 인가가 혼합 -> 관심사 분리가 어렵고, 유지보수성이 떨어짐
    3. Spring Security는 Filter 기반으로 동작하지만, WebSecurityConfigurerAdapter는 매서드 오버라이딩 방식으로
    보안 설정을 강제함

    Spring Security 5.7+에서 공식적으로 권장하는 방법 -> SecurityFilterChain을 빈으로 등록
    1. 클래스를 상속받지 않고 보안 설정 가능
    2. 인증과 인가 분리 -> 코드 가독성 증가
    3. Spring Security의 내부 동작 방식(Filter 기반)과 자연스럽게 연결됨

*/
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider authenticationProvider; // CustomAuthenticationProvider 주입

    // AuthenticationManagerBuilder에서 CustomAuthenticationProvider 등록
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests()
                .anyRequest().authenticated();
    }
}
