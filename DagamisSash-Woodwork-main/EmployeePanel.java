import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EmployeePanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField txtFullName, txtUsername, txtEmail, txtPhone, txtAddress;
    private JPasswordField txtPassword;
    private JComboBox<String> cbRole;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;

    private ArrayList<Employee> employees = new ArrayList<>();
    private int selectedIndex = -1;

    public EmployeePanel() {
        setLayout(new BorderLayout(12, 12));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // ===== TITLE =====
        JLabel title = new JLabel("Employee Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // ===== LEFT FORM PANEL =====
        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setPreferredSize(new Dimension(350, 0));
        left.setBackground(Color.WHITE);

        // Personal Info Card
        JPanel personalCard = createCard("Personal Info", 5);
        personalCard.setLayout(new GridLayout(4, 2, 8, 8));

        personalCard.add(new JLabel("Full Name:"));
        txtFullName = new JTextField();
        personalCard.add(txtFullName);

        personalCard.add(new JLabel("Phone:"));
        txtPhone = new JTextField();
        personalCard.add(txtPhone);

        personalCard.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        personalCard.add(txtEmail);

        personalCard.add(new JLabel("Address:"));
        txtAddress = new JTextField();
        personalCard.add(txtAddress);

        left.add(personalCard);
        left.add(Box.createVerticalStrut(10));

        // Account Info Card
        JPanel accountCard = createCard("Account Info", 5);
        accountCard.setLayout(new GridLayout(3, 2, 8, 8));

        accountCard.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        accountCard.add(txtUsername);

        accountCard.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        accountCard.add(txtPassword);

        accountCard.add(new JLabel("Role:"));
        cbRole = new JComboBox<>(new String[]{"Admin","Cashier","Inventory Staff","Manager"});
        accountCard.add(cbRole);

        left.add(accountCard);
        left.add(Box.createVerticalStrut(10));

        // Action Buttons
        JPanel actions = new JPanel(new GridLayout(2, 2, 10, 10));
        actions.setBackground(Color.WHITE);
        btnAdd = createButton("Add", new Color(40, 167, 69));
        btnUpdate = createButton(" Update", new Color(0, 123, 255));
        btnDelete = createButton(" Delete", new Color(220, 53, 69));
        btnClear = createButton(" Clear", new Color(108, 117, 125));

        actions.add(btnAdd);
        actions.add(btnUpdate);
        actions.add(btnDelete);
        actions.add(btnClear);

        left.add(actions);
        add(left, BorderLayout.WEST);

        // ===== CENTER TABLE =====
        String[] cols = {"Full Name","Username","Role","Email","Phone","Address"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };

        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setSelectionBackground(new Color(200, 200, 255));
        table.setSelectionForeground(Color.BLACK);

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // ===== EVENTS =====
        btnAdd.addActionListener(e -> addEmployee());
        btnUpdate.addActionListener(e -> updateEmployee());
        btnDelete.addActionListener(e -> deleteEmployee());
        btnClear.addActionListener(e -> clearForm());

        table.getSelectionModel().addListSelectionListener(e -> selectRow());
    }

    private JPanel createCard(String title, int padding) {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createTitledBorder(title));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(title),
                BorderFactory.createEmptyBorder(padding,padding,padding,padding)
        ));
        return card;
    }

    private JButton createButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    // ===== EMPLOYEE CRUD =====
    private void addEmployee() {
        String fullName = txtFullName.getText().trim();
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String address = txtAddress.getText().trim();
        String role = (String) cbRole.getSelectedItem();

        if(fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name, Username, Password are required!");
            return;
        }

        Employee emp = new Employee(fullName, username, password, email, phone, address, role);
        employees.add(emp);
        refreshTable();
        clearForm();
    }

    private void updateEmployee() {
        if(selectedIndex < 0) {
            JOptionPane.showMessageDialog(this, "Select an employee to update!");
            return;
        }

        Employee emp = employees.get(selectedIndex);
        emp.fullName = txtFullName.getText().trim();
        emp.username = txtUsername.getText().trim();
        String pw = new String(txtPassword.getPassword());
        if(!pw.isEmpty()) emp.password = pw;
        emp.email = txtEmail.getText().trim();
        emp.phone = txtPhone.getText().trim();
        emp.address = txtAddress.getText().trim();
        emp.role = (String) cbRole.getSelectedItem();

        refreshTable();
        clearForm();
    }

    private void deleteEmployee() {
        if(selectedIndex < 0) {
            JOptionPane.showMessageDialog(this, "Select an employee to delete!");
            return;
        }
        employees.remove(selectedIndex);
        refreshTable();
        clearForm();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for(Employee emp : employees) {
            tableModel.addRow(new Object[]{
                    emp.fullName, emp.username, emp.role, emp.email, emp.phone, emp.address
            });
        }
    }

    private void selectRow() {
        selectedIndex = table.getSelectedRow();
        if(selectedIndex < 0) return;

        Employee emp = employees.get(selectedIndex);
        txtFullName.setText(emp.fullName);
        txtUsername.setText(emp.username);
        txtPassword.setText("");
        txtEmail.setText(emp.email);
        txtPhone.setText(emp.phone);
        txtAddress.setText(emp.address);
        cbRole.setSelectedItem(emp.role);
    }

    private void clearForm() {
        txtFullName.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
        cbRole.setSelectedIndex(0);
        table.clearSelection();
        selectedIndex = -1;
    }

    // ===== EMPLOYEE MODEL =====
    private static class Employee {
        String fullName, username, password, email, phone, address, role;
        Employee(String fullName, String username, String password, String email,
                 String phone, String address, String role) {
            this.fullName = fullName;
            this.username = username;
            this.password = password;
            this.email = email;
            this.phone = phone;
            this.address = address;
            this.role = role;
        }
    }
}
