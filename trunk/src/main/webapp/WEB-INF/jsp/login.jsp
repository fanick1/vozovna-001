<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="header.title" /></title>
<link href="<c:url value="/resources/css/style.css" />" type="text/css" rel="stylesheet" />
<style>
.loginForm {
    margin-top: 20px;
    margin-left: 100px;
    border: none;
    text-align: left;
    width: auto;
}
</style>
</head>
<body onload='document.f.j_username.focus();'>

    <%@ include file="/WEB-INF/jspf/header.jspf"%>

    <h1>
        <fmt:message key="header.title" />
    </h1>

    <h3>
        <fmt:message key="login.request" />
    </h3>


    Loginy jsou zatím natvrdo v security-config.xml
        <span style="margin-left: 20px"> <br />&lt;user name="admin" password="admin" authorities="ROLE_USER,
            ROLE_ADMIN" /&gt;
        </span>
        <span style="margin-left: 20px"> <br />&lt;user name="user" password="user" authorities="ROLE_USER" /&gt;
        </span>

    <c:if test="${not empty error}">
        <div class="errorblock">
            <fmt:message key="login.error.retry" />
            <br />
            <fmt:message key="login.error.caused" />
            ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
        </div>
    </c:if>

    <form name='f' action="<c:url value='j_spring_security_check' />" method='POST'>

        <table class="loginForm">
            <tr class="loginForm">
                <td class="loginForm"><fmt:message key="login.username" />:</td>
                <td class="loginForm"><input type='text' name='j_username' value=''></td>
            </tr>
            <tr class="loginForm">
                <td class="loginForm"><fmt:message key="login.password" />:</td>
                <td class="loginForm"><input type='password' name='j_password' /></td>
            </tr>
            <tr class="loginForm">
                <td class="loginForm"><input name="submit" type="submit" value="<fmt:message key="login.submit" />" /></td>
            </tr>
        </table>

    </form>
</body>
</html>