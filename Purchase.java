package budget;

public class Purchase {
    private final String product;
    private final float price;
    private final Category category;

    public Purchase(String product, float price, Category category) {
        this.product = product;
        this.price = price;
        this.category = category;
    }

    public String getProduct() {
        return product;
    }

    public float getPrice() {
        return price;
    }

    public String getPurchaseInfo() {
        return product +
                " $" +
                String.format("%.2f", price);
    }

    public Category getCategory() {
        return category;
    }
}
