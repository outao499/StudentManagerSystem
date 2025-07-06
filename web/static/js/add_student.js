async function addStudent() {
    // 获取表单值
    const name = document.getElementById('name').value.trim();
    const age = document.getElementById('age').value.trim();
    const college = document.getElementById('college').value.trim();
    const className = document.getElementById('className').value.trim();

    // 获取选中的性别
    const gender = document.querySelector('input[name="gender"]:checked').value;

    // 获取错误消息元素
    const errorElement = document.getElementById('errorMessage');
    errorElement.textContent = '';

    // 表单验证
    if (!name) {
        errorElement.textContent = '请填写学生姓名';
        return;
    }

    if (!age) {
        errorElement.textContent = '请填写学生年龄';
        return;
    }

    if (!college) {
        errorElement.textContent = '请填写学生学院';
        return;
    }

    if (!className) {
        errorElement.textContent = '请填写学生班级';
        return;
    }

    // 年龄验证
    const ageNum = parseInt(age);
    if (isNaN(ageNum) || ageNum < 1 || ageNum > 100) {
        errorElement.textContent = '年龄必须在1-100岁之间';
        return;
    }

    try {
        // 创建学生数据对象
        const studentData = {
            name: name,
            gender: gender,
            age: ageNum,
            college: college,
            classname: className
        };

        // 发送POST请求到后端
        const response = await fetch('http://127.0.0.1:8060/student/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(studentData)
        });

        // 处理响应
        const result = await response.json();

        if (response.ok && result.code === 200) {
            // 添加成功
            alert(result.message || '学生信息添加成功！');

            // 清空表单
            document.getElementById('name').value = '';
            document.getElementById('age').value = '';
            document.getElementById('college').value = '';
            document.getElementById('className').value = '';

            // 重定向到学生查询页面
            window.location.href = 'search_students.html';
        } else {
            // 显示服务器返回的错误消息
            errorElement.textContent = result.message || '添加学生信息失败';
        }
    } catch (error) {
        console.error('添加学生错误:', error);
        errorElement.textContent = '无法连接到服务器，请稍后重试';
    }
}