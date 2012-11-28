<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="admin.users.title" /></title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
    <script type="text/javascript">
        $("a.remove").live("click",function(event){
            event.stopPropagation();
            if(!confirm("<fmt:message key="admin.users.confirm.delete" />")) {
                event.preventDefault();
            }       
         });
    </script>
</head>
<body>


    <div>
        <a href="<c:url value="/admin/users/new/" />"><fmt:message key="users.add" /></a>
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
            <th><fmt:message key="user.labels.id" /></th>
            <th><fmt:message key="user.labels.username" /></th>
            <th><fmt:message key="user.labels.firstName" /></th>
            <th><fmt:message key="user.labels.lastName" /></th>
            <th><fmt:message key="user.labels.enabled" /></th>
            <th><fmt:message key="user.labels.isAdmin" /></th>
            
            
            
            <th><fmt:message key="user.labels.userClass" /></th>
            <th>&nbsp;</th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td class="number"><c:out value="${user.id}"/></td>
                <td><c:out value="${user.username}"/></td>
                <td><c:out value="${user.firstName}"/></td>
                <td><c:out value="${user.lastName}"/></td>
                <td><c:choose><c:when test="${user.enabled}"><fmt:message key="users.enabled.true.short" /></c:when>
                    <c:otherwise><fmt:message key="users.enabled.false.short" /></c:otherwise></c:choose></td>
                <td><c:choose><c:when test="${user.isAdmin}"><fmt:message key="users.role.admin" /></c:when>
                    <c:otherwise><fmt:message key="users.role.user" /></c:otherwise></c:choose></td>
                
                
                <td><fmt:message key="${user.userClass.code}" /></td>
                <td><a href="<c:url value="/admin/users/edit/${user.id}" />"><fmt:message key="actions.edit" /></a>
                    | <a href="<c:url value="/admin/users/delete/${user.id}" />" class="remove"><fmt:message key="actions.delete" /></a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>



</body>
</html>