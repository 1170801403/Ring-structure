package strategyio;

import circularorbit.AtomStructure;
import circularorbit.SocialNetworkCircle;

public class AtomStrategyOrganizerInput {
  AtomStrategyInput strategy;

  public AtomStrategyOrganizerInput(String choice) {
    switch (choice) {
      case "a":
        strategy = new AtomStrategyInputA();
        break;
      case "b":
        strategy = new AtomStrategyInputB();
        break;
      case "c":
        strategy = new AtomStrategyInputC();
        break;
      default:
        break;
      // TODO Auto-generated constructor stub
    }
  }
  
  public boolean arrange(AtomStructure atom,String name)
  {
    return strategy.atomInput(atom,name);
  }
}
