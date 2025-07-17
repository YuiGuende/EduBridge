package util;

import java.util.HashMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AuthenticationUtilIT {

    private AuthenticationUtil instance;

    // Class cần test
    public static class AuthenticationUtil {
        private HashMap<String, String> users = new HashMap<>();

        private boolean isValidEmail(String email) {
            return email != null && email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
        }

        private boolean isValidFullName(String fullName) {
            if (fullName == null) return false;
            fullName = fullName.trim();
            return fullName.length() >= 3 && fullName.length() <= 50 && fullName.matches("^[a-zA-Z\\s]+$");
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

        // Dành cho test: cho phép thêm user
        public void addUser(String email, String password) {
            users.put(email, password);
        }
    }

    @Before
    public void setUp() {
        instance = new AuthenticationUtil();
        // Tạo sẵn 1 user cho test login
        instance.addUser("user@example.com", "password123");
    }

    @After
    public void tearDown() {
        instance = null;
    }

    // ==== TEST SIGNUP ====
    @Test
    public void testSignup_Valid() {
        boolean result = instance.signup("newuser@gmail.com", "New User", "newpass123");
        assertTrue(result);
    }

    @Test
    public void testSignup_InvalidEmail() {
        boolean result = instance.signup("invalid@", "New User", "newpass123");
        assertFalse(result);
    }

    @Test
    public void testSignup_ShortPassword() {
        boolean result = instance.signup("user2@gmail.com", "New User", "123");
        assertFalse(result);
    }

    @Test
    public void testSignup_ShortName() {
        boolean result = instance.signup("user3@gmail.com", "Jo", "newpass123");
        assertFalse(result);
    }

    @Test
    public void testSignup_ExistingEmail() {
        instance.addUser("user4@gmail.com", "pass");
        boolean result = instance.signup("user4@gmail.com", "Another Name", "pass123");
        assertFalse(result);
    }

    // ==== TEST LOGIN ====
    @Test
    public void testLogin_Valid() {
        boolean result = instance.login("user@example.com", "password123");
        assertTrue(result);
    }

    @Test
    public void testLogin_WrongPassword() {
        boolean result = instance.login("user@example.com", "wrongpass");
        assertFalse(result);
    }

    @Test
    public void testLogin_NotExistEmail() {
        boolean result = instance.login("unknown@gmail.com", "somepass");
        assertFalse(result);
    }
}
