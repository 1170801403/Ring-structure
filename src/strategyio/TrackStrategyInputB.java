package strategyio;

import circularorbit.FunctionTrackGame;

public class TrackStrategyInputB extends TrackStrategyInput {
  @Override
  public boolean trackInput(FunctionTrackGame track, String name) {
    return track.creatingTrackFromFilesFile(name);
  }
}