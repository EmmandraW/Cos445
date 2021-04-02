// Ultimatum.java: interface for Ultimatum
// COS 445, Spring 2018
// Created by Andrew Wonnacott

public class Ultimatum_coalitionV2_emmandra_rtwebb implements Ultimatum {
    // We will instantiate an Alice and a Bob, then call methods as follows:
    // Setup on Alice and Bob in no particular order
    // Cheap talk on Bob
    // Propose on Alice
    // Accept on Bob

    private int bobTalk; //said as bob
    private int bobSawAlice; //saw alice say as bob
    private boolean bobAccepted;
    private int aliceSawCheap; // as alice saw bobs cheap
    private int alicePropose; // as alice propose 

    private int cheap = 136393;
    private int warning = 99;
    private int verified = 0;
    private int gamesAsBob = 0;
    private int opponentGameAsBob;
    private boolean inCoalition;
  
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
        int opponentAsBobRejectedCumulative) {

        bobTalk = opponentAsBobSaid;
        bobSawAlice = opponentSawAliceSaid;
        bobAccepted = opponentAsBobAccepted;
        aliceSawCheap = opponentSawBobSaid;
        alicePropose = opponentAsAliceSaid;

        if((bobTalk == -1) && (bobSawAlice == -1) && (bobAccepted)){ 
            bobSawAlice = verified;
            bobAccepted = true;
        }

        if((aliceSawCheap == -1) && (alicePropose == -1) && (opponentAsAliceWasAccepted)){
            alicePropose = warning;
        }

        opponentGameAsBob = opponentAsBobAcceptedCumulative + opponentAsBobRejectedCumulative;
        
        if((bobSawAlice == verified || (!bobAccepted)) && 
            (alicePropose == verified || alicePropose == warning)){
            
            inCoalition = true;
        }
        
        }
  
    // This method will be called on Bobs to get their cheap talk proposal
    public int cheapTalk() {
      // FCFCQQQ
      gamesAsBob++;
      return (gamesAsBob * cheap + opponentGameAsBob) % 100;
    }
  
    // This method will be called on Alices to get their real proposal
    public int propose(int bobCheapTalk) {
      opponentGameAsBob++;
      if (bobCheapTalk == ((opponentGameAsBob * cheap + gamesAsBob) % 100) 
          && inCoalition)
          return verified;
      else{
        return warning;
      }
    }
  
    // This method will be called on Bobs to see if they accept Alice's proposal
    public boolean accept(int aliceProposal) {
        //add cheap talk of cheap 
        if (aliceProposal == verified){
            return true;
        }
        else{
            return false;
        }
      
    }
  }
  