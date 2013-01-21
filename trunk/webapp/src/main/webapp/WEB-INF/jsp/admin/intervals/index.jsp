<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="admin.serviceIntervals.title" /></title>

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() { 
            $("a.remove").on("click",function(){
                event.stopPropagation();
                if(!confirm("<fmt:message key="admin.intervals.confirmDelete" />")) {
                    event.preventDefault();
                }
            });
            $("a.inspect").on("click",function(){
                event.stopPropagation();
                if(!confirm("<fmt:message key="admin.intervals.confirmInspection" />")) {
                    event.preventDefault();
                }
            });
        });
    </script>

</head>
<body>

    <div>
        <a href="<c:url value="/admin/intervals/vehicleSelect" />"><fmt:message key="admin.interval.add" /></a>
        | <fmt:message key="admin.interval.found" />: ${intervals.size()}
    </div>
    <table class="grid">
        <colgroup>
            <col style="width: 50px;" />
            <col />
            <col style="width: 80px;" />
            <col />
            <col style="width: 200px;"/>
        </colgroup>
        <tr>
            <th><fmt:message key="interval.id" /></th>
            <th><fmt:message key="interval.description" /></th>
            <th><fmt:message key="interval.inspectionInterval" /></th>
            <th><fmt:message key="interval.vehicle" /></th>
            <th></th>
        </tr>
        <c:forEach  items="${intervals}" var="interval">
            <tr>
                <td class="number"><a href="<c:url value="/admin/intervals/show?id=${interval.id}" />">${interval.id}</a></td>
                <td><a href="<c:url value="/admin/intervals/show?id=${interval.id}" />"><c:out value="${interval.description}" /></a></td>
                <td class="number"><c:out value="${interval.inspectionInterval}"/></td>
                <td><a href="<c:url value="/admin/vehicles/show?id=${interval.vehicle.id}" />"> <span class="registrationPlate">[<c:out value="${interval.vehicle.registrationPlate}"/>]</span> <c:out value="${interval.vehicle.fullName}"/></a></td>
                <td>
                    <a href="<c:url value="/admin/intervals/show?id=${interval.id}" />"><fmt:message key="actions.show" /></a> |
                    <a href="<c:url value="/admin/intervals/inspect?id=${interval.id}" />" class="inspect"><fmt:message key="admin.intervals.inspect" /></a> | 
                    <a href="<c:url value="/admin/intervals/edit?id=${interval.id}" />"><fmt:message key="actions.edit" /></a> |
                    <a href="<c:url value="/admin/intervals/delete?id=${interval.id}" />" class="remove"><fmt:message key="actions.delete" /></a>
                </td>
            </tr>
        </c:forEach>

    </table><br>

</body>
</html>