<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="admin.users.title" /></title>
</head>
<body>


    <div>
        <a href="<c:url value="/admin/users/new/" />"><fmt:message key="users.add" /></a>
    </div>

    <table>
        <tr>
            <th><fmt:message key="user.labels.id" /></th>
            <th><fmt:message key="user.labels.enabled" /></th>
            <th><fmt:message key="user.labels.isAdmin" /></th>
            <th><fmt:message key="user.labels.firstName" /></th>
            <th><fmt:message key="user.labels.lastName" /></th>
            <th><fmt:message key="user.labels.username" /></th>
            <th><fmt:message key="user.labels.userClass" /></th>
            <th>&nbsp;</th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.id}</td>
                <td><c:choose><c:when test="${user.enabled}"><fmt:message key="users.enabled.true.short" /></c:when>
                    <c:otherwise><fmt:message key="users.enabled.false.short" /></c:otherwise></c:choose></td>
                <td><c:choose><c:when test="${user.isAdmin}"><fmt:message key="users.role.admin" /></c:when>
                    <c:otherwise><fmt:message key="users.role.user" /></c:otherwise></c:choose></td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.username}</td>
                <td><fmt:message key="${user.userClass.code}" /></td>
                <td><a href="<c:url value="/admin/users/edit/${user.id}" />"><fmt:message key="actions.edit" /></a>
                    | <a href="<c:url value="/admin/users/delete/${user.id}" />"><fmt:message key="actions.delete" /></a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>



</body>
</html>