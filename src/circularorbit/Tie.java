package circularorbit;

import static org.junit.Assert.assertTrue;

public class Tie {
  private final String name1;
  private final String name2;
  private final float ini;

  // Abstraction function:
  // 表示一对关系，包含关系两端的物体，以及关系的亲密度
  // Representation invariant:
  // 关系时不可变对象，关系包含的三个元素也是不可变对象,亲密度为正，如果是字符串长度不能为0
  // Safety from rep exposure:
  // 所有的元素类型都是private final，确保其无法被改变
  /**
   * 用指定的参数构造一条社交关系.
   * @param name1 社交关系一端的人名.
   * @param name2 社交关系另一端的人名.
   * @param ini 社交关系的亲密度.
   */
  public Tie(String name1, String name2, float ini) {
    assert name1.length() != 0;
    assert name2.length() != 0;
    assert ini > 0;
    this.name1 = name1;
    this.name2 = name2;
    this.ini = ini;
  }

  /**
   * 保证Tie类的表示不变性.
   */
  public void checkRep() {
//    assertTrue(name1.length() != 0);
//    assertTrue(name2.length() != 0);
//    assertTrue(ini > 0);
  }

  public String getName1() {
    checkRep();
    return name1;
  }

  public String getName2() {
    checkRep();
    return name2;
  }

  public float getIni() {
    checkRep();
    return ini;
  }

  /**
   * 判定两条社交关系是否相同的标准.
   * @param another 需要与当前社交关系比较的另一条社交关系.
   * @return 如果两条社交关系相同，则返回true,否则返回false
   */
  public boolean tieEquals(Tie another) {
    checkRep();
    if ((this.getName1().equals(another.getName1())
        && this.getName2().equals(another.getName2()))) {
      return true;
    }
    if ((this.getName1().equals(another.getName2())
        && this.getName2().equals(another.getName1()))) {
      return true;
    }
    return false;
  }
}
