package com.siu.oop.service;

import com.siu.oop.model.User;
import org.mindrot.jbcrypt.BCrypt;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private final Map<String, String> userDatabase = new HashMap<>();
    private final Map<String, String> roleDatabase = new HashMap<>();
    private final Map<String, String> nameDatabase = new HashMap<>();
    private static User currentUser;

    public AuthService() {
        // Tạo tài khoản mẫu với mật khẩu gốc hash bảo mật qua BCrypt
        // Tài khoản quản trị: đăng nhập bằng admin / mật khẩu admin123
        userDatabase.put("admin", BCrypt.hashpw("admin123", BCrypt.gensalt()));
        roleDatabase.put("admin", "Admin");
        nameDatabase.put("admin", "Administrator Huy");

        // Tài khoản chỉ xem: đăng nhập bằng viewer / mật khẩu viewer123
        userDatabase.put("viewer", BCrypt.hashpw("viewer123", BCrypt.gensalt()));
        roleDatabase.put("viewer", "Viewer");
        nameDatabase.put("viewer", "Guest Viewer");
    }

    public boolean login(String username, String password) {
        if (userDatabase.containsKey(username)) {
            String hashedPassword = userDatabase.get(username);
            if (BCrypt.checkpw(password, hashedPassword)) {
                currentUser = new User(username, roleDatabase.get(username), nameDatabase.get(username));
                return true;
            }
        }
        return false;
    }

    public static void logout() {
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}