<%@ include file="../jspf/taglibs.jspf"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><sitemesh:write property='title' /></title>
    <link href="<c:url value="/resources/css/style.css" />" type="text/css" rel="stylesheet" />
    <sitemesh:write property='head' />
<style>
</style>
</head>

<body>
    <%@ include file="/WEB-INF/jspf/header.jspf"%>

    <%--    <fmt:message key="header.userLogged" />: <sec:authentication property="principal.username" /> --%>
    <%--     (<a href="<c:url value="/j_spring_security_logout" />" ><fmt:message key="header.logout" /></a>) --%>

    <ul id="menu">
        <li class="menu"><a href="<c:url value="/admin/users" />"><fmt:message key="links.admin.users" /></a></li>
        <li class="menu"><a href="<c:url value="/admin/vehicles" />"><fmt:message key="links.admin.vehicles" /></a></li>
        <li class="menu"><a href="<c:url value="/admin/drives" />"><fmt:message key="links.admin.drives" /></a></li>
        <li class="menu"><a href="<c:url value="/admin/intervals" />"><fmt:message key="links.admin.serviceIntervals" /></a></li>
        <li class="menu"><a href="<c:url value="j_spring_security_logout" />"><fmt:message key="links.logout" /></a></li>
    </ul>
    <br/>

    <sitemesh:write property='body' />
</body>
</html>