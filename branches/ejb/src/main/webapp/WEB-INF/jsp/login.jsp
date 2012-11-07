<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/heading.jspf" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Page</title>
<link href="<c:url value="/resources/css/style.css" />" type="text/css" rel="stylesheet" />
<style>

body {
  font-size: small;
  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
  margin: 0;
  padding: 0;
  
  margin-left: 200px;
  margin-right:150px;
  padding: 20px;
  border-left: 1px solid #eee;
  border-right: 1px solid #eee;
}

h1 {
text-align:center;
padding: 20px;
font-size: 50px;

}

.errorblock {
    color: #ff0000;
    background-color: #ffEEEE;
    border: 3px solid #ff0000;
    padding: 8px;
    margin: 16px;
}

</style>
</head>
<body onload='document.f.j_username.focus();'>

    <%@ include file="/WEB-INF/jspf/menu.jspf" %>
    
    <h3><fmt:message key="login.request" /></h3>
    
    
    <p>Loginy uživatelů jsou zatím natvrdo v security-config.xml</p>
    <textarea style="width:80%" rows="2">
        <user name="admin" password="pass" authorities="ROLE_USER, ROLE_ADMIN" />
        <user name="user" password="pass" authorities="ROLE_USER" /></textarea>
     
 
    <c:if test="${not empty error}">
        <div class="errorblock">
            <fmt:message key="login.error.retry" /><br />
            <fmt:message key="login.error.caused" />
            ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
        </div>
    </c:if>
 
    <form name='f' action="<c:url value='j_spring_security_check' />"
        method='POST'>
 
        <table>
            <tr>
                <td>User:</td>
                <td><input type='text' name='j_username' value=''>
                </td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type='password' name='j_password' />
                </td>
            </tr>
            <tr>
                <td colspan='2'><input name="submit" type="submit"
                    value="submit" />
                </td>
            </tr>
            <tr>
                <td colspan='2'><input name="reset" type="reset" />
                </td>
            </tr>
        </table>
 
    </form>
</body>
</html>