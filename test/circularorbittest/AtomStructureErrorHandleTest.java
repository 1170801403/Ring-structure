package circularorbittest;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import circularorbit.AtomStructure;
import circularorbit.SocialNetworkCircle;

// ��Ҫ��Զ��ļ�ʱ�Ĵ���
public class AtomStructureErrorHandleTest {
  // Ԫ�����Ƶ�һλ��ĸСд
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ��������ԭ�ӹ��ϵͳ��creatingTrackFromFiles����
  // �жϸú����Ƿ��ܹ�ʶ�����Щ�쳣
  @Test
  public void firstLowercaseTest() {
    AtomStructure temp = new AtomStructure();
    assertFalse(temp.creatingTrackFromFiles("AtomStructure_FirstLowercase."));
  }

  // �ڶ�λ��ĸ��д
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ��������ԭ�ӹ��ϵͳ��creatingTrackFromFiles����
  // �жϸú����Ƿ��ܹ�ʶ�����Щ�쳣
  @Test
  public void secondCapitalTest() {
    AtomStructure temp = new AtomStructure();
    assertFalse(temp.creatingTrackFromFiles("AtomStructure_SecondCapital."));
  }

  // ԭ�����Ƴ�����λ
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ��������ԭ�ӹ��ϵͳ��creatingTrackFromFiles����
  // �жϸú����Ƿ��ܹ�ʶ�����Щ�쳣
  @Test
  public void morethanThreeTest() {
    AtomStructure temp = new AtomStructure();
    assertFalse(temp.creatingTrackFromFiles("AtomStructure_MorethanThree."));
  }

  // �����ĿΪ����
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ��������ԭ�ӹ��ϵͳ��creatingTrackFromFiles����
  // �жϸú����Ƿ��ܹ�ʶ�����Щ�쳣
  @Test
  public void negativeElectronicNumberTest() {
    AtomStructure temp = new AtomStructure();
    assertFalse(temp.creatingTrackFromFiles("AtomStructure_NegativeElectronicNumber."));
  }

  // ����ϵĵ�����ĿΪ����
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ��������ԭ�ӹ��ϵͳ��creatingTrackFromFiles����
  // �жϸú����Ƿ��ܹ�ʶ�����Щ�쳣
  @Test
  public void negativeTrackNumberTest() {
    AtomStructure temp = new AtomStructure();
    assertFalse(temp.creatingTrackFromFiles("AtomStructure_NegativeTrackNumber."));
  }

  // ������Ϊ��
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ��������ԭ�ӹ��ϵͳ��creatingTrackFromFiles����
  // �жϸú����Ƿ��ܹ�ʶ�����Щ�쳣
  @Test
  public void negativeTrackTest() {
    AtomStructure temp = new AtomStructure();
    assertFalse(temp.creatingTrackFromFiles("AtomStructure_NegativeTrack."));
  }

  // ���������Ŀì��
  // Testing strategy for exception
  // ���ô����ض��쳣���ļ���Ϊ��������ԭ�ӹ��ϵͳ��creatingTrackFromFiles����
  // �жϸú����Ƿ��ܹ�ʶ�����Щ�쳣
  @Test
  public void contradictionTest() {
    AtomStructure temp = new AtomStructure();
    assertFalse(temp.creatingTrackFromFiles("AtomStructure_Contradiction."));
  }

  // ��������
  // Testing strategy for exception
  // �����������ļ���Ϊ��������ԭ�ӹ��ϵͳ��creatingTrackFromFiles����
  // �жϸú����ܷ���������
  @Test
  public void atomStructureTest() {
    AtomStructure temp = new AtomStructure();
    assertTrue(temp.creatingTrackFromFiles("AtomicStructure."));
  }
}
