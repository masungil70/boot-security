<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 로그인시 오류 메시지 출력 -->

<form id="loginForm" action="<c:url value='/login'/>?${_csrf.parameterName}=${_csrf.token}" method="post">
	<%-- csrf 토큰 설정 --%>
	<sec:csrfInput/>
	아이디 : <input type="text" name="email"/><br/>
	비밀번호 : <input type="password" name="password"/><br/>
	<input type="submit" value="로그인">
</form>
<script type="text/javascript" src="<c:url value='/resources/js/common.js'/>"></script>
<script>
	msg = "${error ? exception : ''}";
	if (msg !== "")  {
		alert(msg);
	}
	
	const loginForm = document.getElementById("loginForm");
	loginForm.addEventListener("submit", e => {
		//서버에 form data를 전송하지 않는다 
		e.preventDefault();
		myFetch(loginForm.action, "loginForm", json => {
			switch(json.status) {
			case 0:
				//성공
				alert("로그인 성공 했음 ");
				location = "list";
				break;
			default:
				alert(json.statusMessage);
			}
		});
	});
	
</script>
</body>
</html>