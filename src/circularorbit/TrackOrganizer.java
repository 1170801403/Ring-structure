package circularorbit;

public class TrackOrganizer {
  private TrackStrategy yes;

  /**
   * 策略模式中的组织者角色，此为构造函数.
   * @param choice 由用户选定的安排比赛方案的方法
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
