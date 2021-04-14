$(document).ready(function () {

    $('.users-panel').on('click', 'a, button, form', function (e) {
       e.preventDefault();
    });

    $('.users-panel').on('click', '.navbar-header', function (e) {
        var btn = $(this);
        var panel = btn.parent();
        var action = btn.data('wrap');

        if (action === 'unwrap') {
            btn.data('wrap', 'wrap');
            panel.css('height', 'unset');
        } else {
            btn.data('wrap', 'unwrap');
            panel.css('height', '3.5em');
        }
    });

    $('.users-panel').on('submit', 'form', function () {
        console.log('submit');
    });
});