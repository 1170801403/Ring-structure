package abstractfactory;

import centralobject.AtomL1;
import physicalobject.AtomE1;

public class AtomFactory {
  public AtomL1 manufactureL(String name) {
    return new AtomL1(name);
  }

  public AtomE1 manufactureE(String name) {
    return new AtomE1(name);
  }
}
