package centralobjecttest;

import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import centralobject.AtomL1;

public class AtomL1Test {
  String name = "a";
  AtomL1 test = new AtomL1(name);

  // Testing strategy for getName()
  // �����ض��Ĳ����½�һ��atomL1ʵ������ʾԭ�Ӻ˶���Ȼ�����getName()������
  // �жϷ��ص��ַ�����Ԥ���ַ����Ƿ����
  @Test
  public void testGetName() {
    assertTrue(test.getName().equals(name));
  }
}
