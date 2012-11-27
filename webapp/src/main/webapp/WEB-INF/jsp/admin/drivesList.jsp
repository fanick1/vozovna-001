<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="admin.drives.title" /></title>
</head>
<body>

    <div>
        <a href="<c:url value="/admin/drives/new/" />"><fmt:message key="drives.add" /></a>
    </div>

    <table class="grid">
    <colgroup>
            <col style="width: 40px;"/>
            <col />
            <col />
            <col />
            <col style="width: 60px;"/>
            <col style="width: 60px;"/>
            <col />
            <col style="width: 120px;"/>
        </colgroup>
        <tr>
            <th><fmt:message key="drive.id" /></th>
            <th><fmt:message key="drive.dateFrom" /></th>
            <th><fmt:message key="drive.dateTo" /></th>
            <th><fmt:message key="drive.state" /></th>
            <th><fmt:message key="drive.distance" /></th>
            <th><fmt:message key="drive.user" /></th>
            <th><fmt:message key="drive.vehicle" /></th>
            <th>&nbsp;</th>
        </tr>
        <c:forEach items="${drives}" var="drive">
            <tr>
                <td>${drive.id}</td>
                <td><joda:format pattern="d.M.yyyy" value="${drive.dateFrom}" /></td>
                <td><joda:format pattern="d.M.yyyy" value="${drive.dateTo}" /></td>
                <td><fmt:message key="${drive.state.code}" /></td>
                <td>${drive.distance}</td>
                <td><c:if test="${not empty drive.user != null}">
                [${drive.user.id}] ${drive.user.firstName} ${drive.user.lastName} </c:if></td>
                <td><c:if test="${not empty drive.vehicle}">
                [${drive.vehicle.id}] ${drive.vehicle.brand} ${drive.vehicle.type} ${drive.vehicle.vin} </c:if></td>
                <td><a href="<c:url value="/admin/drives/edit/${drive.id}" />"><fmt:message key="actions.edit" /></a>
                    | <a href="<c:url value="/admin/drives/delete/${drive.id}" />" class="remove"><fmt:message key="actions.delete" /></a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>


</body>
</html>