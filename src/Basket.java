import java.io.*;
import java.util.*;

public class Basket {

    static protected int[] prices;
    static protected String[] products;
    protected int total = 0;
    protected int[] totalPrice;
    protected int[] totalAmountOfProducts;

    //    protected int[] productNumber;
    public Basket(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;

        this.totalPrice = new int[prices.length];
        this.totalAmountOfProducts = new int[products.length];
    }

    protected void addToCart(int productNum, int amount) {
        productNum -= 1;
        totalAmountOfProducts[productNum] += amount;
        totalPrice[productNum] += prices[productNum] * totalAmountOfProducts[productNum];
    }

    protected void printCart() {
        System.out.println("Ваша корзина:\n");
        for (int i = 0; i < products.length; i++) {
            if (totalAmountOfProducts[i] != 0) {
                System.out.printf("%s %d шт. %d руб./шт. %d руб. в сумме%n",
                        products[i], totalAmountOfProducts[i], prices[i], totalPrice[i]);
            }
            total += totalPrice[i];
        }
        System.out.println("\nИтого:" + total + " руб.");
    }

    protected void saveTxt(File textFile) throws IOException {
        try (PrintWriter writer = new PrintWriter(textFile)) {
            for (int i = 0; i < products.length; i++) {
                if (totalAmountOfProducts[i] != 0) {
                    writer.print(totalAmountOfProducts[i] + " ");
                } else {
                    writer.print(0 + " ");
                }
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    protected static Basket loadFromTxtFile(File textFile) throws IOException {
        // новая строка
        String line = null;

        // считываем файл
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            // записываем в новую строку
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // разбиваем строку на строковый массив с разделителем
        String[] arrayLine = line.split(" ");
        // создаем числовой массив размером в строковый массив выше
        int[] loadedAmount = new int[arrayLine.length];
        // переносим элементы строкового массива в элементы числового массива
        for (int i = 0; i < loadedAmount.length; i++) {
            loadedAmount[i] = Integer.parseInt(arrayLine[i]);
        }
        // ... и че дальше??))
        Basket load = new Basket(prices, products){
        };

        return null;
    }

    //геттеры, которые вы посчитаете нужными.
    protected int[] getPrices() {
        return prices;
    }

    protected String[] getProducts() {
        return products;
    }

}

