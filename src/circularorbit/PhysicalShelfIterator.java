package circularorbit;

import physicalobject.E1;

public class PhysicalShelfIterator implements PhysicalIterator {
  private PhysicalShelf realPhysicalShelf;
  private int index;

  public PhysicalShelfIterator(PhysicalShelf physicalShelf) {
    this.realPhysicalShelf = physicalShelf;
    this.index = 0;
  }

  
  /**
      *  ����Ƿ�����һ������.s
   */
  public boolean hasNext() { 
    if (index < realPhysicalShelf.getLength()) {
      return true;
    } else {
      return false;
    }
  }

  // ����ָ��λ�õ��鼮
  @Override
  public E1 next() {
    E1 physical = realPhysicalShelf.getphysicalAt(index);
    index++;
    return physical;
  }

}
