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
</head>
<body>
    <div class="form">
        <form:form commandName="driveDTO" method="post" modelAttribute="driveDTO">
            <div class="chyby">
                <form:errors path="*" cssStyle="color:red" />
            </div>
            <table class="detail">
                <colgroup>
                    <col style="width: 200px" />
                    <col style="width: 800px" />
                </colgroup>
                <tr>
                    <td><form:label path="distance"><fmt:message key="drive.distance" />:</form:label></td>
                    <td><form:input readonly="true" path="distance" /></td>
                    <td><form:errors path="distance" /></td>
                </tr>
                <tr>
                    <td><form:label path="user"><fmt:message key="drive.user" />:</form:label></td>
                    <td><form:input readonly="true" path="user.fullName" /></td>
                    <td><form:errors path="user" /></td>
                </tr>
                <tr>
                    <td><form:label path="vehicle"><fmt:message key="drive.vehicle" />:</form:label></td>
                    <td><form:input readonly="true" path="vehicle.fullName"  /></td>
                    <td><form:errors path="vehicle" /></td>
                </tr>

                <tr>
                    <td><form:label path="dateFrom"><fmt:message key="drive.dateFrom" />:</form:label></td>
                    <td><form:input readonly="true" path="dateFrom" /></td>
                    <td><form:errors path="dateFrom" /></td>
                </tr>
                <tr>
                    <td><form:label path="dateTo"><fmt:message key="drive.dateTo" />:</form:label></td>
                    <td><form:input readonly="true" path="dateTo" /></td>
                    <td><form:errors path="dateTo" /></td>
                </tr>
                <tr>
                    <td><form:label path="state"><fmt:message key="drive.state" />:</form:label></td>
                    <td><form:input readonly="true" path="state" maxlength="17" /><fmt:message key="${item.code}" /></td>
                    <td><form:errors path="state" /></td>
                </tr>
                <tr>
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