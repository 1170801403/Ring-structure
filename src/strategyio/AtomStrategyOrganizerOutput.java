package strategyio;

import circularorbit.AtomStructure;
import circularorbit.SocialNetworkCircle;

public class AtomStrategyOrganizerOutput {
  AtomStrategyOutput strategy;

  public AtomStrategyOrganizerOutput(String choice) {
    switch (choice) {
      case "a":
        strategy = new AtomStrategyOutputA();
        break;
      case "b":
        strategy = new AtomStrategyOutputB();
        break;
      case "c":
        strategy = new AtomStrategyOutputC();
        break;
      default:
        break;
      // TODO Auto-generated constructor stub
    }
  }
  
  public boolean arrange(AtomStructure atom)
  {
    return strategy.atomOutput(atom);
  }
}
