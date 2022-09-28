package ru.netology;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class Basket implements Serializable {

    private static final long SerialVersionUID = 1L;
    private int[] prices;
    private String[] products;
    private int[] amountOfProducts;

    protected Basket(@NotNull int[] prices, @NotNull String[] products) {
        this.prices = prices;
        this.products = products;
        amountOfProducts = new int[prices.length];
    }

    protected Basket() {
    }

    protected void addToCart(int productNum, int amount) {
        if (amount != 0) {
            amountOfProducts[productNum] += amount;
        } else {
            System.out.println("Товар: '" + products[productNum] + "' удален из корзины");
            amountOfProducts[productNum] = 0;
            prices[productNum] = 0;
        }
    }

    protected void printCart() {
        int total = 0;
        System.out.println("Ваша корзина:\n");
            for (int i = 0; i < products.length; i++) {
                if (amountOfProducts[i] != 0) {
                    System.out.println(products[i] + " " +
                            amountOfProducts[i] + " шт. " + prices[i] + " руб./шт. " +
                            (prices[i] * amountOfProducts[i]) + " руб. в сумме");
                    total += prices[i] * amountOfProducts[i];
                }
            }
        System.out.println("\nИтого:" + total + " руб.");
    }

    protected void saveTxt(File textFile) throws IOException {
        try (PrintWriter writer = new PrintWriter(textFile)) {
            for (int i : amountOfProducts) {
                writer.print(i + " ");
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    protected static Basket loadFromTxtFile(File textFile) throws IOException {
        String line = null;
        Basket basket = new Basket();
        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] arrayLine = Objects.requireNonNull(line).split(" ");
        basket.amountOfProducts = new int[arrayLine.length];

        for (int i = 0; i < basket.amountOfProducts.length; i++) {
            basket.amountOfProducts[i] = Integer.parseInt(arrayLine[i]);
        }
        return basket;
    }

    protected void saveJson(File file) throws IOException {
        Gson parser = new Gson();
        try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
            String jsonString = parser.toJson(this);
            out.write(jsonString);
            out.flush();
        } catch (IOException e) {
            throw new IOException (e);
        }
    }

    protected static Basket loadFromJsonFile(File file) throws IOException {
        Gson parser = new Gson();
        Basket jsBasket = null;
        if (file.exists()) {
            try (Reader reader = new BufferedReader(new FileReader(file))) {
                jsBasket = parser.fromJson(reader, Basket.class);
                jsBasket.printCart();
            } catch (IOException e) {
                throw new IOException(e);
            }
        }
        return jsBasket;
    }
}