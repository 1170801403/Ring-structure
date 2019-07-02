package centralobject;

import static org.junit.Assert.assertTrue;

public class SocialL1 extends L1 {
  private final int age;
  private final char sex;

  // Abstraction function:
  // �����罻������һ�����ĵ��˵�ȫ����Ϣ
  // Representation invariant:
  // ���е���Ϣ���Ͷ��ǲ��ɱ�ģ����е���Ϣ������Ϊ�գ������Ϣ�����֣���������0
  // Safety from rep exposure
  // ���е���Ϣ��ͨ��private final����
  public void socialcheckRep() {
//    checkRep();
//    assertTrue(age > 0);
  }


  /**
   * ͨ��ָ�����������䡢�Ա�������һ��������.
   * @return
   */
  public SocialL1(String name, int age, char sex) {
    super(name);
    this.age = age;
    this.sex = sex;
  }

  /**
   * �����罻���������˵����ԣ�����.
   * @return
   */
  public int getAge() {
    socialcheckRep();
    return age;
  }

  /**
   * �����罻���������˵����ԣ��Ա�.
   * @return
   */
  public char getSex() {
    socialcheckRep();
    return sex;
  }
}
