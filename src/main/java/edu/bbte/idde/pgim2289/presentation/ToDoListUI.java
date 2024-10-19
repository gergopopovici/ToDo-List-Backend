package edu.bbte.idde.pgim2289.presentation;
import edu.bbte.idde.pgim2289.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.exceptions.InvalidInputException;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Objects;

public class ToDoListUI {
    private ToDoService toDoService;
    private DefaultTableModel tableModel;
    private String title;
    private String description;
    private String dueDate;
    private String priority;
    private JTextField titleField;
    private JTextField descriptionField;
    private JTextField dueDateField;
    private JComboBox<Integer> priorityComboBox;

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
        titleField = new JTextField();
        inputPanel.add(titleLabel);
        inputPanel.add(titleField);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField();
        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionField);

        JLabel dueDateLabel = new JLabel("Due Date (YYYY-MM-DD):");
        dueDateField = new JTextField();
        inputPanel.add(dueDateLabel);
        inputPanel.add(dueDateField);

        JLabel priorityLabel = new JLabel("Priority level (1-3):");
        priorityComboBox = new JComboBox<>(new Integer[]{1, 2, 3});
        inputPanel.add(priorityLabel);
        inputPanel.add(priorityComboBox);

        String[] columnNames = {"ID","Title", "Description", "Due Date", "Priority Level"};
        JTable toDoTable = new JTable();
        tableModel = new DefaultTableModel(columnNames, 0);
        toDoTable.setModel(tableModel);
        toDoTable.setFont(new Font("Times New Roman", Font.BOLD,12));
        toDoTable.setRowHeight(45);

        JTableHeader tableHeader = toDoTable.getTableHeader();
        Font headerFont = new Font("Times New Roman", Font.BOLD, 18);
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
                HandleInputs();
                try {
                    if(title == null || title.trim().isEmpty()){
                        throw new InvalidInputException("Invalid input for Title:" + title);
                    }
                    if(description == null || description.trim().isEmpty()){
                        throw new InvalidInputException("Invalid input for Description"+description);
                    }
                    toDoService.create(title, description, dueDate, priority);
                }catch(ParseException|InvalidInputException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                refreshTodoList(0);
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HandleInputs();
                try {
                    if(title == null || title.trim().isEmpty()){
                        throw new InvalidInputException("Invalid input for Title:" + title);
                    }
                    if(description == null || description.trim().isEmpty()){
                        throw new InvalidInputException("Invalid input for Description"+description);
                    }
                    String idInput = JOptionPane.showInputDialog(null, "Enter ID of the entity to update:");
                    Long idInputInteger = Long.parseLong(idInput);
                    toDoService.update(idInputInteger, title, description, dueDate, priority);
                }catch (EntityNotFoundException | ParseException | NumberFormatException | InvalidInputException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
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
                try {
                    String idInput = JOptionPane.showInputDialog(null, "Enter ID of the entity to delete:");
                    Long idInputInteger = Long.parseLong(idInput);
                    toDoService.delete(idInputInteger);
                    refreshTodoList(0);
                }catch (EntityNotFoundException  | NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        getButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String priorityInput = JOptionPane.showInputDialog(null, "Enter the priority to list the To Does according to it:");
                try {
                    Integer priorityInputInteger = Integer.parseInt(priorityInput);
                    refreshTodoList(priorityInputInteger);
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
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
    private void HandleInputs(){
        title = titleField.getText();
        description = descriptionField.getText();
        dueDate = dueDateField.getText();
        priority = Objects.requireNonNull(priorityComboBox.getSelectedItem()).toString();
        titleField.setText("");
        descriptionField.setText("");
        dueDateField.setText("");
        priorityComboBox.setSelectedIndex(0);
    }
    public static void main(String[] args) {
        new ToDoListUI();
    }
}
