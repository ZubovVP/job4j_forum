<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 04.12.2020
  Time: 23:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
<form:form method="POST" action="/updatePost" modelAttribute="currentPost">
    <table>
        <tr>
            <td>Id :</td>
            <td><spring:input path="id" readonly="true"/></td>
        </tr>
        <tr>
            <td>Название поста:</td>
            <td><spring:input path="name"/></td>
        </tr>
        <tr>
            <td>Описание:</td>
            <td><spring:input path="desc"/></td>
        </tr>
        <tr>
            <td>Дата создания:</td>
            <td><spring:input path="created" readonly="true"/></td>
        </tr>
        <tr>
            <td colspan='2'><spring:button>Submit</spring:button></td>
        </tr>
        <spring:hidden path="owner"/>
        <spring:hidden path="comments"/>
    </table>
</form:form>
</body>
</html>
