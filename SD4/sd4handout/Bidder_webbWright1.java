import java.util.List;

public class Bidder_webbWright1 implements Bidder{
    private double budget = Auctioneer.defaultConfig.getBudget();
    private static final double factor = 1;
    private double initial = 35000;
    private double minimum = 5;
    private int round = 1;
    private double sum = 0;
    // Return your bid for the current day
    // Called once per day before the auction
    public double getBid(double dailyValue){
        
      double first = Math.min(sum /(10 * round), budget);
      double second = Math.min(first, dailyValue);
      return second;
  
    }
  
    // Let you know if you won, and how much the winners paid
    // Called once per day after the auction
    public void addResults(List<Double> bids, int myBid, double myPayment){
      if (myBid >= 0) {
        budget -= myPayment;
      }
  
    for(int i = 0; i < bids.size(); i++){
      double bid = bids.get(i);
      sum += bid;
    }

    round++;
  

  
  }
    
    
}
