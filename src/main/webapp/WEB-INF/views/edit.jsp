<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 04.12.2020
  Time: 23:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<form action="<c:url value='/update'/>" method='POST'>
    <table>
        <tr>
            <td>Id:</td>
            <td><input type='text' name='id' size="40" value="${post.id}" READONLY></td>
        </tr>
        <tr>
            <td>Название поста:</td>
            <td><input type='text' name='name' size="40" value="${post.name}"></td>
        </tr>
        <tr>
            <td>Описание:</td>
            <td><input type='text' name='desc' size="40" value="${post.desc}"></td>
        </tr>
        <tr>
            <td>Дата создания:</td>
            <td><input type='text' name='data' size="40" value="${stringData}" READONLY></td>
        </tr>
        <tr>
            <td colspan='2'><input name="submit" type="submit" value="Готово"/></td>
        </tr>
    </table>
</form>
</body>
</html>
