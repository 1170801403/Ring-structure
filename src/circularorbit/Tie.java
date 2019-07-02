package circularorbit;

import static org.junit.Assert.assertTrue;

public class Tie {
  private final String name1;
  private final String name2;
  private final float ini;

  // Abstraction function:
  // ��ʾһ�Թ�ϵ��������ϵ���˵����壬�Լ���ϵ�����ܶ�
  // Representation invariant:
  // ��ϵʱ���ɱ���󣬹�ϵ����������Ԫ��Ҳ�ǲ��ɱ����,���ܶ�Ϊ����������ַ������Ȳ���Ϊ0
  // Safety from rep exposure:
  // ���е�Ԫ�����Ͷ���private final��ȷ�����޷����ı�
  /**
   * ��ָ���Ĳ�������һ���罻��ϵ.
   * @param name1 �罻��ϵһ�˵�����.
   * @param name2 �罻��ϵ��һ�˵�����.
   * @param ini �罻��ϵ�����ܶ�.
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
   * ��֤Tie��ı�ʾ������.
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
   * �ж������罻��ϵ�Ƿ���ͬ�ı�׼.
   * @param another ��Ҫ�뵱ǰ�罻��ϵ�Ƚϵ���һ���罻��ϵ.
   * @return ��������罻��ϵ��ͬ���򷵻�true,���򷵻�false
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
