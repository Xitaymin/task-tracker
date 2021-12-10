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
        <%--        <div class="form-group">--%>
        <%--            <label class="col-form-label">Project</label>--%>
        <%--            <form:select path="project">--%>
        <%--                <c:forEach var="project" items="${projects}">--%>
        <%--                    <form:option value="${project.id}" label="${project.name}"/>--%>
        <%--                </c:forEach>--%>
        <%--            </form:select>--%>
        <%--        </div>--%>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $.ajax({
                    // url: "GetCountryStateservlet",
                    url: "task-tracker/projects", //get projects list in json
                    method: "GET",
                    data: {operation: 'project'},
                    // data: {operation: 'country'},
                    success: function (data, textStatus, jqXHR) {
                        console.log(data);
                        let obj = $.parseJSON(data);
                        $.each(obj, function (key, value) {
                            $('#project').append('<option value="' + value.id + '">' + value.name + '</option>')
                        });
                        $('select').formSelect();
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        $('#project').append('<option>Project Unavailable</option>');
                    },
                    cache: false
                });

                // $('#project').change(function () {
                //     $('#state').find('option').remove();
                //     $('#state').append('<option>Select State</option>');
                //     $('#city').find('option').remove();
                //     $('#city').append('<option>Select City</option>');
                //
                //     let cid = $('#project').val();
                //     let data = {
                //         operation: "state",
                //         id: cid
                //     };
                //
                //     $.ajax({
                //         url: "GetprojectStateservlet",
                //         method: "GET",
                //         data: data,
                //         success: function (data, textStatus, jqXHR) {
                //             console.log(data);
                //             let obj = $.parseJSON(data);
                //             $.each(obj, function (key, value) {
                //                 $('#state').append('<option value="' + value.id + '">' + value.name + '</option>')
                //             });
                //             $('select').formSelect();
                //         },
                //         error: function (jqXHR, textStatus, errorThrown) {
                //             $('#state').append('<option>State Unavailable</option>');
                //         },
                //         cache: false
                //     });
                // });
                //
                // $('#state').change(function () {
                //     $('#city').find('option').remove();
                //     $('#city').append('<option>Select City</option>');
                //
                //     let sid = $('#state').val();
                //     let data = {
                //         operation: "city",
                //         id: sid
                //     };
                //
                //     $.ajax({
                //         url: "GetprojectStateservlet",
                //         method: "GET",
                //         data: data,
                //         success: function (data, textStatus, jqXHR) {
                //             console.log(data);
                //             let obj = $.parseJSON(data);
                //             $.each(obj, function (key, value) {
                //                 $('#city').append('<option value="' + value.id + '">' + value.name + '</option>')
                //             });
                //             $('select').formSelect();
                //         },
                //         error: function (jqXHR, textStatus, errorThrown) {
                //             $('#city').append('<option>City Unavailable</option>');
                //         },
                //         cache: false
                //     });
                // });

            });
        </script>

        <input type="submit" value="Submit"/>
    </form:form>

</main>
</html>