<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<<<<<<< HEAD
=======

>>>>>>> origin/duong_jpa
    <head>
        <title>Báo cáo lạm dụng</title>
        <style>
            /*        body {
                        font-family: Arial, sans-serif;
                    }
                    #reportModal {
                        display: none;
                        position: fixed;
                        top: 50%; left: 50%;
                        transform: translate(-50%, -50%);
                        background: white;
                        padding: 24px;
                        border-radius: 10px;
                        box-shadow: 0 0 15px rgba(0,0,0,0.3);
                        width: 450px;
                        z-index: 1001;
                    }
                    .modal-overlay {
                        display: none;
                        position: fixed;
                        top: 0; left: 0;
                        width: 100%; height: 100%;
                        background: rgba(0,0,0,0.5);
                        z-index: 1000;
                    }
                    .modal-header {
                        font-size: 20px;
                        font-weight: bold;
                        margin-bottom: 12px;
                    }
                    .modal-body {
                        margin-top: 10px;
                    }
                    select, textarea {
                        width: 100%;
                        padding: 8px;
                        margin-top: 6px;
                        margin-bottom: 12px;
                    }
                    .btn {
                        padding: 10px 18px;
                        border: none;
                        border-radius: 6px;
                        font-weight: bold;
                        cursor: pointer;
                    }
                    .btn-primary {
                        background-color: #5624d0;
                        color: white;
                    }
                    .btn-secondary {
                        background-color: #ddd;
                    }
                    .btn-group {
                        text-align: right;
                    }*/
            .btn {
                padding: 10px 16px;
                border: none;
                border-radius: 6px;
                font-weight: bold;
                cursor: pointer;
                font-size: 14px;

            }
            .btn-primary {
                background-color: #5624d0;
                color: white;
            }
            .report-button{
                width: 250px;
            }
            .btn-secondary {
                background-color: #e4e6eb;
                color: #333;
            }

            #reportModal {
                display: none;
                position: fixed;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background: white;
                padding: 20px;
                border-radius: 12px;
                box-shadow: 0 8px 30px rgba(0, 0, 0, 0.2);
                width: 90%;
                max-width: 360px;
                z-index: 1001;
            }
            .modal-overlay {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(0, 0, 0, 0.4);
                z-index: 1000;
            }
            .modal-header {
                font-size: 18px;
                font-weight: 600;
                color: #222;
                margin-bottom: 12px;
                display: flex;
                align-items: center;
                gap: 8px;
            }
            .modal-body label {
                display: block;
                margin-top: 12px;
                font-weight: 500;
                font-size: 14px;
            }
            select, textarea {
                width: 100%;
                padding: 8px 10px;
                margin-top: 6px;
                border: 1px solid #ccc;
                border-radius: 6px;
                font-size: 14px;
            }
            textarea {
                resize: vertical;
            }
            .btn-group {
                margin-top: 20px;
                text-align: right;
            }
        </style>
    </head>
    <body>
        <!-- Nút bấm mở form -->
        <!--        <button class="btn btn-primary" onclick="openModal()">Báo cáo lạm dụng</button>
        
                 Overlay + Modal 
                <div class="modal-overlay" id="modalOverlay"></div>
                <div id="reportModal">
                    <div class="modal-header">Báo cáo lạm dụng</div>
                    <div class="modal-body">
                        <p>
                            Nhân viên sẽ xem xét nội dung bị gắn cờ để xác định xem nội dung đó có vi phạm Điều khoản dịch vụ hoặc Nguyên tắc cộng đồng hay không.
                            Nếu bạn có câu hỏi hoặc gặp vấn đề kỹ thuật, vui lòng liên hệ với <a href="#">nhóm hỗ trợ tại đây</a>.
                        </p>
                        <form method="post" action="report">
        
                            <input type="hidden" name="learnerId" value="2"/>
                            <input type="hidden" name="action" value="create"/>
                            <input type="hidden" name="targetId" value="1"/>
                            <input type="hidden" name="reportType" value="COURSE"/>
        
                            <label for="type">Loại vấn đề</label>
                            <select name="type" required>
                                <option value="">Chọn một vấn đề</option>
                                <option value="INAPPROPRIATE_HARMFUL_CONTENT">Nội dung khóa học không phù hợp – Có hại, bạo lực, thù hận hoặc tội phạm</option>
                                <option value="INAPPROPRIATE_OTHER">Nội dung khóa học không phù hợp – Khác</option>
                                <option value="MISCONDUCT">Hành vi không phù hợp</option>
                                <option value="POLICY_VIOLATION">Vi phạm chính sách của Udemy</option>
                                <option value="SPAM">Nội dung rác</option>
                                <option value="OTHER">Ý khác</option>
                            </select>
        
                            <label for="reason">Thông tin về vấn đề</label>
                            <textarea name="reason" rows="4" required></textarea>
        
                            <div class="btn-group">
                                <button type="button" class="btn btn-secondary" onclick="closeModal()">Hủy</button>
                                <button type="submit" class="btn btn-primary">Gửi</button>
                            </div>
                        </form>
                    </div>
                </div>-->
        <div style="text-align: right; margin: 40px auto;">
            <button class="btn btn-primary report-button" onclick="openModal()">
                <i class="fas fa-flag"></i> Báo cáo lạm dụng
            </button>
        </div>

        <!-- Modal Overlay -->
        <div class="modal-overlay" id="modalOverlay"></div>

        <!-- Modal Form -->
        <div id="reportModal">
            <div class="modal-header">Báo cáo lạm dụng</div>
            <div class="modal-body">
                <p style="font-size: 14px; color: #333;">
                    Nếu bạn thấy nội dung vi phạm chính sách hoặc không phù hợp, hãy báo cáo để chúng tôi xem xét.
                    Mọi báo cáo sẽ được kiểm duyệt kỹ lưỡng.
                </p>
                <form method="post" action="report">
                    <input type="hidden" name="learnerId" value="${user.id}" />
                    <input type="hidden" name="action" value="create"/>
                    <input type="hidden" name="targetId" value="${course.id}" />
                    <input type="hidden" name="reportType" value="COURSE" />

                    <label for="type">Loại vấn đề</label>
                    <select name="type" required>
                        <option value="">-- Chọn --</option>
                        <option value="INAPPROPRIATE_HARMFUL_CONTENT">Nội dung không phù hợp (bạo lực, thù hận...)</option>
                        <option value="INAPPROPRIATE_OTHER">Nội dung không phù hợp (khác)</option>
                        <option value="MISCONDUCT">Hành vi không phù hợp</option>
                        <option value="POLICY_VIOLATION">Vi phạm chính sách</option>
                        <option value="SPAM">Nội dung rác</option>
                        <option value="OTHER">Khác</option>
                    </select>

                    <label for="reason">Chi tiết</label>
                    <textarea name="reason" rows="3" placeholder="Mô tả vấn đề..." required></textarea>

                    <div class="btn-group">
                        <button type="button" class="btn btn-secondary" onclick="closeModal()">Hủy</button>
                        <button type="submit" class="btn btn-primary">Gửi</button>
                    </div>
                </form>
            </div>
        </div>
        <script>
            function openModal() {
                document.getElementById("modalOverlay").style.display = "block";
                document.getElementById("reportModal").style.display = "block";
            }

            function closeModal() {
                document.getElementById("modalOverlay").style.display = "none";
                document.getElementById("reportModal").style.display = "none";
            }
        </script>
    </body>
</html>
