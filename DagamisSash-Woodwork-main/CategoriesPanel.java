import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.DefaultTableCellRenderer;

public class CategoriesPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JButton addBtn, editBtn;
    private ItemsPanel itemsPanel;
    private ArrayList<String> categories = new ArrayList<>();

    // --- COLOR CONSTANTS ---
    private static final Color PRIMARY_COLOR = new Color(33, 150, 243); // Blue
    private static final Color SUCCESS_COLOR = new Color(102, 187, 106); // Green
    private static final Color DANGER_COLOR = new Color(220, 50, 50); // Red
    private static final Color BORDER_COLOR = new Color(200, 200, 200);
    private static final Color BG_LIGHT = new Color(245, 245, 245);

    public CategoriesPanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);

        // -------------------------
        // TITLE
        // -------------------------
        JLabel title = new JLabel("Product Categories");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        add(title, BorderLayout.NORTH);

        // -------------------------
        // ACTION BUTTONS PANEL
        // -------------------------
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        addBtn = createButton("➕ Add Category", PRIMARY_COLOR, 180, 40);
        editBtn = createButton("✍️ Edit Selected", SUCCESS_COLOR, 180, 40);
        
        editBtn.setEnabled(false); 

        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // -------------------------
        // TABLE
        // -------------------------
        String[] columns = {"Category Name", "Action"}; 
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return col == 1; }
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1) return JButton.class;
                return String.class;
            }
        };

        table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(50, 50, 50));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setIntercellSpacing(new Dimension(0, 0)); 

        table.setDefaultRenderer(String.class, new CategoryTableCellRenderer());

        table.getColumnModel().getColumn(1).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(1).setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setMaxWidth(80);
        table.getColumnModel().getColumn(0).setPreferredWidth(400);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        add(scroll, BorderLayout.CENTER);

        // -------------------------
        // ACTION LISTENERS
        // -------------------------
        addBtn.addActionListener(e -> showCustomCategoryModal(null));
        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                showCustomCategoryModal(categories.get(row));
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                editBtn.setEnabled(table.getSelectedRow() != -1);
            }
        });

        // Sample data
        categories.add("Doors");
        categories.add("Furniture");
        categories.add("Windows");
        loadCategories();
    }

    // --- MODAL FUNCTIONS --------------------------------------------------------------------------------

    /**
     * Creates and displays a stunning, professional modal dialog for adding or editing a category.
     * @param initialName The category name to pre-fill (null for Add, existing name for Edit).
     */
    private void showCustomCategoryModal(String initialName) {
        // --- 1. SETUP COMPONENTS ---
        boolean isEditing = initialName != null;
        String dialogTitle = isEditing ? "Edit Category: " + initialName : "Add New Category";

        JTextField inputField = new JTextField(25);
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        
        // Apply professional Titled Border
        inputField.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
            "Category Name",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.PLAIN, 12),
            new Color(100, 100, 100)
        ));
        
        if (isEditing) {
            inputField.setText(initialName);
            inputField.selectAll();
        }

        // --- 2. CREATE CUSTOM PANEL LAYOUT ---
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(400, 120));
        panel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        panel.add(inputField, gbc);

        // --- 3. SHOW DIALOG AND HANDLE RESULT ---
        int option = JOptionPane.showConfirmDialog(
            this,
            panel,
            dialogTitle,
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {
            String newName = inputField.getText().trim();
            handleCategoryAction(isEditing, initialName, newName);
        }
    }

    private void handleCategoryAction(boolean isEditing, String oldName, String newName) {
        if (newName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Category name cannot be empty.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (isEditing) {
            int row = categories.indexOf(oldName);
            if (newName.equals(oldName)) {
                // No change
            } else if (categories.contains(newName)) {
                JOptionPane.showMessageDialog(this, "Category '" + newName + "' already exists.", "Duplicate Category", JOptionPane.WARNING_MESSAGE);
            } else {
                categories.set(row, newName);
                loadCategories();
                JOptionPane.showMessageDialog(this, "Category updated to '" + newName + "'.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else { // Adding
            if (categories.contains(newName)) {
                JOptionPane.showMessageDialog(this, "Category '" + newName + "' already exists.", "Duplicate Category", JOptionPane.WARNING_MESSAGE);
            } else {
                categories.add(newName);
                loadCategories();
                JOptionPane.showMessageDialog(this, "Category '" + newName + "' successfully added!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // --- UTILITY FUNCTIONS (same as before) -------------------------------------------------------------

    private JButton createButton(String text, Color color, int width, int height) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(width, height));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(color.darker(), 1, true), 
            new EmptyBorder(5, 10, 5, 10)
        ));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(color.darker()); }
            public void mouseExited(MouseEvent e) { btn.setBackground(color); }
        });
        return btn;
    }

    public void setItemsPanel(ItemsPanel itemsPanel) { 
        this.itemsPanel = itemsPanel;
    }

    public void loadCategories() {
        model.setRowCount(0);
        for (String category : categories) {
            model.addRow(new Object[]{category, "Delete"}); 
        }
        if (table.getRowCount() == 0) {
            editBtn.setEnabled(false);
        }
        if (itemsPanel != null) {
            itemsPanel.loadCategories();
        }
    }

    public void deleteCategory(int row) {
        if (row >= 0 && row < categories.size()) {
            String name = categories.get(row);
             int response = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete the category '" + name + "'? This cannot be undone.",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
             
             if (response == JOptionPane.YES_OPTION) {
                categories.remove(row);
                loadCategories();
                JOptionPane.showMessageDialog(this,
                    "Category '" + name + "' deleted.",
                    "Category Deleted",
                    JOptionPane.INFORMATION_MESSAGE);
             }
        }
    }

    public ArrayList<String> getCategories() {
        return new ArrayList<>(categories);
    }
    
    // --- CUSTOM RENDERERS AND EDITORS (same as before) --------------------------------------------------
    
    private class CategoryTableCellRenderer extends DefaultTableCellRenderer {
        private final Color SELECTION_COLOR = new Color(180, 215, 255); 

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (isSelected) {
                setBackground(SELECTION_COLOR);
                setForeground(Color.BLACK);
            } else {
                setBackground(row % 2 == 0 ? Color.WHITE : BG_LIGHT);
                setForeground(Color.BLACK);
            }
            
            setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

            return this;
        }
    }

    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setText("Delete");
            setBackground(DANGER_COLOR);
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setFont(new Font("Segoe UI Semibold", Font.BOLD, 12));
            setBorder(BorderFactory.createLineBorder(DANGER_COLOR.darker(), 1, true));
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setBackground(DANGER_COLOR.brighter()); 
            } else {
                 setBackground(DANGER_COLOR);
            }
            return this;
        }
    }

    private class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private int currentRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.setBackground(DANGER_COLOR); 
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Segoe UI Semibold", Font.BOLD, 12));
            button.setFocusPainted(false);
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            currentRow = row;
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                SwingUtilities.invokeLater(() -> deleteCategory(currentRow));
            }
            isPushed = false;
            return label;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}