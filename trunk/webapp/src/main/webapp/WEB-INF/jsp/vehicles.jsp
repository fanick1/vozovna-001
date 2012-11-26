<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="vehicles.title" /></title>
</head>
<body>

    <div>
        <spring:url var="actionUrl" value="/vehicles" />
        <form method="post" action="${actionUrl}" >

            <tr>
                <td><label for="startDate" >Start date:</label></td>
                <td><input type="date" name="startDate" id="startDate" /></td>
            </tr>
            <tr>
                <td><label for="endDate" >End date:</label></td>
                <td><input type="date" name="endDate" id="endDate" /></td>
            </tr>
            <tr>
                <td><input type="submit" name="findVehicles"  /></td>
            </tr>

        </form>

        <table class="grid">
            <colgroup>
                <col span="3" />
                <col style="width: 230px;"/>
            </colgroup>
            <tr>
                <th><fmt:message key="vehicle.id" /></th>
                <th><fmt:message key="vehicle.brand" /></th>
                <th><fmt:message key="vehicle.type" /></th>
                <th></th>
            </tr>
            <c:forEach  items="${vehicles}" var="vehicle">
                <tr>
                    <td class="number">${vehicle.id}</td>
                    <td>${vehicle.brand}</td>
                    <td>${vehicle.type}</td>
                    <td>
                        <a href="<c:url value="/reserveForm?id=${vehicle.id}" />"><fmt:message key="vehicle.reserve" /></a>
                    </td>
                </tr>
            </c:forEach>

        </table><br>


    </div>


</body>
</html>