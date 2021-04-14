<%--
  Created by IntelliJ IDEA.
  User: zakhar
  Date: 09.05.2018
  Time: 13:27
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${title}</title>

    <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=2.0, user-scalable=yes" />

    <link rel="icon" href="<c:url value="/resources/img/favicon.ico" />" type="image/x-icon">
    <link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico" />" type="image/x-icon">

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">

    <script src="<c:url value="/resources/js/jquery.min.js" />"></script>

    <script>
        function getStatuses() {
            // $.get('/stage/statuses').done(function (data, status) {
            //     if(status == "success"){
            //         $.each(data, function (key, stgStatus) {
            //             if(stgStatus['object'].indexOf('planned stage') > -1){
            //                 planned = stgStatus['value'];
            //             }else if(stgStatus['object'].indexOf('active stage') > -1){
            //                 active = stgStatus['value'];
            //             }else if(stgStatus['object'].indexOf('completed stage') > -1){
            //                 completed = stgStatus['value'];
            //             }
            //         });
            //
            //         loadStagesList();
            //     }else{
            //
            //     }
            // });
        }

        function loadStagesList(){
            $.get('/stage/list?type=in-progress&userName=${user.name}').done(function (data, status){
                $('#overlay').css('display', 'none');

                var cont = $('#main-list');
                var statusClass = '', content = '';

                if(status === 'success'){ // 200
                    $.each(data, function (key, stage) {
                        if(stage['completeDate'] == null)
                            statusClass = 'active';
                        else
                            statusClass = 'completed';

                        content = '';

                        if(stage['type'] != null)
                            content += stage['type'] + '<hr>';
                        if(stage['product'] != null)
                            content += stage['product'];
                        if(stage['clientName'] != null)
                            content += '<hr><span>' + stage['clientName'] + '</span>';

                        cont.append('<li><a class="button large-size stage ' + statusClass + '" ' +
                            'href="' + stage['id'] + '">' + content + '</a></li>');
                    });
                }else if(status === 'nocontent'){ // 204
                    alert('Ничего не найдено');

                    $.post('/logout').always(function () {
                        $(location).attr("href", '/${user.post}');
                    });
                }

                cont.append('<li><hr></li>');
                cont.append('<li><a class="button medium-size" href="/logout">Выйти</a></li>');

                $('a.stage').on('click', function (e){
                    e.preventDefault();

                    showForm($(this).attr('href'));
                });
            }).fail(function(jqXHR) {
                if(jqXHR.status === 403){ // Forbidden
                    alert('Сессия завершена. Необходима повторная авторизация.');
                }else if(jqXHR.status === 500){
                    alert('Внутренняя ошибка сервера');
                }

                $.post('/logout').always(function () {
                    $(location).attr("href", '/${user.post}');
                });
            });
        }

        function loadStage(id){
            $.get('/${user.post}/${user.name}/stage/' + id).done(function (data, status) {
                if(status === 'success'){ // 200
                    $('#overlay').css('display', 'none');

                    var completeBtnText = 'Завершить';

                    if(data['completeDate'] != null)
                        completeBtnText = 'Отменить завершение';

                    $('#form-container').html('' +
                        '<h2>Клиент:</h2><p>' + data['clientName'] + '</p>' +
                        '<h2>Продукт:</h2><p>' + data['product'] + '</p>' +
                        '<h2>Техпроцесс:</h2><p>' + data['type'] + '</p>' +
                        '<h2>Плановая дата:</h2><p>' + new Date(data['planeDate']).toLocaleString() + '</p>' +
                        '<h2>Заметки:</h2><p>' + data['notes'] + '</p>' +
                        '<h2>Назначены:</h2><p>' + data['userNames'] + '</p>' +
                        '<br>' +
                        '<a id="btn-back" class="button medium-size" href="">Назад</a>' +
                        '<br><hr><br>' +
                        '<a id="btn-complete" class="button medium-size" href="">' + completeBtnText + '</a>'
                    );

                    $('#btn-back').on('click', function (e){
                        e.preventDefault();

                        hideForm();
                    });

                    $('#btn-complete').on('click', function (e){
                        e.preventDefault();

                        if(data['completeDate'] != null)
                            changeStageState(id, 'resume');
                        else
                            changeStageState(id, 'complete');
                    })

                }
            }).fail(function (jqXHR, textStatus, errorThrown) {
                if (textStatus === 'timeout')
                    console.log('The server is not responding');

                if (textStatus === 'error')
                    console.log(errorThrown);

                if(jqXHR.status === 403){ // Forbidden
                    alert('Сессия завершена. Необходима повторная авторизация.');
                }else if(jqXHR.status === 404){
                    alert('Техпроцесс не существует');
                }else if(jqXHR.status === 500){
                    alert('Внутренняя ошибка сервера');
                }

                $.post('/logout').always(function () {
                    $(location).attr("href", '/${user.post}');
                });
            });
        }

        function showForm(id){
            $('#overlay').css('display', 'block');
            $('#page-messge').css('display', 'none');

            $('#main-list').empty();

            loadStage(id);
        }

        function hideForm(){
            $('#overlay').css('display', 'block');
            $('#page-messge').css('display', 'block');

            $('#form-container').empty();

            loadStagesList();
        }

        function changeStageState(id, state){
            $.post('/stage/state/' + state + '/' + id).done(function (data, status) {
                if(status === 'success' || status === 'nocontent')
                    hideForm();
            }).fail(function (jqXHR) {
                if(jqXHR.status === 403){ // Forbidden
                    alert('Сессия завершена. Необходима повторная авторизация.');
                }else if(jqXHR.status === 404){
                    alert('Техпроцесс не существует');
                }else if(jqXHR.status === 500){
                    alert('Внутренняя ошибка сервера');
                }

                $.post('/logout').always(function () {
                    $(location).attr("href", '/${user.post}');
                });
            });
        }

        $(document).ready(function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");

            $.ajaxSetup({
                headers: {
                    'X-CSRF-Token': token
                }
            });

            //getStatuses();

            loadStagesList();
        });
    </script>

</head>
<body>

<div id="overlay"></div>

<div id="wrapper">

    <h1 id="page-title">${title}</h1>
    <h2 id="page-messge">${message}</h2>
    <br>

    <ul id="main-list"></ul>

    <div id="form-container"></div>

</div>

</body>
</html>
