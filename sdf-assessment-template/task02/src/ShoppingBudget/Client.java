package ShoppingBudget;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    
    static String server;
    static int port;
    static Product product = new Product();
    static List<Product> products = new ArrayList<>();
    

    public static void main(String[] args) throws IOException {
        //
        if (args.length == 0) { //no parameters provided, uses default
            server = "localhost";
            port = 3000;
        } else if (args.length == 1) { //
            try {
                port = Integer.parseInt(args[0]); //safer to wrap it in a try catch block
            } catch (Exception e) {
                System.out.println("Not valid port number");
                System.exit(1); //1 means program exits abnormally, 0 - proper exit
            }
            
        } else if (args.length == 2) { //given in assignment that is server, port
            server = args[0];
            port = Integer.parseInt(args[1]); 
        } else {
            System.out.println("invalid command, try again"); 
        }

        System.out.println("Connecting to server...");
        Socket s = new Socket(server, port);
        System.out.println("Connected to server");

        
        
        InputStream is = s.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String line2 = "";

        while ((line2 = br.readLine()) != null) { //inital method will read 1st line over and over again
            System.out.println(line2);
        }

        String line;
        line = br.readLine();
        int count = 0;
        int item_count = Integer.MAX_VALUE;

        String key;
        String value;

        while (line != null) {
            if (line.length() <= 0) { //to ignore null lines
                continue;
            }
            if (line.contains(":")) {
                String [] lines = line.trim().split(":");
                key = lines[0].trim();
                value = lines[1].trim();
            } else {
                key = line;
                value = "";
            }

            String requestID = "";
            String itemCount = "";
            double budgetDouble = 0;
            int prodId = 0;
            String title = "";
            double price = 0;
            double rating = 0;

            switch (key) {
                case ProductResponse.REQUEST_ID:
                    productResponse.setRequestId(value);
                    break;
                case ProductResponse.ITEM_COUNT:
                    itemCount = Integer.parseInt(value);
                    productResponse.setItemCount(itemCount);
                    break;
                case ProductResponse.BUDGET:
                    productResponse.setBudget(Double.parseDouble(value));
                    break;
                case ProductResponse.PROD_START:
                    product = new Product();
                    break;
                case ProductResponse.PROD_ID:
                    product.setId(value);
                    break;
                case ProductResponse.TITLE:
                    product.setTitle(value);
                    break;
                case ProductResponse.PRICE:
                    product.setPrice(Double.parseDouble(value));
                    break;
                case ProductResponse.RATING:
                    product.setRating(Double.parseDouble(value));
                    break;
                case ProductResponse.PROD_END:
                    products.add(product);
                    count++;
                    break;
            }
            System.out.println("Completed parsing information from server.");

            if (count == item_count) {
                break;
            }

        }
        productResponse.setProducts(
                products.stream()
                .sorted((a, b) -> a.getRating().intValue() - b.getRating().intValue())
                .toList()
            );

         List<Product> products2 = productResponse.getProducts();
         double budget = productResponse.getBudget();
         double spent = 0.0d;
         List<String> requestItems = new LinkedList<>();
         for (Product product2 : products2) {
                requestItems.add(product2.getId());
                spent += product2.getPrice();
                if ((budget -= product2.getPrice()) <= 0) {
                    break;
                }
         }

        ProductRequest productRequest = new ProductRequest();
         productRequest.setRequestId(productResponse.getRequestId());
         productRequest.setName("anjali");
         productRequest.setEmail("anjali006@e.ntu.edu.sg");
         productRequest.setItems(requestItems
            .stream()
            .collect(Collectors.joining(","))
        );
        productRequest.setSpent(spent);
        productRequest.setRemaining(budget - spent);

        OutputStream os = s.getOutputStream();
            OutputStreamWriter osr = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osr);

            bw.write("%s: %s\n".formatted(ProductRequest.REQUEST_ID, productRequest.getRequestId()));
            bw.write("%s: %s\n".formatted(ProductRequest.NAME, productRequest.getName()));
            bw.write("%s: %s\n".formatted(ProductRequest.EMAIL, productRequest.getEmail()));
            bw.write("%s: %s\n".formatted(ProductRequest.ITEMS, productRequest.getItems()));
            bw.write("%s: %s\n".formatted(ProductRequest.SPENT, productRequest.getSpent()));
            bw.write("%s: %s\n".formatted(ProductRequest.REMAINING, productRequest.getRemaining()));
            bw.write("%s\n".formatted(ProductRequest.CLIENT_END));
            bw.flush();
        

        InputStream is2 = s.getInputStream();
            InputStreamReader isr2 = new InputStreamReader(is);
            BufferedReader br2 = new BufferedReader(isr);
            String line3;
            while ((line3 = br2.readLine()) != null) {
                System.out.println(line2);
            }

            br.close();
            isr.close();
            is.close();
            bw.close();
            osr.close();
            os.close();
            br2.close();
            isr2.close();
            is2.close();
            s.close();
    }
}