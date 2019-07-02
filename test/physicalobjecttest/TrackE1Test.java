package physicalobjecttest;

import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import physicalobject.TrackE1;

public class TrackE1Test {
  int number = 1;
  String name = "a";
  String nationaility = "China";// 国籍
  int age = 12;// 年龄
  double best = 13.02;// 本年度最好成绩
  TrackE1 test = new TrackE1(name, number, nationaility, age, best);
  TrackE1 test1 = new TrackE1(name, number, nationaility, age, best);

  // Testing strategy for getNumber()
  // 采用特定的参数新建一个trackE1实例，表示田径运动员对象，然后调用getNumber()函数，
  // 判断返回的数字与预期数字是否相等
  @Test
  public void testGetNumber() {
    assertTrue(test.getNumber() == number);
  }

  // Testing strategy for getNumber()
  // 采用特定的参数新建一个trackE1实例，表示田径运动员对象，然后调用getAge()函数，
  // 判断返回的数字与预期数字是否相等
  @Test
  public void testGetAge() {
    assertTrue(test.getAge() == age);
  }

  @Test
  public void testGetNationaility() {
    assertTrue(test.getNationaility().equals(nationaility));
  }

  // Testing strategy for getNumber()
  // 采用特定的参数新建一个trackE1实例，表示田径运动员对象，然后调用getNumber()函数，
  // 判断返回的数字与预期数字是否相等
  @Test
  public void testGetBest() {
    assertTrue((test.getBest() - best) < 1e-6);
  }

  // Testing strategy for getNumber()
  // 采用特定的参数新建两个trackE1实例，表示田径运动员对象，然后调用equals()函数，
  // 判断返回值是否为真
  @Test
  public void testEquals() {
    assertTrue(test.equals(test1));
  }
}
