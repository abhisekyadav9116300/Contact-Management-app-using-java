Intern Id = CITS3652
# Contact-Management-app-using-java
This is an intresting project using the java language as its main language and it will make your contact book organized
# 📒 Contact Manager — Java Swing App

A beginner-level desktop Contact Management application built using **Java** and **Swing**.  
You can add, view, and delete contacts with their Name, Phone Number, and Gmail ID.

---

## 📸 App Overview

The app has two sections side by side:

| Section | What it does |
|---|---|
| **Left panel** | Form to add a new contact |
| **Right panel** | List of all saved contacts with a detail view |

---

## 🚀 How to Run

### Requirements
- Java JDK 8 or above installed on your system
- A terminal / command prompt

### Steps

**Step 1 — Compile the file**
```bash
javac ContactManager.java
```

**Step 2 — Run the app**
```bash
java ContactManager
```

That's it! The app window will open.

---

## 📋 Features

### ➕ Add a Contact
1. Fill in the **Full Name**, **Phone Number**, and **Gmail ID** fields on the left.
2. Click the **"+ Add Contact"** button.
3. The contact appears instantly in the list on the right.

### 👁️ View Contact Details
- Click any contact in the list.
- A detail card appears at the bottom showing their full Name, Phone, and Gmail.

### 🗑️ Delete a Contact
1. Click on a contact in the list to select it.
2. Click the **"Delete Selected"** button.
3. A confirmation box will appear — click **Yes** to confirm deletion.

### ✕ Clear the Form
- Click **"Clear Fields"** to reset all three input fields at once.

---

## ✅ Validations Built In

The app checks for the following before saving a contact:

| Condition | Error shown |
|---|---|
| Name is empty | "Name cannot be empty!" |
| Phone is empty | "Phone number cannot be empty!" |
| Gmail is empty | "Gmail ID cannot be empty!" |
| Email has no `@` | "Please enter a valid email address!" |
| Duplicate name | "A contact with this name already exists!" |

---

## 🗂️ Project Structure

```
ContactManager.java       ← Single file containing the entire app
```

Everything is in one `.java` file to keep it simple for beginners.

### Classes inside the file

| Class / Section | Purpose |
|---|---|
| `Contact` | Inner class — stores name, phone, email for one contact |
| `ContactManager` | Main JFrame class — builds the UI and handles all logic |
| `ContactCellRenderer` | Inner class — controls how each row looks in the list |

---

## 🧠 Concepts Used (Beginner Level)

| Concept | Where it's used |
|---|---|
| `JFrame` | Main application window |
| `JPanel` | Groups UI sections together |
| `JTextField` | Input fields for name, phone, email |
| `JButton` | Add, Delete, Clear buttons |
| `JLabel` | Text labels and status messages |
| `JList` + `DefaultListModel` | Displays the contact list dynamically |
| `ArrayList<Contact>` | Stores all contacts in memory |
| `ActionListener` | Detects button clicks |
| `FocusListener` | Placeholder text in input fields |
| `JOptionPane` | Confirmation dialog before deleting |
| `BorderLayout` / `BoxLayout` | Arranges components in panels |
| Inner class | `Contact` class groups related data |
| Custom renderer | Alternating row colors in the contact list |

---

## ⚠️ Limitations (Beginner App)

- Contacts are **not saved to a file** — they are lost when you close the app.
- No search or filter feature.
- No edit feature — you must delete and re-add to update a contact.

These can be added as next steps once you're comfortable with the basics!

---

## 💡 Possible Next Steps to Upgrade

1. **Save contacts to a file** using `FileWriter` / `BufferedReader`
2. **Add a Search bar** to filter contacts by name
3. **Edit a contact** by selecting it and modifying the form
4. **Sort contacts** alphabetically using `Collections.sort()`
5. **Use a database** like SQLite with JDBC for permanent storage

---

## 👨‍💻 Author

Built as a beginner Java Swing project for learning GUI programming concepts.
