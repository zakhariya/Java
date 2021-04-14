<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Emails list getter</title>
    <link href="/css/style.css" rel="stylesheet">
    <script src="/js/jquery-3.4.1.min.js"></script>
    <script src="/js/emails.js"></script>
    <script>
        var urlGetEmailList = '${urlGetEmailList}';
    </script>
</head>
<body>
<header id="header">
    <h1>Получить список e-mail со страниц</h1>
    <nav>
        <ul>
            <li><a href="/">&lArr; назад</a></li>
        </ul>
    </nav>
</header>
<main>
    <div id="wrapper">
        <div id="content">
            <div>
                <h2>Список адресов страниц:</h2>
                <form id="urls-list">
                    <textarea rows="20" cols="70" ></textarea>
                    <button type="submit">GO</button>
                    <button type="reset">Clear</button>
                </form>
            </div>
            <div id="result-container">
                <h2>Результат:</h2>
                <ul></ul>
            </div>
        </div>
        <aside>
            <span>Time gone: </span>
            <span id="timer">00:00:00</span>
        </aside>
        <div id="sub-content">
            <div id="invalid-list">
                <h2>Invalid links:</h2>
                <ol></ol>
            </div>
        </div>
    </div>
</main>
<footer id="footer">
    <section>
        <span>Developed by <a href="mailto:alexander.zakhariya@gmail.com">alexander.zakhariya@gmail.com</a></span>
    </section>
    <span id="footer-down"><img src="/img/down.png"></span>
</footer>
</body>
</html>