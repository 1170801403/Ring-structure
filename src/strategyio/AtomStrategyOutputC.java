package strategyio;

import circularorbit.AtomStructure;

public class AtomStrategyOutputC extends AtomStrategyOutput {
  public boolean atomOutput(AtomStructure atom)
  {
    return atom.atomWriteBackC();
  }
}
