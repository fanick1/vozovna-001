<%@ include file="../jspf/taglibs.jspf"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="header.title" /> - <sitemesh:write property='title' /></title>
<link href="<c:url value="/resources/css/style.css" />" type="text/css" rel="stylesheet" />
<sitemesh:write property='head' />

</head>

<body>
    
    <%@ include file="/WEB-INF/jspf/header.jspf"%>
     
    <%--    <fmt:message key="header.userLogged" />: <sec:authentication property="principal.username" /> --%>
    <%--     (<a href="<c:url value="/j_spring_security_logout" />" ><fmt:message key="header.logout" /></a>) --%>
    <div id="layout-header-wrapper">
        <div id="layout-header">    
            
            
            <div id="menu">
                <ul>
                    <li class="menu"><a href="<c:url value="/admin/users" />"><fmt:message key="links.admin.users" /></a></li>
                    <li class="menu"><a href="<c:url value="/admin/vehicles" />"><fmt:message key="links.admin.vehicles" /></a></li>
                    <li class="menu"><a href="<c:url value="/admin/drives" />"><fmt:message key="links.admin.drives" /></a></li>
                    <li class="menu"><a href="<c:url value="/admin/intervals/index" />"><fmt:message
                                key="links.admin.serviceIntervals" /></a></li>
                    <li class="menu"><a href="<c:url value="/j_spring_security_logout" />"><fmt:message key="links.logout" /></a></li>
                </ul>
            </div>
        </div>
    </div>
    <div id="layout-body">
        <h1>
            <sitemesh:write property='title' />
        </h1>

        <c:if test="${not empty error}">
            <div class="errorblock">
                <fmt:message key="${error}" />
            </div>
            <%
                synchronized (session) {
                    session.removeAttribute("error");
                }
            %>
        </c:if>

        <c:if test="${not empty message}">
            <div class="messageblock">
                <fmt:message key="${message}" />
            </div>
            <%
                synchronized (session) {
                    session.removeAttribute("message");
                }
            %>
        </c:if>

        <sitemesh:write property='body' />
    </div>
</body>
</html>