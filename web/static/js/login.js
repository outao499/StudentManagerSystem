$(document).ready(function () {
    $('#loginForm').on('submit', function (e) {
        e.preventDefault(); // 阻止表单默认提交行为

        const username = $('#username').val();
        const password = $('#password').val();

        $.ajax({
            url: 'http://127.0.0.1:8060/student/login', // 替换为你的实际路径
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ username, password }), // 发送 JSON 数据
            success: function (response) {
                console.log('登录请求:', response);

                if (response.code === 200) {
                    alert('登录成功');
                    window.location.href = 'dashboard.html'; // 跳转到首页
                } else {
                    $('#errorMessage').text(response.message || '登录失败');
                }
            },
            error: function (xhr, status, error) {
                console.error('登录失败:', error);
                $('#errorMessage').text(this.response.message);
            }
        });
    });
});
