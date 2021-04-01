// Ultimatum.java: interface for Ultimatum
// COS 445, Spring 2018
// Created by Andrew Wonnacott

public class Ultimatum_levelzero implements Ultimatum {
  // We will instantiate an Alice and a Bob, then call methods as follows:
  // Setup on Alice and Bob in no particular order
  // Cheap talk on Bob
  // Propose on Alice
  // Accept on Bob

  // The first three are the new opponent's last play as Bob
  // The next three are the new opponent's last play as Alice
  // The defaults are -1, -1, accept (true) for each triple
  // Feel free to rename these variables in your implementation!
  public void setup(
      int opponentAsBobSaid,
      int opponentSawAliceSaid,
      boolean opponentAsBobAccepted,
      int opponentSawBobSaid,
      int opponentAsAliceSaid,
      boolean opponentAsAliceWasAccepted,
      int opponentAsBobAcceptedCumulative,
      int opponentAsBobRejectedCumulative) {}

  // This method will be called on Bobs to get their cheap talk proposal
  public int cheapTalk() {
    // FCFCQQQ
    return 33;
  }

  // This method will be called on Alices to get their real proposal
  public int propose(int bobCheapTalk) {
    return 99;
  }

  // This method will be called on Bobs to see if they accept Alice's proposal
  public boolean accept(int aliceProposal) {
    return true;
  }
}
