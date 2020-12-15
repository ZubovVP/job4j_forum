<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 07.12.2020
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <style type="text/css">
        textarea {
            resize: none;
            width: 850px;
            height: 50px;
        }
    </style>
    <title>Пост</title>
</head>

<body>
<div class="container mt-3">
    <div class="row">
        <h4>Название: ${post.name}</h4>
    </div>
    <div class="row">
        <h3>Описание: ${post.desc}</h3>
    </div>
    <form action="<c:url value='/addComment'/>" method='POST'>
        <div class="row">
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Сообщение</th>
                    <th scope="col">Автор</th>
                    <th scope="col">Дата</th>

                </tr>
                </thead>
                <tbody>
                <c:forEach items="${post.comments}" var="comment">
                    <tr>
                        <td>${comment.comment}</td>
                        <td>${comment.user.name}</td>
                        <td>${comment.created.time}</td>
                    </tr>
                </c:forEach>
                <tr>
                    <td>
                        <textarea name="text" placeholder="Оставить комментарий"></textarea>
                        <input type="hidden" name="id" value="${post.id}">
                    </td>
                </tr>
                </tbody>
            </table>
            <p><input name="submit" align="right" type="submit" value="Отправить"/></p>
        </div>
    </form>
    <p><a href="<c:url value='/index'/>">На главную</a></p>
</div>
</body>
</html>
