<%--
  Created by IntelliJ IDEA.
  User: zakhar
  Date: 04.05.2018
  Time: 13:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>${title}</title>

    <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=2.0, user-scalable=yes"/>

    <link rel="icon" href="<c:url value="/resources/img/favicon.ico" />" type="image/x-icon">
    <link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico" />" type="image/x-icon">

    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css" />">
    <link rel="stylesheet" type="text/css" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.css">
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">

    <script src="<c:url value="/resources/js/jquery.min.js" />"></script>

    <script>
        function login(){
            var token = $('input[name="_csrf"]').attr('value');

            var user = {
                id: $('form#login-form #name').val(),
                name: $('form#login-form #name').find('option:selected').html(),
                password: $('form#login-form #password').val(),
                post: $('form#login-form #post').val()
            }

            $.ajax({
                url: "/login",
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(user),
                contentType: 'application/json',
                headers:{
                    'X-CSRF-Token': token
                },
                success:function(data, status) {
                    if (status == 'success') {
                        window.location.replace('/'+data['post']+'/'+data['name']);
                    } else {
                        console.log(status);
                    }
                },
                error:function(jqXHR) {
                    if(jqXHR.status == 403){
                        alert('Неверный пароль');
                        $('input#password').css('border-color', 'red');
                    }else if(jqXHR.status == 404){
                        alert('Пользователя не существует');
                    }else if(jqXHR.status == 500){
                        alert('Внутренняя ошибка сервера');
                    }
                    console.log(jqXHR.status);
                }
            });
        }

        $(document).ready(function () {
            $('form#login-form').on('submit', function (e) {
                e.preventDefault();

                login();
            });
        });
    </script>

</head>
<body>

<div id="wrapper">

    <h1>${title}</h1>
    <h2>${message}</h2>
    <br>

    <spring:form id="login-form" method="POST" action="/login">
        <div class="input-group input-sm">
            <spring:label class="input-group-addon" path="name">
                <i class="fa fa-user"></i>
            </spring:label>
            <spring:select path="name">
                <spring:options items="${usersMap}" />
            </spring:select>
        </div>
        <div class="input-group input-sm">
            <spring:label class="input-group-addon" path="password">
                <i class="fa fa-lock"></i>
            </spring:label>
            <spring:password path="password" placeholder="Пароль" required="true" />
        </div>
        <spring:hidden path="post" />
        <spring:button class="button medium-size">Войти</spring:button>
    </spring:form>

    <hr/>
    <a class="button medium-size" href="/">Назад</a>

</div>

</body>
</html>