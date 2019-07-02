package physicalobject;

import static org.junit.Assert.assertTrue;

public class SocialE1 extends E1 {
  private final int age;
  private final char sex;

  // Abstraction function:
  // 描述社交网络中一个人的全部信息
  // Representation invariant:
  // 所有的信息类型都是不可变的，所有的信息域不允许为空，如果信息是数字，则必须大于0
  // Safety from rep exposure
  // 所有的信息域都通过private final修饰
  public void socialEcheckRep() {
//    checkRep();
//    assertTrue(age > 0);
  }

  /**
   * 用指定的参数生成一个社交网络轨道人.
   * @param name 人的名字.
   * @param realAge 人的年龄.
   * @param string 人的性别.
   */
  public SocialE1(String name, int realAge, char string) {
    super(name);
    this.age = realAge;
    this.sex = string;
  }

  public int getAge() {
    socialEcheckRep();
    return age;
  }

  public char getSex() {
    socialEcheckRep();
    return sex;
  }

  /**
   * 判断两个社交网络轨道人是否相等.
   * @param e 等待判断的社交网络轨道人.
   * @return 如果两人是同一个人，返回true,否则返回false.
   */
  public boolean sociaE1Equals(SocialE1 e) {
    socialEcheckRep();
    if (e.getName().equals(this.getName()) && e.getAge() == this.age && e.getSex() == this.sex) {
      return true;
    } else {
      return false;
    }
  }

}
