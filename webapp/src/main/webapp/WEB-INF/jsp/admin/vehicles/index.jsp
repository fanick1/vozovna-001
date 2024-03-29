    <%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="admin.vehicles.title" /></title>

        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function() { 
                $("a.remove").on("click",function(){
                    event.stopPropagation();
                    if(!confirm("<fmt:message key="admin.vehicles.confirmDelete" />")) {
                        event.preventDefault();
                    }       
                 });
             });
        </script>
    </head>
    <body>
        <div>
            <a href="<c:url value="/admin/vehicles/add" />"><fmt:message key="admin.vehicles.add" /></a>
            | <fmt:message key="admin.vehicles.found" />: ${vehicles.size()}
        </div>
        <table class="grid">
            <colgroup>
                <col style="width:  60px;" />
                <col style="width: 130px;" />
                <col style="width: 120px;" />
                <col style="width:  80px;" />
                <col style="width: 120px;" />
                <col style="width: 200px;" />
            </colgroup>
            <tr>
                <th><fmt:message key="vehicle.registrationPlate" /></th>
                <th><fmt:message key="vehicle.brand" /></th>
                <th><fmt:message key="vehicle.type" /></th>
                <th><fmt:message key="vehicle.mileage" /></th>
                <th><fmt:message key="vehicle.engineType" /></th>
                <th></th>
            </tr>
            <c:forEach  items="${vehicles}" var="vehicle">
                <tr>
                    <td><a href="<c:url value="/admin/vehicles/show?id=${vehicle.id}" />"><c:out value="${vehicle.registrationPlate}"/></a></td>
                    <td><c:out value="${vehicle.brand}"/></td>
                    <td><c:out value="${vehicle.type}"/></td>
                    <td class="number">
                        <c:if test="${vehicle.mileage > vehicle.distanceCount}" >    
                           <img width="20" height="20" align="left" title="<fmt:message key="admin.vehicle.reachedMaxMileage"/>" src="<spring:url value="/resources/img/stop.png"/>" />
                       </c:if>
                        <c:out value="${vehicle.mileage}"/>
                    </td>
                    <td><c:out value="${vehicle.engineType}"/></td>
                    <td>
                        <a href="<c:url value="/admin/vehicles/show?id=${vehicle.id}" />"><fmt:message key="actions.show" /></a> |
                        <a href="<c:url value="/admin/vehicles/edit?id=${vehicle.id}" />"><fmt:message key="actions.edit" /></a>
                        <c:if test="${vehicle.canRemove}">
                             | <a href="<c:url value="/admin/vehicles/delete?id=${vehicle.id}" />" class="remove"><fmt:message key="actions.delete" /></a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>

        </table><br>





    </body>
    </html>