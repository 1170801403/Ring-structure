package strategyio;

import circularorbit.SocialNetworkCircle;

public class SocialStrategyOrganizerOutput {
  SocialStrategyOutput strategy;

  public SocialStrategyOrganizerOutput(String choice) {
    switch (choice) {
      case "a":
        strategy = new SocialStrategyOutputA();
        break;
      case "b":
        strategy = new SocialStrategyOutputB();
        break;
      case "c":
        strategy = new SocialStrategyOutputC();
        break;
      default:
        break;
      // TODO Auto-generated constructor stub
    }
  }
  
  public boolean arrange(SocialNetworkCircle society)
  {
    return strategy.socialOutput(society);
  }
}
