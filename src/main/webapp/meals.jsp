<%--
  Created by IntelliJ IDEA.
  User: LeTRy
  Date: 06.02.2021
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://example.com/functions" %>
<html>
<head>
    <title>Meals list</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<table>
    <style type="text/css">
        TABLE {
            border-collapse: collapse;
        }
        TD, TH {
            padding: 6px;
            border: 1px solid black;
        }
    </style>
<tr>
    <th>Date</th>
    <th>Description</th>
    <th>Calories</th>
<%--    <th></th>--%>
<%--    <th></th>--%>
</tr>
    <c:forEach var="meal" items="${requestScope.meals}">
        <tr style="color: ${meal.excess ? 'red' : 'green'}">
            <td>${f:formatLocalDateTime(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
<%--            <td></td>--%>
<%--            <td></td>--%>
        </tr>
    </c:forEach>

</table>

</body>
</html>
