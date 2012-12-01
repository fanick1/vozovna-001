<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            $('input[type=text]').datepicker({
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
        
        <div class="detail">
            <form method="get" action="vehicles" >
                <table id="choose-dates">
                <tr>
                    <td><label for="from" ><fmt:message key="drives.startDate" /></label></td>
                    <td><input type="text" name="from" id="from" value="<c:out value="${dateFrom}"/>" /></td>
                    <td><label for="to" ><fmt:message key="drives.endDate" /></label></td>
                    <td><input type="text" name="to" id="to" value="<c:out value="${dateTo}"/>" /></td>
                    <td><input type="submit" /></td>
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
                    <td class="number"><c:out value="${vehicle.id}"/></td>
                    <td><c:out value="${vehicle.brand}"/></td>
                    <td><c:out value="${vehicle.type}"/></td>
                    <td>
                        <a href="<c:url value="/reserveForm?id=${vehicle.id}&from=${dateFrom}&to=${dateTo}" />"><fmt:message key="vehicle.reserve" /></a>
                    </td>
                </tr>
            </c:forEach>

        </table><br>


    </div>


</body>
</html>