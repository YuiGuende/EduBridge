/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.HashMap;
import java.util.regex.Pattern;

public class AuthenticationUtil {

    private final HashMap<String, String> users = new HashMap<>();

    // Biểu thức regex cho email, full name
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern NAME_REGEX = Pattern.compile("^[\\p{L}\\s]{3,50}$");

    // Kiểm tra email hợp lệ
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_REGEX.matcher(email).matches();
    }

    // Kiểm tra họ tên hợp lệ (3–50 ký tự, chỉ chữ và khoảng trắng)
    public static boolean isValidFullName(String fullName) {
        return fullName != null && NAME_REGEX.matcher(fullName.trim()).matches();
    }

    // Kiểm tra mật khẩu >= 6 ký tự
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    // Kiểm tra confirm password
    public static boolean isPasswordConfirmed(String password, String confirm) {
        return password != null && password.equals(confirm);
    }

    // Hàm dùng để test hoặc mô phỏng đăng ký
    public boolean signup(String email, String fullname, String password) {
        return isValidEmail(email)
                && isValidFullName(fullname)
                && isValidPassword(password)
                && !users.containsKey(email);
    }

    public boolean login(String email, String password) {
        return users.containsKey(email) && users.get(email).equals(password);
    }
}
