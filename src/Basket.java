import java.io.*;
import java.util.Objects;

public class Basket {
    private final int[] prices;
    private final String[] products;
    private static int[] amountOfProducts;

    public Basket(int[] prices, String[] products) {
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

    protected void saveTxt(File textFile) throws IOException {
        try (PrintWriter writer = new PrintWriter(textFile)) {
            for (int i : amountOfProducts) {
                writer.print(i + " ");
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    protected void printCart() {
        int total = 0;
        System.out.println("Ваша корзина:\n");
        for (int i = 0; i < products.length; i++) {
            if (amountOfProducts[i] != 0) {
                System.out.println(products[i] + " " + amountOfProducts[i] + " шт. " + prices[i] + " руб./шт. " +
                        (prices[i] * amountOfProducts[i]) + " руб. в сумме");
                total += (prices[i] * amountOfProducts[i]);
            }
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

        String[] arrayLine = Objects.requireNonNull(line).split(" ");
        amountOfProducts = new int[arrayLine.length];

        for (int i = 0; i < amountOfProducts.length; i++) {
            amountOfProducts[i] = Integer.parseInt(arrayLine[i]);
        }
    }
}
