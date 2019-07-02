package strategyio;

import circularorbit.AtomStructure;

public class AtomStrategyOutputB extends AtomStrategyOutput {
  public boolean atomOutput(AtomStructure atom)
  {
    return atom.atomWriteBackB();
  }
}
