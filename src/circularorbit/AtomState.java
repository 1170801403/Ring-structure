package circularorbit;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AtomState {
  private Map<Integer, Integer> trackObjectNumber;

  // static atomState instance = new atomState();
  public AtomState(Map<Integer, Integer> trackNumber) {
    this.trackObjectNumber = trackNumber;
  }

  /**
   * 通过防御性克隆返回轨道数与电子数之间的映射.
   * @return 轨道数与电子数之间的映射
   */
  public Map<Integer, Integer> getTrackObjectNumber() {
    Map<Integer, Integer> tempTrackObjectNumber = new HashMap<Integer, Integer>();
    Iterator<Integer> iterator = trackObjectNumber.keySet().iterator();
    while (iterator.hasNext()) {
      int temp = iterator.next();
      tempTrackObjectNumber.put(temp, trackObjectNumber.get(temp));
    }
    return tempTrackObjectNumber;
  }
}
