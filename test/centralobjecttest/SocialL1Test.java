package centralobjecttest;

import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import centralobject.SocialL1;

public class SocialL1Test {
  String name = "a";
  int age = 12;
  char sex = "M".charAt(0);
  SocialL1 test = new SocialL1(name, age, sex);

  // Testing strategy for getName()
  // 采用特定的参数新建一个socialL1实例，表示社交网络中心人对象，然后调用getName()函数，
  // 判断返回的字符串与预期字符串是否相等
  @Test
  public void testGetName() {
    assertTrue(test.getName().equals(name));
  }

  // Testing strategy for getName()
  // 采用特定的参数新建一个socialL1实例，表示社交网络中心人对象，然后调用getAge()函数，
  // 判断返回的数字与预期数字是否相等
  @Test
  public void testGetAge() {
    assertTrue(test.getAge() == age);
  }

  // Testing strategy for getName()
  // 采用特定的参数新建一个socialL1实例，表示社交网络中心人对象，然后调用getSex()函数，
  // 判断返回的字符与预期字符是否相等
  @Test
  public void testGetSex() {
    assertTrue(test.getSex() == sex);
  }
}
