// 全局变量存储学生数据
let studentsData = [];

// 显示通知
function showNotification(message, isError = false) {
  const notification = document.getElementById('notification');
  const messageSpan = document.getElementById('notificationMessage');

  messageSpan.textContent = message;
  notification.classList.remove('error');

  if (isError) {
    notification.classList.add('error');
  }

  notification.classList.add('show');

  // 5秒后自动关闭通知
  setTimeout(() => {
    notification.classList.remove('show');
  }, 5000);
}

// 关闭通知
function closeNotification() {
  document.getElementById('notification').classList.remove('show');
}

// 清空输入框
function clearInput(inputId) {
  document.getElementById(inputId).value = '';
}

// 加载所有学生数据
function loadAllStudents() {
  const tbody = document.getElementById('studentTableBody');
  tbody.innerHTML = '<tr><td colspan="6" class="loading">正在加载学生数据...</td></tr>';

  // 发送GET请求获取学生数据
  fetch('http://127.0.0.1:8060/student/users')
      .then(response => {
        if (!response.ok) {
          throw new Error('服务器响应错误');
        }
        return response.json();
      })
      .then(data => {
        if (data.code !== 200) {
          throw new Error(data.message || '获取数据失败');
        }

        studentsData = data.data || [];
        renderStudents(studentsData);
        showNotification(`成功加载 ${studentsData.length} 名学生数据`);
      })
      .catch(error => {
        console.error('数据加载失败:', error);
        tbody.innerHTML = '<tr><td colspan="6" class="no-data">数据加载失败，请重试</td></tr>';
        showNotification('数据加载失败: ' + error.message, true);
      });
}

// 渲染学生数据到表格
function renderStudents(students) {
  const tbody = document.getElementById('studentTableBody');

  if (!students || students.length === 0) {
    tbody.innerHTML = '<tr><td colspan="6" class="no-data">没有找到学生数据</td></tr>';
    return;
  }

  let htmlContent = '';

  students.forEach(student => {
    htmlContent += `
                    <tr>
                        <td>${student.name || '--'}</td>
                        <td>${student.gender || '--'}</td>
                        <td>${student.age || '--'}</td>
                        <td>${student.college || '--'}</td>
                        <td>${student.classname || '--'}</td>
                        <td>
                            <div class="actions">
                                <button class="action-btn view-btn" onclick="viewStudent(${student.id})">查看</button>
                                <button class="action-btn edit-btn" onclick="editStudent(${student.id})">编辑</button>
                                <button class="action-btn delete-btn" onclick="deleteStudent(${student.id})">删除</button>
                            </div>
                        </td>
                    </tr>
                `;
  });

  tbody.innerHTML = htmlContent;
}

// 按姓名搜索学生
function searchStudentByName() {
  const searchTerm = document.getElementById('searchName').value.trim();

  if (!searchTerm) {
    renderStudents(studentsData);
    showNotification('已显示所有学生');
    return;
  }

  const tbody = document.getElementById('studentTableBody');
  tbody.innerHTML = '<tr><td colspan="6" class="loading">正在搜索学生数据...</td></tr>';

  // 使用POST请求搜索接口
  fetch('http://127.0.0.1:8060/student/info', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ name: searchTerm })
  })
      .then(response => {
        if (!response.ok) {
          throw new Error('服务器响应错误');
        }
        return response.json();
      })
      .then(data => {
        if (data.code !== 200) {
          throw new Error(data.message || '搜索失败');
        }

        const searchResults = data.data || [];
        renderStudents(searchResults);
        showNotification(`找到 ${searchResults.length} 条匹配记录`);
      })
      .catch(error => {
        console.error('搜索失败:', error);
        tbody.innerHTML = '<tr><td colspan="6" class="no-data">搜索失败: ' + error.message + '</td></tr>';
        showNotification('搜索失败: ' + error.message, true);
      });
}

// 查看学生详情
function viewStudent(studentId) {
  const student = studentsData.find(s => s.id === studentId);
  if (student) {
    showNotification(`学生详情：${student.name} | ${student.gender} | ${student.age}岁 | ${student.college} | ${student.classname}`);
  } else {
    showNotification('未找到学生信息', true);
  }
}

// 打开编辑模态框
function editStudent(studentId) {
  const student = studentsData.find(s => s.id === studentId);
  if (!student) {
    showNotification('未找到学生信息', true);
    return;
  }

  // 填充6个核心字段数据
  document.getElementById('editId').value = student.id;
  document.getElementById('editName').value = student.name || '';
  document.getElementById('editGender').value = student.gender || '';
  document.getElementById('editAge').value = student.age || '';
  document.getElementById('editCollege').value = student.college || '';
  document.getElementById('editClass').value = student.classname || '';

  // 打开模态框
  document.getElementById('editModal').classList.add('active');
}

// 关闭编辑模态框
function closeEditModal() {
  document.getElementById('editModal').classList.remove('active');
}

// 提交更新表单 - POST请求仅包含6个核心字段
function submitUpdate() {
  const form = document.getElementById('editForm');
  const studentId = parseInt(document.getElementById('editId').value);
  const student = studentsData.find(s => s.id === studentId);

  if (!student) {
    showNotification('更新失败: 未找到学生信息', true);
    closeEditModal();
    return;
  }

  // 从表单获取6个核心字段数据
  const updatedData = {
    id: studentId,
    name: document.getElementById('editName').value,
    gender: document.getElementById('editGender').value,
    age: parseInt(document.getElementById('editAge').value),
    college: document.getElementById('editCollege').value,
    classname: document.getElementById('editClass').value
  };

  // 显示加载状态
  showNotification('正在更新学生信息...');

  // 发送POST请求更新数据
  fetch('http://127.0.0.1:8060/student/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(updatedData)
  })
      .then(response => {
        const contentType = response.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
          return response.json();
        } else {
          return response.text().then(text => {
            throw new Error(`服务器返回了非JSON数据：${text.substring(0, 100)}...`);
          });
        }
      })
      .then(data => {
        if (data.code === 200) {
          // 更新本地数据
          const index = studentsData.findIndex(s => s.id === studentId);
          if (index !== -1) {
            studentsData[index] = {...studentsData[index], ...updatedData};
          }

          renderStudents(studentsData);
          closeEditModal();
          showNotification('学生信息更新成功');
        } else {
          showNotification('更新失败: ' + (data.message || '未知错误'), true);
        }
      })
      .catch(error => {
        console.error('更新请求错误:', error);
        let errorMessage = error.message;

        // 错误处理
        if (error.message.includes('Unexpected token')) {
          errorMessage = '服务器返回了无效数据';
        } else if (error.message.includes('failed to fetch')) {
          errorMessage = '无法连接到服务器';
        }

        showNotification(`更新失败: ${errorMessage}`, true);
      });
}

// 删除学生
function deleteStudent(studentId) {
  const student = studentsData.find(s => s.id === studentId);
  if (student) {
    if (confirm(`确定要删除学生 ${student.name} 吗？此操作不可恢复。`)) {
      // 显示加载状态
      showNotification('正在删除中...');

      fetch('http://127.0.0.1:8060/student/delete', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ id: studentId })
      })
          .then(response => {
            const contentType = response.headers.get('content-type');
            if (contentType && contentType.includes('application/json')) {
              return response.json();
            } else {
              return response.text().then(text => {
                throw new Error(`服务器返回了非JSON数据：${text.substring(0, 100)}...`);
              });
            }
          })
          .then(data => {
            if (data.code === 200) {
              studentsData = studentsData.filter(s => s.id !== studentId);
              renderStudents(studentsData);
              showNotification(data.message || `学生 ${student.name} 已被成功删除`);
            } else {
              showNotification('删除失败: ' + (data.message || '未知错误'), true);
            }
          })
          .catch(error => {
            console.error('删除请求错误:', error);
            let errorMessage = error.message;

            if (error.message.includes('Unexpected token')) {
              errorMessage = '服务器返回了无效数据';
            } else if (error.message.includes('failed to fetch')) {
              errorMessage = '无法连接到服务器';
            }

            showNotification(`删除失败: ${errorMessage}`, true);
          });
    }
  } else {
    showNotification('未找到学生信息', true);
  }
}