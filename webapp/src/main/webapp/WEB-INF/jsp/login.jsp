<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="header.title" /></title>
<link href="<c:url value="/resources/css/style.css" />" type="text/css" rel="stylesheet" />
<style>
.loginForm {
    /*margin-top: 20px;
    margin-left: 100px;*/
    border: none;
    text-align: left;
    width: auto;
}
    .detail {
        width: 400px;
        text-align: center;
        margin: 0 auto;
        margin-top: 20px;
    }
    input[type=text], input[type=password], select, textarea  {
        width: 150px;
    }
</style>

</head>
<body onload='document.f.j_username.focus();'>
    <%@ include file="/WEB-INF/jspf/header.jspf"%>
    
    <div id="layout-body">
        
        <h1>
            <fmt:message key="header.title" />
        </h1>

        <h3>
            <fmt:message key="login.request" />
        </h3>

        <a href="<c:url value='login/generate' />">Vygenerovat testovaci loginy admin-admin a user-user</a>


        <c:if test="${not empty error}">
            <div class="errorblock">
                <fmt:message key="login.error.retry" />
                <br />
                <fmt:message key="login.error.caused" />
                ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
            </div>
        </c:if>

        <form name='f' action="<c:url value='j_spring_security_check' />" method='POST'>
            <div class="detail">
                
                <table class="loginForm">
                    <colgroup>
                        <col style="width: 200px;"/>
                        <col/>
                    </colgroup>
                    <tr class="loginForm">
                        <th class="loginForm"><fmt:message key="login.username" />:</th>
                        <td class="loginForm"><input type='text' name='j_username' value=''></td>
                    </tr>
                    <tr class="loginForm">
                        <th class="loginForm"><fmt:message key="login.password" />:</th>
                        <td class="loginForm"><input type='password' name='j_password' /></td>
                    </tr>
                    <tr class="loginForm">
                        <th></th>
                        <td class="loginForm"><input name="submit" type="submit" value="<fmt:message key="login.submit" />" /></td>
                    </tr>
                </table>
            </div>

        </form>

    </div>
</body>
</html>