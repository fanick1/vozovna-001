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
            .detail td, .detail th {
                padding-top: 4px;
                padding-bottom: 4px;
            }
        </style>
        
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
        <script type="text/javascript">
            $(function() {
                $("a.remove").on("click", function(){
                    event.stopPropagation();
                    if(!confirm("<fmt:message key="admin.vehicles.confirmDelete" />")) {
                        event.preventDefault();
                    }       
                 });
            });
        </script>
    </head>
    <body>
        
        <table class="detail">
            <colgroup>
                <col style="width: 200px" />
                <col style="width: 300px" />
            </colgroup>
            <tr>
                <th><fmt:message key="vehicle.id" />:</th>
                <td><c:out value="${vehicle.id}"/></td>
            </tr>
            <tr>
                <th><fmt:message key="vehicle.registrationPlate" />:</th>
                <td><c:out value="${vehicle.registrationPlate}" /></td>
            </tr>
           <tr>
                <th><fmt:message key="vehicle.brand" />:</th>
                <td><c:out value="${vehicle.brand}"/></td>
            </tr>
            <tr>
                <th><fmt:message key="vehicle.type" />:</th>
                <td><c:out value="${vehicle.type}"/></td>

            </tr>
            <tr>
                <th><fmt:message key="vehicle.engineType" />:</th>
                <td><c:out value="${vehicle.engineType}"/></td>
            </tr>
            <tr>
                <th><fmt:message key="vehicle.yearMade" />:</th>
                <td><c:out value="${vehicle.yearMade}"/></td>
            </tr>
            <tr>
                <th><fmt:message key="vehicle.distanceCount" />:</th>
                <td><c:out value="${vehicle.distanceCount}"/></td>
            </tr>
            <tr>
                <th><fmt:message key="vehicle.vin" />:</th>
                <td><c:out value="${vehicle.vin}"/></td>
            </tr>
            <tr>
                <th><fmt:message key="vehicle.userClass" />:</th>
                <td><fmt:message key="${vehicle.userClass.code}" /></td>
            </tr>
            
            <tr>
                <table class="grid">
                    <colgroup>
                        <col style="width: 50px;" />
                        <col />
                        <col style="width: 20px;" />
                        <col />
                        <col style="width: 200px;"/>
                    </colgroup>
                    <tr>
                        <th><fmt:message key="interval.description" /></th>
                        <th><fmt:message key="interval.inspectionInterval" /></th>
                        <th></th>
                    </tr>
                    <c:forEach  items="${vehicle.serviceIntervals}" var="interval">
                        <tr >       
                                <td><a href="<c:url value="/admin/intervals/show?id=${interval.id}" />"><c:out value="${interval.description}" /></a></td>
                                <td class="number"><c:out value="${interval.inspectionInterval}"/></td>
                                <td>
                                    <a href="<c:url value="/admin/intervals/show?id=${interval.id}" />"><fmt:message key="actions.show" /></a>
                                    <c:if test="${interval.hasRequiredInspection}" >
                                        <img width="15" height="15" title="<fmt:message key="admin.intervals.requiredInspection"/>" src="<spring:url value="/resources/img/error.png"/>" />
                                    </c:if>
                                </td>
                            </tr>
                    </c:forEach>
                </table>
            </tr>
            
        </table>
        <br>
        <div class="inpage-nav">
            <a href="<c:url value="/admin/vehicles" />"><fmt:message key="vehicles.list" /></a> |
            <a href="<c:url value="/admin/vehicles/edit?id=${vehicle.id}" />"><fmt:message key="actions.edit" /></a> | 
            <a href="<c:url value="/admin/vehicles/delete?id=${vehicle.id}" />" class="remove"><fmt:message key="actions.delete" /></a>
        </div>            
    </body>
</html>
