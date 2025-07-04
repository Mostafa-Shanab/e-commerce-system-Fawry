package E_Commerce_System.Services;

import E_Commerce_System.Cart.CartCar;
import E_Commerce_System.Cart.CartItem;
import E_Commerce_System.Customer.Customer;
import E_Commerce_System.Products.ExpirableProduct;
import E_Commerce_System.Products.NonExpirableProduct;
import E_Commerce_System.Products.Product;
import E_Commerce_System.Services.Ship.Shippable;
import E_Commerce_System.Services.Ship.Shipping;

import java.util.Date;
import java.util.List;

public class Checkout {

    public void checkout(Customer customer, CartCar cartCar){
        //If CartCar is empty
        if (cartCar.getAllItems().isEmpty()){
            throw new IllegalStateException("Cart is empty");
        }

        validationCheckForAllProducts(cartCar);

        double priceOfProducts = calcTotalPrice(cartCar);
        double priceOfShippableProducts = calcShipmentPrice(cartCar);
        double weightOfShippableProducts = calcShipmentWeight(cartCar);
        double shippingFee = 50;
        double totalPrice = priceOfProducts + shippingFee;

        if (customer.getBalance() < priceOfProducts){
            throw new IllegalArgumentException("Insufficient balance");
        }

        // All Conditions are Valid then minus sold products from original
        for (CartItem item : cartCar.getAllItems()){
            int numberInCart = item.getQuantity();
            int totalNumber = item.getProduct().getQuantity();
            item.getProduct().setQuantity(totalNumber - numberInCart);
        }
        // reduce Customer Balance
        double newBalance = customer.getBalance() - totalPrice;
        customer.setBalance(newBalance);

        Shipping shipping = new Shipping();
        List<Shippable> shippableList = shipping.getShippableProducts(cartCar);
        System.out.println("** Shipment notice **");
        for (Shippable shippableItem : shippableList) {
            System.out.printf("%dx %s    %.0fg%n", shippableItem.getQuantity(), shippableItem.getName(), shippableItem.getWeight() * 1000);
        }
        System.out.printf("Total price of Shippable Items %.1f%n", priceOfShippableProducts);
        System.out.printf("Total package weight %.1fkg%n%n", weightOfShippableProducts);

        System.out.println("** Checkout receipt **");
        System.out.println("Quantity \t Name \t Price");
        for (CartItem item : cartCar.getAllItems()){
            System.out.println(item.getQuantity() + "x \t\t " + item.getProduct().getName() + " \t " + item.getProduct().getPrice());
        }
        System.out.println("--------------------------------------------");
        System.out.printf("Subtotal    %.0f%n", priceOfProducts);
        System.out.printf("Shipping    %.0f%n", shippingFee);
        System.out.printf("Amount    %.0f%n", totalPrice);
        System.out.printf("Customer balance after payment: %.0f%n", newBalance);
    }

    private double calcTotalPrice(CartCar cartCar){
        double totalPrice = 0;
        for (CartItem item : cartCar.getAllItems()){
            double priceItem = item.getProduct().getPrice();
            int quantityInCartCar = item.getQuantity();
            totalPrice += priceItem * quantityInCartCar;
        }
        return totalPrice;
    }
    private double calcShipmentPrice(CartCar cartCar){
        double totalPrice = 0;
        for (CartItem item : cartCar.getAllItems()){
            Product product = item.getProduct();
            if (product instanceof ExpirableProduct){
                if(((ExpirableProduct) product).isShippable()){
                    double priceItem = product.getPrice();
                    int quantityInCartCar = item.getQuantity();
                    totalPrice += priceItem * quantityInCartCar;
                }
            }
            else {
                if(((NonExpirableProduct) product).isShippable()){
                    double priceItem = product.getPrice();
                    int quantityInCartCar = item.getQuantity();
                    totalPrice += priceItem * quantityInCartCar;
                }
            }
        }
        return totalPrice;
    }
    private double calcShipmentWeight(CartCar cartCar){
        double totalWeight = 0;
        for (CartItem item : cartCar.getAllItems()){
            Product product = item.getProduct();
            if (product instanceof ExpirableProduct){
                if(((ExpirableProduct) product).isShippable()){
                    double weightItem = ((ExpirableProduct) product).getWeight();
                    int quantityInCartCar = item.getQuantity();
                    totalWeight += weightItem * quantityInCartCar;
                }
            }
            else {
                if(((NonExpirableProduct) product).isShippable()){
                    double weightItem = ((NonExpirableProduct) product).getWeight();
                    int quantityInCartCar = item.getQuantity();
                    totalWeight += weightItem * quantityInCartCar;
                }
            }
        }
        return totalWeight;
    }
    private void validationCheckForAllProducts(CartCar cartCar){
//        Check Date for expiry elements and number of products available
        for (CartItem item : cartCar.getAllItems()){
            int totalProductQuantity = item.getProduct().getQuantity();
            int productQuantityInCart = item.getQuantity();
            if (totalProductQuantity < productQuantityInCart){
                throw new IllegalArgumentException("Product '" + item.getProduct().getName() + "' is Out of Stock");
            }
            if (item.getProduct() instanceof ExpirableProduct) {
                Date expiryDate = ((ExpirableProduct) item.getProduct()).getExpiryDate(); // expiration date
                Date currentDate = new Date(); //current date
                if (expiryDate.before(currentDate)) {
                    throw new IllegalArgumentException("Product '" + item.getProduct().getName() + "' is Expired");
                }
            }
        }
    }
}
