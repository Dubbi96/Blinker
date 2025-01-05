<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f9f9f9;
        }
        .signup-container {
            width: 320px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .signup-header {
            background-color: #b63829;
            color: white;
            text-align: center;
            padding: 15px;
            font-size: 1.2em;
            font-weight: bold;
            border-top-left-radius: 8px;
            border-top-right-radius: 8px;
        }
        .signup-body {
            padding: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-size: 0.9em;
            color: #333;
        }
        .form-group input,
        .form-group select {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 0.9em;
        }
        .signup-button {
            margin-top: 10px;
            width: 100%;
            padding: 10px;
            font-size: 1em;
            background: linear-gradient(135deg, #444, #666);
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-transform: uppercase;
        }
        .signup-button:hover {
            background: linear-gradient(135deg, #333, #555);
        }
    </style>
</head>
<body>
    <div class="signup-container">
        <div class="signup-header">Sign Up</div>
        <div class="signup-body">
            <form id="signup-form">
                <div class="form-group">
                    <label for="username">아이디</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password">암호</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="form-group">
                    <label for="role">관리등급</label>
                    <select id="role" name="role">
                        <option value="USER">USER</option>
                        <option value="ADMIN">ADMIN</option>
                    </select>
                </div>
                <button type="submit" class="signup-button">회원가입</button>
            </form>
        </div>
    </div>
    <script>
        document.getElementById('signup-form').addEventListener('submit', async (event) => {
            event.preventDefault();

            const username = document.getElementById('username').value.trim();
            const password = document.getElementById('password').value.trim();
            const role = document.getElementById('role').value;

            try {
                const response = await fetch('/auth/sign-up', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ username, password, role }),
                });

                const data = await response.json();
                if (data.code === "SUCCESS") {
                    alert('회원가입 성공! 로그인 화면으로 이동합니다.');
                    window.location.href = '/auth/sign-in';
                } else {
                    alert(data.message || '회원가입 실패');
                }
            } catch (error) {
                console.error('회원가입 오류:', error);
                alert('회원가입 중 오류가 발생했습니다.');
            }
        });
    </script>
</body>
</html>