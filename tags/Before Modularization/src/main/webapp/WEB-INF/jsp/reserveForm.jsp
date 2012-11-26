<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="vehicles.title" /></title>

    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
    <script type="text/javascript" src="http://ajax.microsoft.com/ajax/jquery.validate/1.7/jquery.validate.js"></script>
    <script type="text/javascript">
        $("#drive-create-cancel").live("click",function(event){
            if(confirm("<fmt:message key="user.drive.confirmLeave" />")) {
                window.location = "<c:url value="/vehicles" />";
            }
        });
    </script>
    <style type="text/css" media="all">
        .detail {
            width: 500px;
            text-align: center;
            margin: 0 auto;
        }
        input[type=text],  select, textarea  {
            width: 280px;
        }
    </style>
</head>
<body>
    <div class="form">
        <form:form commandName="driveDTO" method="post" modelAttribute="driveDTO">
             <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.driveDTO'].allErrors}">
                    <div class="form-errors">
                        <form:errors path="*" cssStyle="color:red" />
                    </div>
            </c:if>
            <table class="detail">
                <colgroup>
                    <col style="width: 200px" />
                    <col style="width: 300px" />
                </colgroup>
                <tr>
                    <td><form:label path="distance"><fmt:message key="drive.distance" />:</form:label></td>
                    <td>
                        <form:input readonly="true" path="distance" />
                        <form:errors path="distance" cssClass="error" />
                    </td>
                </tr>
                <tr>
                    <td><form:label path="user"><fmt:message key="drive.user" />:</form:label></td>
                    <td>
                        <form:input readonly="true" path="user.fullName" />
                        <form:errors path="user" cssClass="error" />
                    </td>
                </tr>
                <tr>
                    <td><form:label path="vehicle"><fmt:message key="drive.vehicle" />:</form:label></td>
                    <td>
                        <form:input readonly="true" path="vehicle.fullName"  />
                        <form:errors path="vehicle" cssClass="error" />
                    </td>
                </tr>

                <tr>
                    <td><form:label path="dateFrom"><fmt:message key="drive.dateFrom" />:</form:label></td>
                    <td>
                        <form:input readonly="true" path="dateFrom" />
                        <form:errors path="dateFrom" cssClass="error" />
                    </td>
                </tr>
                <tr>
                    <td><form:label path="dateTo"><fmt:message key="drive.dateTo" />:</form:label></td>
                    <td>
                        <form:input readonly="true" path="dateTo" />
                        <form:errors path="dateTo" cssClass="error" />
                    </td>
                </tr>
                <!--<tr>
                    <td><form:label path="state"><fmt:message key="drive.state" />:</form:label></td>
                    <td>
                        <form:input readonly="true" path="state" maxlength="17" />
                        <fmt:message key="${item.code}" />
                        <form:errors path="state" cssClass="error" />
                    </td>
                </tr>-->
                <tr>
                    <td>&nbsp;</td>
                    <td>
                        <input type="submit" value="<fmt:message key="actions.save" />"/>
                        <input type="button" onclick="cancelReservation" id="drive-create-cancel" value="<fmt:message key="actions.cancel" />" />
                    </td>
                </tr>
            </table>
        </form:form>

    </div>
</body>
</html>