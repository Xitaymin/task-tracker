<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Teachers</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
          integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
</head>
<body>
<header>
    <div class="jumbotron pt-4">
        <div class="container">
            <%--            <h3>${create ? 'Создание' : 'Редактирование'} пользователя ${type}</h3>--%>
            <h3>${create ? 'Создание' : 'Редактирование'} пользователя</h3>
        </div>
    </div>
</header>
<main>
    <div class="jumbotron pt-4">
        <div class="container">
            <div class="row">
                <div class="col-5 offset-3">
                    <form:form class="form-group" modelAttribute="user" method="post"
                               action="${pageContext.servletContext.contextPath}/admin/save" charset="utf-8"
                               accept-charset="UTF-8">
                        <input name="id" value="${user.id}" type="hidden">
                        <%--                        <input name="type" value="${type}" type="hidden">--%>
                        <%--                        <input name="banned" value="${user.banned}" type="hidden">--%>
                        <div class="form-group">
                            <label class="col-form-label">Имя</label>
                            <form:input path="name" type="text" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label class="col-form-label">Email</label>
                            <form:input path="email" type="text" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label class="col-form-label">Roles</label>
                                <%--                            <form:select path="roles"  class="form-control"/>--%>
                            <form:select path="roles" multiple="true" class="form-control">
                                <form:options items="${availableRoles}"/>
                            </form:select>
                        </div>
                        <%--                        <c:if test="${type.equals('TEACHER') || type.equals('STUDENT')}">--%>
                        <%--                            <div class="form-group">--%>
                        <%--                                <label class="col-form-label">Список курсов:</label>--%>
                        <%--                                <form:checkboxes itemLabel="title" itemValue="id" items="${allCourses}" path="courses"/>--%>
                        <%--                            </div>--%>

                        <%--                        </c:if>--%>
                        <div class="text-right">
                            <a class="btn btn-secondary" href="#" onclick="window.history.back()">
                                <span class="fa fa-close"></span>
                                Отмена
                            </a>
                            <button type="submit" class="btn btn-primary">
                                <span class="fa fa-check"></span>
                                Сохранить
                            </button>
                        </div>
                    </form:form>
                </div>
            </div>
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
