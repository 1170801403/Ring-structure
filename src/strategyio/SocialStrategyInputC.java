package strategyio;

import java.util.logging.Logger;
import circularorbit.SocialNetworkCircle;

public class SocialStrategyInputC extends SocialStrategyInput {

  @Override
  public boolean socialInput(SocialNetworkCircle society, String name) {
    
    return society.creatingTrackFromFilesScanner(name);
  }
}
