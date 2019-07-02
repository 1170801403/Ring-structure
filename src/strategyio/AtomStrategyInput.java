package strategyio;

import circularorbit.AtomStructure;
import circularorbit.SocialNetworkCircle;

public abstract class AtomStrategyInput {
  public abstract boolean atomInput(AtomStructure atom,String name);
}
