package physicalobject;

import static org.junit.Assert.assertTrue;

public class TrackE1 extends E1 {
  private final int number;// ����
  private final String nationaility;// ����
  private final int age;// ����
  private final double best;// �������óɼ�
  // Abstraction function:
  // ��ʾһ���˶�Ա��������Ϣ
  // Representation invariant:
  // ÿһ����Ϣ���ǲ��ɱ����ͣ�����Ϊnull,�������ϢΪ���֣���������0
  // Safety from rep exposure:
  // ���е���Ϣ����private��final����

  /**
   * ��֤TrackE1��ı�ʾ������.
   */
  public void trackcheckRep() {
    //��Щ�����ڶ��ļ��ͽ��������ʱ����Ѿ�����һ���ˣ�û��Ҫ�ټ��һ��
//    assertTrue(nationaility != null);
//    assertTrue(best > 0);
//    assertTrue(age > 0);
//    assertTrue(number > 0);
  }

  /**
   * ��ָ���Ĳ�������һ���˶�Ա����.
   * @param name �˶�Ա����
   * @param number �˶�Ա���
   * @param nationaility �˶�Ա����
   * @param age �˶�Ա����
   * @param best �˶�Ա��óɼ�
   */
  public TrackE1(String name, int number, String nationaility, int age, double best) {
    super(name);// ���๹�캯��
    this.number = number;
    this.nationaility = nationaility;
    this.age = age;
    this.best = best;
    checkRep();
  }

  // �������඼�ǲ��ɱ��
  public int getNumber() {
    trackcheckRep();
    return number;
  }

  public String getNationaility() {
    trackcheckRep();
    return nationaility;
  }

  /**
   * �����˶�Ա������.
   * @return �˶�Ա������.
   */
  public int getAge() { // ���ص�ֻ��һ������
    trackcheckRep();
    return age;
  }

  /**
   * �����˶�Ա����óɼ�.
   * @return ��óɼ�.
   */
  public double getBest() {
    trackcheckRep();
    return best;
  }

  /**
   * �Ƚ������˶�Ա�Ƿ���ͬһ���˶�Ա.
   * @param temp ��һ���˶�Ա.
   * @return �����ͬһ���˶�Ա���򷵻�true;���򷵻�false.
   */
  public boolean trackE1equals(TrackE1 temp) { // �Ƚ������˶�Ա�Ƿ���ͬһ���˶�Ա

    //    if(temp.equals(this)) {
    if (temp.getName().equals(this.getName()) && temp.getAge() == this.age
        && temp.getNumber() == this.number && temp.getNationaility().equals(this.nationaility)) {
      return true;
    } else {
      return false;
    }
  }

}
