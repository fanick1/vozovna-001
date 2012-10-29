<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body> 
    <h3>Přihlášený uživatel : ${username}</h3> 
 
    <a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
 
</body>
</html>