package com.laurentiuspilca.ssia.security;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
// AuthenticationProvider 재정의
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    //  인증 로직 구현 - 인증 요청이 들어오면 실행되는 메서드
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName(); // 요청된 사용자의 아이디
        String password = String.valueOf(authentication.getCredentials()); // 요청된 사용자의 비밀번호

        // 위와 같이 인증 논리를 대체해 UserDetailsService, PasswordEncoder의 역할을 대체할 수도 있다.
        // 기본 구현이 요구사항과 많이 다를 경우 맞춤형 인증 논리를 구현하기 위해 사용한다.
        if ("john".equals(username) && "12345".equals(password)) {
            return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList());
        } else {
            throw new AuthenticationCredentialsNotFoundException("Error!");
        }
    }

    // 지원하는 인증 타입 정의 (5장)
    @Override
    public boolean supports(Class<?> authenticationType) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authenticationType);
    }
}
