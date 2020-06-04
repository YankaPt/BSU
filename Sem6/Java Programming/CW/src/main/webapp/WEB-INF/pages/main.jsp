<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="m" uri="/WEB-INF/tags/myTag.tld" %>

<html lang="${param.lang}">
<head>
    <title>Main Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<c:forEach items="${forms}" var="students">
<m:outerTag marker="square" color="aqua" name="${students[0].form}${students[0].formLetter}">
    <c:forEach items="${students}" var="student">
        <m:innerTag>${student.name}</m:innerTag>
    </c:forEach>
</m:outerTag>
</c:forEach>
<p>count of 11A = ${count}</p>
<form method="post">
    <input type="text" name="name">
    <input type="text" name="form">
    <input type="text" name="formLetter">
    <input type="submit">
</form>
</body>
</html>
