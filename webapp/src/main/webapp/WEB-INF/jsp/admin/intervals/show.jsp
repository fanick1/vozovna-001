<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="admin.intervals.show.title" /></title>

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    <script type="text/javascript">
        $(function() {
            $("a.remove").on("click", function(){
                event.stopPropagation();
                if(!confirm("<fmt:message key="admin.intervals.confirmDelete" />")) {
                    event.preventDefault();
                }
            });
            $("a.inspect").on("click", function(){
                event.stopPropagation();
                if(!confirm("<fmt:message key="admin.intervals.confirmInspection" />")) {
                    event.preventDefault();
                }
            });
        });
    </script>
    
    <style>
        .detail {
            width: 500px;
            text-align: center;
            margin: 0 auto;
        }
        .detail td, .detail th {
            padding-top: 5px;
            padding-bottom: 5px;
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
        <td><b><c:out value="${interval.description}"/></b></td>
    </tr>
    <tr>
        <th><fmt:message key="interval.inspectionInterval" />:</th>
        <td><c:out value="${interval.inspectionInterval}"/></td>

    </tr>
    <tr>
        <th><fmt:message key="interval.dated" />:</th>
        <td>
           <fmt:message var="datePattern" key="date.pattern.date" /> 
            <c:forEach var="item" items="${interval.dated}">
                <fmt:formatDate pattern="${datePattern}" value="${item}"/><br>
            </c:forEach>
            <a href="<c:url value="/admin/intervals/inspect?id=${interval.id}" />" class="inspect"><fmt:message key="admin.intervals.inspect" /></a>
        </td>

    </tr>
    <tr>
        <th><fmt:message key="interval.vehicle" />:</th>
        <td>
            ID: <a href="<c:url value="/admin/vehicles/show?id=${interval.vehicle.id}" />"><c:out value="${interval.vehicle.id}"/></a> <br/>
            <c:out value="${interval.vehicle.brand}"/> <c:out value="${interval.vehicle.type}"/> <br />
            <span class="registrationPlate">[<c:out value="${interval.vehicle.registrationPlate}"/>]</span>  <br/>
            (<c:out value="${interval.vehicle.yearMade}"/>)
        </td>
    </tr>
</table>

<br/>
<div class="inpage-nav">
    <a href="<c:url value="/admin/intervals/index" />"><fmt:message key="intervals.list" /></a> |
    <a href="<c:url value="/admin/intervals/inspect?id=${interval.id}" />" class="inspect"><fmt:message key="admin.intervals.inspect" /></a> | 
    <a href="<c:url value="/admin/intervals/edit?id=${interval.id}" />"><fmt:message key="actions.edit" /></a> |
    <a href="<c:url value="/admin/intervals/delete?id=${interval.id}" />" class="remove"><fmt:message key="actions.delete" /></a>
</div>

</body>
</html>
