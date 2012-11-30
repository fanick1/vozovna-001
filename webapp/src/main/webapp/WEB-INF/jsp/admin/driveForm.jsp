<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="admin.drives.title" /></title>
    <style type="text/css" media="all">
        .detail {
            width: 500px;
            text-align: center;
            margin: 0 auto;
        }
        input[type=text],  select, textarea  {
            width: 200px;
        }
    </style>
</head>
<body>

    <div>
        <form:form commandName="driveDTO" method="post" id="driveForm">
             <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.driveDTO'].allErrors}">
                    <div class="form-errors">
                        <form:errors path="*" cssStyle="color:red" />
                    </div>
            </c:if>
            <table class="detail">

                <c:if test="${drive.id != null}">
                    <tr>
                        <td>
                            <form:label path="id"><fmt:message key="drive.labels.id" /></form:label>
                        </td>
                        <td><form:hidden path="id" />${drive.id}</td>
                    </tr>
                </c:if>

                <tr>
                    <td>
                        <form:label path="dateFrom"><fmt:message key="drive.dateFrom" />:</form:label>
                    </td>
                    <td>
                        <form:input path="dateFrom" type="date" />
                        <form:errors path="dateFrom" cssClass="error" />
                    </td>
                </tr>

                <tr>
                    <td>
                        <form:label path="dateTo"><fmt:message key="drive.dateTo" />:</form:label>
                    </td>
                    <td>
                        <form:input path="dateTo" type="date" />
                        <form:errors path="dateTo" cssClass="error" />
                    </td>
                </tr>

                <tr>
                    <td>
                        <form:label path="state">
                            <fmt:message key="drive.state" />
                        </form:label>
                    </td>
                    <td>
                        <form:select path="state">
                            <c:forEach var="item" items="${states}">
                                <form:option value="${item}">
                                    <fmt:message key="${item.code}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="state" cssClass="error" />
                    </td>
                </tr>

                <tr>
                    <td><form:label path="distance">
                            <fmt:message key="drive.distance" />:</form:label></td>
                    <td>
                        <form:input path="distance" maxlength="5" />
                        <form:errors path="distance" cssClass="error" />
                    </td>

                </tr>

                <tr>
                    <td>
                        <form:label path="user">
                            <fmt:message key="drive.user" />:
                        </form:label>
                    </td>
                    <td>
                        <form:select path="userId">
                            <c:forEach var="item" items="${users}">
                                <form:option value="${item.id}">
                                    [${item.id}] ${item.firstName} ${item.lastName} 
                                </form:option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="user" cssClass="error" />
                    </td>
                </tr>

                <tr>
                    <td>
                        <form:label path="vehicle">
                            <fmt:message key="drive.vehicle" />:
                        </form:label>
                    </td>
                    <td>
                        <form:select path="vehicleId">
                            <c:forEach var="item" items="${vehicles}">
                                <form:option value="${item.id}">
                                    [${item.id}] ${item.brand} ${item.type} ${item.vin} 
                                </form:option>
                            </c:forEach>
                        </form:select>
                        <form:errors path="vehicle" cssClass="error" />
                    </td>
                </tr>

                <tr>
                    <td>&nbsp;</td>
                    <td><input type="submit" value="<fmt:message key="actions.save" />" /></td>
                </tr>
            </table>
        </form:form>

    </div>

</body>
</html>