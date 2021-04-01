// Ultimatum_smweinberg.java: sample implementation for Ultimatum
// COS 445, Spring 2018
// Created by Andrew Wonnacott

public class Ultimatum_smweinberg implements Ultimatum {

  private int pastTalk;
  private int pastProposal;

  public void setup(
      int opponentAsBobSaid,
      int b,
      boolean c,
      int d,
      int opponentAsAliceSaid,
      boolean f,
      int g,
      int h) {
    pastTalk = opponentAsBobSaid;
    pastProposal = opponentAsAliceSaid;
  }

  // sample Alice strategy
  public int propose(int cheapTalk) {
    // first round: propose that I get 60, you get 80
    if (pastProposal == -1) {
      return 60;
    }

    // otherwise, propose opponent's past play +/- 5
    int p = (int) Math.round(pastProposal + 10 * Math.random() - 5);
    if (p < 0) p = 0;
    if (p > 99) p = 99;
    return p;
  }

  // sample Bob strategy
  public int cheapTalk() {
    if (pastTalk == -1) {
      return 25;
    }
    return pastTalk;
  }

  public boolean accept(int proposal) {
    // first round: accept if I get at least 60
    if (pastProposal == -1) {
      return proposal <= 70;
    }

    // otherwise, accept if proposal is at most $2 worse for me than what my opponent proposed last
    // time
    // (remember that my payoff is 200-2x)
    return proposal <= pastProposal + 2;
  }
}
