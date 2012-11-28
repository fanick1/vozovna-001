<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="admin.intervals.show.title" /></title>

    <style>
        .detail {
            width: 500px;
            text-align: center;
            margin: 0 auto;
        }
    </style>
</head>
<body>

<table class="detail">
    <colgroup>
        <col style="width: 200px" />
        <col style="width: 300px" />
    </colgroup>
    <tr>
        <th><fmt:message key="interval.id" />:</th>
        <td><c:out value="${interval.id}"/></td>
    </tr>
    <tr>
        <th><fmt:message key="interval.description" />:</th>
        <td><c:out value="${interval.description}"/></td>
    </tr>
    <tr>
        <th><fmt:message key="interval.inspectionInterval" />:</th>
        <td><c:out value="${interval.inspectionInterval}"/></td>

    </tr>
    <tr>
        <th><fmt:message key="interval.vehicle" />:</th>
        <td>[ID: <c:out value="${interval.vehicle.id}"/>] <br/> <c:out value="${interval.vehicle.brand}"/> <br/> <c:out value="${interval.vehicle.type}"/> <br/>(<c:out value="${interval.vehicle.yearMade}"/>)</td>
    </tr>
</table>
<br>
<a href="<c:url value="/admin/intervals/index" />"><fmt:message key="intervals.list" /></a>
</body>
</html>
