package circularorbittest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import circularorbit.FunctionTrackGame;
import circularorbit.SocialNetworkCircle;

public class TrackGameErrorHandleTest {
  // �˶�Ա�ɼ���С����λ������
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ���������ﾶ�������ϵͳ��creatingTrackFromFiles����
  // �жϸú����ܷ�ʶ����쳣
  @Test
  public void lessFractionTest() {
    FunctionTrackGame temp = new FunctionTrackGame();
    assertFalse(temp.creatingTrackFromFiles("TrackGame_LessFraction."));
  }

  // �˶�Ա�ɼ�������λ����
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ���������ﾶ�������ϵͳ��creatingTrackFromFiles����
  // �жϸú����ܷ�ʶ����쳣
  @Test
  public void moreIntegerTest() {
    FunctionTrackGame temp = new FunctionTrackGame();
    assertFalse(temp.creatingTrackFromFiles("TrackGame_MoreInteger."));
  }

  // �˶�Ա�Ĺ�������Сд��ĸ
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ���������ﾶ�������ϵͳ��creatingTrackFromFiles����
  // �жϸú����ܷ�ʶ����쳣
  @Test
  public void nationailityLowercaseTest() {
    FunctionTrackGame temp = new FunctionTrackGame();
    assertFalse(temp.creatingTrackFromFiles("TrackGame_NationailityLowercase."));
  }

  // �˶�Ա�Ĺ���������λ��ĸ
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ���������ﾶ�������ϵͳ��creatingTrackFromFiles����
  // �жϸú����ܷ�ʶ����쳣
  @Test
  public void nationailityMorethanThreeTest() {
    FunctionTrackGame temp = new FunctionTrackGame();
    assertFalse(temp.creatingTrackFromFiles("TrackGame_NationailityMorethanThree."));
  }

  // �˶�Ա������Ϊ����
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ���������ﾶ�������ϵͳ��creatingTrackFromFiles����
  // �жϸú����ܷ�ʶ����쳣
  @Test
  public void negativeAthleteAgeTest() {
    FunctionTrackGame temp = new FunctionTrackGame();
    assertFalse(temp.creatingTrackFromFiles("TrackGame_NegativeAthleteAge."));
  }

  // �˶�Ա�ı��Ϊ����
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ���������ﾶ�������ϵͳ��creatingTrackFromFiles����
  // �жϸú����ܷ�ʶ����쳣
  @Test
  public void negativeAthleteNumberTest() {
    FunctionTrackGame temp = new FunctionTrackGame();
    assertFalse(temp.creatingTrackFromFiles("TrackGame_NegativeAthleteNumber."));
  }

  // �˶�Ա����óɼ�Ϊ����
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ���������ﾶ�������ϵͳ��creatingTrackFromFiles����
  // �жϸú����ܷ�ʶ����쳣
  @Test
  public void negativeResultTest() {
    FunctionTrackGame temp = new FunctionTrackGame();
    assertFalse(temp.creatingTrackFromFiles("TrackGame_NegativeResult."));
  }

  // ����ͬ�����˶�Ա
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ���������ﾶ�������ϵͳ��creatingTrackFromFiles����
  // �жϸú����ܷ�ʶ����쳣
  @Test
  public void sameAthleteTest() {
    FunctionTrackGame temp = new FunctionTrackGame();
    assertFalse(temp.creatingTrackFromFiles("TrackGame_SameAthlete."));
  }

  // �������ļ�
  // Testing strategy for exception
  // �����������ļ���Ϊ���������ﾶ�������ϵͳ��creatingTrackFromFiles����
  // �жϸú����ܷ���������
  @Test
  public void trackGameTest() {
    FunctionTrackGame temp = new FunctionTrackGame();
    assertTrue(temp.creatingTrackFromFiles("TrackGame."));
  }
}
