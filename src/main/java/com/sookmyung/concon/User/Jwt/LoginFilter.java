package com.sookmyung.concon.User.Jwt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sookmyung.concon.KakaoLogin.dto.KakaoAccount;
import com.sookmyung.concon.KakaoLogin.dto.KakaoUserInfoResponse;
import com.sookmyung.concon.KakaoLogin.service.KakaoService;
import com.sookmyung.concon.User.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, String loginProcessingUrl, String kakaoLoginProcessingUrl) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl(loginProcessingUrl);
        RequestMatcher loginMatcher = new AntPathRequestMatcher(loginProcessingUrl, "POST");
        RequestMatcher kakaoLoginMatcher = new AntPathRequestMatcher(kakaoLoginProcessingUrl, "GET");
        setRequiresAuthenticationRequestMatcher(new OrRequestMatcher(loginMatcher, kakaoLoginMatcher));
    }

    CachedBodyHttpServletRequest cachedBodyHttpServletRequest;


    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter("email");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String email = null;
        String password = null;
        String kakaoCode = null;

        log.info("여기는 실행");
        try {
            cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);
            if (request.getRequestURI().equals("/api/auth/kakao/login")) {
                return handleKakaoLogin(cachedBodyHttpServletRequest);
            } else {
                return handleLogin(cachedBodyHttpServletRequest);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse request", e);
        }
    }

    private Authentication handleKakaoLogin(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null) {
            throw new RuntimeException("카카오 로그인 정보가 부족합니다. ");
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);
        System.out.println("loginfilter");
        return authenticationManager.authenticate(authToken);
    }

    private Authentication handleLogin(HttpServletRequest request) {
        String email = null;
        String password = null;

        log.info("일반 로그인 실행 중");

        try {
            if ("application/json".equals(cachedBodyHttpServletRequest.getContentType())) {

                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> jsonRequest = objectMapper.readValue(cachedBodyHttpServletRequest.getInputStream(), new TypeReference<Map<String, String>>() {});

                email = jsonRequest.get("email");
                password = jsonRequest.get("password");

            } else{
                email = obtainUsername(cachedBodyHttpServletRequest);
                password = obtainPassword(cachedBodyHttpServletRequest);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);
        System.out.println("loginfilter");
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("login success");
        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();
        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(username, role, 10 * 60 * 60 * 1000L);

        System.out.println(token);
        response.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed)

        throws IOException, ServletException {


        BufferedReader reader = cachedBodyHttpServletRequest.getReader();
        String requestBody = reader.lines().collect(Collectors.joining(System.lineSeparator()));

        System.out.println("login failed" + failed.getMessage());
        System.out.println("Request URI: " + cachedBodyHttpServletRequest.getRequestURI());
        System.out.println("Request Method: " + cachedBodyHttpServletRequest.getMethod());

        System.out.println("Request Headers: " + Collections.list(cachedBodyHttpServletRequest.getHeaderNames()).stream()
            .collect(Collectors.toMap(h -> h, cachedBodyHttpServletRequest::getHeader)));

        System.out.println("Request Body: " + requestBody);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + failed.getMessage() + "\"}");

        System.out.println("Response Status: " + response.getStatus());
        System.out.println("Response Content-Type: " + response.getContentType());
    }
}

