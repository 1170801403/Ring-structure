package strategyio;


import java.util.logging.Logger;
import circularorbit.SocialNetworkCircle;

public abstract class SocialStrategyInput {
  public abstract boolean socialInput(SocialNetworkCircle society,String name);
}
