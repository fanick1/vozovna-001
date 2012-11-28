<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="admin.serviceIntervals.title" /></title>

    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
    <script type="text/javascript">
        $("a.remove").live("click",function(event){
            event.stopPropagation();
            if(!confirm("<fmt:message key="admin.intervals.confirmDelete" />")) {
                event.preventDefault();
            }
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
            <col />
            <col />
            <col style="width: 200px;"/>
        </colgroup>
        <tr>
            <th><fmt:message key="interval.id" /></th>
            <th><fmt:message key="interval.inspectionInterval" /></th>
            <th><fmt:message key="interval.vehicle" /></th>
            <th><fmt:message key="interval.description" /></th>
            <th></th>
        </tr>
        <c:forEach  items="${intervals}" var="interval">
            <tr>
                <td class="number">${interval.id}</td>
                <td><c:out value="${interval.inspectionInterval}"/></td>
                <td><c:out value="${interval.vehicle.fullName}"/></td>
                <td><c:out value="${interval.description}" /></td>
                <td>
                    <a href="<c:url value="/admin/intervals/show?id=${interval.id}" />"><fmt:message key="actions.show" /></a> |
                    <a href="<c:url value="/admin/intervals/edit?id=${interval.id}" />"><fmt:message key="actions.edit" /></a> |
                    <a href="<c:url value="/admin/intervals/delete?id=${interval.id}" />" class="remove"><fmt:message key="actions.delete" /></a>
                </td>
            </tr>
        </c:forEach>

    </table><br>

</body>
</html>