package strategyio;

import circularorbit.FunctionTrackGame;

public class TrackStrategyOutputC extends TrackStrategyOutput {
  @Override
  public boolean trackOutput(FunctionTrackGame track) {
    return track.trackWriteBackA();
  }
}
