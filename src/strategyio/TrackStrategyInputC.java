package strategyio;

import circularorbit.FunctionTrackGame;

public class TrackStrategyInputC extends TrackStrategyInput {
  @Override
  public boolean trackInput(FunctionTrackGame track, String name) {
    return track.creatingTrackFromFilesScanner(name);
  }
}
