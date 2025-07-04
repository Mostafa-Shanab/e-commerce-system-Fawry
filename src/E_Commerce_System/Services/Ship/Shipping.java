package E_Commerce_System.Services.Ship;


import E_Commerce_System.Cart.CartCar;
import E_Commerce_System.Cart.CartItem;
import E_Commerce_System.Products.ExpirableProduct;
import E_Commerce_System.Products.NonExpirableProduct;
import E_Commerce_System.Products.Product;

import java.util.ArrayList;
import java.util.List;

public class Shipping {
    //first check if it is shippable then add to list
    public List<Shippable> getShippableProducts(CartCar cartCar){
        List<Shippable> shippableList = new ArrayList<>();
        for (CartItem item : cartCar.getAllItems()){
            Product product = item.getProduct();
            if (product instanceof ExpirableProduct){
                if(((ExpirableProduct) product).isShippable()){
                    Shippable shippable = new Shippable() {
                        @Override
                        public String getName() {
                            return product.getName();
                        }

                        @Override
                        public double getWeight() {
                            return ((ExpirableProduct) product).getWeight();
                        }

                        @Override
                        public int getQuantity() {
                            return item.getQuantity();
                        }
                    };
                    shippableList.add(shippable);
                }
            }
            else {
                if(((NonExpirableProduct) product).isShippable()){
                    Shippable shippable = new Shippable() {
                        @Override
                        public String getName() {
                            return product.getName();
                        }

                        @Override
                        public double getWeight() {
                            return ((NonExpirableProduct) product).getWeight();
                        }

                        @Override
                        public int getQuantity() {
                            return item.getQuantity();
                        }
                    };
                    shippableList.add(shippable);
                }
            }
        }
        return shippableList;
    }
}
