
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<c:if test="${param.result == 'error'}">
    <script>
        alert("로그인 에러!");
    </script>
</c:if>

<form action="/login" method="post">
    <input type="text" name="mid"><br>
    <input type="text" name="mpw"><br>
    <input type="checkbox" name="auto"> 로그인 상태 유지<br>
    <button type="submit">LOGIN</button>
</form>
</body>
</html>
