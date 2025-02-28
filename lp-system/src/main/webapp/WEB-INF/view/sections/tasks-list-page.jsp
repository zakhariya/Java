<%--
  Created by IntelliJ IDEA.
  User: zakhar
  Date: 08.05.2018
  Time: 11:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${title}</title>

    <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=2.0, user-scalable=yes"/>

    <link rel="icon" href="<c:url value="/resources/img/favicon.ico" />" type="image/x-icon">
    <link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico" />" type="image/x-icon">

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">

    <script src="<c:url value="/resources/js/jquery.min.js" />"></script>
    <script src="<c:url value="/resources/js/search-menu.js" />"></script>

    <script>
        var planned = '', active = '', completed = '';
        var task = null;
        var isTaskLoaded = false;

        function wrapperFunction() {
            if($('#dropdown-menu-cont').attr('class').indexOf('open') > -1)
                hideDropdownMenu($('#dropdown-menu-cont'));
        }

        function loadTasksList(){
            $.get('/task/list').done(function (data, status){
                $('#overlay').css('display', 'none');

                var cont = $('#main-list');
                var statusClass = '', content = '';

                if(status === 'success'){
                    $.each(data, function (key, task) {
                        if(task['status'] === planned)
                            statusClass = 'planned';
                        else if(task['status'] === active)
                            statusClass = 'active';
                        else
                            statusClass = 'completed';

                        if(task['clientName'] != null)
                            content = task['clientName'] + ' <br><span>' + task['title'] + '</span>';
                        else
                            content = task['title'];

                        cont.append('<li><a class="button large-size task ' + statusClass + '" ' +
                            'href="' + task['id'] + '">' + content + '</a></li>');
                    });

                    cont.append('<li><hr></li>');
                    cont.append('<li><a id="add-task" class="button medium-size" href="/task/add">Добавить</a></li>');
                    cont.append('<li><a id="logout" class="button medium-size" href="/logout">Выйти</a></li>');

                    $('a.task').on('click', function (e){
                        e.preventDefault();

                        showForm($(this).attr('href'), 'info');
                    });

                    $('a#add-task').on('click', function (e){
                        e.preventDefault();

                        showForm(0, 'add');
                    });
                }else if(status === 'nocontent'){ // 204
                    alert('Ничего не найдено');
                    $.post('/logout').always(function () {
                        $(location).attr("href", '/${user.post}');
                    });
                }
            }).fail(function (jqXHR) {
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

        function loadTask(id){
            if(id === 0){

                task = {
                    id: 0,
                    status: planned,
                    clientName: null,
                    title: '',
                    notes: '',
                    imageData: null
                };

                isTaskLoaded = true;
                return;
            }

            $.get('/${user.post}/${user.name}/task/' + id).done(function (data, status) {
                if(status === 'success'){ // 200
                    task = data;
                }else{
                    task = null;
                }

                isTaskLoaded = true;
            }).fail(function (jqXHR) {
                if(jqXHR.status === 403){ // Forbidden
                    alert('Сессия завершена. Необходима повторная авторизация.');
                }else if(jqXHR.status === 404){
                    alert('Событие не существует');
                }else if(jqXHR.status === 500){
                    alert('Внутренняя ошибка сервера');
                }

                $(location).attr("href", '/${user.post}');
            });
        }

        function fillForm(type) {
            $('#overlay').css('display', 'none');

            var editBtn = '';
            if(task != null && task['addedUser'] === '${user.name}')
                editBtn = '<br><br><a id="btn-edit" class="button medium-size" href="">Редактировать</a>';

            var completeBtnText = 'Завершить';
            if(task != null && task['status'] === completed)
                completeBtnText = 'Отменить завершение';

            var imageSrc = '', imageUrl = '#', imgPrevDisplay = 'display:none', imgBtnDisplay = '';
            if(task != null
                && task['imageData'] != null){

                imageSrc = task['imageData'];
                imageUrl = '/task/' + task['id'] +'/image';

                imgPrevDisplay = '';
                imgBtnDisplay = 'display:none';
            }

            if(task['clientName'] === null)
                task['clientName'] = 'без клиента';

            let logoFilePath = getLogoFilesPath();

            console.log(task['notes']);
            console.log(logoFilePath);

            if(type === 'info'){
                $('#form-container').html('' +
                    '<h2>Клиент:</h2><p>' + task['clientName'] + '</p>' +
                    '<h2>Тема:</h2><p>' + task['title'] + '</p>' +
                    '<h2>Заметки:</h2><p>' + task['notes'] + '</p>' +
                    '<h2>Статус:</h2><p>' + task['status'] + '</p>' +
                    '<div class="image-preview" style="' + imgPrevDisplay + '">' +
                    '<a href="' + imageUrl + '">' +
                    '<img id="img-preview" src="' + imageSrc + '"/>' +
                    '</a>' +
                    '</div>' +
                    '<br>' +
                    '<a id="btn-back" class="button medium-size" href="">Назад</a>' +
                    '<br><hr><br>' +
                    '<a id="btn-change-state" class="button medium-size" href="">' + completeBtnText + '</a>' +
                    editBtn
                );
            }else if(type === 'edit' || type === 'add'){
                $('#form-container').html('' +
                    '<form id="task" enctype="multipart/form-data">' +
                    '<input id="id" name="id" value="' + task['id'] + '" type="hidden">' +
                    '<input id="status" name="status" value="' + task['status'] + '" type="hidden">' +
                    '<div id="dropdown-menu-cont" class="input-group input-sm form-custom"><h2>Клиент:</h2>' +
                    '<button id="client-name" type="button" class="btn dropdown-toggle btn-default" ' +
                    'value="' + task['clientName'] + '">' +
                    '<span class="filter-option pull-left">' + task['clientName'] + '</span>' +
                    '<span class="bs-caret">&nbsp;&nbsp;<span class="caret"></span></span>' +
                    '</button></div>' +
                    '<div class="input-group input-sm form-custom"><h2>Тема:</h2>' +
                    '<input id="title" name="title" required="true" value="' + task['title'] + '" type="text"></div>' +
                    '<div class="input-group input-sm form-custom"><h2>Заметки:</h2>' +
                    '<textarea id="notes" name="notes" rows="4">' + task['notes'] + '</textarea></div>' +
                    '<div class="image-preview" style="' + imgPrevDisplay + '">' +
                    '<img id="img-preview" src="' + imageSrc + '"/>' +
                    '<button id="close-image" class="close" type="button" aria-label="Close" title="Удалить фото">' +
                    '<span aria-hidden="true">×</span></button>' +
                    '</div>' +
                    '<div id="file-container" style="' + imgBtnDisplay + '" class="input-group input-sm form-custom">' +
                    '<label id="lbl-file" class="btn btn-block btn-primary" for="file">Добавить фото</label>' +
                    '<input id="file" name="file" accept=".jpg,.jpeg,.png,.bmp" type="file">' +
                    '</div>' +
                    '<br>' +
                    '<button type="submit" class="button medium-size" value="Submit">ОК</button>' +
                    '<br><hr><br>' +
                    '<a id="btn-back" class="button medium-size" href="">Назад</a>' +
                    '</form>'
                );
            }

            $('#wrapper').animate({
                scrollTop: 0
            }, 700);

            $('button#client-name').on('click', function () {
                $('#wrapper').off('click', wrapperFunction);

                if($(this).parent().attr('class').indexOf('open') < 0)
                    showDropdownMenu($(this));
                else
                    hideDropdownMenu($(this).parent());

                setTimeout(function () {
                    $('#wrapper').on('click', wrapperFunction);
                }, 1);
            });

            $('#close-image').on('click', function(){
                $('div.image-preview').css('display', 'none');
                $('#img-preview').attr('src', '');
                $('#image-link').attr('href', '#');

                $('#lbl-file').text('Добавить фото');
                $('#file').val('');

                $('#file-container').css('display', 'table');
            });

            $('#lbl-file').on('click', function() {
                $('#lbl-file').text('Добавить фото');
                $('#file').val('');

                $('#img-preview').attr('src', '');
                $('#image-link').attr('href', '#');
            });

            $('#file').on('change', function() {

                var input = $('#file')[0];

                if (input.files && input.files[0] && input.files[0].size > 0) {
                    if(!(input.files[0].size < ${maxFileSize} * 1024 * 1024)){
                        alert("Размер файла больше\nдопустимых " + ${maxFileSize} + " Mb");

                        $('#lbl-file').text('Добавить фото');
                        $('#file').val('');
                    }else{
                        if (input.files[0].type.match('image.*')) {
                            $('#lbl-file').text(input.files[0].name);

                            var reader = new FileReader();
                            reader.onload = function (ev) {
                                $('#img-preview').attr('src', ev.target.result);
                            };
                            reader.readAsDataURL(input.files[0]);

                            $('div.image-preview').css('display', 'inherit');
                            $('#file-container').css('display', 'none');
                        }
                    }
                }
            });

            $('#btn-edit').on('click', function (e) {
                e.preventDefault();

                showForm(task['id'], 'edit');
            });

            $('#btn-change-state').on('click', function (e){
                e.preventDefault();

                if(task['status'] === completed)
                    changeTaskStatus(task['id'], 'resume');
                else
                    changeTaskStatus(task['id'], 'complete');
            });

            $('form#task').on('submit', function (e) {
                e.preventDefault();

                var task = {
                    id: $('form#task #id').val(),
                    clientName: $('form#task #client-name').val(),
                    title: $('form#task #title').val(),
                    notes: $('form#task #notes').val(),
                    status: $('form#task #status').val(),
                    imageBase64String: $('form#task img#img-preview').attr('src').split(',')[1]
                };

                var reqType = 'POST';

                if(type === 'edit')
                    reqType = 'PUT';

                $.ajax({
                    url: '/task/',
                    type: reqType,
                    contentType: "application/json",
                    data: JSON.stringify(task),
                    //dataType: 'json',
                    success: function(data, status) {
                        console.log('status: ' + status);
                    },
                    error: function (jqXHR) {
                        console.log('error: ' + jqXHR.status);
                    },
                    complete: function () {
                        hideForm();
                    }
                });
            });

            $('#btn-back').on('click', function (e){
                e.preventDefault();

                hideForm();
            });
        }

        function showForm(id, type){
            $('#overlay').css('display', 'block');
            $('#page-messge').css('display', 'none');

            $('#main-list').empty();

            loadTask(id);

            var loop = setInterval(function () {
                if(isTaskLoaded){
                    fillForm(type);
                    isTaskLoaded = false;
                    clearInterval(loop);
                }
            }, 100);
        }

        function hideForm(){
            $('#overlay').css('display', 'block');
            $('#page-messge').css('display', 'block');

            $('#form-container').empty();

            $('#wrapper').off();

            loadTasksList();
        }

        function changeTaskStatus(id, state){
            $.post('/task/state/' + state + '/' + id).done(function (data, status) {
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

        function getLogoFilesPath() {
            $.get('/logo_files_path').done(function (data, status) {
                if(status === "success"){

                    <%--$.each(data, function (key, actStatus) {--%>
                    <%--    if(actStatus['object'].indexOf('${plannedStatus}') > -1){--%>
                    <%--        planned = actStatus['value'];--%>
                    <%--    }else if(actStatus['object'].indexOf('${activeStatus}') > -1){--%>
                    <%--        active = actStatus['value'];--%>
                    <%--    }else if(actStatus['object'].indexOf('${completedStatus}') > -1){--%>
                    <%--        completed = actStatus['value'];--%>
                    <%--    }--%>
                    <%--});--%>

                    console.log(data);
                }
            }).fail(function (jqXHR) {

            });
        }

        $(document).ready(function (){
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");

            $.ajaxSetup({
                headers: {
                    'X-CSRF-Token': token
                }
            });

            $.get('/task/statuses').done(function (data, status) {
                if(status === "success"){
                    $.each(data, function (key, actStatus) {
                        if(actStatus['object'].indexOf('${plannedStatus}') > -1){
                            planned = actStatus['value'];
                        }else if(actStatus['object'].indexOf('${activeStatus}') > -1){
                            active = actStatus['value'];
                        }else if(actStatus['object'].indexOf('${completedStatus}') > -1){
                            completed = actStatus['value'];
                        }
                    });

                    loadTasksList();
                }
            }).fail(function (jqXHR) {

            });
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
