var main = {
    init: function () {
        var _this = this;
        $('#btn-member-save').on('click', function () {
            _this.memberSave()
        });

        $('#btn-post-save').on('click', function () {
            _this.postSave();
        });

    },
    memberSave: function () {
        var data = {
            name: $('#name').val(),
            nickname: $('#nickname').val(),
            email: $('#email').val(),
            password: $('#password').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/members',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('가입을 축하드려요.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    postSave: function () {
        var data = {
            author: $('#author').val(),
            title: $('#title').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글 등록 성공!');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }


}
main.init()