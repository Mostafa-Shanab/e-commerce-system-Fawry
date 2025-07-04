package E_Commerce_System.Products;

import java.util.Date;

public class ExpirableProduct extends Product{
    private final Date expiryDate;
    private final boolean shippable;
    private final double weight;
    public ExpirableProduct(String name, double price, int quantity, boolean shippable, double weight, Date expiryDate) {
        super(name, price, quantity);
        this.weight = weight;
        this.expiryDate = expiryDate;
        this.shippable = shippable;
    }

    public double getWeight() {
        return weight;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public boolean isShippable() {
        return shippable;
    }
}
