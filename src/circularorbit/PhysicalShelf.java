package circularorbit;

import java.util.Iterator;
import physicalobject.E1;

public class PhysicalShelf implements Aggregate {
  private E1[] physical;
  private int last = 0;

  // 构造函数
  // 查找物体
  public E1 getphysicalAt(int index) {
    return physical[index];
  }

  // 添加物体
  public void appendphysical(E1 book) {
    this.physical[last] = book;
    last++;
  }

  // 获得容器中物体的数量
  public int getLength() {
    return physical.length;
  }

  // 获得物体迭代器对象
  @Override
  public PhysicalIterator iterator() {
    return new PhysicalShelfIterator(this);

  }
}
