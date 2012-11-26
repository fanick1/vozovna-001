<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="admin.vehicles.title" /></title>

</head>
<body>
<table class="grid">
    <colgroup>
        <col style="width: 50px;" />
        <col />
        <col />
        <col />
        <col style="width: 200px;"/>
    </colgroup>
    <tr>
        <th><fmt:message key="vehicle.id" /></th>
        <th><fmt:message key="vehicle.brand" /></th>
        <th><fmt:message key="vehicle.type" /></th>
        <th><fmt:message key="vehicle.engineType" /></th>
        <th></th>
    </tr>
    <c:forEach  items="${vehicles}" var="vehicle">
        <tr>
            <td class="number">${vehicle.id}</td>
            <td>${vehicle.brand}</td>
            <td>${vehicle.type}</td>
            <td>${vehicle.engineType}</td>
            <td>
                <a href="<c:url value="/admin/intervals/edit?id=${intervalDTO.id}&vehicleId=${vehicle.id}" />"><fmt:message key="actions.select" /></a>
            </td>
        </tr>
    </c:forEach>

</table><br>





</body>
</html>