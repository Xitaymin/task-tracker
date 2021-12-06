<%--Создание/редактирования таски--%>
<%--выбор проекта--%>
<%--выбор assignee--%>
<%--репортер ставится из текущего залогиненного пользователя--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html>
<head>
    <title>Task</title>
</head>

<header>
    <div class="container">
        <h3>${create? 'Create' : 'Edit'} task</h3>
    </div>
</header>

<main>
    <form:form class="form-group" modelAttribute="task" method="post"
               action="${pageContext.servletContext.contextPath}/admin/task/upsert" charset="utf-8"
               accept-charset="UTF-8">
        <input name="id" value="${task.id}" type="hidden">
        <div class="form-group">
            <label class="col-form-label">Title</label>
            <form:input path="title" type="text" class="form-control"/>
        </div>
        <div class="form-group">
            <label class="col-form-label">Description</label>
            <form:input path="description" type="text" class="form-control"/>
        </div>
        <div class="form-group">
            <label class="col-form-label">Project</label>
            <form:select path="project">
                <c:forEach var="project" items="${projects}">
                    <form:option value="${project.id}" label="${project.name}"/>
                </c:forEach>
            </form:select>
        </div>

        <input type="submit" value="Submit"/>
    </form:form>

</main>
</html>