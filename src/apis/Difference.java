package apis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Difference<L, E> {
  // Ĭ������£���ͬһ�����ڵĳ���ɼ�
  final int differenceNumber;// �����������

  public Difference(int n) {
    differenceNumber = n;
  }

  public int getDifferenceNumber() {
    return differenceNumber;
  }

  Map<Integer, Integer> numberTrack = new HashMap<Integer, Integer>();// ��i������ϵ�������
  Map<Integer, String> stringTrack = new HashMap<Integer, String>();// ��i������ϵ������

  /**
     * ����ÿ��������������ӳ��.
   * 
   * @return ��¡���ӳ��numberTrack
   */
  
  public Map<Integer, Integer> getNumberTrack() {
    Map<Integer, Integer> temp = new HashMap<Integer, Integer>();
    Iterator<Integer> iterator = numberTrack.keySet().iterator();
    while (iterator.hasNext()) {
      int a1 = iterator.next();
      int a2 = numberTrack.get(a1);
      temp.put(a1, a2);
    }
    return temp;
  }
  /**
      * ����ÿ�������������ӳ��.
   * @return ��¡���ӳ��stringTrack.
   */
  
  public Map<Integer, String> getStringTrack() {
    Map<Integer, String> temp = new HashMap<Integer, String>();
    Iterator<Integer> iterator = stringTrack.keySet().iterator();
    while (iterator.hasNext()) {
      Integer a1 = iterator.next();
      String a2 = stringTrack.get(a1);
      temp.put(a1, a2);
    }
    return temp;
  }
}
