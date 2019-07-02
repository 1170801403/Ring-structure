package centralobjecttest;

import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import centralobject.AtomL1;

public class AtomL1Test {
  String name = "a";
  AtomL1 test = new AtomL1(name);

  // Testing strategy for getName()
  // 采用特定的参数新建一个atomL1实例，表示原子核对象，然后调用getName()函数，
  // 判断返回的字符串与预期字符串是否相等
  @Test
  public void testGetName() {
    assertTrue(test.getName().equals(name));
  }
}
