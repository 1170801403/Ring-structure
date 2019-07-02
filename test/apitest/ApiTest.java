package apitest;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import apis.CircularOrbitApis;
import apis.Difference;
import centralobject.L1;
import centralobject.AtomL1;
import centralobject.SocialL1;
import circularorbit.AtomStructure;
import circularorbit.FunctionTrackGame;
import circularorbit.SocialNetworkCircle;
import circularorbit.TrackOrganizer;
import physicalobject.AtomE1;
import physicalobject.SocialE1;
import physicalobject.TrackE1;


public class ApiTest {
  CircularOrbitApis<AtomL1, AtomE1> api = new CircularOrbitApis<AtomL1, AtomE1>();
  Difference<AtomL1, AtomE1> di = new Difference<AtomL1, AtomE1>(1);

  CircularOrbitApis<L1, TrackE1> api3 = new CircularOrbitApis<L1, TrackE1>();
  Difference<L1, TrackE1> di3 = new Difference<L1, TrackE1>(1);

  CircularOrbitApis<SocialL1, SocialE1> api2 = new CircularOrbitApis<SocialL1, SocialE1>();

  // Testing strategy for getDifference()
  // ��ԭ�ӹ��ϵͳ��Ϊ��������getDifference�������۲췵�ص�difference
  // ���������Ƿ���Ԥ�������
  @Test
  public void testDifference() {
    AtomStructure test1 = new AtomStructure();
    AtomStructure test2 = new AtomStructure();
    test1.creatingTrackFromFiles("AtomicStructure.");
    test1.creatingTrack(test1.getTrackNumber2(), test1.getTrackObjectNumber());
    test2.creatingTrackFromFiles("AtomicStructure_Medium.");
    test2.creatingTrack(test2.getTrackNumber2(), test2.getTrackObjectNumber());
    Difference<AtomL1, AtomE1> temp = api.getDifference(test2, test1);
    System.out.println(temp.getDifferenceNumber());
    assertTrue(temp.getDifferenceNumber() == 1);
    assertTrue(temp.getNumberTrack().size() == 6);
  }

  // Testing strategy for getDifference()
  // ���ﾶ�������ϵͳ��Ϊ��������getDifference�������۲췵�ص�difference
  // ���������Ƿ���Ԥ�������
  @Test
  public void testDifference2() {
    FunctionTrackGame test1 = new FunctionTrackGame();
    test1.creatingTrackFromFiles("TrackGame.");
    // ������ģʽ
    TrackOrganizer t = new TrackOrganizer("r");
    t.arrange(test1);

    Difference<AtomL1, AtomE1> temp =
        api.getDifference(test1.getGroupTrackSystem().get(0), test1.getGroupTrackSystem().get(1));
    System.out.println(temp.getDifferenceNumber());
    assertTrue(temp.getDifferenceNumber() == 0);
    assertTrue(temp.getStringTrack().size() == 5);
  }

  // Testing strategy for getLogicalDistance()
  // ���罻����ϵͳ��Ϊ��������getLogicalDistance�������۲췵�ص��߼�����
  // ��Ԥ�ھ����Ƿ����
  @Test
  public void testLogicalDistance() {
    SocialNetworkCircle test = new SocialNetworkCircle();
    test.creatingTrackFromFiles("SocialNetworkCircle.");
    test.creatingTrack();
    assertTrue(1 == api2.getLogicalDistance(test, test.getFriend().get("DavidChen"),
        test.getFriend().get("FrankLee")));
  }
}


