package task_9;


import java.io.IOException;
import java.util.List;

public class ConsoleApp {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Использование: java ConsoleApp <inputFile> <outputFile>");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];

        try {
            // Чтение списка из файла
            List<Integer> inputList = ListProcessor.readListFromFile(inputFile);
            System.out.println("Входной список: " + inputList);

            // Обработка списка
            List<Integer> outputList = ListProcessor.createNewList(inputList);
            System.out.println("Выходной список: " + outputList);

            // Запись результата в файл
            ListProcessor.writeListToFile(outputList, outputFile);
            System.out.println("Результат записан в файл: " + outputFile);
        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлами: " + e.getMessage());
        }
    }
}

