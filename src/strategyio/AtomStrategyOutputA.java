package strategyio;

import circularorbit.AtomStructure;

public class AtomStrategyOutputA extends AtomStrategyOutput{
  public boolean atomOutput(AtomStructure atom)
  {
    return atom.atomWriteBackA();
  }
}
