<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>AI Chat Widget</title>
        <!-- Bootstrap 5 -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Font Awesome for chat icon -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

        <!-- Style -->
        <style>
            .chat-icon {
                position: fixed;
                bottom: 20px;
                right: 20px;
                width: 60px;
                height: 60px;
                display: flex;
                justify-content: center;
                align-items: center;
                font-size: 24px;
                cursor: pointer;
                z-index: 999;
            }

            .chat-box {
                position: fixed;
                bottom: 90px;
                right: 20px;
                width: 330px;
                max-height: 500px;
                display: none;
                z-index: 1000;
                border-radius: 12px;
            }

            .chat-messages {
                height: 300px;
                overflow-y: auto;
                padding: 10px;
                background-color: #f9f9f9;
                display: flex;
                flex-direction: column;
            }

            .chat-bubble {
                padding: 8px 12px;
                border-radius: 16px;
                margin: 6px 0;
                max-width: 90%;
                word-wrap: break-word;
            }

            .user-msg {
                background-color: #dcf8c6;
                align-self: flex-end;
                margin-left: auto;
            }

            .ai-msg {
                background-color: #f1f0f0;
                align-self: flex-start;
                margin-right: auto;
            }

            .card-footer input[type="text"] {
                border-radius: 20px;
            }

            .card-footer button {
                border-radius: 20px;
            }
        </style>
    </head>
    <body>

        <!-- Chat Icon Button -->
        <div id="chat-toggle" class="chat-icon btn btn-primary rounded-circle">
            <i class="bi bi-chat-dots-fill"></i>
        </div>

        <!-- Chat Box -->
        <div id="chat-box" class="chat-box card shadow-sm">
            <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
                <span>Trợ lý AI</span>
                <button id="close-chat" class="btn-close btn-close-white" aria-label="Close"></button>
            </div>
            <div class="card-body chat-messages" id="chat-messages">
                <c:forEach items="${sessionScope.conversations}" var="c">
                    <div class="chat-bubble user-msg"><strong>Bạn:</strong> ${c.request}</div>
                    <div class="chat-bubble ai-msg">
                        <strong>AI:</strong>
                        <c:out value="${c.response}" escapeXml="false"/>
                    </div>
                </c:forEach>
            </div>
            <div class="card-footer p-2">
                <form action="suggestion-ai-agent" method="get" class="d-flex">
                    <input name="message" type="text" class="form-control me-2" placeholder="Nhập tin nhắn..." required>
                    <button type="submit" class="btn btn-primary">Gửi</button>
                </form>
            </div>
        </div>



        <!-- JavaScript -->
        <script>
            const chatToggle = document.getElementById('chat-toggle');
            const chatBox = document.getElementById('chat-box');
            const closeChat = document.getElementById('close-chat');

            chatToggle.addEventListener('click', () => {
                chatBox.style.display = 'block';
                chatToggle.style.display = 'none';
            });

            closeChat.addEventListener('click', () => {
                chatBox.style.display = 'none';
                chatToggle.style.display = 'flex';
            });
        </script>

    </body>
</html>
