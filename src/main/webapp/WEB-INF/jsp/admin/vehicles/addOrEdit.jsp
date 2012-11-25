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
                    brand: 			{required:true, 
                                     maxlength: 20 },
                    type: 			{required:true, 
                                     maxlength: 40 },
                    engineType: 	{required:true, 
                                     maxlength: 20 },
                    vin:            {required:true, 
                                     maxlength: 17 },
                    distanceCount: 	{required:true, 
                                     number:true,
                                     min: 0},
                    yearMade:       {required:true, 
                                     number:true,
                                     min: 1900,
                                     max: 2100}
                    }, 
                messages: { 
                    brand: 			{required:  " <fmt:message key="error.vehicle.brand" />",
                                     maxlength: " <fmt:message key="error.vehicle.brand.maxLength" />"  },
                    type: 			{required:  " <fmt:message key="error.vehicle.type" />", 
                                     maxlength: " <fmt:message key="error.vehicle.type.maxLength" />" },
                    engineType: 	{required:  " <fmt:message key="error.vehicle.engineType" />", 
                                     maxlength: " <fmt:message key="error.vehicle.engineType.maxLength" />" },
                    vin:            {required:  " <fmt:message key="error.vehicle.vin" />", 
                                     maxlength: " <fmt:message key="error.vehicle.vin.maxLength" />"  },
                    distanceCount: 	{required:  " <fmt:message key="error.vehicle.distanceCount" />", 
                                     number:    " <fmt:message key="error.vehicle.distanceCount.number" />",
                                     min:       " <fmt:message key="error.vehicle.distanceCount.aboveZero" />"},
                    yearMade:       {required:  " <fmt:message key="error.vehicle.yearMade" />", 
                                     number:    " <fmt:message key="error.vehicle.yearMade.number" />",
                                     min:       " <fmt:message key="error.vehicle.yearMade.min" />",
                                     max:       " <fmt:message key="error.vehicle.yearMade.max" />"}
                } 
              }); 
            }); 

        $("#vehicle-edit-form-cancel").live("click",function(event){
            if(confirm("<fmt:message key="admin.vehicles.confirmLeave" />")) {
               window.location = "<c:url value="/admin/vehicles" />";
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
            width: 200px;
        }
    </style>

</head>
<body>  
    
        <c:set var="userClasses" value="<%=cz.muni.fi.pa165.vozovna.enums.UserClassEnum.values()%>" />
        <div class="form">
            <form:form commandName="vehicleDTO" method="post" id="vehicle-edit-form">
                <div class="chyby">
                    <form:errors path="*" cssStyle="color:red" />
                </div>
                <table class="detail">
                    <colgroup>
                        <col style="width: 200px" />
                        <col style="width: 300px" />
                    </colgroup>
                    <tr>
                        <c:if test="${vehicleDTO.id != null}">
                            <td><form:label path="id"><fmt:message key="vehicle.id" />:</form:label></td>

                            <td class="value"><form:hidden path="id" />${vehicleDTO.id}</td>
                        </c:if>
                    </tr>
                    <tr>
                        <td>
                            <form:label path="brand"><fmt:message key="vehicle.brand" />:</form:label>
                        </td>
                        <td><form:input path="brand" maxlength="20" /></td>
                        <td><form:errors path="brand" /></td>
                    </tr>
                    <tr>
                        <td><form:label path="type"><fmt:message key="vehicle.type" />:</form:label></td>
                        <td><form:input path="type" maxlength="40" /></td>
                        <td><form:errors path="type" /></td>
                    </tr>
                    <tr>
                        <td><form:label path="engineType"><fmt:message key="vehicle.engineType" />:</form:label></td>
                        <td><form:input path="engineType" maxlength="20" /></td>
                        <td><form:errors path="engineType" /></td>
                    </tr>

                    <tr>
                        <td><form:label path="yearMade"><fmt:message key="vehicle.yearMade" />:</form:label></td>
                        <td><form:input path="yearMade" maxlength="4"  /></td>
                        <td><form:errors path="yearMade" /></td>
                    </tr>
                    <tr>
                        <td><form:label path="distanceCount"><fmt:message key="vehicle.distanceCount" />:</form:label></td>
                        <td><form:input path="distanceCount" maxlength="7" /></td>
                        <td><form:errors path="distanceCount" /></td>
                    </tr>
                    <tr>
                        <td><form:label path="vin"><fmt:message key="vehicle.vin" />:</form:label></td>
                        <td><form:input path="vin" maxlength="17" /></td>
                        <td><form:errors path="vin" /></td>
                    </tr>
                    <tr>
                        <td><form:label path="userClass"><fmt:message key="vehicle.userClass" />:</form:label></td>
                        <td>
                            
                            <form:select path="userClass">
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
                        <td>&nbsp; </td>
                        <td>
                            <input type="submit" value="<fmt:message key="actions.save" />"/>
                            <input type="button" id="vehicle-edit-form-cancel" value="<fmt:message key="actions.cancel" />" />
                        </td>
                    </tr>
                </table>
            </form:form>

        </div>

    </body>
</html>
 