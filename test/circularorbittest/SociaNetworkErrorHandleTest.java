package circularorbittest;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import circularorbit.SocialNetworkCircle;

public class SociaNetworkErrorHandleTest {
  // ����Ϊ��
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ���������罻������ϵͳ��creatingTrackFromFiles����
  // �жϸú����Ƿ��ܹ�ʶ�����Щ�쳣
  @Test
  public void negativeAgeTest() {
    SocialNetworkCircle temp = new SocialNetworkCircle();
    assertFalse(temp.creatingTrackFromFiles("SocialNetworkCircle_NegativeAge."));
  }

  // �Ա�Ϊm��f
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ���������罻������ϵͳ��creatingTrackFromFiles����
  // �жϸú����Ƿ��ܹ�ʶ�����Щ�쳣
  @Test
  public void sexTest() {
    SocialNetworkCircle temp = new SocialNetworkCircle();
    assertFalse(temp.creatingTrackFromFiles("SocialNetworkCircle_SocialSex."));
  }

  // ���ܶȵ�С��λ��������
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ���������罻������ϵͳ��creatingTrackFromFiles����
  // �жϸú����Ƿ��ܹ�ʶ�����Щ�쳣
  @Test
  public void socialTieFractionTest() {
    SocialNetworkCircle temp = new SocialNetworkCircle();
    assertFalse(temp.creatingTrackFromFiles("SocialNetworkCircle_SocialTieFraction."));
  }

  // ���ܶȹ���
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ���������罻������ϵͳ��creatingTrackFromFiles����
  // �жϸú����Ƿ��ܹ�ʶ�����Щ�쳣
  @Test
  public void socialTieLargerTest() {
    SocialNetworkCircle temp = new SocialNetworkCircle();
    assertFalse(temp.creatingTrackFromFiles("SocialNetworkCircle_SocialTieLarger."));
  }

  // ���ܶȹ�С
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ���������罻������ϵͳ��creatingTrackFromFiles����
  // �жϸú����Ƿ��ܹ�ʶ�����Щ�쳣
  @Test
  public void socialTieLessTest() {
    SocialNetworkCircle temp = new SocialNetworkCircle();
    assertFalse(temp.creatingTrackFromFiles("SocialNetworkCircle_SocialTieLess."));
  }

  // ĳ��δ��Friend�ж���ͳ�����socialTie��
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ���������罻������ϵͳ��creatingTrackFromFiles����
  // �жϸú����Ƿ��ܹ�ʶ�����Щ�쳣
  @Test
  public void socialTieFriendTest() {
    SocialNetworkCircle temp = new SocialNetworkCircle();
    assertFalse(temp.creatingTrackFromFiles("SocialNetworkCircle_SocialTieFriend."));
  }

  // ����������ͬ��socialTie
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ���������罻������ϵͳ��creatingTrackFromFiles����
  // �жϸú����Ƿ��ܹ�ʶ�����Щ�쳣
  @Test
  public void socialTieSameTest() {
    SocialNetworkCircle temp = new SocialNetworkCircle();
    assertFalse(temp.creatingTrackFromFiles("SocialNetworkCircle_SocialTieSame."));
  }

  // ����ָ�������socialTie
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ���������罻������ϵͳ��creatingTrackFromFiles����
  // �жϸú����Ƿ��ܹ�ʶ�����Щ�쳣
  @Test
  public void socialTieSelfTest() {
    SocialNetworkCircle temp = new SocialNetworkCircle();
    assertFalse(temp.creatingTrackFromFiles("SocialNetworkCircle_SocialTieSelf."));
  }

  // �����Ĺ���ļ�
  // Testing strategy for exception
  // �����������ļ���Ϊ���������罻������ϵͳ��creatingTrackFromFiles����
  // �жϸú����ܷ���������
  @Test
  public void socialTest() {
    SocialNetworkCircle temp = new SocialNetworkCircle();
    assertTrue(temp.creatingTrackFromFiles("SocialNetworkCircle."));
  }

}
