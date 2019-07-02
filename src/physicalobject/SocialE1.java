package physicalobject;

import static org.junit.Assert.assertTrue;

public class SocialE1 extends E1 {
  private final int age;
  private final char sex;

  // Abstraction function:
  // �����罻������һ���˵�ȫ����Ϣ
  // Representation invariant:
  // ���е���Ϣ���Ͷ��ǲ��ɱ�ģ����е���Ϣ������Ϊ�գ������Ϣ�����֣���������0
  // Safety from rep exposure
  // ���е���Ϣ��ͨ��private final����
  public void socialEcheckRep() {
//    checkRep();
//    assertTrue(age > 0);
  }

  /**
   * ��ָ���Ĳ�������һ���罻��������.
   * @param name �˵�����.
   * @param realAge �˵�����.
   * @param string �˵��Ա�.
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
   * �ж������罻���������Ƿ����.
   * @param e �ȴ��жϵ��罻��������.
   * @return ���������ͬһ���ˣ�����true,���򷵻�false.
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
