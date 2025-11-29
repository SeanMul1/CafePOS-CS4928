// File: cafepos/src/main/java/com/cafepos/demo/CafePOSCLI.java
package com.cafepos.demo;

import com.cafepos.factory.ProductFactory;
import com.cafepos.catalog.Product;
import com.cafepos.domain.Order;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.OrderIds;
import com.cafepos.common.Money;

import java.util.*;

public final class CafePOSCLI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ProductFactory factory = new ProductFactory();
    private static final Map<String, Order> orders = new LinkedHashMap<>();

    public static void main(String[] args) {
        System.out.println("Welcome to CafePOS CLI!");
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. List Products");
            System.out.println("2. Create New Order");
            System.out.println("3. Add Item to Order");
            System.out.println("4. View Order Details");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> listProducts();
                case "2" -> createOrder();
                case "3" -> addItemToOrder();
                case "4" -> viewOrderDetails();
                case "5" -> { System.out.println("Goodbye!"); return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void listProducts() {
        System.out.println("Available base products:");
        System.out.println(" - ESP: Espresso ($2.50)");
        System.out.println(" - LAT: Latte ($3.20)");
        System.out.println(" - CAP: Cappuccino ($3.00)");
        System.out.println("Add-ons: SHOT (Extra Shot), OAT (Oat Milk), SYP (Syrup), L (Large Size)");
        System.out.println("Example recipe: LAT+OAT+SHOT");
    }

    private static void createOrder() {
        long orderId = OrderIds.next();
        orders.put(String.valueOf(orderId), new Order(orderId));
        System.out.println("Created new order with ID: " + orderId);
    }


    private static void addItemToOrder() {
        if (orders.isEmpty()) {
            System.out.println("No orders. Create an order first.");
            return;
        }
        System.out.print("Enter order ID: ");
        String orderId = scanner.nextLine().trim();
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        System.out.print("Enter product recipe (e.g., LAT+OAT+SHOT): ");
        String recipe = scanner.nextLine().trim();
        Product product;
        try {
            product = factory.create(recipe);
        } catch (Exception e) {
            System.out.println("Invalid recipe: " + e.getMessage());
            return;
        }
        System.out.print("Enter quantity: ");
        int qty;
        try {
            qty = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity.");
            return;
        }
        order.addItem(new LineItem(product, qty));
        System.out.println("Added: " + product.name() + " x" + qty + " to order " + orderId);
    }

    private static void viewOrderDetails() {
        if (orders.isEmpty()) {
            System.out.println("No orders.");
            return;
        }
        System.out.print("Enter order ID: ");
        String orderId = scanner.nextLine().trim();
        Order order = orders.get(orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        System.out.println("Order #" + order.id());
        for (LineItem li : order.items()) {
            System.out.println(" - " + li.product().name() + " x" + li.quantity() + " = " + li.lineTotal());
        }
        System.out.println("Subtotal: " + order.subtotal());
        System.out.println("Tax (10%): " + order.taxAtPercent(10));
        System.out.println("Total: " + order.totalWithTax(10));
    }
}
