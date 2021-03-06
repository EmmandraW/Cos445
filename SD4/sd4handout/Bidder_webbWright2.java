
import java.util.List;
public class Bidder_webbWright2 implements Bidder{
  private double budget = Auctioneer.defaultConfig.getBudget();
  private static final double factor = 1;
  private double avg = 0;
  private double initial = 35000;
  private double minimum = 5;
  // Return your bid for the current day
  // Called once per day before the auction
  public double getBid(double dailyValue){
    double first = Math.min(avg, budget);
    double second = Math.min(first, dailyValue);
    return second;

  }

  // Let you know if you won, and how much the winners paid
  // Called once per day after the auction
  public void addResults(List<Double> bids, int myBid, double myPayment){
    if (myBid >= 0) {
      budget -= myPayment;
    }

  avg = 0;
  for(int i = 0; i < bids.size(); i++){
    double bid = bids.get(i);
    avg += bid;
    initial -= bid;
  }

  avg = avg/10;

}
    
}
