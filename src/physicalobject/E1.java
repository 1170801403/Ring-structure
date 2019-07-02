package physicalobject;

import static org.junit.Assert.assertTrue;
import java.util.*;

public class E1 {
  private final String name;// 物体名称
  // Abstraction function:
  // 作为所有轨道物体的父类，具有共同属性：“名称”
  // Representation invariant:
  // 一个轨道物体的名称是String类型，不可变
  // Safety from rep exposure:
  // String本身是不可变的，同时中心物体的名称是final类型，指向不会改变

  public void checkRep() {
//    assertTrue(name.length() != 0);
  }

  public String getName() {
    checkRep();
    return name;
  }

  public E1(String name) {
    this.name = name;
    checkRep();
  }

  /**
   * 判定轨道物体的父类是否相等的标准.
   * @param e 另一个轨道物体父类.
   * @return 如果两者相等，则返回true,否则返回false.
   */
  public boolean e1Equals(E1 e) {
   
    if (e.getName().equals(this.name)) {
      return true;
    } else {
      return false;
    }
  }
}
