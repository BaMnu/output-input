import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        File textFile = new File("myBasket.txt");

        int[] prices = {40, 250, 100, 80, 200, 120};
        String[] products = {"Хлеб", "Сыр", "Гречневая крупа", "Яблоки", "Помидоры", "Огурцы"};

        Basket basket = new Basket(prices, products);

        System.out.println("Список доступных товаров:\n");
        for (int i = 0; i < products.length; i++) {
            System.out.printf("%d. %s %d руб./шт.%n", i + 1, products[i], prices[i]);
        }
        System.out.println();

        if (textFile.exists()) {
            try {
                basket = Basket.loadFromTxtFile(textFile);
                basket.printCart();
            } catch (IOException e) {
                throw new IOException(e);
            }
        } else {
            textFile = new File("myBasket.txt");
        }

        while (true) {
            System.out.print("Введите номер продукта: ");

            String productNum = scanner.nextLine();
            if ("end".equals(productNum)) {
                break;
            } else if (Integer.parseInt(productNum) - 1 > (products.length) || Integer.parseInt(productNum) <= 0) {
                System.out.println("Ошибка! Введены некорректные данные. " +
                        "Номер товара не может больше [6] и меньше [1]. Вы ввели: " + "[ " + productNum + " ]");
                continue;
            }

            System.out.print("Введите количество: ");
            String amount = scanner.nextLine();
            if ("end".equals(amount)) {
                break;
            } else if (Integer.parseInt(amount) < 1) {
                System.out.println("Ошибка! Введены некорректные данные. " +
                        "Количество товара не может быть меньше [1]. Вы ввели: " + "[ " + amount + " ]");
                continue;
            }

            try {
                basket.addToCart(Integer.parseInt(productNum), Integer.parseInt(amount));
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: " + e);
                System.out.println("Введены некорректные данные. " +
                        "Введите номер продукта и/или кол-во единиц цифрами. " +
                        "Пример: [2 3]. Вы ввели: " + "[Номер продукта: " + productNum + " Кол-во: " + amount + "]");
            }
        }

        basket.printCart();

        try {
            basket.saveTxt(textFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        scanner.close();
    }
}