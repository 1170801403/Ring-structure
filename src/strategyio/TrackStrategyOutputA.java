package strategyio;

import circularorbit.FunctionTrackGame;

public class TrackStrategyOutputA extends TrackStrategyOutput {
  @Override
  public boolean trackOutput(FunctionTrackGame track) {
    return track.trackWriteBackA();
  }
}
