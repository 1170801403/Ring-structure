package physicalobjecttest;

import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import physicalobject.TrackE1;

public class TrackE1Test {
  int number = 1;
  String name = "a";
  String nationaility = "China";// ����
  int age = 12;// ����
  double best = 13.02;// �������óɼ�
  TrackE1 test = new TrackE1(name, number, nationaility, age, best);
  TrackE1 test1 = new TrackE1(name, number, nationaility, age, best);

  // Testing strategy for getNumber()
  // �����ض��Ĳ����½�һ��trackE1ʵ������ʾ�ﾶ�˶�Ա����Ȼ�����getNumber()������
  // �жϷ��ص�������Ԥ�������Ƿ����
  @Test
  public void testGetNumber() {
    assertTrue(test.getNumber() == number);
  }

  // Testing strategy for getNumber()
  // �����ض��Ĳ����½�һ��trackE1ʵ������ʾ�ﾶ�˶�Ա����Ȼ�����getAge()������
  // �жϷ��ص�������Ԥ�������Ƿ����
  @Test
  public void testGetAge() {
    assertTrue(test.getAge() == age);
  }

  @Test
  public void testGetNationaility() {
    assertTrue(test.getNationaility().equals(nationaility));
  }

  // Testing strategy for getNumber()
  // �����ض��Ĳ����½�һ��trackE1ʵ������ʾ�ﾶ�˶�Ա����Ȼ�����getNumber()������
  // �жϷ��ص�������Ԥ�������Ƿ����
  @Test
  public void testGetBest() {
    assertTrue((test.getBest() - best) < 1e-6);
  }

  // Testing strategy for getNumber()
  // �����ض��Ĳ����½�����trackE1ʵ������ʾ�ﾶ�˶�Ա����Ȼ�����equals()������
  // �жϷ���ֵ�Ƿ�Ϊ��
  @Test
  public void testEquals() {
    assertTrue(test.equals(test1));
  }
}
