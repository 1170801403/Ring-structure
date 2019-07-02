package physicalobjecttest;

import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import physicalobject.AtomE1;

public class AtomE1Test {
  String name = "a";
  AtomE1 test = new AtomE1(name);
  AtomE1 test2 = new AtomE1(name);

  // Testing strategy for getName()
  // �����ض��Ĳ����½�һ��atomE1ʵ������ʾ������Ӷ���Ȼ�����getName()������
  // �жϷ��ص��ַ�����Ԥ���ַ����Ƿ����
  @Test
  public void testGetName() {
    assertTrue(test.getName().equals(name));
  }

  // Testing strategy for getName()
  // �����ض��Ĳ����½�����atomE1ʵ������ʾ������Ӷ���Ȼ�����equals()������
  // �ж�����ʵ���Ƿ����
  @Test
  public void testEquals() {
    assertTrue(test.equals(test2));
  }
}
