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
      *  检查是否还有下一个物体.s
   */
  public boolean hasNext() { 
    if (index < realPhysicalShelf.getLength()) {
      return true;
    } else {
      return false;
    }
  }

  // 返回指定位置的书籍
  @Override
  public E1 next() {
    E1 physical = realPhysicalShelf.getphysicalAt(index);
    index++;
    return physical;
  }

}
