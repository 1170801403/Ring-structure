package strategyio;

import circularorbit.AtomStructure;

public class AtomStrategyInputC extends AtomStrategyInput{
  @Override
  public boolean atomInput(AtomStructure atom,String name)
  {
    return atom.creatingTrackFromFilesScanner(name);
  }
}
