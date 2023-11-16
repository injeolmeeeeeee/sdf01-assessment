package ShoppingBudget;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class Client implements Runnable{

    private static Socket clientSocket;

    public Client(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    Map<String, Product> receivedInfo = new HashMap<>();
    
    @Override
    public void run() {

        try (InputStream is = clientSocket.getInputStream();
            OutputStream os = clientSocket.getOutputStream();
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            OutputStreamWriter osw = new OutputStreamWriter(clientSocket.getOutputStream());
            BufferedWriter bw = new BufferedWriter(osw);
            BufferedReader br = new BufferedReader(isr)){

                int itemCount = 0;
                int serverBudget = 0;
                
                String line = br.readLine();

                while ((line != null) && (!line.isEmpty())) { //skipping through empty and error lines
                    if (line.startsWith("item_count")) {
                        line = line.substring(13);
                        itemCount = Integer.parseInt(line);
                    }
                    for (int i = 0; i < itemCount; i++) {//need to read message and save into list n times, where n = item_count, then the comparison can be done
                        line = br.readLine();
                        String[] information = line.split("\n"); //store message in Array
                        
                        if (line.startsWith("request_id: ")) {
                            String requestID = information[0].substring(12);
                        } else if (line.startsWith("prod_id: ")) {
                            int productID = Integer.parseInt(information[5].substring(10));
                        } else if (line.startsWith("title: ")) {
                            String title = information[6].substring(8);
                        } else if (line.startsWith("price: ")) {
                            double price = Double.parseDouble(information[7].substring(8));
                        } else if (line.startsWith("rating: ")) {
                            double serverRating = Double.parseDouble(information[8].substring(9));
                        }
                    }
                }

                //Comparator to compare against ratings and pricing across products given
                
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int readBudget(BufferedReader br) throws IOException {
        InputStream is = clientSocket.getInputStream();
        OutputStream os = clientSocket.getOutputStream();
        InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
        OutputStreamWriter osw = new OutputStreamWriter(clientSocket.getOutputStream());
        BufferedWriter bw = new BufferedWriter(osw);
        BufferedReader br2 = new BufferedReader(isr);

        String line = br.readLine();
        if (line.startsWith("budget: ")) {
            line = line.substring(9);
            int serverBudget = Integer.parseInt(line);

            return serverBudget;
            } 
    }

    private static List<Product> selectProducts(List<Product> products, double budget) {
        products.sort(Comparator.comparing(Product::getRating).reversed().thenComparing(Product::getPrice).reversed());
        List<Product> selectedProducts = new ArrayList<>();
        double remainingBudget = budget;

        for (Product product : products) {
            if (product.getPrice() <= remainingBudget) {
                selectedProducts.add(product);
                remainingBudget -= product.getPrice();
            }
        }
        double spent = budget - remainingBudget;

        return selectedProducts;
    }

    private static void sendSelectedProducts(BufferedWriter bw, List<Product> selectedProducts) throws IOException {
        for (Product product : selectedProducts) {
            bw.write("request_id: " + requestID + "\n");
            bw.write("name: Anjali\n");
            bw.write("email: anjali@gmail.com" + "\n");
            bw.write("items: " + product.getTitle() + "\n");
            bw.write("spent: " + spent + "\n");
            bw.write("remaining" + remainingBudget + "\n");
            bw.write("client_end\n");
        }

        bw.close();

    }

