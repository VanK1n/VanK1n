package task_9;

// File: GuiApp.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiApp extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton loadButton, saveButton, processButton;

    public GuiApp() {
        setTitle("Обработка списка");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        // Панель с кнопками
        JPanel panel = new JPanel();
        loadButton = new JButton("Загрузить из файла");
        saveButton = new JButton("Сохранить в файл");
        processButton = new JButton("Обработать список");
        panel.add(loadButton);
        panel.add(saveButton);
        panel.add(processButton);
        add(panel, BorderLayout.NORTH);

        // Таблица
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Обработчики кнопок
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadFromFile();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveToFile();
            }
        });

        processButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processList();
            }
        });
    }

    private void loadFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(GuiApp.this);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getPath();
            try {
                List<List<Integer>> twoDList = ListProcessor.read2DListFromFile(filename);
                display2DList(twoDList);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(GuiApp.this, "Ошибка при чтении файла: " + ex.getMessage());
            }
        }
    }

    private void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(GuiApp.this);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getPath();
            try {
                List<List<Integer>> twoDList = get2DListFromTable();
                ListProcessor.write2DListToFile(twoDList, filename);
                JOptionPane.showMessageDialog(GuiApp.this, "Данные сохранены успешно.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(GuiApp.this, "Ошибка при записи файла: " + ex.getMessage());
            }
        }
    }

    private void processList() {
        try {
            List<List<Integer>> twoDList = get2DListFromTable();
            List<Integer> flatList = ListProcessor.flatten2DList(twoDList);
            List<Integer> processedList = ListProcessor.createNewList(flatList);
            // Отобразить обработанный список в таблице (например, в одной строке)
            displayList(processedList);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(GuiApp.this, "Ошибка при обработке списка: " + ex.getMessage());
        }
    }

    private void display2DList(List<List<Integer>> twoDList) {
        if (twoDList.isEmpty()) {
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);
            return;
        }

        // Установка столбцов
        int maxColumns = 0;
        for (int i = 0; i < twoDList.size(); i++) {
            if (twoDList.get(i).size() > maxColumns) {
                maxColumns = twoDList.get(i).size();
            }
        }
        String[] columns = new String[maxColumns];
        for (int i = 0; i < maxColumns; i++) {
            columns[i] = "Col " + (i + 1);
        }
        tableModel.setColumnIdentifiers(columns);

        // Установка строк
        tableModel.setRowCount(0);
        for (int i = 0; i < twoDList.size(); i++) {
            List<Integer> row = twoDList.get(i);
            Object[] rowData = new Object[maxColumns];
            for (int j = 0; j < maxColumns; j++) {
                if (j < row.size()) {
                    rowData[j] = row.get(j);
                } else {
                    rowData[j] = "";
                }
            }
            tableModel.addRow(rowData);
        }
    }

    private void displayList(List<Integer> list) {
        // Отображение списка в таблице в одной строке
        String[] columns = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            columns[i] = "Element " + (i + 1);
        }
        tableModel.setColumnIdentifiers(columns);
        tableModel.setRowCount(0);
        Object[] rowData = new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            rowData[i] = list.get(i);
        }
        tableModel.addRow(rowData);
    }

    private List<List<Integer>> get2DListFromTable() {
        int rows = tableModel.getRowCount();
        int cols = tableModel.getColumnCount();
        List<List<Integer>> twoDList = new ArrayList<List<Integer>>();
        for (int i = 0; i < rows; i++) {
            List<Integer> row = new ArrayList<Integer>();
            for (int j = 0; j < cols; j++) {
                Object value = tableModel.getValueAt(i, j);
                if (value != null && !value.toString().trim().isEmpty()) {
                    try {
                        Integer num = Integer.parseInt(value.toString().trim());
                        row.add(num);
                    } catch (NumberFormatException e) {
                        // Игнорировать некорректные значения
                    }
                }
            }
            twoDList.add(row);
        }
        return twoDList;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GuiApp().setVisible(true);
            }
        });
    }
}

