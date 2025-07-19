<%-- 
    Document   : fail
    Created on : Jul 19, 2025, 12:41:45 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Payment Failed</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background: #f9f9f9;
                padding: 40px;
                text-align: center;
            }
            .fail-box {
                display: inline-block;
                background: #fff;
                border: 2px solid #1A2A6C;
                border-radius: 8px;
                padding: 40px;
            }
            .fail-box h2 {
                color: #1A2A6C;
            }
            .fail-box p {
                margin: 1rem 0;
            }
            .back-link {
                display: inline-block;
                margin-top: 20px;
                padding: 10px 20px;
                background: #1A2A6C;
                color: #fff;
                text-decoration: none;
                border-radius: 4px;
            }
            .back-link:hover {
                background: #142052;
            }
        </style>
    </head>
    <body>

        <div class="fail-box">
            <h2>Payment Failed</h2>
            <p>Something went wrong with your payment.</p>
            <a href="cart" class="back-link">Back to Cart</a>
        </div>

    </body>
</html>
