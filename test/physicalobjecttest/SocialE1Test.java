package physicalobjecttest;

import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import physicalobject.SocialE1;

public class SocialE1Test {
  int age = 12;
  char sex = "M".charAt(0);
  String name = "a";
  SocialE1 test = new SocialE1(name, age, sex);
  SocialE1 test1 = new SocialE1(name, age, sex);

  // Testing strategy for getAge()
  // 采用特定的参数新建一个socialE1实例，表示田径运动员对象，然后调用getAge()函数，
  // 判断返回的数字与预期数字是否相等
  @Test
  public void testGetAge() {
    assertTrue(test1.getAge() == age);
  }

  // Testing strategy for getSex()
  // 采用特定的参数新建一个socialE1实例，表示田径运动员对象，然后调用getSex()函数，
  // 判断返回的字符与预期字符是否相等
  @Test
  public void testGetSex() {
    assertTrue(test.getSex() == sex);
  }

  // Testing strategy for getAge()
  // 采用特定的参数新建两个socialE1实例，表示田径运动员对象，然后调用equals()函数，
  // 判断两个实例是否相同
  @Test
  public void getEquals() {
    assertTrue(test.equals(test1));
  }
}
