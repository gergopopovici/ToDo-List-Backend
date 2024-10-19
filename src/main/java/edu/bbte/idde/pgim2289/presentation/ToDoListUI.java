package edu.bbte.idde.pgim2289.presentation;

import edu.bbte.idde.pgim2289.model.ToDo;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ToDoListUI {
    public ToDoListUI() {
        toDoListUi();
    }

    public void toDoListUi() {
        JFrame frame = new JFrame("ToDo List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField();
        inputPanel.add(titleLabel);
        inputPanel.add(titleField);

        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new JTextField();
        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionField);

        JLabel dueDateLabel = new JLabel("Due Date (YYYY-MM-DD):");
        JTextField dueDateField = new JTextField();
        inputPanel.add(dueDateLabel);
        inputPanel.add(dueDateField);

        JLabel priorityLabel = new JLabel("Priority level (1-3):");
        JTextField priorityField = new JTextField();
        inputPanel.add(priorityLabel);
        inputPanel.add(priorityField);

        String[] columnNames = {"Title", "Description", "Due Date", "Priority Level"};
        JTable toDoTable = new JTable();
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        toDoTable.setModel(tableModel);

        JTableHeader tableHeader = toDoTable.getTableHeader();
        Font headerFont = new Font("Arial", Font.BOLD, 16);
        tableHeader.setFont(headerFont);
        tableHeader.setPreferredSize(new Dimension(100, 30));

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) toDoTable.getTableHeader().getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        JScrollPane scrollPane = new JScrollPane(toDoTable);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("Add ToDo");
        JButton getButton = new JButton("Get ToDo by Priority");
        JButton listButton = new JButton("List All ToDos");
        JButton deleteButton = new JButton("Delete ToDo By ID");
        JButton updateButton = new JButton("Update ToDo By ID");

        buttonPanel.add(addButton);
        buttonPanel.add(getButton);
        buttonPanel.add(listButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(outputScrollPane, BorderLayout.EAST);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new ToDoListUI();
    }
}
