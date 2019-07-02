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
  // �����ض��Ĳ����½�һ��Trackʵ������ʾ�������Ȼ�����getRep()������
  // ��ȡ����İ뾶���жϷ��ص�������Ԥ�������Ƿ����
  @Test
  public void testGetRep() {
    assertTrue(test.getRep() == rep);
  }
}
