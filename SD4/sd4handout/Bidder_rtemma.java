import java.util.List;

public class Bidder_rtemma implements Bidder {
    private double budget = Auctioneer.defaultConfig.getBudget();
    private static final double factor = 1;


  // Return your bid for the current day
  // Called once per day before the auction
  public double getBid(double dailyValue){

  }

  // Let you know if you won, and how much the winners paid
  // Called once per day after the auction
  public void addResults(List<Double> bids, int myBid, double myPayment){
    if (myBid >= 0) {
        budget -= myPayment;
      }

  }
    
}
