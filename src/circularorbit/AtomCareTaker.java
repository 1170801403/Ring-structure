package circularorbit;

import java.util.ArrayList;
import javax.management.RuntimeErrorException;

public class AtomCareTaker {
  private ArrayList<AtomMemento> mementos = new ArrayList<AtomMemento>();

  public void addMemento(AtomMemento m) {
    mementos.add(m);
  }

  /**
   * 备忘录模式中获取备忘录的过程.
   * @return
   */
  public AtomMemento getMemento(int i) {
    if (mementos.size() - i < 0) {
      throw new RuntimeException("Cannot rollback so many backs!");
    }
    return mementos.get(mementos.size() - i);// 获得倒数某一步的状态
  }
}
