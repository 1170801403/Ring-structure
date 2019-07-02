package strategyio;

import java.util.logging.Logger;
import circularorbit.SocialNetworkCircle;

public class SocialStrategyInputB extends SocialStrategyInput {
  @Override
  public boolean socialInput(SocialNetworkCircle society, String name) {
    return society.creatingTrackFromFilesFile(name);
  }
}
