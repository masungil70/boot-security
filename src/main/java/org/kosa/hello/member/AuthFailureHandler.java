package org.kosa.hello.member;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		
	    String msg = "Invalid Email or Password";
	
	    // exception 관련 메세지 처리
	    if (exception instanceof LockedException) {
        	msg = "계정이 잠겼습니다";
	    } else if (exception instanceof DisabledException) {
        	msg = "DisabledException account";
        } else if(exception instanceof CredentialsExpiredException) {
        	msg = "CredentialsExpiredException account";
        } else if(exception instanceof BadCredentialsException ) {
        	msg = "아이디 또는 비밀번호가 잘못되었습니다";
        }
	
//	    setDefaultFailureUrl("/login/loginForm?error=true&exception=" +URLEncoder.encode(msg, "utf-8"));
//	    super.onAuthenticationFailure(request, response, exception);
	    Map<String, Object> map = new HashMap<>();
	    map.put("status", -1);
	    map.put("statusMessage", msg);
	    response.setContentType("application/json; charset=utf-8");
	    response.getWriter().append(objectMapper.writeValueAsString(map));
	}
}
