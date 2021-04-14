$(document).ready(function () {
    console.log("admin.js");

    $.ajax({
        type: 'GET',
        //используем CORS-proxy для кросс-домен запроса
        url: 'https://cors-anywhere.herokuapp.com/https://lpr.ua',
        crossDomain: true,
        // data: null,
        // dataType: 'jsonp',
        // contentType: 'application/json',
        // headers: {
        //     'Access-Control-Allow-Credentials' : true,
        //     'Access-Control-Allow-Origin':'*',
        //     'Access-Control-Allow-Methods':'GET',
        //     'Access-Control-Allow-Headers':'application/json'
        // },
        beforeSend: function (xhr, opts) {
            if (xhr.status !== 0) {
                console.log('Before: ' + xhr.responseText);
            }
        },
        success: function (response) {
            console.log('Success: ' + response.toString());
        },
        error: function (xhr) {
            console.log('Error: ' + xhr.status + ' ' + xhr.responseText + '\n' + xhr);
        },
        complete: function () {
            console.log('completed');
        }
    });
});