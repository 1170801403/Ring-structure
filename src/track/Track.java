package track;

import static org.junit.Assert.assertTrue;
import java.util.*;
import physicalobject.*;

public class Track {
  private final int rep;

  // Abstraction function:
  // 作为所有轨道的父类，具有共同属性：“半径”
  // Representation invariant:
  // 一个轨道物体的名称是String类型，不可变
  // Safety from rep exposure:
  // String本身是不可变的，同时中心物体的名称是final类型，指向不会改变
  public void checkRep() {
//    assertTrue(rep >= 0);
  }

  public Track(int rep) { // 构造函数
    checkRep();
    this.rep = rep;
  }

  public int getRep() {
    checkRep();
    return rep;
  }
}
