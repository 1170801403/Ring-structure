package circularorbit;

import java.util.ArrayList;
import javax.management.RuntimeErrorException;

public class AtomCareTaker {
  private ArrayList<AtomMemento> mementos = new ArrayList<AtomMemento>();

  public void addMemento(AtomMemento m) {
    mementos.add(m);
  }

  /**
   * ����¼ģʽ�л�ȡ����¼�Ĺ���.
   * @return
   */
  public AtomMemento getMemento(int i) {
    if (mementos.size() - i < 0) {
      throw new RuntimeException("Cannot rollback so many backs!");
    }
    return mementos.get(mementos.size() - i);// ��õ���ĳһ����״̬
  }
}
