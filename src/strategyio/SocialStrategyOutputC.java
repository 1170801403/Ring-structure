package strategyio;

import circularorbit.SocialNetworkCircle;

public class SocialStrategyOutputC extends SocialStrategyOutput {
  @Override
  public boolean socialOutput(SocialNetworkCircle society) {
    return society.socialWriteBackC();
  }
}
