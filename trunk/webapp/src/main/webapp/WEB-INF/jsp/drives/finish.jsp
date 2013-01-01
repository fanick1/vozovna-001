<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="drives.finish.title" /></title>

    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script> 
          <script type="text/javascript" src="http://ajax.microsoft.com/ajax/jquery.validate/1.7/jquery.validate.js"></script> 
          <script type="text/javascript"> 
            $(document).ready(function() { 
            $("#drive-finish").validate({ 
                rules: { 
                    distance: { required:   true, 
                                number:     true,
                                min:        0}
                    }, 
                messages: { 
                    distance: 	{required:  " <fmt:message key="error.drive.distance" />", 
                                 number:    " <fmt:message key="error.drive.distance.number" />",
                                 min:       " <fmt:message key="error.drive.distance.gtZero" />"}
                    } 
              }); 
            }); 

    </script>
</head>
<body>  
    <fmt:message var="datePattern" key="date.pattern.joda" /> 
    <div class="form">

        <form:form commandName="drive" method="post" id="drive-finish">
            <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.drive'].allErrors}">
                    <div class="form-errors">
                        <form:errors path="*" cssStyle="color:red" />
                    </div>
            </c:if>
            <table class="detail">
                <tr>
                    <th><form:label path="distance"><fmt:message key="drive.distance" /> (<fmt:message key="drive.km" />):</form:label></th>
                    <td><form:input path="distance" maxlength="6" /> <form:errors path="distance" /></td>
                    <td>
                        <input type="submit" value="<fmt:message key="drives.action.finish" />"/>
                    </td>
                </tr>

                <tr>

                </tr>
            </table>
        </form:form>

    </div>
    <br /><br />

    <div id="drive" >
        <table class="detail">
            <colgroup>
                <col style="width: 80px;" />
            </colgroup>
            <tr>
                <th><fmt:message key="drive" /></th>
                <th><fmt:message key="drive.dateFrom" /></th>
                <th><fmt:message key="drive.dateTo" /></th>
            </tr>
            <tr>
                <td>${drive.id}</td>
                <td><joda:format pattern="${datePattern}" value="${drive.dateFrom}"/></td>
                <td><joda:format pattern="${datePattern}" value="${drive.dateTo}"/></td>
            </tr>
        </table>
    </div>
    <br/>	

    <div id="vehicle" >
        <table class="detail">
            <colgroup>
                <col style="width: 80px;" />
            </colgroup>
            <tr>
                <th><fmt:message key="vehicle" /></th>
                <th><fmt:message key="vehicle.brand" /></th>
                <th><fmt:message key="vehicle.type" /></th>
                <th><fmt:message key="vehicle.vin" /></th>

            </tr>
            <tr>
                <td><c:out value="${drive.vehicle.id}"/></td>
                <td><c:out value="${drive.vehicle.brand}"/></td>
                <td><c:out value="${drive.vehicle.type}"/></td>
                <td><c:out value="${drive.vehicle.vin}"/></td>
            </tr>
        </table>
    </div>
    <br/>



    </body>
</html>