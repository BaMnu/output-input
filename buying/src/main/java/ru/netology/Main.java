package ru.netology;

import com.google.gson.Gson;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        String[] products = {"Хлеб", "Сыр", "Гречневая крупа", "Яблоки", "Помидоры", "Огурцы"};
        int[] prices = {40, 250, 100, 80, 200, 120};

        int prodNum;
        int prodCount;

        Basket basket = new Basket(prices, products);
        ClientLog clientLog = new ClientLog();

        Gson parser = new Gson();

//        File file = new File("basket.bin");
        File logFile = new File("log.csv");
        File jsonFile = new File("basket.json");

        System.out.println("Список доступных товаров:\n");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + "." + " " + products[i] + " " + prices[i] + " руб./шт.");
        }

        if (jsonFile.exists()) {
            try (Reader reader = new BufferedReader(new FileReader(jsonFile))) {
                basket = parser.fromJson(reader, Basket.class);
                basket.printCart();
            } catch (IOException e) {
                throw new IOException(e);
            }
        } else {
            System.out.println("Ваша корзина пуста. Наполните корзину товарами.");
            jsonFile = new File("basket.json");
        }

        while (true) {
            System.out.println("\nВыберите товар и кол-во. " +
                    "Введите 0 в кол-во, чтобы удалить товар из корзины. " +
                    "Введите 'end' для завершения");
            try {
                String insert = sc.nextLine();
                if ("end".equals(insert)) {
                    break;
                }

                String[] parts = insert.split(" ");

                prodNum = Integer.parseInt(parts[0]) - 1;
                prodCount = Integer.parseInt(parts[1]);

            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Нужно вводить только числа!");
                continue;
            }
            basket.addToCart(prodNum, prodCount);
            clientLog.log((prodNum + 1), prodCount);
        }
        basket.printCart();

        if (logFile.exists()) {
            try {
                clientLog.exportAsCSV(logFile);
            } catch (IOException e) {
                throw new IOException(e);
            }
        } else {
            logFile = new File("log.csv");
            clientLog.exportAsCSV(logFile);
        }

        try (BufferedWriter out = new BufferedWriter(new FileWriter(jsonFile))) {
            String jsonString = parser.toJson(basket);
            out.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sc.close();
    }
}