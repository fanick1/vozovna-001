<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="drives.title" /></title>
    
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    <script type="text/javascript">
        $(function() {
            $("a.link-drive-cancel").on("click",function(){
                event.stopPropagation();
                if(!confirm("<fmt:message key="drives.confirm.cancel" />")) {
                    event.preventDefault();
                }       
             });
             $("a.link-drive-start").on("click",function(){
                event.stopPropagation();
                if(!confirm("<fmt:message key="drives.confirm.start" />")) {
                    event.preventDefault();
                }       
             });
         });
    </script>
</head>
<body>
    <fmt:message var="datePattern" key="date.pattern.joda" /> 
    <div>
        <a href="<c:url value="/vehicles" />"><fmt:message key="drives.reserveVehicle" /></a>
         | 
        <c:if test="${drives.size() > 0}">
            <fmt:message key="drives.index.found" /> ${drives.size()}
        </c:if>
        <c:if test="${drives.size() < 1}">
            <fmt:message key="drives.index.nofound" />
        </c:if>
    </div>
    <table class="grid">
        <colgroup>
            <col style="width: 60px;" />
            <col style="width: 100px;" />
            <col style="width: 100px;" />
            <col style="width: 100px;" />
            <col style="width: 250px;" />
            <col style="min-width: 100px;" />
        </colgroup>
        <tr>
            <th><fmt:message key="drive.id" /></th>
            <th><fmt:message key="drive.dateFrom" /></th>
            <th><fmt:message key="drive.dateTo" /></th>
            <th><fmt:message key="drive.state" /></th>
            <th><fmt:message key="drive.vehicle" /></th>
            
            <th><fmt:message key="drive.actions" /></th>
        </tr>
        
        <c:forEach var="item" items="${drives}">
            <tr>
                <td class="number">${item.id}</td>
                <!-- < fmt : formatDate value="" / > --->
                <td><joda:format pattern="${datePattern}" value="${item.dateFrom}"/></td>
                <td><joda:format pattern="${datePattern}" value="${item.dateTo}"/></td>
                <td><fmt:message key="${item.state.code}" /></td>
                <td> #<c:out value="${item.vehicle.id}"/>: <span class="registrationPlate">[<c:out value="${item.vehicle.registrationPlate}"/>]</span> <c:out value="${item.vehicle.brand}"/> <c:out value="${item.vehicle.type}"/></td>
                <c:choose>
                    <c:when test="${item.state.name == 'reserved'}">
                        <td>
                            <!--< jsp: useBean id="now" class="java.util.Date"/>
                            < c :if test=" $ { item.dateFrom.compare(now) gt 0}">-->
                                <a href="<c:url value="drives/start?id=${item.id}" />" class="link-drive-start"><fmt:message key="drives.changeState.start" /></a> | 
                            <!--< /c : if>-->
                            <a href="<c:url value="drives/cancel?id=${item.id}" />" class="link-drive-cancel"><fmt:message key="drives.changeState.cancel" /></a>
                        </td>
                    </c:when>
                    <c:when test="${item.state.name == 'ongoing'}">
                        <td>
                            <a href="<c:url value="drives/finish?id=${item.id}" />" class="link-drive-finish"><fmt:message key="drives.changeState.finish" /></a>
                        </td>
                    </c:when>
                    <c:otherwise>
                        
                        <td>
                            &nbsp;
                        </td>
                    </c:otherwise>
                </c:choose>
                
            </tr>
        </c:forEach>
        
    </table>


</body>
</html>