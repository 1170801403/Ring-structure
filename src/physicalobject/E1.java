package physicalobject;

import static org.junit.Assert.assertTrue;
import java.util.*;

public class E1 {
  private final String name;// ��������
  // Abstraction function:
  // ��Ϊ���й������ĸ��࣬���й�ͬ���ԣ������ơ�
  // Representation invariant:
  // һ����������������String���ͣ����ɱ�
  // Safety from rep exposure:
  // String�����ǲ��ɱ�ģ�ͬʱ���������������final���ͣ�ָ�򲻻�ı�

  public void checkRep() {
//    assertTrue(name.length() != 0);
  }

  public String getName() {
    checkRep();
    return name;
  }

  public E1(String name) {
    this.name = name;
    checkRep();
  }

  /**
   * �ж��������ĸ����Ƿ���ȵı�׼.
   * @param e ��һ��������常��.
   * @return ���������ȣ��򷵻�true,���򷵻�false.
   */
  public boolean e1Equals(E1 e) {
   
    if (e.getName().equals(this.name)) {
      return true;
    } else {
      return false;
    }
  }
}
