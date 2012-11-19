<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="admin.vehicles.show.title" /></title>
    </head>
    <body>
        <h1><fmt:message key="admin.vehicles.show.title" /></h1>
        <a href="<c:url value="/admin/vehicles" />"><fmt:message key="vehicles.list" /></a>
        <table>
            <tr>
                <td><fmt:message key="vehicles.id" /></td>
                <td>${vehicle.id}</td>
            </tr>
            <tr>
                <td><fmt:message key="vehicles.brand" /></td>
                <td>${vehicle.brand}</td>
            </tr>
            <tr>
                <td><fmt:message key="vehicles.type" /></td>
                <td>${vehicle.type}</td>

            </tr>
            <tr>
                <td><fmt:message key="vehicles.engineType" /></td>
                <td>${vehicle.engineType}</td>
            </tr>
            <tr>
                <td><fmt:message key="vehicles.yearMade" /></td>
                <td>${vehicle.yearMade}</td>
            </tr>
            <tr>
                <td><fmt:message key="vehicles.distanceCount" /></td>
                <td>${vehicle.distanceCount}</td>
            </tr>
            <tr>
                <td><fmt:message key="vehicles.vin" /></td>
                <td>${vehicle.vin}</td>
            </tr>
            <tr>
                <td><fmt:message key="vehicles.userClass" /></td>
                <td>${vehicle.userClass}</td>
                <td><form:errors path="userClass" /></td>
            </tr>
        </table>
    </body>
</html>
