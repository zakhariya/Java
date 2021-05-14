$(document).ready(function () {
    textarea = $('textarea');
    resultList = $('div#result-container ul');
    invalidList = $('div#invalid-list ol');
    clock = $('span#timer');
    buttons = $('button[type=submit], button[type=reset]');

    $('form#urls-list').on('submit', function (e) {
        e.preventDefault();

        sendRequest(textarea.val().split('\n'));
    });

    buttons.on('click', function () {
        textarea.css('border-color', '');
        resultList.html('');
        invalidList.html('');
        invalidList.parent().css('visibility', 'hidden');
        clock.text('00:00:00');
    });
});

function sendRequest(urls) {
    var invalid = [];

    $.each(urls, function (index, value) {
        if (!isUrlValid(value)){
            var url = 'http://' + value;

            if (isUrlValid(url)) {
                urls[index] = url;
            } else {
                invalid.push(value);
            }
        }
    });

    if (invalid.length > 0) {
        invalidList.parent().css('visibility', 'visible');

        $.each(invalid, function (index, value) {
            invalidList.append('<li>' + value + '</li>');

            urls = jQuery.grep(urls, function(val) {
                return val != value;
            });
        });
    }

    var timer, sec = 0;

    $.ajax({
        type: 'POST',
        url: '/emails/get-list',
        dataType: 'json',
        contentType:'application/json',
        data: JSON.stringify(urls),
        beforeSend: function(xhr, opts) {
            if (invalid.length > 0) {
                // xhr.abort();
            }

            if (xhr.status !== 0) {
                showLoading(true);

                timer = setInterval(function() {
                    setClock(++sec);
                }, 1000);
            }
        },
        success: function(response) {
            showLoading(false);

            clearInterval(timer);

            showResult(response['emails']);
            showErrors(response['urlErrors']);
        },
        error: function(xhr) {
            showLoading(false);

            clearInterval(timer);

            alert("Ошибка: " + xhr.status
                + "\n" + xhr.responseText);
        },
        complete: function() {

        }
    });
}

function showResult(emails) {
    $.each(emails, function (key, value) {
        resultList.append('<li>' + value + '</li>');
    });
}

function showErrors(errors) {
    $.each(errors, function (key, value) {
        invalidList.append('<li>' + key + ': ' + value + '</li>');
        invalidList.parent().css('visibility', 'visible');
    });
}

function showLoading(bool) {
    buttons.attr('disabled', bool);
    if (bool) {
        resultList.append('<img src="/img/loader.gif" />');
    } else {
        resultList.html('');
    }
}

function setClock(sec) {
    var mins = parseInt(sec/60, 10);
    var hours = parseInt(mins/60, 10);


    var clockSet = getPartOfClockSet(hours) + ':'
        + getPartOfClockSet(mins % 60) + ':' + getPartOfClockSet(sec % 60);

    clock.text(clockSet);
}

function getPartOfClockSet(time) {
    var part = '';

    if (time < 10) {
        part += '0';
    }

    return part + time;
}