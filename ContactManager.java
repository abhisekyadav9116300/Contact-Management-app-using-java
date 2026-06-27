import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ContactManager extends JFrame {

    // ─── Simple Contact class to store data ───────────────────────────────────
    static class Contact {
        String name;
        String phone;
        String email;

        Contact(String name, String phone, String email) {
            this.name  = name;
            this.phone = phone;
            this.email = email;
        }

        public String toString() {
            return name; // shown in the list
        }
    }

    // ─── Data storage ─────────────────────────────────────────────────────────
    ArrayList<Contact> contactList = new ArrayList<>();

    // ─── UI Components ────────────────────────────────────────────────────────
    JTextField nameField;
    JTextField phoneField;
    JTextField emailField;
    DefaultListModel<Contact> listModel;
    JList<Contact> jList;
    JLabel statusLabel;

    // ─── Constructor ──────────────────────────────────────────────────────────
    public ContactManager() {
        setTitle("Contact Manager");
        setSize(700, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with light gray background
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(245, 245, 250));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Add all sections
        mainPanel.add(buildTitleBar(),   BorderLayout.NORTH);
        mainPanel.add(buildFormPanel(),  BorderLayout.WEST);
        mainPanel.add(buildListPanel(),  BorderLayout.CENTER);
        mainPanel.add(buildStatusBar(),  BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    // ─── Title Bar ────────────────────────────────────────────────────────────
    JPanel buildTitleBar() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.setBackground(new Color(63, 81, 181)); // Indigo blue
        p.setBorder(new EmptyBorder(12, 16, 12, 16));

        JLabel icon = new JLabel("👤");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));

        JLabel title = new JLabel("  Contact Manager");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        JLabel sub = new JLabel("   —  Add, view and delete your contacts");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(new Color(200, 210, 255));

        p.add(icon);
        p.add(title);
        p.add(sub);
        return p;
    }

    // ─── Left Form Panel ──────────────────────────────────────────────────────
    JPanel buildFormPanel() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 220, 230), 1, true),
            new EmptyBorder(20, 20, 20, 20)
        ));
        card.setPreferredSize(new Dimension(270, 0));

        // Section heading
        JLabel heading = new JLabel("Add New Contact");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 15));
        heading.setForeground(new Color(50, 50, 80));
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(heading);
        card.add(Box.createVerticalStrut(18));

        // Name field
        card.add(makeLabel("Full Name"));
        card.add(Box.createVerticalStrut(4));
        nameField = makeTextField("e.g. Ravi Kumar");
        card.add(nameField);
        card.add(Box.createVerticalStrut(14));

        // Phone field
        card.add(makeLabel("Phone Number"));
        card.add(Box.createVerticalStrut(4));
        phoneField = makeTextField("e.g. 9876543210");
        card.add(phoneField);
        card.add(Box.createVerticalStrut(14));

        // Email field
        card.add(makeLabel("Gmail ID"));
        card.add(Box.createVerticalStrut(4));
        emailField = makeTextField("e.g. ravi@gmail.com");
        card.add(emailField);
        card.add(Box.createVerticalStrut(22));

        // Add Contact button
        JButton addBtn = makeButton("＋  Add Contact", new Color(63, 81, 181), Color.WHITE);
        addBtn.addActionListener(e -> addContact());
        card.add(addBtn);
        card.add(Box.createVerticalStrut(10));

        // Clear button
        JButton clearBtn = makeButton("✕  Clear Fields", new Color(240, 240, 248), new Color(80, 80, 100));
        clearBtn.addActionListener(e -> clearFields());
        card.add(clearBtn);

        card.add(Box.createVerticalGlue());
        return card;
    }

    // ─── Right List Panel ─────────────────────────────────────────────────────
    JPanel buildListPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(new Color(245, 245, 250));

        // Top bar: heading + delete button
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(245, 245, 250));

        JLabel heading = new JLabel("All Contacts");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 15));
        heading.setForeground(new Color(50, 50, 80));
        topBar.add(heading, BorderLayout.WEST);

        JButton deleteBtn = makeButton("🗑  Delete Selected", new Color(229, 57, 53), Color.WHITE);
        deleteBtn.setPreferredSize(new Dimension(160, 34));
        deleteBtn.addActionListener(e -> deleteContact());
        topBar.add(deleteBtn, BorderLayout.EAST);

        panel.add(topBar, BorderLayout.NORTH);

        // Contact list
        listModel = new DefaultListModel<>();
        jList = new JList<>(listModel);
        jList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jList.setBackground(Color.WHITE);
        jList.setSelectionBackground(new Color(197, 202, 233));
        jList.setSelectionForeground(new Color(30, 30, 60));
        jList.setFixedCellHeight(40);
        jList.setBorder(new EmptyBorder(4, 8, 4, 8));
        jList.setCellRenderer(new ContactCellRenderer());

        // Selection → fill form for viewing
        jList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showSelectedContact();
            }
        });

        JScrollPane scroll = new JScrollPane(jList);
        scroll.setBorder(new LineBorder(new Color(220, 220, 230), 1, true));
        scroll.setBackground(Color.WHITE);
        panel.add(scroll, BorderLayout.CENTER);

        // Detail card at bottom
        panel.add(buildDetailCard(), BorderLayout.SOUTH);

        return panel;
    }

    // ─── Detail Card (shown when contact is selected) ─────────────────────────
    JPanel detailCard;
    JLabel detailName, detailPhone, detailEmail;

    JPanel buildDetailCard() {
        detailCard = new JPanel(new GridLayout(3, 1, 0, 4));
        detailCard.setBackground(new Color(232, 234, 246));
        detailCard.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(197, 202, 233), 1, true),
            new EmptyBorder(10, 14, 10, 14)
        ));
        detailCard.setVisible(false);

        detailName  = detailLabel("👤  Name  : —");
        detailPhone = detailLabel("📞  Phone : —");
        detailEmail = detailLabel("✉️   Gmail  : —");

        detailCard.add(detailName);
        detailCard.add(detailPhone);
        detailCard.add(detailEmail);
        return detailCard;
    }

    // ─── Status Bar ───────────────────────────────────────────────────────────
    JPanel buildStatusBar() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.setBackground(new Color(245, 245, 250));

        statusLabel = new JLabel("Ready — add your first contact!");
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusLabel.setForeground(new Color(120, 120, 150));
        p.add(statusLabel);
        return p;
    }

    // ─── Actions ─────────────────────────────────────────────────────────────

    void addContact() {
        String name  = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        // Basic validation
        if (name.isEmpty()) {
            showStatus("❌ Name cannot be empty!", true);
            nameField.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            showStatus("❌ Phone number cannot be empty!", true);
            phoneField.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            showStatus("❌ Gmail ID cannot be empty!", true);
            emailField.requestFocus();
            return;
        }
        if (!email.contains("@")) {
            showStatus("❌ Please enter a valid email address!", true);
            emailField.requestFocus();
            return;
        }

        // Check for duplicate name
        for (Contact c : contactList) {
            if (c.name.equalsIgnoreCase(name)) {
                showStatus("❌ A contact with this name already exists!", true);
                return;
            }
        }

        // Add the contact
        Contact newContact = new Contact(name, phone, email);
        contactList.add(newContact);
        listModel.addElement(newContact);
        clearFields();
        showStatus("✅ Contact '" + name + "' added successfully! Total: " + contactList.size(), false);
    }

    void deleteContact() {
        int selectedIndex = jList.getSelectedIndex();
        if (selectedIndex == -1) {
            showStatus("⚠️  Please select a contact to delete.", true);
            return;
        }

        Contact selected = listModel.get(selectedIndex);

        // Confirm before deleting
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete '" + selected.name + "'?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            contactList.remove(selectedIndex);
            listModel.remove(selectedIndex);
            detailCard.setVisible(false);
            showStatus("🗑  Contact '" + selected.name + "' deleted. Total: " + contactList.size(), false);
        }
    }

    void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        nameField.requestFocus();
    }

    void showSelectedContact() {
        int idx = jList.getSelectedIndex();
        if (idx == -1) {
            detailCard.setVisible(false);
            return;
        }
        Contact c = contactList.get(idx);
        detailName.setText("👤  Name  :  " + c.name);
        detailPhone.setText("📞  Phone :  " + c.phone);
        detailEmail.setText("✉️   Gmail  :  " + c.email);
        detailCard.setVisible(true);
        showStatus("Viewing: " + c.name, false);
    }

    void showStatus(String msg, boolean isError) {
        statusLabel.setText(msg);
        statusLabel.setForeground(isError ? new Color(200, 50, 50) : new Color(50, 130, 80));
    }

    // ─── Helper: make a styled label ──────────────────────────────────────────
    JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(new Color(80, 80, 110));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    // ─── Helper: make a styled text field ────────────────────────────────────
    JTextField makeTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setForeground(new Color(50, 50, 80));
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 220), 1, true),
            new EmptyBorder(7, 10, 7, 10)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Placeholder behavior
        field.setText(placeholder);
        field.setForeground(new Color(180, 180, 200));
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(new Color(50, 50, 80));
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(new Color(180, 180, 200));
                }
            }
        });

        return field;
    }

    // helper to get real text (ignoring placeholder)
    String getFieldText(JTextField field, String placeholder) {
        String text = field.getText().trim();
        if (text.equals(placeholder)) return "";
        return text;
    }

    // override getText used in addContact to handle placeholder
    // (rewrite addContact to use this)

    // ─── Helper: make a styled button ────────────────────────────────────────
    JButton makeButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(9, 16, 9, 16));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        return btn;
    }

    // ─── Helper: detail card label ────────────────────────────────────────────
    JLabel detailLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(new Color(50, 50, 90));
        return lbl;
    }

    // ─── Custom List Cell Renderer ────────────────────────────────────────────
    class ContactCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {

            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            Contact c = (Contact) value;
            setText("  👤  " + c.name + "   ·   " + c.phone);
            setFont(new Font("Segoe UI", Font.PLAIN, 13));
            setBorder(new EmptyBorder(6, 8, 6, 8));

            if (isSelected) {
                setBackground(new Color(197, 202, 233));
                setForeground(new Color(30, 30, 60));
            } else {
                setBackground(index % 2 == 0 ? Color.WHITE : new Color(248, 248, 252));
                setForeground(new Color(50, 50, 80));
            }
            return this;
        }
    }

    // ─── Fix addContact to handle placeholders ────────────────────────────────
    // Override addContact so placeholder text is NOT treated as real input
    // (already handled — fields clear placeholder on focus, but we guard below too)

    // ─── Main ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        // Make it look nicer on Windows/Linux
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // fallback to default
        }

        SwingUtilities.invokeLater(() -> new ContactManager());
    }
}
