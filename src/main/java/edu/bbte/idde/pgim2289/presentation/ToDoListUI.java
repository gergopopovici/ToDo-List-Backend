package edu.bbte.idde.pgim2289.presentation;
import edu.bbte.idde.pgim2289.model.ToDo;
import edu.bbte.idde.pgim2289.services.ToDoService;
import edu.bbte.idde.pgim2289.services.ToDoServiceImplementation;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Collection;

public class ToDoListUI {
    private ToDoService toDoService;
    private DefaultTableModel tableModel;

    public ToDoListUI() {
        toDoListUi();
    }

    public void toDoListUi() {
        JFrame frame = new JFrame("ToDo List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        toDoService = new ToDoServiceImplementation();
        frame.setSize(800, 400);
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

        String[] columnNames = {"ID","Title", "Description", "Due Date", "Priority Level"};
        JTable toDoTable = new JTable();
        tableModel = new DefaultTableModel(columnNames, 0);
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
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String description = descriptionField.getText();
                String dueDate = dueDateField.getText();
                String priority = priorityField.getText();
                titleField.setText("");
                descriptionField.setText("");
                dueDateField.setText("");
                priorityField.setText("");
                toDoService.create(title,description,dueDate,priority);
                refreshTodoList(0);
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String description = descriptionField.getText();
                String dueDate = dueDateField.getText();
                String priority = priorityField.getText();
                titleField.setText("");
                descriptionField.setText("");
                dueDateField.setText("");
                priorityField.setText("");
                String idInput = JOptionPane.showInputDialog(null, "Enter ID of the entity to update:");
                Long idInputInteger = Long.parseLong(idInput);
                toDoService.update(idInputInteger,title,description,dueDate,priority);
                refreshTodoList(0);
            }
        });
        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTodoList(0);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idInput = JOptionPane.showInputDialog(null, "Enter ID of the entity to delete:");
                Long idInputInteger = Long.parseLong(idInput);
                toDoService.delete(idInputInteger);
                refreshTodoList(0);
            }
        });

        getButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String priorityInput = JOptionPane.showInputDialog(null, "Enter ID of the entity to delete:");
                Integer priorityInputInteger = Integer.parseInt(priorityInput);
                refreshTodoList(priorityInputInteger);
            }
        });
    }

    private void refreshTodoList(Integer x) { //ha X == 0 akkor find all ha x==2 akkor kihagyja
        tableModel.setRowCount(0);
        Collection<ToDo> todos;
        if(x==0) {
            todos = toDoService.findAll();
        }else{
            todos = toDoService.findByPriority(x);
        }
        for (ToDo todo : todos) {
            Object[] rowData = {
                    todo.getId(),
                    todo.getTitle(),
                    todo.getDescription(),
                    new SimpleDateFormat("yyyy-MM-dd").format(todo.getDate()),
                    todo.getPriority()
            };
            tableModel.addRow(rowData);
        }

    }
    public static void main(String[] args) {
        new ToDoListUI();
    }
}
