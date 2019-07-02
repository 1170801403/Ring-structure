package tracktest;

import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import physicalobject.TrackE1;
import track.Track;

public class TrackTest {
  int rep = 0;
  Track test = new Track(rep);

  // Testing strategy for getName()
  // 采用特定的参数新建一个Track实例，表示轨道对象，然后调用getRep()函数，
  // 获取轨道的半径，判断返回的数字与预期数字是否相等
  @Test
  public void testGetRep() {
    assertTrue(test.getRep() == rep);
  }
}
