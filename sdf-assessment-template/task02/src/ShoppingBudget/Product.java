package ShoppingBudget;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private int id;
    // private String title;
    private int price;
    private int rating;

    public Product(int id, int price, int rating) {
        this.id = id;
        // this.title = title;
        this.price = price;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public int getRating() {
        return rating;
    }

    
}
