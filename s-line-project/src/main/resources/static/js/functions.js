$(document).ready(function () {

    $('#top-nav-bar .account-info .dropdown').on('click', function (e) {
        e.preventDefault();
    });

    $(".logout-btn").on('click', function (e) {
        e.preventDefault();

        doLogout();
    });
});

function detectBrowserName() {
    var isOpera = (!!window.opr && !!opr.addons) || !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;
    var isFirefox = typeof InstallTrigger !== 'undefined';
    var isSafari = /constructor/i.test(window.HTMLElement) || (function (p) { return p.toString() === "[object SafariRemoteNotification]"; })(!window['safari'] || (typeof safari !== 'undefined' && safari.pushNotification));
    var isIE = /*@cc_on!@*/false || !!document.documentMode;
    var isEdge = !isIE && !!window.StyleMedia;
    var isChrome = !!window.chrome && (!!window.chrome.webstore || !!window.chrome.runtime);
    var isBlink = (isChrome || isOpera) && !!window.CSS;

    var browserName = "";

    if (isIE || isEdge) {
        browserName = "IE";
    } else if (isOpera) {
        browserName = "Opera";
    } else if (isFirefox) {
        browserName = "FF";
    } else if (isChrome) {
        browserName = "Chrome";
    } else if (isSafari) {
        browserName = "Safari";
    }

    return browserName;
}

function doLogout() {
    var secureUrl = '/admin';
    var redirectUrl = '/';
    var browserName = detectBrowserName();

    if (browserName === "IE") {
        document.execCommand('ClearAuthenticationCache', 'false');
    } else if (browserName === "Opera" || browserName === "Chrome") {
        $.ajax({
            url: secureUrl,
            type: 'GET',
            beforeSend: function(request) {
                request.setRequestHeader("Authorization", "Basic bG9nb3V0");
            },
            error: function () {
                window.location.href = redirectUrl;
            }
        });
    } else if (browserName === "FF") {
        $.ajax({
            async: false,
            url: secureUrl,
            type: 'GET',
            username: 'logout',
            error: function () {
                window.location.href = redirectUrl;
            }
        });
    } else {
        alert("Can't logout");
    }
}

