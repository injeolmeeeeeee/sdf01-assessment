package ShoppingBudget;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client implements Runnable{

    private Socket clientSocket;

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
            BufferedReader br = new BufferedReader(isr);) {

                int itemCount = 0;
                int serverBudget = 0;
                
                String line = br.readLine();

                while ((line != null) && (!line.isEmpty())) { //skipping through empty and error lines
                    if (line.startsWith("item_count")) {
                        line = line.substring(13);
                        itemCount = Integer.parseInt(line);
                    }
                    if (line.startsWith("budget: ")) {
                        line = line.substring(9);
                        serverBudget = Integer.parseInt(line);
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

                List<Product> sortedProduct = receivedInfo.values().stream();
          

            } catch (IOException e) {
                e.printStackTrace();
        }
    }
}

String name = parts[0];
