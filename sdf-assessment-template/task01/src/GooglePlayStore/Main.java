package GooglePlayStore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main{

    public static void main(String[] args) {

        if (args.length <= 0) {
            System.out.println("You have entered an invalid file name");
            System.exit(1);
        }

        Map<String, CategoryStatistics> categoryStatsMap = new HashMap<>();
        
        System.out.printf("Processing file: /s%n", args[0]);

        try {FileReader fr = new FileReader(args[0]);
        BufferedReader br = new BufferedReader(fr);
            //read header first
        String header = br.readLine();

        String line;
        int totalLines = 0;

        while ((line = br.readLine()) != null) {
            totalLines++;

            String[] fields = line.split(",");

            if (fields.length<3) {
                continue;
            }

            String appName = fields[0].trim().toUpperCase();
            String categoryName = fields[1].trim().toUpperCase();
            double rating;

            try {
                rating = Double.parseDouble(fields[2]);
            } catch (NumberFormatException e) {
                CategoryStatistics.discardedCount();;
                continue;
            }

            CategoryStatistics categoryStatistics = categoryStatsMap.get(categoryName);
            if (categoryStatistics == null) {
                categoryStatistics = new CategoryStatistics();
                categoryStatsMap.put(categoryName, categoryStatistics);
            }
            categoryStatistics.appStats(appName, rating);
        }

        categoryStatsMap.forEach((category, stats) -> {
            System.out.println("Catergory: " + category);
            CategoryStatistics.printStats();
        });

        System.out.println("Total lines in file: " + totalLines);
        br.close();
    } catch (IOException e) {
        e.printStackTrace();
    }  
    }
}