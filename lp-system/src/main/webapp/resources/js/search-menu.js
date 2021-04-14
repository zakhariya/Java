$(document).ready(function () {
    reqCount = 0;
});

function showDropdownMenu(button) {
    var container = button.parent();
    container.addClass('open');

    container.remove('div.dropdown-menu.open');

    var menu = '' +
        '<div class="dropdown-menu open">' +
        '<div class="bs-searchbox">' +
        '<input id="search-field" class="form-control" autocomplete="off" type="text"></div>' +
        '<ul id="dropdown-menu-list" class="dropdown-menu inner"></ul>' +
        '</div>';

    container.append(menu);
    fillDropdownMenuList($('ul#dropdown-menu-list'), $('input#search-field'), 'default');

    $('input#search-field').on('click', function () {
        $('#wrapper').off('click', wrapperFunction);

        setTimeout(function () {
            $('#wrapper').on('click', wrapperFunction);
        }, 1);
    });

    // $('input#search-field').focusout(function () {
    //     //hideDropdownMenu(container);
    //     //console.log('focusout gdff');
    // });

    $('input#search-field').on('keyup', function (){
        fillDropdownMenuList($('ul#dropdown-menu-list'), $('input#search-field'), 'like');
    });

    $('input#search-field').focus();
}

function hideDropdownMenu(container) {
    $('#' + container.attr('id') + ' div.dropdown-menu.open').remove();

    container.removeClass('open');
}

function fillDropdownMenuList(listCont, searchField, type) {

    listCont.empty();

    var ddm = listCont.parent();
    var scrlPos = 0;

    ddm.off();
    ddm.on('scroll', {ddm:ddm, listCont:listCont, scrlPos:scrlPos}, ddmFunction);

    var list = '' +
        '<li>' + //class="selected active"
        '<a>' +
        '<span class="text">без клиента</span></a>' +
        '</li>';

    var button = ddm.parent().find('button#client-name');

    listCont.append(list);

    var url = '';
    var data = '';

    if(type == 'default'){
        url = '/client/list-limit';
        data = {limit:10, offset:0};
    }else{
        ddm.off();

        var v = $('input#search-field').val();
        var l = v.length;
        var s = '';

        for(var i = 0; i < l; i++ ){
            if(v[i] == ' ')
                s += ', ';
            else
                s += v[i];
        }

        if(s.length < 1){
            fillDropdownMenuList(listCont, searchField, 'default');

            return;
        }

        url = '/client/list-like';
        data = {partsOfName:s};
    }

    var reqNum = ++reqCount;

    $.get(url, data).done(function (data, status) {
        if(status == 'success'){
            $.each(data, function (key, client) {
                if(reqNum != reqCount)
                    return;

                list = '' +
                    '<li>' +
                    '<a>' +
                    '<span class="text">' + client['name'] + '</span></a>' +
                    '</li>';

                listCont.append(list);
            });
        }
        $('#' + listCont.attr('id') + ' li').on('click', function () {
            var clientName = $(this).find('span.text').text();
            button.val(clientName).find('span.filter-option').text(clientName);
        });
    }).fail(function (jqXHR) {
        if(jqXHR.status == 403){ // Forbidden
            alert('Сессия завершена. Необходима повторная авторизация.');
        }else if(jqXHR.status == 500){
            alert('Внутренняя ошибка сервера');
        }

        $(location).attr("href", '/${user.post}');
    });
}

function addToList(ddm, listCont, scrlPos) {
    var count = $('#' + listCont.attr('id') + ' li').length;

    $.get('/client/list-limit', {limit:3, offset:count}).done(function (data, status) {
        if(status == 'success'){
            if(typeof data[0]['name'] ==  undefined)
                return;

            list = '' +
                '<li class="new">' +
                '<a>' +
                '<span class="text">' + data[0]['name'] + '</span></a>' +
                '</li>';

            listCont.append(list);
        }else {
            return;
        }
        $('#' + listCont.attr('id') + ' li.new').on('click', function () {
            var clientName = $(this).find('span.text').text();
            var button = listCont.parent().parent().find('button#client-name');
            button.val(clientName).find('span.filter-option').text(clientName);
        });
        $('#' + listCont.attr('id') + ' li.new').removeClass('new');
        ddm.on('scroll', {ddm:ddm, listCont:listCont, scrlPos:scrlPos}, ddmFunction);
    }).fail(function (jqXHR) {
        if(jqXHR.status == 403){ // Forbidden
            alert('Сессия завершена. Необходима повторная авторизация.');
        }else if(jqXHR.status == 500){
            alert('Внутренняя ошибка сервера');
        }

        $(location).attr("href", '/${user.post}');
    });

}

function ddmFunction(e) {
    var scrollHeight = e.data.ddm.height();
    var scrollPosition = e.data.ddm.height() + e.data.ddm.scrollTop();

    if(scrollPosition > e.data.scrlPos){
        e.data.scrlPos = scrollPosition;
        addToList(e.data.ddm, e.data.listCont , e.data.scrlPos);

        e.data.ddm.off('scroll', ddmFunction);
    }
}
