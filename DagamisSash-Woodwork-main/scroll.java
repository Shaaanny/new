import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class scroll extends BasicScrollBarUI {
    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = new Color(100, 100, 100);
        this.trackColor = new Color(230, 230, 230);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }

    @Override
    protected JButton createIncreaseButton(int orientation) { return createZeroButton(); }

    private JButton createZeroButton() {
        JButton btn = new JButton();
        btn.setPreferredSize(new Dimension(0,0));
        btn.setMinimumSize(new Dimension(0,0));
        btn.setMaximumSize(new Dimension(0,0));
        return btn;
    }
}
