import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Party_emmandra implements Party {
    final boolean _isBeta;
    int districts;

    private Party_emmandra(boolean isBeta, int numDistricts) {
        _isBeta = isBeta;
        districts = numDistricts;
      }

      /* We guarantee that we will always call the methods in this order:
   * * New on the class of each of alpha and beta
   * * Repeating \texttt{numDistricts} times, with the active player initially alpha:
   *   * cut on the active party
   *   * choose on the nonactive party
   *   * accept on the active party
   *   * swap the active and nonactive parties
   */

  /* Each of the following functions must terminate within the time limit of 200 ms */

  // must construct and return a Party based on the provided blocks. Do any initialization here.
  public static Party New(boolean isBeta, int numDistricts, List<Block> blocks) {
    return new Party_emmandra(isBeta, numDistricts);


  }

  // must partition the remaining blocks into numDistricts, each with remaining.size() / numDistricts elements
  // We will issue a penalty if your strategy does any of the following:
  // * outputs the wrong number of districts
  // * outputs districts with different numbers of blocks
  // * does not include all the remaining blocks
  // * includes any other blocks.
  public List<List<Block>> cut(int numDistrictsRemaining, List<Block> remaining){
    //number of blocks in a district
    int remSize = remaining.size();
    int r = numDistrictsRemaining;
    //int alpha = 0;
    //int beta = 0;
    int diff = 0;
    //int total = 0;
    //double threshold = (100 / numDistrictsRemaining) / 2;
    //List<Integer> aWins = new ArrayList<Integer>();
    //List<Integer> bWins = new ArrayList<Integer>();

    //know number of total votes a has and all the blocks a wins
    for (int i = 0; i < remSize; i++){
     diff += remaining.get(i).betaSwing();
    }
    //if alpha has more votes
    if((diff < 0 && !_isBeta) || (diff > 0 && _isBeta)){
        remaining = new ArrayList<Block>(remaining); // get mutability
        Collections.sort(remaining, new Block.BlockComparator(true, true, true));
        // now sorted in decreasing order of partisanship
        final int districtSize = remaining.size() / r;
        List<List<Block>> ret = new ArrayList<List<Block>>();
        long[] betaSwings = new long[r];
        for (int i = 0; i < r; ++i) {
          List<Block> district = new ArrayList<Block>();
          ret.add(district);
        }
        // this is inefficient, but idrc
        for (Block block : remaining) {
          int extremum = -1;
          for (int i = 0; i < r; ++i) {
            List<Block> district = ret.get(i);
            if (district.size() == districtSize) continue;
            if (extremum == -1) extremum = i;
            // we swing beta and this is the most alpha blockyet
            if (block.betaSwing() > 0 && betaSwings[i] < betaSwings[extremum]) extremum = i;
            // we swing alpha and this is the most beta block yet
            if (block.betaSwing() < 0 && betaSwings[i] > betaSwings[extremum]) extremum = i;
            // if we hit zero swing blocks then it doesn't matter anyhow;
          }
          ret.get(extremum).add(block);
          // pulls down the swing on the beta swing
          betaSwings[extremum] += block.betaSwing();
        }
        return ret;

    //beta has more votes
    }else{
        remaining = new ArrayList<Block>(remaining); // get mutability
        Collections.sort(remaining, new Block.BlockComparator(false, true, !_isBeta));
        // now sorted in increasing order of how many more votes the other party gets
        List<List<Block>> ret = new ArrayList<List<Block>>();
        final int districtSize = remaining.size() / numDistrictsRemaining;
        for (int i = 0; i < numDistrictsRemaining; ++i) {
          List<Block> district = new ArrayList<Block>();
          for (int j = 0; j < districtSize; ++j) {
            district.add(remaining.get(i * districtSize + j));
          }
          ret.add(district);
        }
        return ret;

    }
}
   

  // must chose and return one of the provided districts
  // We will issue a penalty if your strategy does not return one of the provided lists
  public List<Block> choose(List<List<Block>> districts){
    long closestWinMargin = -1;
    List<Block> closestWinDistrict = null;
    long furthestLossMargin = -1;
    List<Block> furthestLossDistrict = null;
    for (List<Block> district : districts) {
      long ourFavor = 0;
      for (Block block : district) {
        ourFavor += _isBeta ? block.betaSwing() : -block.betaSwing();
      }
      // n.b. tiebreaks in favor of beta
      if (ourFavor > 0 || (ourFavor == 0 && _isBeta)) {
        if (closestWinDistrict == null || closestWinMargin > ourFavor) {
          closestWinMargin = ourFavor;
          closestWinDistrict = district;
        }
      } else {
        // ourFavor is negative, so store the min
        if (furthestLossDistrict == null || furthestLossMargin > ourFavor) {
          furthestLossMargin = ourFavor;
          furthestLossDistrict = district;
        }
      }
    }
    return (closestWinDistrict != null) ? closestWinDistrict : furthestLossDistrict;
  }


  // inform the active party of the choice made by the nonactive party
  public void accept(List<Block> chosen){}
    
}
