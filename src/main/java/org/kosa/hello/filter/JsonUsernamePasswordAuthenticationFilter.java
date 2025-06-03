package org.kosa.hello.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	private static final String DEFAULT_LOGIN_REQUEST_URL = "/login";  // /login/oauth2/ + ????? 로 오는 요청을 처리할 것이다
	private static final String HTTP_METHOD = "POST";    //HTTP 메서드의 방식은 POST 이다.
	private static final String CONTENT_TYPE = "application/json";//json 타입의 데이터로만 로그인을 진행한다.
	private final ObjectMapper objectMapper;
	private static final String USERNAME_KEY="email";
	private static final String PASSWORD_KEY="password";


	private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
			new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD); //=>   /login 의 요청에, POST로 온 요청에 매칭

	public JsonUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER, authenticationManager);
		this.objectMapper = new ObjectMapper();
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		log.info("ContentType = {}", request.getContentType());
		if(request.getContentType() == null || !request.getContentType().startsWith(CONTENT_TYPE)  ) {
			throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
		}

		String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

		Map<String, String> usernamePasswordMap = objectMapper.readValue(messageBody, Map.class);

		String username = usernamePasswordMap.get(USERNAME_KEY);
		String password = usernamePasswordMap.get(PASSWORD_KEY);

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);//principal 과 credentials 전달

		return super.getAuthenticationManager().authenticate(authRequest);
	
	}
	
}
