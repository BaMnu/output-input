package ru.netology;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        String[] products = {"Хлеб", "Сыр", "Гречневая крупа", "Яблоки", "Помидоры", "Огурцы"};
        int[] prices = {40, 250, 100, 80, 200, 120};
        Basket basket = new Basket(prices, products);

        int prodNum;
        int prodCount;

        ClientLog clientLog = new ClientLog();

        File textFile = new File("myBasket.txt");
        File logFile = new File("log.csv");
        File jsonFile = new File("basket.json");

        System.out.println("Список доступных товаров:\n");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + "." + " " + products[i] + " " + prices[i] + " руб./шт.");
        }

        File xmlFile = new File("shop.xml");

        Document doc = Jsoup.parse(xmlFile, "UTF-8");

        Elements load = doc.getElementsByTag("load");
        Elements save = doc.getElementsByTag("save");
        Elements log = doc.getElementsByTag("log");
// load configuration
        for (Element line : load) {

            if ((line.select("enabled").get(0).text()).equals("true")) {
                String[] parts = line.select("fileName").get(0).text().split("\\.");
                String name = parts[0];
                String extension = line.select("format").get(0).text();
                File newFile = new File(String.join(".", name, extension));

                if (extension.equals("json") && (jsonFile.exists())) {
                    basket = Basket.loadFromJsonFile(jsonFile);
                } else if (extension.equals("json") && (!jsonFile.exists())) {
                    System.out.println("Ваша корзина пуста. Наполните корзину товарами.");
                    jsonFile = newFile;

                } else if (extension.equals("txt") && (textFile.exists())) {
                    try {
                        basket = Basket.loadFromTxtFile(textFile);
                        basket.printCart();
                    } catch (IOException e) {
                        throw new IOException(e);
                    }

                } else if (extension.equals("txt") && (!textFile.exists())) {
                    System.out.println("Ваша корзина пуста. Наполните корзину товарами.");
                    textFile = newFile;
                }
            }
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
// log configuration

        for (Element row : log) {

            if ((row.select("enabled").get(0).text()).equals("true")) {
                if (logFile.exists()) {
                    try {
                        clientLog.exportAsCSV(logFile);
                    } catch (IOException e) {
                        throw new IOException(e);
                    }

                } else {
                    clientLog.exportAsCSV(new File(row.select("fileName").get(0).text()));
                }
            }
        }
// save configuration

        for (Element element : save) {

            if ((element.select("enabled").get(0).text()).equals("true")) {
                String[] parts = element.select("fileName").get(0).text().split("\\.");
                String name = parts[0];
                String extension = element.select("format").get(0).text();
                File newFile = new File(String.join(".", name, extension));

                if (extension.equals("json") && (jsonFile.exists())) {
                    basket.saveJson(jsonFile);

                } else if (extension.equals("json") && (!jsonFile.exists())) {
                    basket.saveJson(newFile);

                } else if (extension.equals("txt") && (textFile.exists())) {
                    basket.saveTxt(textFile);

                } else if (extension.equals("txt") && (!textFile.exists())) {
                    basket.saveTxt(newFile);
                }
            }
        }
        sc.close();
    }
}