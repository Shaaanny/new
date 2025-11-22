import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.HashMap;
import java.util.Map;

// Sales Reports & Summary Panel
public class SalesReportsPanel extends JPanel {

    // Colors
    private static final Color BACKGROUND_COLOR = new Color(242, 245, 249);
    private static final Color HEADER_COLOR = new Color(74, 184, 102);
    private static final Color TEXT_DARK_COLOR = new Color(55, 71, 79);
    private static final Color PH_CURRENCY_COLOR = new Color(50, 50, 50);
    private static final Color DATE_BAR_COLOR = Color.WHITE;

    public SalesReportsPanel() {
        setLayout(new BorderLayout(0, 0));
        setBackground(BACKGROUND_COLOR);

        // ============== TOP HEADER BAR ==================
        JPanel topHeader = new JPanel();
        topHeader.setBackground(HEADER_COLOR);
        topHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel headerLabel = new JLabel("Sales Summary");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(Color.WHITE);
        topHeader.add(headerLabel);
        topHeader.setBorder(new EmptyBorder(10, 20, 10, 20));
        add(topHeader, BorderLayout.NORTH);

        // ============== MAIN CONTENT (WHITE) ==================
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(Color.WHITE);
        mainContent.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Filter Bar
        mainContent.add(createFilterBar());

        // Summary Box Row
        mainContent.add(createSummaryPanel());

        // Chart Area
        mainContent.add(createChartArea());

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(BACKGROUND_COLOR);
        centerWrapper.setBorder(new EmptyBorder(0, 20, 20, 20));
        centerWrapper.add(mainContent, BorderLayout.CENTER);

        add(centerWrapper, BorderLayout.CENTER);
    }

    // ====================== FILTER BAR ==========================
    private JPanel createFilterBar() {
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterPanel.setBackground(DATE_BAR_COLOR);

        JButton btnPrev = new JButton("<");
        JButton btnNext = new JButton(">");
        styleFilterButton(btnPrev);
        styleFilterButton(btnNext);

        filterPanel.add(btnPrev);

        JButton dateRangeButton = new JButton("Nov 16, 2025 - Nov 22, 2025");
        styleFilterButton(dateRangeButton);
        filterPanel.add(dateRangeButton);

        filterPanel.add(btnNext);

        JButton allDayButton = new JButton("All day");
        JButton employeesButton = new JButton("All employees");
        styleFilterButton(allDayButton);
        styleFilterButton(employeesButton);

        filterPanel.add(allDayButton);
        filterPanel.add(employeesButton);

        filterPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        return filterPanel;
    }

    private void styleFilterButton(JButton button) {
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(button.getPreferredSize().width + 10, 30));
        button.setBorder(new LineBorder(new Color(200, 200, 200), 1));
        button.setBackground(DATE_BAR_COLOR);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    // ====================== SUMMARY 5 BOXES ==========================
    private JPanel createSummaryPanel() {
        JPanel summaryPanel = new JPanel(new GridLayout(1, 5, 1, 1));
        summaryPanel.setBackground(new Color(230, 230, 230));

        Map<String, String[]> summaryData = new HashMap<>();
        summaryData.put("Gross sales", new String[]{"₱0.00", "0%"});
        summaryData.put("Refunds", new String[]{"₱0.00", "0%"});
        summaryData.put("Discounts", new String[]{"₱0.00", "0%"});
        summaryData.put("Net sales", new String[]{"₱0.00", "0%"});
        summaryData.put("Gross profit", new String[]{"₱0.00", "0%"});

        for (Map.Entry<String, String[]> e : summaryData.entrySet()) {
            boolean highlight = e.getKey().equals("Gross sales");
            summaryPanel.add(createSummaryBox(e.getKey(), e.getValue()[0], e.getValue()[1], highlight));
        }

        summaryPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        return summaryPanel;
    }

    private JPanel createSummaryBox(String title, String amount, String percentage, boolean isSelected) {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBackground(Color.WHITE);
        box.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel amountLabel = new JLabel(amount);
        amountLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        amountLabel.setForeground(PH_CURRENCY_COLOR);
        amountLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel percentageLabel = new JLabel("(" + percentage + ")");
        percentageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        percentageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        box.add(titleLabel);
        box.add(amountLabel);
        box.add(percentageLabel);

        if (isSelected) {
            JPanel linePanel = new JPanel();
            linePanel.setBackground(new Color(74, 184, 102));
            linePanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 3));
            linePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            box.add(Box.createVerticalStrut(5));
            box.add(linePanel);
        }

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(box, BorderLayout.CENTER);
        return wrapper;
    }

    // ====================== CHART AREA ==========================
    private JPanel createChartArea() {
        JPanel chartPanel = new JPanel(new BorderLayout());
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel chartHeader = new JPanel(new BorderLayout());
        chartHeader.setBackground(Color.WHITE);

        JLabel grossSalesLabel = new JLabel("Gross sales");
        grossSalesLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        chartHeader.add(grossSalesLabel, BorderLayout.WEST);

        JPanel secondaryFilters = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        secondaryFilters.setBackground(Color.WHITE);

        JButton areaButton = new JButton("Area");
        JButton daysButton = new JButton("Days");
        styleSecondaryFilterButton(areaButton);
        styleSecondaryFilterButton(daysButton);

        secondaryFilters.add(areaButton);
        secondaryFilters.add(daysButton);

        chartHeader.add(secondaryFilters, BorderLayout.EAST);
        chartPanel.add(chartHeader, BorderLayout.NORTH);

        JPanel chartPlaceholder = new JPanel(new BorderLayout());
        chartPlaceholder.setBackground(Color.WHITE);
        chartPlaceholder.setBorder(new EmptyBorder(30, 0, 0, 0));

        JPanel lineGraphSim = new JPanel(new BorderLayout(5, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(74, 184, 102));

                int y = getHeight() / 2;
                g.drawLine(0, y, getWidth(), y);

                int dots = 7;
                for (int i = 0; i < dots; i++) {
                    int x = (int) ((double) getWidth() / (dots - 1) * i);
                    g.fillOval(x - 3, y - 3, 6, 6);
                }
            }
        };
        lineGraphSim.setBackground(Color.WHITE);
        chartPlaceholder.add(lineGraphSim, BorderLayout.CENTER);

        JPanel xAxisPanel = new JPanel(new GridLayout(1, 7));
        xAxisPanel.setBackground(Color.WHITE);
        String[] dates = {"Nov 16", "Nov 17", "Nov 18", "Nov 19", "Nov 20", "Nov 21", "Nov 22"};

        for (String d : dates) {
            JLabel lbl = new JLabel(d, SwingConstants.CENTER);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            xAxisPanel.add(lbl);
        }

        chartPlaceholder.add(xAxisPanel, BorderLayout.SOUTH);
        chartPanel.add(chartPlaceholder, BorderLayout.CENTER);

        return chartPanel;
    }

    private void styleSecondaryFilterButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(TEXT_DARK_COLOR);
        button.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
