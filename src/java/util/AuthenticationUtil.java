/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.HashMap;

/**
 *
 * @author DELL
 */
public class AuthenticationUtil {

    private HashMap<String, String> users = new HashMap<>();

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
    
    private boolean isValidFullName(String fullName) {
        if (fullName == null) {
            return false;
        }
        fullName = fullName.trim();
        return fullName.length() >= 3 && fullName.length() <= 50
                && fullName.matches("^[a-zA-Z\\s]+$");
    }
    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
    public boolean signup(String email, String fullname, String password) {
        return isValidEmail(email) && isValidFullName(fullname) && isValidPassword(password) && !users.containsKey(email);
    }

    public boolean login(String email, String password) {
        return users.containsKey(email) && users.get(email).equals(password);
    }
}
