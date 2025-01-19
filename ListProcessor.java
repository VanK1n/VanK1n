package task_9;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListProcessor {

    // Функция для создания нового списка без подряд идущих одинаковых элементов
    public static List<Integer> createNewList(List<Integer> list) {
        List<Integer> newList = new ArrayList<Integer>();
        if (list.size() == 0) {
            return newList;
        }
        Integer previous = null;
        for (int i = 0; i < list.size(); i++) {
            Integer current = list.get(i);
            if (previous == null || !current.equals(previous)) {
                newList.add(current);
                previous = current;
            }
        }
        return newList;
    }

    // Функция для чтения списка чисел из файла
    public static List<Integer> readListFromFile(String filename) throws IOException {
        List<Integer> list = new ArrayList<Integer>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.trim().split("\\s+");
            for (int i = 0; i < tokens.length; i++) {
                try {
                    Integer num = Integer.parseInt(tokens[i]);
                    list.add(num);
                } catch (NumberFormatException e) {
                    // Игнорировать некорректные числа
                }
            }
        }
        reader.close();
        return list;
    }

    // Функция для записи списка чисел в файл
    public static void writeListToFile(List<Integer> list, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < list.size(); i++) {
            writer.write(list.get(i).toString());
            if (i < list.size() - 1) {
                writer.write(" ");
            }
        }
        writer.newLine();
        writer.close();
    }

    // Дополнительные функции для оконного приложения (работа с двумерным массивом)

    // Чтение двумерного массива из файла
    public static List<List<Integer>> read2DListFromFile(String filename) throws IOException {
        List<List<Integer>> twoDList = new ArrayList<List<Integer>>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            List<Integer> row = new ArrayList<Integer>();
            String[] tokens = line.trim().split("\\s+");
            for (int i = 0; i < tokens.length; i++) {
                try {
                    Integer num = Integer.parseInt(tokens[i]);
                    row.add(num);
                } catch (NumberFormatException e) {
                    // Игнорировать некорректные числа
                }
            }
            twoDList.add(row);
        }
        reader.close();
        return twoDList;
    }

    // Запись двумерного массива в файл
    public static void write2DListToFile(List<List<Integer>> twoDList, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < twoDList.size(); i++) {
            List<Integer> row = twoDList.get(i);
            for (int j = 0; j < row.size(); j++) {
                writer.write(row.get(j).toString());
                if (j < row.size() - 1) {
                    writer.write(" ");
                }
            }
            writer.newLine();
        }
        writer.close();
    }

    // Преобразование двумерного списка в одномерный список (например, для обработки)
    public static List<Integer> flatten2DList(List<List<Integer>> twoDList) {
        List<Integer> flatList = new ArrayList<Integer>();
        for (int i = 0; i < twoDList.size(); i++) {
            List<Integer> row = twoDList.get(i);
            for (int j = 0; j < row.size(); j++) {
                flatList.add(row.get(j));
            }
        }
        return flatList;
    }

    // Преобразование одномерного списка в двумерный список с заданным количеством столбцов
    public static List<List<Integer>> unflattenList(List<Integer> flatList, int columns) {
        List<List<Integer>> twoDList = new ArrayList<List<Integer>>();
        List<Integer> row = new ArrayList<Integer>();
        for (int i = 0; i < flatList.size(); i++) {
            row.add(flatList.get(i));
            if ((i + 1) % columns == 0) {
                twoDList.add(row);
                row = new ArrayList<Integer>();
            }
        }
        if (row.size() > 0) {
            twoDList.add(row);
        }
        return twoDList;
    }
}

