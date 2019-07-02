package strategyio;

import circularorbit.FunctionTrackGame;

public class TrackStrategyInputA extends TrackStrategyInput {
  @Override
  public boolean trackInput(FunctionTrackGame track, String name) {
    return track.creatingTrackFromFiles(name);
  }
}
