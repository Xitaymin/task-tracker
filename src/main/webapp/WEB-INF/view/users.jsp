<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Пользователи</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
          integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
</head>
<body>
<header>
    <div class="jumbotron pt-4">
        <div class="container">
            <h3>Все пользователи </h3>
        </div>
    </div>
</header>
<main>
    <div class="jumbotron pt-4">
        <div class="container">
            <%--            <a class="btn btn-primary" href="/admin/users/new-user?type=ADMIN">--%>
            <%--                <span class="fa fa-plus"></span>--%>
            <%--                Добавить админа--%>
            <%--            </a>--%>
            <%--            <a class="btn btn-primary" href="/admin/users/new-user?type=TEACHER">--%>
            <%--                <span class="fa fa-plus"></span>--%>
            <%--                Добавить учителя--%>
            <%--            </a>--%>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Email</th>
<%--                    <th scope="col">Deleted</th>--%>
<%--                    <th scope="col">Team</th>--%>
                    <th scope="col">Roles</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.name}</td>
                        <td>${user.email}</td>
<%--                        <td>${user.deleted}</td>--%>
<%--                        <td>${user.team}</td>--%>
                        <td><c:forEach var="role" items="${user.roles}">
                            <c:out value="${role}"/>
                        </c:forEach> </td>
                        <td>

                                <%--                            <c:if test="!${user.type.equals('ADMIN')}">--%>
                                <%--                                <c:choose>--%>
                                <%--                                    <c:when test="${user.banned}">--%>
                                <%--                                        <a class="btn btn-danger" href="admin/users/ban/${user.id}">--%>
                                <%--                                            <span class="fas fa-user-times"></span>--%>
                                <%--                                        </a>--%>
                                <%--                                    </c:when>--%>
                                <%--                                    <c:otherwise>--%>
                                <%--                                        <a class="btn btn-danger" href="admin/users/unban/${user.id}">--%>
                                <%--                                            <span class="fas fa-user-plus"></span>--%>
                                <%--                                        </a>--%>
                                <%--                                    </c:otherwise>--%>
                                <%--                                </c:choose>--%>
                                <%--                                <a class="btn btn-warn" href="admin/users/resetPassword/${user.id}">--%>
                                <%--                                    <span class="fas fa-key"></span>--%>
                                <%--                                </a>--%>
                                <%--                                <a class="btn btn-warn" href="admin/users/edit/${user.id}">--%>
                                <%--                                    <span class="fas fa-edit"></span>--%>
                                <%--                                </a>--%>
                                <%--                            </c:if>--%>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</main>
<footer>

</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>
</body>
</html>
