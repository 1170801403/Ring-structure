package strategyio;

import java.util.logging.Logger;
import circularorbit.FunctionTrackGame;
import circularorbit.SocialNetworkCircle;
import circularorbit.TrackStrategyG;
import circularorbit.TrackStrategyR;

public class TrackStrategyOrganizerInput {
  TrackStrategyInput strategy;

  public TrackStrategyOrganizerInput(String choice) {
    switch (choice) {
      case "a":
        strategy = new TrackStrategyInputA();
        break;
      case "b":
        strategy = new TrackStrategyInputB();
        break;
      case "c":
        strategy = new TrackStrategyInputC();
        break;
      default:
        break;
      // TODO Auto-generated constructor stub
    }
  }
  
  public boolean arrange(FunctionTrackGame track,String name)
  {
    return strategy.trackInput(track,name);
  }
  
}
