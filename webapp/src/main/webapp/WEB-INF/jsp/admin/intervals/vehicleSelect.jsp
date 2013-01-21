<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="admin.interval.vehicleSelect.title" /></title>

</head>
<body>
    <table class="grid">
        <colgroup>
            <col style="width: 50px;" />
            <col style="width: 110px;" />
            <col />
            <col />
            <col />
            <col style="width: 200px;"/>
        </colgroup>
        <tr>
            <th><fmt:message key="vehicle.id" /></th>
            <th><fmt:message key="vehicle.registrationPlate" /></th>
            <th><fmt:message key="vehicle.brand" /></th>
            <th><fmt:message key="vehicle.type" /></th>
            <th><fmt:message key="vehicle.engineType" /></th>
            <th></th>
        </tr>
        <c:forEach  items="${vehicles}" var="vehicle">
            <tr>
                <td class="number">${vehicle.id}</td>
                <td><c:out value="${vehicle.registrationPlate}"/></td>
                <td><c:out value="${vehicle.brand}"/></td>
                <td><c:out value="${vehicle.type}"/></td>
                <td><c:out value="${vehicle.engineType}"/></td>
                <td>
                    <a href="<c:url value="/admin/intervals/add?vehicleId=${vehicle.id}" />"><fmt:message key="actions.select" /></a>
                </td>
            </tr>
        </c:forEach>

    </table><br>





</body>
</html>