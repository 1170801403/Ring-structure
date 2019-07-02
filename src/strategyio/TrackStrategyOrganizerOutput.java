package strategyio;

import circularorbit.FunctionTrackGame;
import circularorbit.SocialNetworkCircle;

public class TrackStrategyOrganizerOutput {
  TrackStrategyOutput strategy;

  public TrackStrategyOrganizerOutput(String choice) {
    switch (choice) {
      case "a":
        strategy = new TrackStrategyOutputA();
        break;
      case "b":
        strategy = new TrackStrategyOutputB();
        break;
      case "c":
        strategy = new TrackStrategyOutputC();
        break;
      default:
        break;
      // TODO Auto-generated constructor stub
    }
  }
  
  public boolean arrange(FunctionTrackGame track)
  {
    return strategy.trackOutput(track);
  }
}
