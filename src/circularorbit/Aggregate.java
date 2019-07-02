package circularorbit;

import java.util.Iterator;

public interface Aggregate<E> {
  public abstract PhysicalIterator iterator();
}
