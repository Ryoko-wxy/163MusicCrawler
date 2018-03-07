<%@ page language="java"  pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>主页过度页面,跳转到song</title>
</head>
<body>

<%-- c：redirt  重定向，带参数--%>
	<c:redirect url="/Index">
		<c:param name="action" value="showMusicList"></c:param>
		</c:redirect>
</body>
</html>