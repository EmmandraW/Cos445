import java.util.List;

public class Bidder_webbWright3 implements Bidder {
    private double budget = Auctioneer.defaultConfig.getBudget();
    private static final double factor = 1;
    private double avg = 0;
    private double initial = 35000;
    private double minimum = 5;

  //sum over all budgets, continuously subtract 
  //35000 -> 10 -> 1,000
  // Return your bid for the current day
  // Called once per day before the auction
  public double getBid(double dailyValue){
    // if(initial > 0){
      //this or just bid actual value
      double first = Math.min(minimum, budget);
      return Math.min(first, dailyValue);
    // }
    // // else if(initial > 1000 ){
    // //   return Math.min(dailyValue, budget);
    // // }
    // else {
    //   return budget;
    // }

  }

  // Let you know if you won, and how much the winners paid
  // Called once per day after the auction
  public void addResults(List<Double> bids, int myBid, double myPayment){
    if (myBid >= 0) {
        budget -= myPayment;
      }

    for(int i = 0; i < bids.size(); i++){
      initial -= bids.get(i);
    }

  }
    
}
