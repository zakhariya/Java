jQuery(document).ready(function($jq) {
    var count = getCommentsCount();
    var curCount = 0;
    var repliesCount = 0;
    var oDiv = null;
    var file = null;
    var imageInShow = false;
    var content =
        '<div id="comments">' +
        '<ol id="list" class="commentlist"></ol>' +
        '<a id="more_reviews">загрузить еще ▼</a>' +
        '</div>' +
        '<div id="add-review-btn">' +
        '<a href="#add-review" class="open_modal button shadow">Оставить отзыв</a>' +
        '</div>' +
        '<div id="add-review" class="modal_div_container close_modal">' +
        '<div id="review-modal-div" class="modal_div">' +
        '<span class="close_modal">X</span>' +
        '</div>' +
        '</div>';// +
    //'<div id="modal-overlay" class="close_modal" style="display: none;"></div>';
    var form =
        '<form class="form" enctype="multipart/form-data">' +
        '<textarea name="review" required="" placeholder="Сообщние..." /></textarea>' +
        '<input type="text" name="name" autocomplete="name" placeholder="Ваше имя (обязательно)" required="" />' +
        '<input type="text" name="company" autocomplete="organization" placeholder="Название компании" />' +
        '<br>' +
        '<input type="email" name="email" autocomplete="email" placeholder="Ваш e-mail" />' +
        '<input type="url" name="site" autocomplete="url" placeholder="Сайт" />' +
        '<br>' +
        '<label for="image" class="lbl_file button submit">Добавить фото</label>' +
        '<input type="submit" name="add_review" class="submit" value="Отправить" />' +
        '<input id="image" class="image_preview_btn" type="file" name="image" autocomplete="photo" accept=".jpg,.jpeg,.png" />' +
        '<div class="image_preview_cont">' +
        '<div class="remove_image"></div><image class="image_preview" src="" />'+
        '</div>' +
        '<br>' +
        '<input id="parent_id" type="hidden" name="parent" value="0" />' +
        '</form>';

    $jq('#main .entry').append(content);

    loadList(3, 0);

    lookingForReplyButtons();

    $jq('#comments #more_reviews').on('click', function (e) {
        e.preventDefault();

        loadList(5, 0);
    });

    $jq(document.body).on('touchmove', tryToLoad);

    $jq(window).on('scroll', tryToLoad);

    $jq('#add-review-btn a').on('click', showReviewModalForm);

    $jq('.close_modal').on('click', function () {
        $jq('div#review-modal-div form.form').remove();
    });

    $jq(window).resize(function(){
        changeImagePosition();
    });

    function getCommentsCount() {
        var n = 0;

        $jq.ajax({
            type: 'POST',
            url: zData.url,
            data : {
                security: zData.nonce,
                action: 'z_plugin_count_reviews'
            },
            async : false
        }).done(function(data) {
            data = JSON.parse(data);
            var text = data.count;
            var status = data.status;

            if (status === 'success') {
                n = parseInt(text);
            } else {
                $jq('#list').append('Не удалось загрузить...');
                console.log('Error: ' + data.error);
            }
        });

        return n;
    }

    function loadList(offset, parent) {
        var start = 0;

        for (var i = 0; i < offset; i++) {

            if(curCount >= count) {
                $jq('#comments #more_reviews').remove();

                break;
            }

            $jq('#list').append(
                '<li>' +
                '<div id="" ' +
                'class="comment_container loading num_' + curCount + '"></div>' +
                '</li>'
            );

            if (parent > 0) {
                start = i;
                repliesCount++;
            } else {
                start = curCount - repliesCount;
            }

            $jq.ajax({
                type: 'POST',
                url: zData.url,
                data: {
                    security: zData.nonce,
                    action: 'z_plugin_get_review',
                    start: start,
                    num: curCount,
                    parent: parent
                },
                beforeSend: function(xhr, opts) {
                    curCount++;

                    if(curCount >= count) {
                        $jq('#comments #more_reviews').remove();
                    }
                },
                success: function(response) {
                    response = JSON.parse(response);
                    var review = response.review;
                    var num = response.num;
                    var container = $jq('div.num_' + num);

                    container.removeClass('loading');

                    if (response.status !== 'success') {
                        container.append('Не удалось загрузить...');

                        console.log(response.status + ': ' + response.error);

                        return;
                    }

                    if (review.id === null) {
                        container.remove();

                        return;
                    }

                    fillContainer(container, review);

                    if (review.parent > 0) {
                        var parent = $jq('div#' + review.parent);
                        parent.first('div.comment_entry').append(container);

                        var color = hexc(parent.css('backgroundColor'));

                        if (color !== '#ffffff') {
                            container.css('backgroundColor', '#ffffff');
                        } else {
                            container.css('backgroundColor', 'aliceblue');
                        }
                    }

                    if (parseInt(review.have_reply) > 0) {
                        loadList(parseInt(review.have_reply), parseInt(review.id));
                    }
                },
                error: function(jqXHR) {
                    console.log('Сервер не отвечает,\nпопробуйте позже');
                },
                complete: function() {

                }
            });
        }

        tryToLoad();

        lookingForReplyButtons();
    }

    function fillContainer(container, review) {
        container.attr('id', review.id);
        container.append(getContent(review));
    }

    function getContent(review) {
        var date = new Date(review.date);
        var image = '';
        var company = '';

        if (review.image_data !== null) {
            image = '<img class="new" alt="' + review.image_name + '" ' +
                'title="' + review.image_name + '" src="' + review.image_data + '" />';
        }

        if (review.company.length > 0) {
            company =
                '<span class="company">' +
                '<span class="company_name">' + review.company + '</span>' +
                '<span class="company_site">' +
                '<a href="' + review.url + '" target="_blank">' + review.url + '</a>' +
                '</span>'+
                '</span>'
        }

        var content =
            '<div class="comment_head">' +
            '<span class="name">' + review.author + '</span>' +
            '<span class="date">' + getDate(date) + ' в ' + getTime(date) + '</span>' +
            company +
            '</div>' +
            '<div class="comment_entry">' +
            '<p>' + review.comment + '</p>' +
            '<p>' + image + '</p>' +
            '<div class="add_reply_container">' +
            '<a href="' + review.id + '" class="button transparent reply_btn new">Ответить</a>' +
            '<div class="add_reply_form parent_' + review.id + '"></div>' +
            '</div>' +
            '</div>';

        return content;
    }

    function getDate(date) {
        var dd = date.getDate();
        if (dd < 10) dd = '0' + dd;

        var mm = date.getMonth() + 1;
        if (mm < 10) mm = '0' + mm;

        var yy = date.getFullYear(); //% 100;
        if (yy < 10) yy = '0' + yy;

        return dd + '.' + mm + '.' + yy;
    }

    function getTime(date) {
        var hh = date.getHours();
        if (hh < 10) hh = '0' + hh;

        var mm = date.getMinutes();
        if (mm < 10) mm = '0' + mm;

        return hh + ':' + mm;
    }

    function isVisibleSpace() {
        var footerH = $jq('#footer').height();

        if(($jq(window).scrollTop() + window.innerHeight >= document.body.scrollHeight - footerH)
            && !$jq('div').is('.loading'))
            return true;

        return false;
    }

    function tryToLoad() {
        if(isVisibleSpace() && curCount < count){
            loadList(3, 0);
        }
    }

    function hexc(colorval) {
        var parts = colorval.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);

        delete(parts[0]);

        for (var i = 1; i <= 3; ++i) {
            parts[i] = parseInt(parts[i]).toString(16);
            if (parts[i].length == 1) parts[i] = '0' + parts[i];
        }
        return '#' + parts.join('');
    }

    function addRepDiv(e){
        e.preventDefault();

        var id = $jq(this).attr('href');
        var div = $jq('div.add_reply_form.parent_'+id);

        if(div.html() === ''){
            div.append(form);
            div.animate({height: 'show'}, 400);

            $jq('form.form input#parent_id').val(id);

            $jq('form.form input.image_preview_btn').on('change', imageAdd);

            $jq('form.form div.remove_image').on('click', imageRemove);

            $jq('form.form').on('submit', submitForm);

            if(oDiv != null && !oDiv.is(div)){
                oDiv.animate({height: 'hide'}, 400);
                setTimeout(function(){
                    oDiv.empty();
                }, 399);
            }
            setTimeout(function(){
                oDiv = div;
            }, 399);
        }else{
            div.animate({height: 'hide'}, 400);
            setTimeout(function(){
                div.empty();
            }, 399);
        }
    }

    function submitForm(e) {
        e.preventDefault();

        $jq('#overlay-loader').css('display', 'block');

        var formData = new FormData(this);

        formData.append('action', 'z_plugin_add_review');
        formData.append('security', zData.nonce);

        $jq.ajax({
            type: 'POST',
            url: zData.url,
            data: formData,
            cache: false,
            processData: false,
            contentType: false,
            beforeSend: function(xhr, opts) {

            },
            success: function(response) {
                var response = JSON.parse(response);
                var status = response.status;

                $jq('#overlay-loader').css('display', 'none');

                console.log(response.review);

                if(status === 'success') {
                    showMessage('Ваш отзыв принят');

                    if(oDiv != null){
                        oDiv.animate({height: 'hide'}, 400);
                        setTimeout(function(){
                            oDiv.empty();
                        }, 399);
                    }
                }else{
                    showMessage('Не удалось оставить отзыв...\n' + status);
                }
            },
            error: function(jqXHR) {
                $jq('#overlay-loader').css('display', 'none');

                showMessage('Сервер не отвечает,\nпопробуйте позже');
            },
            complete: function() {

            }
        });
    }

    function imageAdd() {
        var input = $jq('form.form input.image_preview_btn')[0];
        var filesExt = ['jpg', 'jpeg', 'png'];
        var parts = $jq(this).val().split('.');

        if (input.files && input.files[0] && input.files[0].size > 0) {
            if(!(input.files[0].size < 5 * 1024 * 1024)){
                showMessage("Размер файла больше\nдопустимых " + 5 + " Mb");
                $jq('form.form input.image_preview-btn').val('');
            }else if(filesExt.join().search(parts[parts.length - 1].toLowerCase()) === -1){
                $jq('form.form input.image_preview-btn').val('');
                $jq('#image').wrap('<form>').closest('form').get(0).reset();
                $jq('#image').unwrap();
                showMessage('Неподдерживаемый тип файла .' + parts[parts.length - 1]);
            }else{
                if (input.files[0].type.match('image.*')) {
                    $jq('div.image_preview_cont').addClass('loading');
                    $jq('img.image_preview').attr('src', '');

                    $jq('form.form .lbl_file').text(input.files[0].name);

                    var reader = new FileReader();
                    reader.onload = function (ev) {
                        $jq('form.form img.image_preview').attr('src', ev.target.result);
                    };
                    reader.readAsDataURL(input.files[0]);

                    $jq('div#review-modal-div').css('max-height', (window.innerHeight - window.innerHeight * 0.2) + 'px');
                    $jq('div.image_preview_cont').css('display', 'inherit');
                }else{
                    $jq('form.form input.image_preview-btn').val('');
                    $jq('#image').wrap('<form>').closest('form').get(0).reset();
                    $jq('#image').unwrap();
                }
            }
        }else{
            $jq('label.lbl_file').text('Добавить фото');
            $jq('img.image_preview').attr('src', '');
            $jq('div.image_preview_cont').css('display', 'none');
        }
    }

    function imageRemove() {
        $jq('label.lbl_file').text('Добавить фото');
        $jq('form.form input.image_preview-btn').val('');
        $jq('img.image_preview').attr('src', '');
        $jq('div.image_preview_cont').css('display', 'none');
        $jq('#image').wrap('<form>').closest('form').get(0).reset();
        $jq('#image').unwrap();
    }

    function lookingForReplyButtons(){
        var i = 0;
        var loop = setInterval(function () {
            if(!$jq('a').is('.reply_btn.new') && ++i >= 30){
                clearInterval(loop);
            }else{
                var replyBtn = $jq('a.reply_btn.new');
                var image = $jq('div.comment_entry img.new');

                replyBtn.removeClass('new');
                image.removeClass('new');

                replyBtn.on('click', addRepDiv);

                image.on('click', showImage);
            }
        }, 500);
    }

    function showImage() {
        $jq('body').on('click', '#close-popup, #overlay, #magnify', function(event) {
            event.preventDefault();

            $jq('#overlay, #magnify').fadeOut('fast', function() {
                $jq('#close-popup, #magnify, #overlay').remove();
            });

            imageInShow = false;
        });

        if(!imageInShow){
            var i_path = $jq(this).attr('src');

            $jq('body').append('<div id="overlay"></div><div id="magnify"><img src="'+i_path+'"><div id="close-popup"><i></i></div></div>');

            $jq('#overlay, #magnify').fadeIn(500);

            changeImagePosition();

            imageInShow = true;
        }else{
            imageInShow = false;
        }
    }

    function changeImagePosition(){
        $jq('#magnify').css({
            left: ($jq(document).width() - $jq('#magnify').outerWidth())/2,
            top: ($jq(window).height() - $jq('#magnify').outerHeight())/2
        });
    }

    function showReviewModalForm() {
        $jq('div#review-modal-div').append(form);

        $jq('form.form').on('change', 'input.image_preview_btn', imageAdd);

        $jq('form.form div.remove_image').on('click', imageRemove);

        $jq('form.form').on('submit', function () {
            $jq('div#review-modal-div span.close_modal').click();
        });

        $jq('form.form').on('submit', submitForm);

        if(oDiv != null){
            oDiv.animate({height: 'hide'}, 600);

            setTimeout(function(){
                oDiv.empty();
            }, 599);
        }
        setTimeout(function(){
            $jq('div#add-review').css('display', 'block').animate({opacity: 1, top: '5%'}, 300);
        }, 450);
    }
});