// Testing code for PS3 problem 4

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Simulator extends Tournament<Ultimatum, Object> {
  Simulator(List<String> strategyNames) {
    super(Ultimatum.class, strategyNames);
  }

  private static class Pair {
    public int l;
    public int r;
    public boolean side = rand.nextBoolean();

    public Pair(int l, int r) {
      this.l = l;
      this.r = r;
    }
  }

  public double[] runTrial(List<Class<? extends Ultimatum>> strategyClasses, Object o) {
    Ultimatum[] strategies = new Ultimatum[strategyClasses.size()];
    for (int i = 0; i < strategyClasses.size(); i++) {
      try {
        strategies[i] = strategyClasses.get(i).getDeclaredConstructor().newInstance();
      } catch (ReflectiveOperationException roe) {
        throw new RuntimeException(strategyClasses.get(i).getSimpleName(), roe);
      }
    }

    // Set up histories
    int[] asBobSaid = new int[strategies.length];
    int[] sawAliceSaid = new int[strategies.length];
    boolean[] asBobAccepted = new boolean[strategies.length];
    int[] sawBobSaid = new int[strategies.length];
    int[] asAliceSaid = new int[strategies.length];
    boolean[] asAliceWasAccepted = new boolean[strategies.length];
    int[] asBobAcceptedCumulative = new int[strategies.length];
    int[] asBobRejectedCumulative = new int[strategies.length];
    double[] score = new double[strategies.length];
    for (int i = 0; i < strategies.length; ++i) {
      asBobSaid[i] = -1;
      sawAliceSaid[i] = -1;
      asBobAccepted[i] = true;
      sawBobSaid[i] = -1;
      asAliceSaid[i] = -1;
      asAliceWasAccepted[i] = true;
    }

    // Randomize order of trials
    List<Pair> trials = new ArrayList<Pair>();
    for (int l = 0; l < strategies.length; ++l) {
      for (int r = 0; r < l; ++r) {
        trials.add(new Pair(l, r));
      }
    }
    Collections.shuffle(trials);

    for (Pair trial : trials) {
      int aliceID = trial.side ? trial.l : trial.r;
      int bobID = trial.side ? trial.r : trial.l;
      Ultimatum alice = strategies[aliceID];
      Ultimatum bob = strategies[bobID];
      alice.setup(
          asBobSaid[bobID],
          sawAliceSaid[bobID],
          asBobAccepted[bobID],
          sawBobSaid[bobID],
          asAliceSaid[bobID],
          asAliceWasAccepted[bobID],
          asBobAcceptedCumulative[bobID],
          asBobRejectedCumulative[bobID]);
      bob.setup(
          asBobSaid[aliceID],
          sawAliceSaid[aliceID],
          asBobAccepted[aliceID],
          sawBobSaid[aliceID],
          asAliceSaid[aliceID],
          asAliceWasAccepted[aliceID],
          asBobAcceptedCumulative[aliceID],
          asBobRejectedCumulative[aliceID]);
      int talk = bob.cheapTalk();
      if (talk == 0) {
      } else if (talk < 0) {
        talk = 0;
      } else if (talk >= 100) {
        talk = 99;
      }
      asBobSaid[bobID] = talk;
      sawBobSaid[aliceID] = talk;
      int proposal = alice.propose(talk);
      if (proposal == 0) {
      } else if (proposal < 0) {
        proposal = 0;
      } else if (proposal >= 100) {
        proposal = 99;
      }
      sawAliceSaid[bobID] = proposal;
      asAliceSaid[aliceID] = proposal;
      boolean accepts = bob.accept(proposal);
      asBobAccepted[bobID] = accepts;
      asAliceWasAccepted[aliceID] = accepts;
      if (accepts) {
        ++asBobAcceptedCumulative[bobID];
        score[aliceID] += proposal;
        score[bobID] += 2 * (100 - proposal);
      } else {
        ++asBobRejectedCumulative[bobID];
      }
    }
    for (int i = 0; i < score.length; ++i) {
      score[i] /= 2 * (strategies.length - 1);
    }
    return score;
  }

  public static void main(String[] args) throws java.io.FileNotFoundException {
    assert args.length >= 1 : "Expected filename of strategies as first argument";
    final int numTrials = 1000;
    final BufferedReader namesFile = new BufferedReader(new FileReader(args[0]));
    final List<String> strategyNames =
        namesFile.lines().map(s -> String.format("Ultimatum_%s", s)).collect(Collectors.toList());
    final int N = strategyNames.size();
    // each strategy in the sample room with the sample strategies (not a component of the grade,
    // just for overfitting comparisons)
    final Simulator withStrategies = new Simulator(strategyNames);

    double[] res = withStrategies.oneEachTrials(numTrials, new Object());
    System.out.println("netID,score");
    for (int i = 0; i < N; ++i) {
      System.out.println(strategyNames.get(i).substring(10) + "," + Double.toString(res[i] / N));
    }
  }
}
