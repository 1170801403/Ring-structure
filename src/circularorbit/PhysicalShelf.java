package circularorbit;

import java.util.Iterator;
import physicalobject.E1;

public class PhysicalShelf implements Aggregate {
  private E1[] physical;
  private int last = 0;

  // ���캯��
  // ��������
  public E1 getphysicalAt(int index) {
    return physical[index];
  }

  // �������
  public void appendphysical(E1 book) {
    this.physical[last] = book;
    last++;
  }

  // ������������������
  public int getLength() {
    return physical.length;
  }

  // ����������������
  @Override
  public PhysicalIterator iterator() {
    return new PhysicalShelfIterator(this);

  }
}
