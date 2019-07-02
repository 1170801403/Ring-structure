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
  // �ֱ����罻����ϵͳ���ﾶ����ϵͳ��Ϊ��������application�еĺϷ��Լ�麯����
  // �жϹ���Ϸ����Ƿ���Ԥ����ͬ
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
  // ���罻����ϵͳ��Ϊ��������application�е�SocialDistance������
  // �жϷ���ֵ�Ƿ���Ԥ����ͬ
  @Test
  public void testSocialDistance() {
    socialTest.creatingTrackFromFiles("SocialNetworkCircle.");
    socialTest.creatingTrack();
    assertEquals(1, t1.socialGetDistance(socialTest, "FrankLee", "DavidChen"));
  }
}
