package circularorbittest;

import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import circularorbit.SocialNetworkCircle;
import physicalobject.SocialE1;

public class SocialNetWorkTest {
  // Testing strategy for addTrackObject()
  // 新建一个socialNetworkCircle对象，调用addTrack()函数增加轨道，
  // 调用addTrackObject()函数增加物体,最后观察存储轨道和物体的对象大小是否符合要求
  @Test
  public void addTrackObjectTest() {
    SocialNetworkCircle testSocial = new SocialNetworkCircle();
    char a = "M".charAt(0);
    SocialE1 ob1 = new SocialE1("a", 1, a);
    SocialE1 ob2 = new SocialE1("b", 1, a);
    SocialE1 ob3 = new SocialE1("c", 1, a);
    testSocial.addTrack();
    testSocial.addTrack();
    testSocial.addTrackObject(ob1, 2);

    testSocial.addTrackObject(ob1, 0);
    testSocial.addTrackObject(ob2, 1);
    assertTrue(testSocial.getFriend().size() == 2);

    assertTrue(testSocial.getTrackObject().size() == 2);
    // assertTrue(testSocial.getTrackObject().get(0) == ob1);
    assertTrue(testSocial.getTrackObject().get(0).contains(ob1));
    // assertTrue(testSocial.getTrackObject().get(1) == ob2);
    assertTrue(testSocial.getTrackObject().get(1).contains(ob2));

    assertTrue(testSocial.getObjectTrack().size() == 2);
    assertTrue(testSocial.getObjectTrack().get(ob1) == 0);
    assertTrue(testSocial.getObjectTrack().get(ob2) == 1);

    assertTrue(testSocial.getFriend().size() == 2);
    assertTrue(testSocial.getFriend().get("a") == ob1);
    assertTrue(testSocial.getFriend().get("b") == ob2);
  }

  // Testing strategy for addTrackObject()
  // 新建一个socialNetworkCircle对象，调用addTrack()函数增加轨道，
  // 调用addTrackObject()函数增加物体,最后观察存储轨道和物体的对象大小是否符合要求
  @Test
  public void clearTest() {
    SocialNetworkCircle testSocial = new SocialNetworkCircle();
    char a = "M".charAt(0);
    SocialE1 ob1 = new SocialE1("a", 1, a);
    SocialE1 ob2 = new SocialE1("b", 1, a);
    SocialE1 ob3 = new SocialE1("c", 1, a);
    testSocial.addTrack();
    testSocial.addTrack();

    testSocial.addTrackObject(ob1, 0);
    testSocial.addTrackObject(ob2, 1);

    testSocial.clear();
    // 并不清空friend集合
    assertTrue(testSocial.getFriend().size() == 2);
    assertTrue(testSocial.getPhysical().size() == 0);
    assertTrue(testSocial.getTrackObject().size() == 0);
    assertTrue(testSocial.getObjectTrack().size() == 0);
    assertTrue(testSocial.getAngle().size() == 0);
    assertTrue(testSocial.getLErelationship().size() == 0);

  }
}
