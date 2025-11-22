import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;

// Custom JPanel for the Sales by Item report view
public class SalesByItemPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtSearch;
    private JSpinner datePicker;

    private static final Color BACKGROUND_COLOR = new Color(242, 245, 249);
    private static final Color HEADER_COLOR = new Color(74, 184, 102); 

    public SalesByItemPanel() {
        setLayout(new BorderLayout(0, 0));
        setBackground(BACKGROUND_COLOR);

        // === HEADER BAR (Simple Green Bar) ===
        JPanel headerBar = new JPanel(new BorderLayout());
        headerBar.setBackground(HEADER_COLOR);
        headerBar.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("Reports - Sales by Item");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        headerBar.add(title, BorderLayout.WEST);
        add(headerBar, BorderLayout.NORTH);

        // === FILTER BAR ===
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        filterPanel.setBackground(Color.WHITE);

        // Search box
        txtSearch = new JTextField(22);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            "Search by Item Name",
            TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("Segoe UI", Font.PLAIN, 12), 
            new Color(100, 100, 100)
        ));

        // Date Picker (using the simpler Date model for date selection)
        datePicker = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(datePicker, "yyyy-MM-dd");
        datePicker.setEditor(dateEditor);
        datePicker.setPreferredSize(new Dimension(180, 50));
        datePicker.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        datePicker.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            "Select Date",
            TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("Segoe UI", Font.PLAIN, 12), 
            new Color(100, 100, 100)
        ));

        // Filter Button
        JButton btnFilter = new JButton("Generate Report");
        btnFilter.setPreferredSize(new Dimension(150, 47));
        btnFilter.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
        btnFilter.setBackground(new Color(33, 150, 243));
        btnFilter.setForeground(Color.WHITE);
        btnFilter.setFocusPainted(false);
        btnFilter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFilter.setBorder(new LineBorder(new Color(21, 101, 192), 1, true));

        // Add to panel
        filterPanel.setBorder(new CompoundBorder(
            new EmptyBorder(10, 20, 10, 20),
            new MatteBorder(0, 0, 1, 0, new Color(230, 230, 230))
        ));

        filterPanel.add(txtSearch);
        filterPanel.add(datePicker);
        filterPanel.add(btnFilter);

        // Add filter panel inside a wrapper to center it
        JPanel filterWrapper = new JPanel(new BorderLayout());
        filterWrapper.setOpaque(false);
        filterWrapper.add(filterPanel, BorderLayout.NORTH);
        
        add(filterWrapper, BorderLayout.NORTH); // The filter panel is now in the NORTH of the main layout


        // === TABLE DESIGN ===
        String[] columns = {"Item ID", "Item Name", "Category", "Quantity Sold", "Total Sales (₱)", "Date"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        table = new JTable(model);
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        table.getTableHeader().setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(240, 240, 240));
        table.getTableHeader().setForeground(new Color(60, 60, 60));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new EmptyBorder(10, 20, 20, 20));
        
        // Add table to center
        add(scroll, BorderLayout.CENTER);

        // === SAMPLE DATA ===
        loadSampleData();
    }

    private void loadSampleData() {
        model.addRow(new Object[]{101, "S4S Pine 1x2 (6ft)", "Lumber", 150, "7,500.00", "2025-11-22"});
        model.addRow(new Object[]{102, "Marine Plywood 3/4", "Plywood", 20, "16,000.00", "2025-11-22"});
        model.addRow(new Object[]{103, "Yakal 2x4 (10ft)", "Hardwood", 5, "12,500.00", "2025-11-21"});
        model.addRow(new Object[]{104, "Varnish (Liter)", "Chemicals", 45, "6,750.00", "2025-11-20"});
    }
}