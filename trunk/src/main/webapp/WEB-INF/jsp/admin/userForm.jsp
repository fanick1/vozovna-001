<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="admin.users.title" /></title>
</head>
<body>

    <div>
        <form:form commandName="userDTO" method="post" id="userForm">
            <div class="chyby">
                <form:errors path="*" cssStyle="color:red" />
            </div>
            <table>
                <c:if test="${user.id != null}">
                    <tr>
                        <td><form:label path="id"><fmt:message key="user.labels.id" /> ${user.id}</form:label></td>
                        <td><form:hidden path="id" /></td>
                    </tr>
                </c:if>
                <tr>
                    <td><form:label path="username"><fmt:message key="user.labels.username" /></form:label></td>
                    <td><form:input path="username" maxlength="20" /></td>
                    <td><form:errors path="username" /></td>
                </tr>
                <tr>
                    <td><form:label path="password"><fmt:message key="user.labels.password" /></form:label></td>
                    <td><form:input path="password" maxlength="20" /></td>
                    <td><form:errors path="password" /></td>
                </tr>
                <tr>
                    <td><form:label path="isAdmin"><fmt:message key="user.labels.isAdmin" /></form:label></td>
                    <td><form:select path="isAdmin">
                            <form:option value="true">
                                <fmt:message key="users.role.admin" />
                            </form:option>
                            <form:option value="false">
                                <fmt:message key="users.role.user" />
                            </form:option>
                        </form:select></td>
                    <td><form:errors path="isAdmin" /></td>
                </tr>
                <tr>
                    <td><form:label path="enabled"><fmt:message key="user.labels.enabled" /></form:label></td>
                    <td><form:radiobutton path="enabled" value="true"/> <fmt:message key="users.enabled.true" />
                        <form:radiobutton path="enabled" value="false"/> <fmt:message key="users.enabled.false" />
                    </td>
                    <td><form:errors path="enabled" /></td>
                </tr>
                <tr>
                    <td><form:label path="firstName"><fmt:message key="user.labels.firstName" /></form:label></td>
                    <td><form:input path="firstName"  maxlength="50" /></td>
                    <td><form:errors path="firstName" /></td>
                </tr>
                <tr>
                    <td><form:label path="lastName"><fmt:message key="user.labels.lastName" /></form:label></td>
                    <td><form:input path="lastName"  maxlength="50" /></td>
                    <td><form:errors path="lastName" /></td>
                </tr>
                <tr>
                    <td><form:label path="userClass"><fmt:message key="user.labels.userClass" /></form:label></td>
                    <td><form:select path="userClass">
                            <c:forEach var="item" items="${userClasses}">
                                <form:option value="${item}">
                                    <fmt:message key="${item.code}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </td>
                    <td><form:errors path="userClass" /></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="<fmt:message key="actions.save" />" /></td>
                </tr>
            </table>
        </form:form>

    </div>

</body>
</html>









