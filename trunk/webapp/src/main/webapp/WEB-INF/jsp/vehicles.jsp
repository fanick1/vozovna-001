<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="vehicles.title" /></title>
    
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
    <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
    
    <script type="text/javascript">
       $(function() {
            // DATE FROM
            // insert alt field
            $('<input type="text" id="dateFrom">').insertBefore("#from");
            // get date
            var from = $('#from').val();
            // convert to localized date format
            var dateFrom = $.datepicker.parseDate('yy-mm-dd', from);
            // put localized date to alt field
            $('#dateFrom').val($.datepicker.formatDate('<fmt:message key="date.pattern.jquery.ui.datepicker"/>', dateFrom));
            // hide original field
            $("#from").hide();
            // set datepicker
            $('#dateFrom').datepicker({
                dateFormat: '<fmt:message key="date.pattern.jquery.ui.datepicker"/>',
                altFormat: 'yy-mm-dd',
                altField: '#from'
            });
            
            // DATE TO
            // insert alt field
            $('<input type="text" id="dateTo">').insertBefore("#to");
            // get date
            var to = $('#to').val();
            // convert to localized date format
            var dateTo = $.datepicker.parseDate('yy-mm-dd', to);
            // put localized date to alt field
            $('#dateTo').val($.datepicker.formatDate('<fmt:message key="date.pattern.jquery.ui.datepicker"/>', dateTo));
            // hide original field
            $("#to").hide();
            // set datepicker
            $('#dateTo').datepicker({
                dateFormat: '<fmt:message key="date.pattern.jquery.ui.datepicker"/>',
                altFormat: 'yy-mm-dd',
                altField: '#to'
            });
            
       
        });
        
    </script>
    <c:choose >
        <c:when test="${pageContext.response.locale.language == 'cs'}">
            <script type="text/javascript" src="<c:url value="/resources/js/jquery.ui.datepicker-cs.min.js" />"></script>
        </c:when>
        <c:when test="${pageContext.response.locale.language == 'sk'}">
            <script type="text/javascript" src="<c:url value="/resources/js/jquery.ui.datepicker-sk.min.js" />"></script>
        </c:when>    
    </c:choose>
   
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
                
                <col style="width: 80px;"/>
                <col style="width: 120px;"/>
                <col span="2" />
                <col style="width: 230px;"/>
            </colgroup>
            <tr>
                <th><fmt:message key="vehicle.registrationPlate" /></th>
                <th><fmt:message key="vehicle.brand" /></th>
                <th><fmt:message key="vehicle.type" /></th>
                <th></th>
            </tr>
            <c:forEach  items="${vehicles}" var="vehicle">
                <tr>
                    <td><c:out value="${vehicle.registrationPlate}"/></td>
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