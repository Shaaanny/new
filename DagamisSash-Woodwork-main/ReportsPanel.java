import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class ReportsPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtSearch;
    private JSpinner datePicker;
    private ArrayList<Report> reports = new ArrayList<>();

    public ReportsPanel() {

        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(242, 245, 249));

        // === TITLE BAR ===
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new Color(242, 245, 249));
        titleBar.setBorder(new EmptyBorder(10, 20, 0, 20));

        JLabel title = new JLabel("Reports");
        title.setFont(new Font("Segoe UI Semibold", Font.BOLD, 28));
        title.setForeground(new Color(55, 71, 79));

        titleBar.add(title, BorderLayout.WEST);
        add(titleBar, BorderLayout.NORTH);

        // === FILTER BAR ===
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        filterPanel.setBackground(Color.WHITE);

        // Rounded search box
        txtSearch = new JTextField(22);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            "Search by Name",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.PLAIN, 12),
            new Color(100, 100, 100)
        ));

        // Date Picker
        datePicker = new JSpinner(new SpinnerDateModel());
        datePicker.setPreferredSize(new Dimension(180, 50));
        datePicker.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        datePicker.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            "Select Date",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.PLAIN, 12),
            new Color(100, 100, 100)
        ));

        // Filter Button
        JButton btnFilter = new JButton("Filter");
        btnFilter.setPreferredSize(new Dimension(120, 47));
        btnFilter.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
        btnFilter.setBackground(new Color(33, 150, 243));
        btnFilter.setForeground(Color.WHITE);
        btnFilter.setFocusPainted(false);
        btnFilter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFilter.setBorder(new LineBorder(new Color(21, 101, 192), 1, true));

        btnFilter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnFilter.setBackground(new Color(30, 136, 229));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnFilter.setBackground(new Color(33, 150, 243));
            }
        });

        // Add to panel
        filterPanel.setBorder(new CompoundBorder(
            new EmptyBorder(10, 20, 10, 20),
            new MatteBorder(0, 0, 1, 0, new Color(230, 230, 230))
        ));

        filterPanel.add(txtSearch);
        filterPanel.add(datePicker);
        filterPanel.add(btnFilter);

        add(filterPanel, BorderLayout.SOUTH);

        // === TABLE DESIGN ===
        String[] columns = {"ID", "Report Name", "Date", "Status"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        table.getTableHeader().setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(240, 240, 240));
        table.getTableHeader().setForeground(new Color(60, 60, 60));

        table.setSelectionBackground(new Color(200, 220, 250));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(225, 225, 225));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new EmptyBorder(10, 20, 20, 20));
        add(scroll, BorderLayout.CENTER);

        // === SAMPLE DATA ===
        reports.add(new Report(1, "Monthly Sales", "2025-11-01", "Completed"));
        reports.add(new Report(2, "Inventory Check", "2025-11-05", "Pending"));
        reports.add(new Report(3, "Employee Performance", "2025-11-10", "Completed"));

        loadReports();

        // === FILTER ACTION ===
        btnFilter.addActionListener(e -> filterReports());
    }

    private void loadReports() {
        model.setRowCount(0);
        for (Report r : reports) {
            model.addRow(new Object[]{r.id, r.name, r.date, r.status});
        }
    }

    private void filterReports() {
        model.setRowCount(0);

        String query = txtSearch.getText().trim().toLowerCase();
        Date selectedDate = (Date) datePicker.getValue();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String dateStr = sdf.format(selectedDate);

        for (Report r : reports) {
            boolean matchesName = r.name.toLowerCase().contains(query);
            boolean matchesDate = r.date.equals(dateStr);

            if (matchesName && matchesDate) {
                model.addRow(new Object[]{r.id, r.name, r.date, r.status});
            }
        }
    }

    // === REPORT MODEL ===
    static class Report {
        int id;
        String name, date, status;

        public Report(int id, String name, String date, String status) {
            this.id = id;
            this.name = name;
            this.date = date;
            this.status = status;
        }
    }
}
