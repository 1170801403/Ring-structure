package circularorbit;

public class TrackOrganizer {
  private TrackStrategy yes;

  /**
   * ����ģʽ�е���֯�߽�ɫ����Ϊ���캯��.
   * @param choice ���û�ѡ���İ��ű��������ķ���
   */
  public TrackOrganizer(String choice) {
    switch (choice) {
      case "r":
        yes = new TrackStrategyR();
        break;
      case "g":
        yes = new TrackStrategyG();
        break;
      default:
        break;
    }
  }

  public void arrange(FunctionTrackGame game) {
    yes.strategy(game);
  }
}
