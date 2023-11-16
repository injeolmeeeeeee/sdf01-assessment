package ShoppingBudget;

public class Product {
    private String title;
    private int price;
    private int rating;

    public Product(String title, int price, int rating) {
        this.title= title;
        this.price = price;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public int getRating() {
        return rating;
    }
}
