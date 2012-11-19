<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="admin.vehicles.title" /></title>
    
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
    <script type="text/javascript">
        $("a.remove").live("click",function(event){
            event.stopPropagation();
            if(!confirm("<fmt:message key="admin.vehicles.confirmDelete" />")) {
                event.preventDefault();
            }       
         });
    </script>
</head>
<body>
    
    <h1>
        <fmt:message key="admin.vehicles.title" />
    </h1>

	<div>
		<a href="<c:url value="/admin/vehicles/add" />"><fmt:message key="admin.vehicles.add" /></a>
        | <fmt:message key="admin.vehicles.found" />: ${vehicles.size()}
	</div>
	<table class="grid">
		<tr>
			<th><fmt:message key="vehicles.id" /></th>
            <th><fmt:message key="vehicles.brand" /></th>
			<th><fmt:message key="vehicles.type" /></th>
            <th colspan="3"></th>
		</tr>
		<c:forEach  items="${vehicles}" var="vehicle">
			<tr>
				<td>${vehicle.id}</td>
				<td>${vehicle.brand}</td>
				<td>${vehicle.type}</td>
				<td><a href="<c:url value="/admin/vehicles/show?id=${vehicle.id}" />"><fmt:message key="actions.show" /></a></td>
				<td><a href="<c:url value="/admin/vehicles/edit?id=${vehicle.id}" />"><fmt:message key="actions.edit" /></a></td>
                <td><a href="<c:url value="/admin/vehicles/delete?id=${vehicle.id}" />" class="remove"><fmt:message key="actions.delete" /></a></td>
			</tr>
		</c:forEach>
	
	</table><br>
	
	
	


</body>
</html>