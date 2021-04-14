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

    var clicks = 0;

    $('span#footer-down').on('click', function () {
        $('footer#footer').animate({opacity: 0, bottom: '0%'}, 200, function(){
            $(this).css('height', '1em');
            $(this).css('font-size', '0.8em');

            clicks++;

            if (clicks >= 2) {
                $(this).css('display', 'none');
            }

            $('footer#footer').animate({opacity: 1}, 200);
        });
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
        url: urlGetEmailList,
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

function isUrlValid(url) {
    return /^(https?|s?ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(url);
}