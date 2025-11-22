import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class AdminDashboard extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel, sidebar, topNavbar;
    private JButton btnDashboard, btnInventory, btnItems, btnCategories, btnReports, btnEmployees, btnSettings, btnLogout;
    private JLabel titleLabel;

    private String username, role;

    private InventoryPanel inventoryPanel;
    private ReportsPanel reportsPanel;
    private CategoriesPanel categoriesPanel;
    private ItemsPanel itemsPanel;
    private EmployeePanel employeesPanel;

    public AdminDashboard(String username, String role) {
        this.username = username;
        this.role = role;

        setTitle("Dagamis Sash and Woodwork - Admin Dashboard");
        setSize(1450, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== TOP NAVBAR =====
        topNavbar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(33, 150, 243),
                        getWidth(), 0, new Color(30, 136, 229));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        topNavbar.setPreferredSize(new Dimension(1400, 70));
        topNavbar.setLayout(new BorderLayout());
        topNavbar.setBorder(new MatteBorder(0, 0, 3, 0, new Color(21, 101, 192)));

        titleLabel = new JLabel("Dashboard");
        titleLabel.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        topNavbar.add(titleLabel, BorderLayout.WEST);

        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        profilePanel.setOpaque(false);
        JLabel userInfo = new JLabel(username + " | " + role + "  ");
        userInfo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        userInfo.setForeground(Color.WHITE);
        profilePanel.add(userInfo);

        topNavbar.add(profilePanel, BorderLayout.EAST);
        add(topNavbar, BorderLayout.NORTH);

        // ===== SIDEBAR (RE-ORDERED as requested) =====
        sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(260, 740));
        sidebar.setBackground(new Color(28, 37, 48));
        sidebar.setLayout(new GridLayout(12, 1, 0, 2));
        sidebar.setBorder(new MatteBorder(0, 0, 0, 2, new Color(45, 57, 69)));

        btnDashboard = createSidebarButton("Dashboard");
        btnInventory = createSidebarButton("Inventory");
        btnItems = createSidebarButton("Items");
        btnCategories = createSidebarButton("Categories");
        btnReports = createSidebarButton("Reports");
        btnEmployees = createSidebarButton("Employee");
        btnSettings = createSidebarButton("Setting");
        btnLogout = createSidebarButton("Logout");

        // ---- Final Order ----
        sidebar.add(btnDashboard);
        sidebar.add(btnInventory);
        sidebar.add(btnItems);
        sidebar.add(btnCategories);
        sidebar.add(btnReports);
        sidebar.add(btnEmployees);
        sidebar.add(btnSettings);

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnLogout);

        add(sidebar, BorderLayout.WEST);

        // ===== MAIN CONTENT =====
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(new Color(242, 245, 249));

        categoriesPanel = new CategoriesPanel();
        itemsPanel = new ItemsPanel(categoriesPanel);
        categoriesPanel.setItemsPanel(itemsPanel);

        inventoryPanel = new InventoryPanel();
        reportsPanel = new ReportsPanel();
        employeesPanel = new EmployeePanel();

        JPanel dashboardUI = createDashboardUI();
        JScrollPane dashboardScroll = new JScrollPane(dashboardUI);
        dashboardScroll.setBorder(BorderFactory.createEmptyBorder());
        dashboardScroll.getVerticalScrollBar().setUnitIncrement(20);

        mainPanel.add(dashboardScroll, "dashboard");
        mainPanel.add(inventoryPanel, "inventory");
        mainPanel.add(itemsPanel, "items");
        mainPanel.add(categoriesPanel, "categories");
        mainPanel.add(reportsPanel, "reports");
        mainPanel.add(employeesPanel, "employees");
        //mainPanel.add(new SettingsPanel(), "settings");


        add(mainPanel, BorderLayout.CENTER);

        // ===== ACTION LISTENERS =====
        btnDashboard.addActionListener(e -> switchPanel("dashboard", "Dashboard"));
        btnInventory.addActionListener(e -> switchPanel("inventory", "Inventory"));
        btnItems.addActionListener(e -> switchPanel("items", "Items"));
        btnCategories.addActionListener(e -> switchPanel("categories", "Categories"));
        btnReports.addActionListener(e -> switchPanel("reports", "Reports"));
        btnEmployees.addActionListener(e -> switchPanel("employees", "Employees"));
        btnSettings.addActionListener(e -> switchPanel("settings", "Settings"));

        btnLogout.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    // ===== Modern Sidebar Button =====
    private JButton createSidebarButton(String text) {
        JButton btn = new JButton("   " + text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 17));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(28, 37, 48));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(12, 25, 12, 10));

        addHoverEffect(btn, new Color(28, 37, 48), new Color(55, 71, 79));
        return btn;
    }

    private void addHoverEffect(JButton btn, Color normal, Color hover) {
        btn.setBackground(normal);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(hover);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(normal);
            }
        });
    }

    private void switchPanel(String panelName, String title) {
        cardLayout.show(mainPanel, panelName);
        titleLabel.setText(title);
    }

    // ===== Dashboard UI =====
    private JPanel createDashboardUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(242, 245, 249));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel cardsPanel = new JPanel(new GridLayout(1, 4, 20, 20));
        cardsPanel.setOpaque(false);

        cardsPanel.add(createDashboardCard("Total Sales (Today)", "₱85,300", new Color(3, 169, 244)));
        cardsPanel.add(createDashboardCard("Low Stock", "42", new Color(255, 111, 0)));
        cardsPanel.add(createDashboardCard("Out of Stock Items", "5", new Color(244, 67, 54)));
        cardsPanel.add(createDashboardCard("Employees", "12", new Color(102, 187, 106)));

        panel.add(cardsPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        panel.add(createLowerContentSection());

        return panel;
    }

    private JPanel createLowerContentSection() {
        JPanel lowerPanel = new JPanel();
        lowerPanel.setOpaque(false);
        lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.Y_AXIS));

        lowerPanel.add(createTopSellingPanel());
        lowerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel split = new JPanel(new GridLayout(1, 2, 20, 0));
        split.setOpaque(false);
        split.add(createRecentActivityPanel());
        split.add(createStockAlertsPanel());

        lowerPanel.add(split);
        return lowerPanel;
    }

    private JPanel createTopSellingPanel() {
        RoundedPanel panel = new RoundedPanel(new BorderLayout(), 15);
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Top Selling Woodwork Items");
        title.setFont(new Font("Segoe UI Semibold", Font.BOLD, 18));
        panel.add(title, BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setOpaque(false);

        list.add(createProductItem("S4S Pine 1x2 (6ft)", "950 sold", 1000, 1));
        list.add(createSeparator());
        list.add(createProductItem("Plywood 1/4", "75 sold", 20, 2));
        list.add(createSeparator());
        list.add(createProductItem("Marine Plywood", "55 sold", 5, 3));
        list.add(createSeparator());
        list.add(createProductItem("Yakal 2x4 (10ft)", "45 sold", 100, 4));
        list.add(createSeparator());
        list.add(createProductItem("Gmelina 2x3 (8ft)", "38 sold", 40, 5));

        panel.add(list, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createProductItem(String name, String sold, int stock, int rank) {
        JPanel item = new JPanel(new BorderLayout(15, 0));
        item.setOpaque(false);
        item.setBorder(new EmptyBorder(10, 0, 10, 0));

        Color stockColor = new Color(76, 175, 80);
        String stockText = "In Stock";

        if (stock <= 20 && stock > 5) {
            stockColor = new Color(255, 152, 0);
            stockText = "Low Stock";
        } else if (stock <= 5) {
            stockColor = new Color(244, 67, 54);
            stockText = "CRITICAL STOCK";
        }

        JLabel badge = new JLabel(" " + stockText + " ");
        badge.setOpaque(true);
        badge.setBackground(stockColor);
        badge.setForeground(Color.WHITE);
        badge.setFont(new Font("Segoe UI", Font.PLAIN, 10));

        JLabel lblSold = new JLabel(sold + " | " + stock + " in stock");
        lblSold.setForeground(Color.GRAY);

        JPanel details = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        details.setOpaque(false);
        details.add(lblSold);
        details.add(badge);

        JLabel lblName = new JLabel(name);
        lblName.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));

        JPanel left = new JPanel(new BorderLayout());
        left.setOpaque(false);
        left.add(lblName, BorderLayout.NORTH);
        left.add(details, BorderLayout.CENTER);

        JLabel lblRank = new JLabel(" #" + rank + " ");
        lblRank.setOpaque(true);
        lblRank.setBackground(new Color(158, 158, 158));
        lblRank.setForeground(Color.WHITE);

        item.add(left, BorderLayout.CENTER);
        item.add(lblRank, BorderLayout.EAST);

        return item;
    }

    private JPanel createRecentActivityPanel() {
        RoundedPanel panel = new RoundedPanel(new BorderLayout(), 15);
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Recent Activity 🕒");
        title.setFont(new Font("Segoe UI Semibold", Font.BOLD, 18));
        panel.add(title, BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setOpaque(false);

        list.add(new JLabel("• New Order #1005 processed — 5 min ago"));
        list.add(new JLabel("• Inventory Update: Pine 2x4s added — 12 min ago"));
        list.add(new JLabel("• Employee Login: J. Dela Cruz — 25 min ago"));
        list.add(new JLabel("• Report Generated: End of Day Sales — 1 hr ago"));

        panel.add(list, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createStockAlertsPanel() {
        RoundedPanel panel = new RoundedPanel(new BorderLayout(), 15);
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Stock Alerts ⚠️");
        title.setFont(new Font("Segoe UI Semibold", Font.BOLD, 18));
        panel.add(title, BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setOpaque(false);

        list.add(createAlertCard("Low Stock Items", "Need restocking soon", "42", new Color(255, 152, 0)));
        list.add(Box.createRigidArea(new Dimension(0, 10)));
        list.add(createAlertCard("Total Products", "Active in inventory", "1,240", new Color(76, 175, 80)));

        panel.add(list, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createAlertCard(String title, String subtitle, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(250, 250, 250));
        card.setBorder(new CompoundBorder(new LineBorder(new Color(230, 230, 230), 1, true),
                new EmptyBorder(10, 15, 10, 15)));

        JLabel lblTitle = new JLabel(title);
        JLabel lblSubtitle = new JLabel(subtitle);
        JLabel lblValue = new JLabel(value);

        lblValue.setFont(new Font("Segoe UI Semibold", Font.BOLD, 28));
        lblValue.setForeground(color);

        JPanel text = new JPanel(new BorderLayout());
        text.setOpaque(false);
        text.add(lblTitle, BorderLayout.NORTH);
        text.add(lblSubtitle, BorderLayout.SOUTH);

        card.add(text, BorderLayout.CENTER);
        card.add(lblValue, BorderLayout.EAST);

        return card;
    }

    private JPanel createDashboardCard(String title, String value, Color color) {
        RoundedPanel card = new RoundedPanel(new BorderLayout(), 15);
        card.setPreferredSize(new Dimension(200, 130));
        card.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel(title);
        JLabel lblValue = new JLabel(value);

        lblValue.setFont(new Font("Segoe UI Semibold", Font.BOLD, 30));
        lblValue.setForeground(color);

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);

        return card;
    }

    private JSeparator createSeparator() {
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setForeground(new Color(235, 235, 235));
        return sep;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard("admin", "Admin"));
    }
}
