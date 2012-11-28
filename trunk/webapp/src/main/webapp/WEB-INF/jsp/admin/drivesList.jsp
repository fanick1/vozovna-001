<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="admin.drives.title" /></title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script> 
    <script type="text/javascript">
        $("a.remove").live("click",function(event){
            event.stopPropagation();
            if(!confirm("<fmt:message key="admin.drives.confirm.delete" />")) {
                event.preventDefault();
            }       
         });
    </script>
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
                <td><c:out value="${drive.id}"/></td>
                <td><joda:format pattern="d.M.yyyy" value="${drive.dateFrom}" /></td>
                <td><joda:format pattern="d.M.yyyy" value="${drive.dateTo}" /></td>
                <td><fmt:message key="${drive.state.code}" /></td>
                <td><c:out value="${drive.distance}"/></td>
                <td><c:if test="${not empty drive.user != null}">
                [<c:out value="${drive.user.id}"/>] <c:out value="${drive.user.firstName}"/> <c:out value="${drive.user.lastName}"/> </c:if></td>
                <td><c:if test="${not empty drive.vehicle}">
                [<c:out value="${drive.vehicle.id}"/>] <c:out value="${drive.vehicle.brand}"/> <c:out value="${drive.vehicle.type}"/> <c:out value="${drive.vehicle.vin}"/> </c:if></td>
                <td><a href="<c:url value="/admin/drives/edit/${drive.id}" />"><fmt:message key="actions.edit" /></a>
                    | <a href="<c:url value="/admin/drives/delete/${drive.id}" />" class="remove"><fmt:message key="actions.delete" /></a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>


</body>
</html>