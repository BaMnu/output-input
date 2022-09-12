import java.io.*;
import java.util.*;

public class Basket {
    public static int[] prices = getPrices();
    public String[] products;
    protected int total = 0;
    protected static int[] totalPrice = new int[6];
    protected static int[] totalAmountOfProducts = new int[6];
    Basket basket;

    public Basket(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;
    }

    protected void addToCart(int productNum, int amount) {
        totalAmountOfProducts[productNum] += amount;
        totalPrice[productNum] += prices[productNum] * totalAmountOfProducts[productNum];

        if (amount == 0 || (totalPrice[productNum] + amount) < 0) {
            System.out.println("Товар: '" + products[productNum] + "' удален из корзины");
            totalPrice[productNum] = 0;
            totalAmountOfProducts[productNum] = 0;
            prices[productNum] = 0;
            total = 0;
        }
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

    protected static void loadFromTxtFile(File textFile) throws IOException {
        String line = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] arrayLine = line.split(" ");
        totalAmountOfProducts = new int[arrayLine.length];

        for (int i = 0; i < totalAmountOfProducts.length; i++) {
            totalAmountOfProducts[i] = Integer.parseInt(arrayLine[i]);
            if (totalAmountOfProducts[i] != 0) {
                totalPrice[i] = prices[i] * totalAmountOfProducts[i];
            }
        }
    }

    public static int[] getPrices() {
        return prices;
    }
}