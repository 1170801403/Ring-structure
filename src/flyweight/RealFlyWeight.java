package flyweight;

import physicalobject.AtomE1;
import physicalobject.TrackE1;

public class RealFlyWeight extends FlyWeight {
  AtomE1 e1;
  public RealFlyWeight(AtomE1 e2)
  {
    this.e1 = e2;
  }
}
