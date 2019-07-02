package circularorbit;

import physicalobject.E1;

public interface PhysicalIterator {
  public abstract boolean hasNext();

  public abstract E1 next();
}
