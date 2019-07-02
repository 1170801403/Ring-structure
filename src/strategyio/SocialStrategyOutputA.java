package strategyio;

import circularorbit.SocialNetworkCircle;

public class SocialStrategyOutputA extends SocialStrategyOutput {
  @Override
  public boolean socialOutput(SocialNetworkCircle society) {
    return society.socialWriteBackA();
  }
}
