// Interface for COS 445 Spring 2018 Problem Set 4 problem 4
// Created by Cyril Zhang
// Modified by Andrew Wonnacott

import java.util.List;
private double budget = Auctioneer.defaultConfig.getBudget();
private static final double factor = 1;

public interface Bidder {
  // Return your bid for the current day
  // Called once per day before the auction
  public double getBid(double dailyValue);

  // Let you know if you won, and how much the winners paid
  // Called once per day after the auction
  public void addResults(List<Double> bids, int myBid, double myPayment);
}
