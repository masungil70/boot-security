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
<!-- 오그인시 오류 메시지 출력 -->

<form action="/login" method="post">
	<%-- csrf 토큰 설정 --%>
	<sec:csrfInput/>
	아이디 : <input type="text" name="email"/><br/>
	비밀번호 : <input type="password" name="password"/><br/>
	<input type="submit" value="로그인">
</form>
<script>
	msg = "${error ? exception : ''}";
	if (msg !== "")  {
		alert(msg);
	}
</script>
</body>
</html>