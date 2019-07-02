package circularorbittest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.sun.source.tree.AssertTree;
import circularorbit.FunctionTrackGame;
import circularorbit.TrackOrganizer;
import physicalobject.TrackE1;

public class FunctionTrackGameTest {
  FunctionTrackGame test1 = new FunctionTrackGame();
  FunctionTrackGame test2 = new FunctionTrackGame();

  @Test
  public void testNormalFunction() {
    test1.creatingTrackFromFiles("TrackGame.");
    // ֤���������û����
    TrackOrganizer t = new TrackOrganizer("r");
    t.arrange(test1);
    // assertTrue(test1.autoCompetitionA() == true);

    // ֤��������������
    assertTrue(test1.getTrackNumber() == 5);
    assertTrue(test1.getAthlete().size() != 0);
    assertTrue(test1.getGroupTrackSystem().size() != 0);


  }

  @Test
  public void testNormalFunction2() {
    test2.creatingTrackFromFiles("TrackGame.");
    // ֤����������ĵڶ��ַ���û����
    TrackOrganizer t = new TrackOrganizer("g");
    t.arrange(test2);
    // assertTrue(test2.autoCompetitionB() == true);
    // ���ӹ��
    test2.addTrack(0);
    assertTrue(test2.getGroupTrackSystem().get(0).getPhysical().size() == 6);
    // ���ٹ��
    test2.deleteTrack(1, 4);
    assertTrue(test2.getGroupTrackSystem().get(1).getPhysical().size() == 5);// physical����
    assertTrue(test2.getGroupTrackSystem().get(1).getTrackObject().size() == 4);
    // �����˶�Ա�����չ�������ʧ�ܣ�
    assertFalse(test2.addObject(0, 0, new TrackE1("1", 1, "1", 1, 1)));
    // �����˶�Ա
    assertTrue(test2.deleteObject(0, 0));
    assertTrue(test2.groupAdjust(1, 1, 2, 2));
  }
}
