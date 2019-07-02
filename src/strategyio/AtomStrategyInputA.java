package strategyio;

import circularorbit.AtomStructure;

public class AtomStrategyInputA extends AtomStrategyInput{

  @Override
  public boolean atomInput(AtomStructure atom,String name)
  {
    return atom.creatingTrackFromFiles(name);
  }
}
