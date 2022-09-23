package ru.netology;

import au.com.bytecode.opencsv.*;

import java.io.*;
import java.util.*;

public class ClientLog implements Serializable {
    private static final long serialVersionUID = 1L;
    private final ArrayList<String> numbers = new ArrayList<>();
    private final ArrayList<String> amountOfProds = new ArrayList<>();

    protected void log(int productNum, int amount) throws RuntimeException {
        numbers.add(String.valueOf(productNum));
        amountOfProds.add(String.valueOf(amount));
    }

    protected void exportAsCSV(File file) throws IOException {
        if (file.length() == 0) {
            // write data with the header on the top
            try (CSVWriter writer = new CSVWriter(new FileWriter(file, true),
                    CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
                writer.writeNext("productNum,amount");
                for (int i = 0; i < numbers.size(); i++) {
                    writer.writeNext(numbers.get(i), amountOfProds.get(i));
                }
                writer.flush();
                System.out.println("История покупок сохранена");
            } catch (IOException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        } else {
            // continue to write data without the header
            try (CSVWriter writer = new CSVWriter(new FileWriter(file, true),
                    CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END)) {
                for (int i = 0; i < numbers.size(); i++) {
                    writer.writeNext(numbers.get(i), amountOfProds.get(i));
                }
                writer.flush();
                System.out.println("История покупок сохранена");
            } catch (IOException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }
}


