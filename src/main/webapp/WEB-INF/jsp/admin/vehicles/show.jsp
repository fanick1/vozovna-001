<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="admin.vehicles.show.title" /></title>

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
                <th><fmt:message key="vehicle.id" />:</th>
                <td>${vehicle.id}</td>
            </tr>
            <tr>
                <th><fmt:message key="vehicle.brand" />:</th>
                <td>${vehicle.brand}</td>
            </tr>
            <tr>
                <th><fmt:message key="vehicle.type" />:</th>
                <td>${vehicle.type}</td>

            </tr>
            <tr>
                <th><fmt:message key="vehicle.engineType" />:</th>
                <td>${vehicle.engineType}</td>
            </tr>
            <tr>
                <th><fmt:message key="vehicle.yearMade" />:</th>
                <td>${vehicle.yearMade}</td>
            </tr>
            <tr>
                <th><fmt:message key="vehicle.distanceCount" />:</th>
                <td>${vehicle.distanceCount}</td>
            </tr>
            <tr>
                <th><fmt:message key="vehicle.vin" />:</th>
                <td>${vehicle.vin}</td>
            </tr>
            <tr>
                <th><fmt:message key="vehicle.userClass" />:</th>
                <td><fmt:message key="${vehicle.userClass.code}" /></td>
            </tr>
        </table>
            <br>
            <a href="<c:url value="/admin/vehicles" />"><fmt:message key="vehicles.list" /></a>
    </body>
</html>
