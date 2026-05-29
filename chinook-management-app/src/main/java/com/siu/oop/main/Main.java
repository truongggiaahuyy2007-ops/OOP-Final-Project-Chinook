/*
 * Food Nutrition Management System
 *
 * Developed by:
 * Truong Gia Huy
Student ID: 87482503626
 * Nguyen Ngoc Nhu Y
 *Student ID: 87482503633
 * Course: Object Oriented Programming
 */
package com.siu.oop.main;

import com.siu.oop.view.LoginFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Đảm bảo khởi tạo giao diện trên Event Dispatch Thread (EDT) theo chuẩn quy định Swing
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}