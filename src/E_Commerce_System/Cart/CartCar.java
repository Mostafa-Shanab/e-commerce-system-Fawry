package E_Commerce_System.Cart;

import E_Commerce_System.Products.Product;

import java.util.ArrayList;
import java.util.List;

public class CartCar {
    private List<CartItem> allItems = new ArrayList<>();
    public void add(Product product, int quantity){
        // Product is added Before (Just add Quantity)
        for (CartItem item : allItems) {
            if (item.getProduct().equals(product)) {
                int newQuantity = item.getQuantity() + quantity;
                allItems.remove(item);
                item = new CartItem(product, newQuantity);
                allItems.add(item);
                return;
            }
        }
        allItems.add(new CartItem(product, quantity));
    }

    public List<CartItem> getAllItems(){
        return allItems;
    }
}
