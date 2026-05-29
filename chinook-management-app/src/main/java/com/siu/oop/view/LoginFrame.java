package com.siu.oop.view;

import com.siu.oop.service.AuthService;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField txtUser = new JTextField(15);
    private final JPasswordField txtPass = new JPasswordField(15);
    private final JButton btnLogin = new JButton("Đăng nhập");
    private final AuthService authService = new AuthService();

    public LoginFrame() {
        setTitle("Hệ thống Chinook - Đăng nhập");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0; add(new JLabel("Tài khoản:"), gbc);
        gbc.gridx = 1; add(txtUser, gbc);
        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1; add(txtPass, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        add(btnLogin, gbc);

        btnLogin.addActionListener(e -> {
            String username = txtUser.getText();
            String password = new String(txtPass.getPassword());
            if (authService.login(username, password)) {
                dispose();
                SwingUtilities.invokeLater(() -> new MainDashboard().setVisible(true));
            } else {
                JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}