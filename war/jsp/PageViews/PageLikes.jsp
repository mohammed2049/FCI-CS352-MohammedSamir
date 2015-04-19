<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.FCI.SWE.Models.PageLike"%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>${it.page_name}</title>
</head>
<body>
	Page Name: ${it.page_name}
	<br/>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<c:forEach items="${it}" var="entry">
   		 User_Email: ${entry.value}<br>
	</c:forEach>
</body>
</html>