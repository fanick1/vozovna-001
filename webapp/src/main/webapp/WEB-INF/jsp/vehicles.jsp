<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="vehicles.title" /></title>
    
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
    <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
    
    <script type="text/javascript">
        //if (datefield.type!="date"){ //if browser doesn't support input type="date", initialize date picker widget:
        $(function() {
            $('input[type=date]').datepicker({
            // specify the same format as the spec
            dateFormat: 'yy-mm-dd'
            });
        });
        //}
    </script>
    <style type="text/css">
        #choose-dates  {
            border:  0px none;
        }
        #choose-dates td {
            vertical-align: middle;
        }
        form {
            margin: 0px;
        }
        .detail {
            padding: 5px;
            margin: 5px 0;
        }
    </style>
</head>
<body>

    <div>
        <spring:url var="actionUrl" value="/vehicles" />
        <div class="detail">
            
            <form method="post" action="${actionUrl}" >
                <table id="choose-dates">
                <tr>
                    <td><label for="startDate" >Start date:</label></td>
                    <td><input type="date" name="startDate" id="startDate" /></td>
                
                    <td><label for="endDate" >End date:</label></td>
                    <td><input type="date" name="endDate" id="endDate" /></td>
               
                    <td><input type="submit" name="findVehicles"  /></td>
                </tr>
                </table>
            </form>
        </div>

        <table class="grid">
            <colgroup>
                <col span="3" />
                <col style="width: 230px;"/>
            </colgroup>
            <tr>
                <th><fmt:message key="vehicle.id" /></th>
                <th><fmt:message key="vehicle.brand" /></th>
                <th><fmt:message key="vehicle.type" /></th>
                <th></th>
            </tr>
            <c:forEach  items="${vehicles}" var="vehicle">
                <tr>
                    <td class="number">${vehicle.id}</td>
                    <td>${vehicle.brand}</td>
                    <td>${vehicle.type}</td>
                    <td>
                        <a href="<c:url value="/reserveForm?id=${vehicle.id}" />"><fmt:message key="vehicle.reserve" /></a>
                    </td>
                </tr>
            </c:forEach>

        </table><br>


    </div>


</body>
</html>