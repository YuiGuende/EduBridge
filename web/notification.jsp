<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page session="true" %>
<%
    // Giả sử bạn đã lưu userId trong session sau khi login
    Integer userId = 999;
%>
<!DOCTYPE html>
<html>
<head>
    <title>Thông báo realtime</title>
    <style>
        #notificationBox {
            border: 1px solid #ccc;
            padding: 10px;
            width: 400px;
            height: 300px;
            overflow-y: auto;
            background-color: #f9f9f9;
            font-family: Arial, sans-serif;
        }

        #notificationBox ul {
            list-style-type: none;
            padding-left: 0;
        }

        #notificationBox li {
            background-color: #fff;
            border: 1px solid #ddd;
            padding: 8px;
            margin-bottom: 5px;
            border-radius: 5px;
        }

        #notificationTitle {
            font-weight: bold;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<h2>🔔 Thông báo</h2>
<div id="notificationBox">
    <div id="notificationTitle">Tin mới:</div>
    <ul id="notificationList"></ul>
</div>

<script>
    // WebSocket kết nối server - chú ý thay tên project nếu khác
    const userId = <%= userId %>;
    const socket = new WebSocket("ws://" + window.location.host + "/EduBridge/notification?userId=" + userId);

    const list = document.getElementById("notificationList");

    socket.onopen = function () {
        const li = document.createElement("li");
        li.textContent = "🟢 Đã kết nối WebSocket.";
        list.appendChild(li);
    };

    socket.onmessage = function (event) {
        const li = document.createElement("li");
        li.textContent = "📩 " + event.data;
        list.prepend(li); // thêm vào đầu danh sách
    };

    socket.onerror = function (err) {
        const li = document.createElement("li");
        li.textContent = "❌ Lỗi kết nối WebSocket.";
        list.appendChild(li);
    };
</script>

</body>
</html>
