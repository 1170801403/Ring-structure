package centralobject;

import static org.junit.Assert.assertTrue;

public class SocialL1 extends L1 {
  private final int age;
  private final char sex;

  // Abstraction function:
  // 描述社交网络中一个中心点人的全部信息
  // Representation invariant:
  // 所有的信息类型都是不可变的，所有的信息域不允许为空，如果信息是数字，则必须大于0
  // Safety from rep exposure
  // 所有的信息域都通过private final修饰
  public void socialcheckRep() {
//    checkRep();
//    assertTrue(age > 0);
  }


  /**
   * 通过指定姓名、年龄、性别来构造一个中心人.
   * @return
   */
  public SocialL1(String name, int age, char sex) {
    super(name);
    this.age = age;
    this.sex = sex;
  }

  /**
   * 返回社交网络中心人的属性：年龄.
   * @return
   */
  public int getAge() {
    socialcheckRep();
    return age;
  }

  /**
   * 返回社交网络中心人的属性：性别.
   * @return
   */
  public char getSex() {
    socialcheckRep();
    return sex;
  }
}
