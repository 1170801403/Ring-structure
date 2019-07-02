package strategyio;

import circularorbit.FunctionTrackGame;

public class TrackStrategyOutputB extends TrackStrategyOutput{
  @Override
  public boolean trackOutput(FunctionTrackGame track){
    return track.trackWriteBackA();
  }
}
