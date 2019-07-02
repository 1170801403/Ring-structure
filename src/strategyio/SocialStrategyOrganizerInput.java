package strategyio;

import java.util.logging.Logger;
import circularorbit.SocialNetworkCircle;
import circularorbit.TrackStrategyG;
import circularorbit.TrackStrategyR;

public class SocialStrategyOrganizerInput {
  SocialStrategyInput strategy;

  public SocialStrategyOrganizerInput(String choice) {
    switch (choice) {
      case "a":
        strategy = new SocialStrategyInputA();
        break;
      case "b":
        strategy = new SocialStrategyInputB();
        break;
      case "c":
        strategy = new SocialStrategyInputC();
        break;
      default:
        break;
      // TODO Auto-generated constructor stub
    }
  }

  public boolean arrange(SocialNetworkCircle society, String name) {
    return strategy.socialInput(society, name);
  }

}
