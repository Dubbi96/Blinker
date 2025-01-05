<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
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
        .login-container {
            width: 320px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .login-header {
            background-color: #b63829;
            color: white;
            text-align: center;
            padding: 15px;
            font-size: 1.2em;
            font-weight: bold;
            border-top-left-radius: 8px;
            border-top-right-radius: 8px;
        }
        .login-body {
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
        .form-group input {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 0.9em;
        }
        .login-button {
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
        .login-button:hover {
            background: linear-gradient(135deg, #333, #555);
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="login-header">Login</div>
        <div class="login-body">
            <form id="login-form">
                <div class="form-group">
                    <label for="username">아이디</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password">암호</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit" class="login-button">로그인</button>
            </form>
        </div>
    </div>
    <script>
    document.addEventListener('DOMContentLoaded', () => {
        const form = document.getElementById('login-form');

        form.addEventListener('submit', async (event) => {
            event.preventDefault();
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;

            try {
                const response = await fetch('/auth/sign-in', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ username, password }),
                });
                const data = await response.json();
                console.log('Response Data:', data); // 응답 데이터 확인

                if (data.code === "SUCCESS") {
                    const token = data.response;
                    localStorage.setItem('Authorization', `Bearer ` + token);
                    window.location.href = '/main';
                } else {
                    alert(data.message || '로그인 실패');
                }
            } catch (error) {
                alert('로그인 중 오류 발생. 다시 시도해주세요.');
                console.error(error);
            }
        });
    });
    </script>
</body>
</html>