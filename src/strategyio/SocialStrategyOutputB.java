package strategyio;

import circularorbit.SocialNetworkCircle;

public class SocialStrategyOutputB extends SocialStrategyOutput{
  @Override
  public boolean socialOutput(SocialNetworkCircle society) {
   return society.socialWriteBackB();
  }
}
