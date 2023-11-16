package GooglePlayStore;

public class CategoryStatistics {

    private static int count;
    private static int discardedCount;
    private static String categoryName;
    private static double totalRating;
    private static double highestAppRating;
    private static double lowestAppRating;
    private static String highestAppName;
    private static String lowestAppName;

    //get lowest/highest ratings, by comparing each app
    public void appStats(String appName, double rating) {
        count++;

        if(rating< lowestAppRating) {
            lowestAppRating = rating;
            lowestAppName = appName;
        } else if (rating > highestAppRating) {
            highestAppRating = rating;
            highestAppName = appName;
        }
        totalRating += rating; 
    }  

    public static int discardedCount() {
        discardedCount++;
        return discardedCount;
    }

    public static void printStats() {
        System.out.println("Category: " + categoryName);
        System.out.println("Highest: " + highestAppName + ", <" + highestAppRating + ">");
        System.out.println("Lowest: " + lowestAppName + ", <" + lowestAppRating + ">");
        System.out.println("Average: "  + (totalRating/count));
        System.out.println("Count: " + count);
        System.out.println("Discarded: " + discardedCount);

    }
}
