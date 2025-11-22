import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {
        setLayout(null);
        setBackground(new Color(240, 240, 240));

        // === Cards Section ===
        JPanel card1 = createCard("Today's Sales", "₱0.00", new Color(66, 133, 244));
        card1.setBounds(260, 100, 260, 120);
        add(card1);

        JPanel card2 = createCard("Stocks Alert", "0", new Color(0, 200, 180));
        card2.setBounds(550, 100, 260, 120);
        add(card2);

        JPanel card3 = createCard("Total Items", "0", new Color(120, 60, 255));
        card3.setBounds(840, 100, 260, 120);
        add(card3);

        // === Sales Overview ===
        JPanel salesOverview = new JPanel();
        salesOverview.setLayout(null);
        salesOverview.setBackground(Color.WHITE);
        salesOverview.setBounds(260, 250, 840, 420);
        salesOverview.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 1));

        JLabel salesLabel = new JLabel("Sales Overview");
        salesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        salesLabel.setForeground(Color.GRAY);
        salesLabel.setBounds(20, 10, 200, 25);
        salesOverview.add(salesLabel);

        JLabel legend = new JLabel("⬜ Sales");
        legend.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        legend.setForeground(new Color(66, 133, 244));
        legend.setBounds(730, 10, 100, 25);
        salesOverview.add(legend);

        add(salesOverview);
    }

    private JPanel createCard(String title, String value, Color color) {
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBackground(color);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 0));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(20, 15, 200, 25);
        card.add(titleLabel);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 25));
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setBounds(20, 50, 200, 30);
        card.add(valueLabel);

        return card;
    }
}
