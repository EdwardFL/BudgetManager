public class Purchase {
    private double price;
    private String category;

    public Purchase(double price, String category) {
        this.price = price;
        this.category = category;
    }
    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

}
