$(document).ready(function () {
    $('.input-group .dropdown-menu a').on('click', function (e) {
        e.preventDefault();

        var text = $(this).text();

        $('.input-group-btn button.dropdown-toggle').html(function(i,oldHtml){
            return oldHtml.replace($(this).text(), text);
        });

        var type = $(this).attr('href');
        var url = $('.input-group input[type="text"]').val();

       $.ajax({
           type: 'GET',
           url: '/links/get',
           dataType: 'json',
           contentType:'application/json',
           data: {
               'type' : type,
               'url' : url
           },
           beforeSend: function(xhr, opts) {
               if (!isUrlValid(url)) {
                   xhr.abort();
               }

               if (xhr.status !== 0) {
                   // showLoading(true);
               }
           },
           success: function(response) {
               // showLoading(false);
               console.log(response);
               // showResult(response['emails']);
               // showErrors(response['urlErrors']);
           },
           error: function(xhr) {
               // showLoading(false);
               //
               // clearInterval(timer);
               //
               // alert("Ошибка: " + xhr.status
               //     + "\n" + xhr.responseText);
           },
           complete: function() {

           }
       });
    });
});

// .append("<table id=\"content\" border=\"1\"><tbody></tbody></table>")
//        StringBuffer buffer =
//                new StringBuffer(String.format("<tr class=\"s-%d\">", link.getStatus()));
//
//        buffer.append("<td>");
//        buffer.append(link.getUrl());
//        buffer.append("</td>");
//        buffer.append("<td>");
//        buffer.append(link.getStatus());
//        buffer.append("</td>");
//        buffer.append("<td>");
//        buffer.append(link.getType());
//        buffer.append("</td>");
//        buffer.append("<td>");
//        buffer.append("<ul>");
//
//        for (String parent : link.getParentPages()) {
//            buffer.append("<li>");
//            buffer.append(parent);
//            buffer.append("</li>");
//        }
//
//        buffer.append("</ul>");
//        buffer.append("</td>");
//
//        htmlDocument.select("#content tbody").append(buffer.toString());