package physicalobjecttest;

import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import physicalobject.AtomE1;

public class AtomE1Test {
  String name = "a";
  AtomE1 test = new AtomE1(name);
  AtomE1 test2 = new AtomE1(name);

  // Testing strategy for getName()
  // 采用特定的参数新建一个atomE1实例，表示核外电子对象，然后调用getName()函数，
  // 判断返回的字符串与预期字符串是否相等
  @Test
  public void testGetName() {
    assertTrue(test.getName().equals(name));
  }

  // Testing strategy for getName()
  // 采用特定的参数新建两个atomE1实例，表示核外电子对象，然后调用equals()函数，
  // 判断两个实例是否相等
  @Test
  public void testEquals() {
    assertTrue(test.equals(test2));
  }
}
