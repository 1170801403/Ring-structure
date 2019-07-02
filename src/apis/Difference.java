package apis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Difference<L, E> {
  // 默认情况下，对同一个包内的程序可见
  final int differenceNumber;// 轨道的数量差

  public Difference(int n) {
    differenceNumber = n;
  }

  public int getDifferenceNumber() {
    return differenceNumber;
  }

  Map<Integer, Integer> numberTrack = new HashMap<Integer, Integer>();// 第i条轨道上的数量差
  Map<Integer, String> stringTrack = new HashMap<Integer, String>();// 第i条轨道上的物体差

  /**
     * 返回每条轨道上数量差的映射.
   * 
   * @return 克隆后的映射numberTrack
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
      * 返回每条轨道上物体差的映射.
   * @return 克隆后的映射stringTrack.
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
