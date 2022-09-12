import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        File textFile = new File("myBasket.txt");

        String[] products = {"Хлеб", "Сыр", "Гречневая крупа", "Яблоки", "Помидоры", "Огурцы"};
        int[] prices = {40, 250, 100, 80, 200, 120};

        Basket basket = new Basket(prices, products);

        System.out.println("Список доступных товаров:\n");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + "." + " " + products[i] + " " + prices[i] + " руб./шт.");
        }

        int prodNum;
        int prodCount;

        if (textFile.exists()) {
            try {
                Basket.loadFromTxtFile(textFile);
                basket.printCart();
            } catch (IOException e) {
                throw new IOException(e);
            }
        } else {
            System.out.println("Ваша корзина пуста. Наполните корзину товарами.");
            textFile = new File("myBasket.txt");
        }

        while (true) {
            System.out.println("\nВыберите товар и кол-во, либо введите 'end' для завершения");
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

        }
        basket.saveTxt(textFile);
        basket.printCart();

        sc.close();
    }
}