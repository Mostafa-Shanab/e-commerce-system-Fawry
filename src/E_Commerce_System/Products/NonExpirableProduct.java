package E_Commerce_System.Products;

public class NonExpirableProduct extends Product{
    private final boolean shippable;
    private final double weight;
    public NonExpirableProduct(String name, double price, int quantity, boolean shippable, double weight){
        super(name, price, quantity);
        this.weight = weight;
        this.shippable = shippable;
    }
    public double getWeight() {
        return weight;
    }

    public boolean isShippable() {
        return shippable;
    }
}
