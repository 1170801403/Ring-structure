package centralobject;

import static org.junit.Assert.assertTrue;
import java.util.*;
import physicalobject.E1;

public class L1 {
  private final String name;// ���������ǲ��ɱ�����
  // Abstraction function:
  // ��Ϊ������������ĸ��࣬���й�ͬ���ԣ������ơ�
  // Representation invariant:
  // һ�����������������String���ͣ����ɱ�
  // Safety from rep exposure:
  // String�����ǲ��ɱ�ģ�ͬʱ���������������final���ͣ�ָ�򲻻�ı�

  public void checkRep() {
//    assertTrue(name.length() != 0);
  }

  /**
   * ����������������ĸ�������ԣ�����.
   * @return
   */
  public String getName() {
    checkRep();
    return name;

  }

  /**
   * ͨ���������ĵ�����һ��name����ʵ�ֹ���һ�����ĵ�����.
   * @return
   */
  public L1(String name) { // ���캯��
    this.name = name;
    checkRep();
  }
}
