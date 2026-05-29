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
package com.siu.oop.view;

import com.siu.oop.dao.ArtistDAO;
import com.siu.oop.dao.ChartDAO;
import com.siu.oop.model.AlbumChartDTO;
import com.siu.oop.model.Artist;
import com.siu.oop.service.AuthService;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class MainDashboard extends JFrame {
    private final ArtistDAO artistDAO = new ArtistDAO();
    private final ChartDAO chartDAO = new ChartDAO();
    private JTable table;
    private DefaultTableModel tableModel;
    
    private final JTextField txtSearch = new JTextField(15);
    private final JTextField txtArtistName = new JTextField(15);
    private final JButton btnSearch = new JButton("Tìm kiếm");
    private final JButton btnAdd = new JButton("Thêm");
    private final JButton btnUpdate = new JButton("Sửa");
    private final JButton btnDelete = new JButton("Xóa");
    private final JButton btnExport = new JButton("Xuất Excel");
    
    private final JLabel lblTotalArtists = new JLabel("Tổng số Nghệ sĩ: 0");
    private final JLabel lblTotalAlbums = new JLabel("Tổng số Album: 0");
    private final JLabel lblTopArtist = new JLabel("Nghệ sĩ Nổi bật: N/A");

    private final JComboBox<String> cbChartLimit = new JComboBox<>(new String[]{"Top 5", "Top 10", "Top 15"});

    private final JPanel barChartPanel = new JPanel(new BorderLayout());
    private final JPanel pieChartPanel = new JPanel(new BorderLayout());

    public MainDashboard() {
        var user = AuthService.getCurrentUser();
        setTitle("Chinook System Dashboard - Xin chào: " + (user != null ? user.displayName() : "Guest"));
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        boolean isAdmin = user != null && user.role().equalsIgnoreCase("Admin");
        btnAdd.setEnabled(isAdmin);
        btnUpdate.setEnabled(isAdmin);
        btnDelete.setEnabled(isAdmin);

        setLayout(new BorderLayout());

        // --- 1. THANH KPI STRIP ---
        JPanel pnlKPI = new JPanel(new GridLayout(1, 3, 10, 0));
        pnlKPI.setBorder(BorderFactory.createTitledBorder("Hệ thống chỉ số KPI"));
        pnlKPI.setBackground(new Color(240, 245, 250));
        
        styleKPILabel(lblTotalArtists);
        styleKPILabel(lblTotalAlbums);
        styleKPILabel(lblTopArtist);
        
        pnlKPI.add(lblTotalArtists);
        pnlKPI.add(lblTotalAlbums);
        pnlKPI.add(lblTopArtist);
        add(pnlKPI, BorderLayout.NORTH);

        // --- 2. CẤU TRÚC PHÂN TAB INTERFACE ---
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel pnlCRUD = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[]{"Artist ID", "Name"}, 0);
        table = new JTable(tableModel);
        pnlCRUD.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel pnlTopSearch = new JPanel(new FlowLayout());
        pnlTopSearch.add(new JLabel("Tìm kiếm tên:"));
        pnlTopSearch.add(txtSearch);
        pnlTopSearch.add(btnSearch);
        pnlTopSearch.add(btnExport);
        pnlCRUD.add(pnlTopSearch, BorderLayout.NORTH);

        JPanel pnlBottomCRUD = new JPanel(new FlowLayout());
        pnlBottomCRUD.add(new JLabel("Tên nghệ sĩ:"));
        pnlBottomCRUD.add(txtArtistName);
        pnlBottomCRUD.add(btnAdd);
        pnlBottomCRUD.add(btnUpdate);
        pnlBottomCRUD.add(btnDelete);
        pnlCRUD.add(pnlBottomCRUD, BorderLayout.SOUTH);

        JPanel pnlAnalytics = new JPanel(new BorderLayout());
        JPanel pnlChartFilter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlChartFilter.add(new JLabel("Bộ lọc hiển thị biểu đồ: "));
        pnlChartFilter.add(cbChartLimit);
        pnlAnalytics.add(pnlChartFilter, BorderLayout.NORTH);

        JPanel pnlSplitCharts = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlSplitCharts.add(barChartPanel);
        pnlSplitCharts.add(pieChartPanel);
        pnlAnalytics.add(pnlSplitCharts, BorderLayout.CENTER);

        tabbedPane.addTab("Quản lý Dữ liệu", pnlCRUD);
        tabbedPane.addTab("Thống kê & Biểu đồ (2 Loại)", pnlAnalytics);
        add(tabbedPane, BorderLayout.CENTER);

        // --- 3. SỰ KIỆN ---
        btnSearch.addActionListener(e -> updateAllData());
        cbChartLimit.addActionListener(e -> updateAllData());
        
        btnAdd.addActionListener(e -> {
            if(!txtArtistName.getText().isBlank()) {
                artistDAO.save(new Artist(0, txtArtistName.getText()));
                updateAllData();
                txtArtistName.setText("");
            }
        });
        btnUpdate.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow >= 0 && !txtArtistName.getText().isBlank()) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                artistDAO.update(new Artist(id, txtArtistName.getText()));
                updateAllData();
                txtArtistName.setText("");
            }
        });
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if(selectedRow >= 0) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                artistDAO.delete(id);
                updateAllData();
                txtArtistName.setText("");
            }
        });
        btnExport.addActionListener(e -> exportToExcel());

        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                txtArtistName.setText(tableModel.getValueAt(selectedRow, 1).toString());
            }
        });

        SwingUtilities.invokeLater(this::updateAllData);
    }

    private void styleKPILabel(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(30, 80, 140));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEtchedBorder());
    }

    // --- PHIÊN BẢN ĐÃ GỘP: CHỈ DÙNG 1 LUỒNG DUY NHẤT ---
    private void updateAllData() {
        new SwingWorker<DashboardDataBundle, Void>() {
            @Override
            protected DashboardDataBundle doInBackground() {
                // Fetch toàn bộ dữ liệu 1 lần
                String keyword = txtSearch.getText().trim();
                List<Artist> artists = keyword.isEmpty() ? artistDAO.findAll() : artistDAO.searchByName(keyword);
                List<AlbumChartDTO> chartData = chartDAO.getTopArtistsByAlbums();
                
                return new DashboardDataBundle(artists, chartData);
            }

            @Override
            protected void done() {
                try {
                    DashboardDataBundle data = get();
                    
                    // 1. Cập nhật Bảng
                    tableModel.setRowCount(0);
                    for (Artist a : data.artists) {
                        tableModel.addRow(new Object[]{a.artistId(), a.name()});
                    }
                    
                    // 2. Cập nhật KPI
                    int totalArtists = data.artists.size();
                    int totalAlbums = data.chartData.stream().mapToInt(AlbumChartDTO::albumCount).sum();
                    String topArtist = data.chartData.isEmpty() ? "N/A" : data.chartData.get(0).artistName();
                    
                    lblTotalArtists.setText("Tổng số Nghệ sĩ: " + totalArtists);
                    lblTotalAlbums.setText("Tổng số Album: " + totalAlbums);
                    lblTopArtist.setText("Nghệ sĩ nhiều Album nhất: " + topArtist);
                    
                    // 3. Cập nhật Biểu đồ
                    updateCharts(data.chartData);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    private void updateCharts(List<AlbumChartDTO> data) {
        if (data == null || data.isEmpty()) return;
        String selectedFilter = cbChartLimit.getSelectedItem().toString();
        int limit = Integer.parseInt(selectedFilter.replace("Top ", ""));

        // Bar Chart
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        data.stream().limit(limit).forEach(item -> barDataset.addValue(item.albumCount(), "Albums", item.artistName()));
        JFreeChart barChart = ChartFactory.createBarChart("Xếp hạng", "Nghệ sĩ", "Số lượng", barDataset, org.jfree.chart.plot.PlotOrientation.VERTICAL, false, true, false);
        barChartPanel.removeAll();
        barChartPanel.add(new ChartPanel(barChart), BorderLayout.CENTER);
        barChartPanel.validate();

        // Pie Chart
        DefaultPieDataset<String> pieDataset = new DefaultPieDataset<>();
        data.stream().limit(limit).forEach(item -> pieDataset.setValue(item.artistName(), item.albumCount()));
        JFreeChart pieChart = ChartFactory.createPieChart("Tỷ lệ Album", pieDataset, true, true, false);
        pieChartPanel.removeAll();
        pieChartPanel.add(new ChartPanel(pieChart), BorderLayout.CENTER);
        pieChartPanel.validate();
    }

    // Helper class để đóng gói dữ liệu
    private static class DashboardDataBundle {
        List<Artist> artists;
        List<AlbumChartDTO> chartData;
        DashboardDataBundle(List<Artist> a, List<AlbumChartDTO> c) { this.artists = a; this.chartData = c; }
    }

    private void exportToExcel() { /* Giữ nguyên code cũ của bạn */ }
}