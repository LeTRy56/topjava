<%--
  Created by IntelliJ IDEA.
  User: LeTRy
  Date: 08.02.2021
  Time: 19:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create or update meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>
<form method="post" action="meals">
<%--    Meal id: ${param["mealId"]}--%>
    <p></p>
    <input type="hidden" name="mealId" value="${requestScope.meal.id}">
    <input type="datetime-local" name="dateTime" value="${requestScope.meal.dateTime}">
    <p></p>
    <label for="description">Description: </label>
    <input type="text" id="description" name="description" value="${requestScope.meal.description}">
    <p></p>
    <label for="calories">Calories: </label>
    <input type="text" id="calories" name="calories" value="${requestScope.meal.calories}">
    <p></p>
    <input type="submit" value="Save">
    <button><a href="meals">Cancel</a> </button>
<%--    <a href="meals"><button>Button 2</button></a>--%>
<%--    <a href="meals" type="submit">Cancel</a>--%>

</form>

<%--<form action="meals">--%>
<%--    <button>Cancel</button>--%>
<%--</form>--%>


</body>
</html>
