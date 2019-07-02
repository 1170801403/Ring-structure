package application;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import applications.Application;
import circularorbit.AtomStructure;
import circularorbit.FunctionTrackGame;
import circularorbit.SocialNetworkCircle;

public class ApplicationTest {
  SocialNetworkCircle socialTest = new SocialNetworkCircle();
  AtomStructure atomTest = new AtomStructure();
  FunctionTrackGame trackTest = new FunctionTrackGame();
  Application t1 = new Application();

  // Testing strategy for check()
  // 分别用社交网络系统和田径比赛系统作为参数调用application中的合法性检查函数，
  // 判断轨道合法性是否与预期相同
  @Test
  public void testCheck() {
    socialTest.creatingTrackFromFiles("SocialNetworkCircle.");
    socialTest.creatingTrack();
    trackTest.creatingTrackFromFiles("TrackGame.");
    trackTest.autoCompetitionA();
    assertTrue(t1.socialCheck(socialTest));
    assertFalse(t1.trackCheck(trackTest));
  }

  // Testing strategy for SocialDistance()
  // 用社交网络系统作为参数调用application中的SocialDistance函数，
  // 判断返回值是否与预期相同
  @Test
  public void testSocialDistance() {
    socialTest.creatingTrackFromFiles("SocialNetworkCircle.");
    socialTest.creatingTrack();
    assertEquals(1, t1.socialGetDistance(socialTest, "FrankLee", "DavidChen"));
  }
}
