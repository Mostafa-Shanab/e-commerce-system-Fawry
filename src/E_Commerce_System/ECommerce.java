package E_Commerce_System;

import E_Commerce_System.Cart.CartCar;
import E_Commerce_System.Customer.Customer;
import E_Commerce_System.Products.ExpirableProduct;
import E_Commerce_System.Products.NonExpirableProduct;
import E_Commerce_System.Products.Product;
import E_Commerce_System.Services.Checkout;

import java.util.Date;

public class ECommerce {
    public static void main(String[] args) {
        Date futureDate = new Date(System.currentTimeMillis() + 86400000L * 30); // 30 days from now
        Date pastDate = new Date(System.currentTimeMillis() - 86400000);

        Product cheese = new ExpirableProduct("Cheese", 100, 10, true, 0.2, futureDate);
        Product biscuits = new ExpirableProduct("Biscuits", 150, 5, true, 0.7, futureDate);
        Product tv = new NonExpirableProduct("TV", 1000, 3, true, 5.0);
        Product scratchCard = new NonExpirableProduct("Scratch Card", 50, 100, false, 0);
        Product expiredCheese = new ExpirableProduct("Expired Cheese", 80, 2, true, 0.2, pastDate);

        Checkout checkoutService = new Checkout();


        // Test case 1: Normal checkout
        System.out.println("=== Test Case 1: Normal Checkout ===");
        Customer customer = new Customer("Mostafa Shanab", 5000);
        CartCar cart1 = new CartCar();
        cart1.add(cheese, 2);
        cart1.add(biscuits, 1);
        cart1.add(tv, 1);
        cart1.add(scratchCard, 3);

        checkoutService.checkout(customer, cart1);


        // Test case 2: Empty cart
        System.out.println();
        System.out.println("=== Test Case 2: Empty Cart ===");
        CartCar cart2 = new CartCar();
        try {
            checkoutService.checkout(customer, cart2);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }


        // Test case 3: Insufficient balance
        System.out.println();
        System.out.println("=== Test Case 3: Insufficient Balance ===");
        CartCar cart3 = new CartCar();
        Customer customer3 = new Customer("Mostafa Shanab", 200);
        cart3.add(tv, 1); // Total will be 1,000 but customer only has 200
        try {
            checkoutService.checkout(customer3, cart3);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Test case 4: Expired product
        System.out.println();
        System.out.println("=== Test Case 4: Expired Product ===");
        CartCar cart4 = new CartCar();
        try {
            cart4.add(expiredCheese, 1);
            checkoutService.checkout(customer, cart4);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println();

        System.out.println();

        // Test case 5: Out of stock
        System.out.println("=== Test Case 5: Out of Stock ===");
        CartCar cart5 = new CartCar();
        try {
            cart5.add(biscuits, 10);
            checkoutService.checkout(customer, cart5);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
