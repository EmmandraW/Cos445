// Ultimatum.java: interface for Ultimatum
// COS 445, Spring 2018
// Created by Andrew Wonnacott

public class Ultimatum_emmandra implements Ultimatum {
    // We will instantiate an Alice and a Bob, then call methods as follows:
    // Setup on Alice and Bob in no particular order
    // Cheap talk on Bob
    // Propose on Alice
    // Accept on Bob
    private int gamesAsBob = 0; // number of games I've played as Bob
    private int opponentGamesAsBob; //number of games oponent played
    private boolean inCoalition = false; //oponent in coalition or not

    private int cheap = 136393;
    private int warning = 99;
    private int verified = 0;
   
  
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


        if((opponentAsBobSaid == -1) && (opponentSawAliceSaid == -1) && (opponentAsBobAccepted)){
            //bobTalk = cheap;
            opponentSawAliceSaid = verified;
            opponentAsBobAccepted = true;
        }

        if((opponentSawBobSaid == -1) && (opponentAsAliceSaid == -1) && (opponentAsAliceWasAccepted)){
            //aliceSawCheap = cheap;
            opponentAsAliceSaid = warning;

        }

        opponentGamesAsBob = opponentAsBobRejectedCumulative + opponentAsBobAcceptedCumulative;

        if( (opponentSawAliceSaid == verified || !opponentAsBobAccepted) && 
            (opponentAsAliceSaid == verified || opponentAsAliceSaid == warning) ){
            
            inCoalition = true;
        }
        
        }
  
    // This method will be called on Bobs to get their cheap talk proposal
    public int cheapTalk() {
      // FCFCQQQ
      gamesAsBob++;
      return (gamesAsBob * cheap + opponentGamesAsBob) % 100;
    }
  
    // This method will be called on Alices to get their real proposal
    public int propose(int bobCheapTalk) {
        opponentGamesAsBob++;
        int hash = (opponentGamesAsBob * cheap + gamesAsBob) % 100;
      if(bobCheapTalk == hash && inCoalition){
        return verified;
      }
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
  
  