<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>
        <!--<fmt:message key="admin.vehicles.title" /> : -->
        <c:choose>
            <c:when test="${model.vehicle.id == null}">
                <fmt:message key="admin.vehicles.add.title" />
            </c:when>
            <c:otherwise>
                <fmt:message key="admin.vehicles.edit.title" />
            </c:otherwise>
        </c:choose>
    </title>

     <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script> 
    <script type="text/javascript" src="http://ajax.microsoft.com/ajax/jquery.validate/1.7/jquery.validate.js"></script> 
    <script type="text/javascript"> 
        $(document).ready(function() { 
            $("#vehicle-edit-form").validate({ 
                rules: { 
                    description:        {required:true},
                    inspectionInterval: {required:true, 
                                         number:true,
                                         min: 0}
                    }, 
                messages: { 
                    description: 	{required:  " <fmt:message key="error.interval.description.missing" />"},
                    inspectionInterval:       {required:  " <fmt:message key="error.interval.inspectionInterval.missing" />", 
                                     number:    " <fmt:message key="error.interval.inspectionInterval.number" />",
                                     min:       " <fmt:message key="error.interval.inspectionInterval.notPositive" />"
                                     }
                } 
              }); 
            }); 
            
        $("#interval-edit-form-cancel").live("click",function(event){
            
            if(confirm("<fmt:message key="admin.vehicles.confirmLeave" />")) {
                window.location = "<c:url value="/admin/intervals/index" />";
            }
        });
    </script>
    <style type="text/css" media="all">
        .detail {
            width: 600px;
            text-align: center;
            margin: 0 auto;
        }
        input[type=text],  select, textarea  {
            width: 200px;
        }
    </style>

</head>
<body>

<c:set var="userClasses" value="<%=cz.muni.fi.pa165.vozovna.enums.UserClassEnum.values()%>" />
<div class="form">
    <form:form commandName="intervalDTO" method="post" id="interval-edit-form">
        <c:if test="${not empty requestScope['org.springframework.validation.BindingResult.intervalDTO'].allErrors}">
            <div class="form-errors">
                <form:errors path="*" cssStyle="color:red" />
            </div>
        </c:if>
        <table class="detail">
            <colgroup>
                <col style="width: 200px" />
                <col style="width: 250px" />
                <col style="width: 150px" />
            </colgroup>
            <tr>
                <c:if test="${intervalDTO.id != null}">
                    <td><form:label path="id"><fmt:message key="interval.id" />:</form:label></td>

                    <td class="value"><form:hidden path="id" />${intervalDTO.id}</td>
                </c:if>
            </tr>
            <tr>
                <td>
                    <form:label path="description"><fmt:message key="interval.description" />:</form:label>
                </td>
                <td>
                    <form:input path="description" maxlength="100" />
                    <form:errors path="description" cssClass="error" />
                </td>
            </tr>
            <tr>
                <td><form:label path="vehicle"><fmt:message key="interval.vehicle" />:</form:label></td>
                <td>
                    <form:input readonly="true" path="vehicle.fullName" maxlength="40" />
                    <form:errors path="vehicle" cssClass="error" />
                </td>
                <td><a href="<c:url value="/admin/intervals/vehicleSelect2?intervalId=${intervalDTO.id}" />"><fmt:message key="admin.intervals.vehicleSelect" /></a></td>
            </tr>
            <tr>
                <td><form:label path="inspectionInterval"><fmt:message key="interval.inspectionInterval" />:</form:label></td>
                <td>
                    <form:input path="inspectionInterval" />
                    <form:errors path="inspectionInterval" cssClass="error" />
                </td>
            </tr>


            <tr>
                <td>&nbsp; </td>
                <td>
                    <input type="submit" value="<fmt:message key="actions.save" />"/>
                    <input type="button" id="interval-edit-form-cancel" value="<fmt:message key="actions.cancel" />" />
                </td>
            </tr>
        </table>
    </form:form>

</div>

</body>
</html>
 