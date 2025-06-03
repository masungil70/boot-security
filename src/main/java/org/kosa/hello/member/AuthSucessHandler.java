package org.kosa.hello.member;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class AuthSucessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Override
    public void onAuthenticationSuccess(
    		HttpServletRequest request
    		, HttpServletResponse response
    		, Authentication authentication //로그인한 사용자 정보가 있는 객체 
    		) throws IOException, ServletException {
        
		//로그인 한 마지막 시간 수정 
		memberMapper.updateMemberLastLogin(authentication.getName());
		//로그인 실패시 카운트를 초기화 한다 
		memberMapper.loginCountClear(authentication.getName());

		System.out.println("authentication ->" + authentication);
		
		//성공시 이동할 주소
		//설정(onfig)에서 defaultSuccessUrl("/") 으로 설정한 것 보다 아래의 코드로 설정한 것이 변경되서 동작함 
//        setDefaultTargetUrl("/board/list");
        
//        super.onAuthenticationSuccess(request, response, authentication);
	    Map<String, Object> map = new HashMap<>();
	    map.put("status", 0);
	    map.put("statusMessage", "로그인 성공");
	    response.setContentType("application/json; charset=utf-8");
	    response.getWriter().append(objectMapper.writeValueAsString(map));
		
    }
}
