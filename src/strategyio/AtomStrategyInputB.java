package strategyio;

import circularorbit.AtomStructure;

public class AtomStrategyInputB extends AtomStrategyInput{
  @Override
  public boolean atomInput(AtomStructure atom,String name)
  {
    return atom.creatingTrackFromFilesFile(name);
  }
}
