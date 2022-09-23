package ru.netology;

import java.io.*;

public class Basket implements Serializable {
    private final int[] prices;
    private final String[] products;
    protected int[] amountOfProducts;
    private static final long SerialVersionUID = 1L;

    protected Basket(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;
        amountOfProducts = new int[products.length];
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
                System.out.println(products[i] + " " + amountOfProducts[i] + " шт. " + +prices[i] + " руб./шт. " +
                (prices[i] * amountOfProducts[i]) + " руб. в сумме");
                total += prices[i] * amountOfProducts[i];
            }
        }
        System.out.println("\nИтого:" + total + " руб.");
    }

    protected void saveBin(File file) throws IOException {
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(file))) {
            writer.writeObject(this);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Basket loadFromBinFile(File file) throws IOException, ClassCastException {
        Basket load = null;
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file))) {
            load = (Basket) reader.readObject();
            load.printCart();
        } catch (IOException io) {
            throw new IOException(io);
        } catch (ClassCastException | ClassNotFoundException cc) {
            cc.printStackTrace();
        }
        return load;
    }
}