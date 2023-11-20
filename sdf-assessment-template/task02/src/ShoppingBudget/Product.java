package ShoppingBudget;

import java.util.List;

public class Product{

    private int id;
    private String title;
    private double price;
    private double rating;
    

    public Product(int id, String title, double price, double rating) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.rating = rating;
    }

    public Product() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Product> getProducts() {
        return null;
    }

    public double getBudget() {
        return 0;
    }
}