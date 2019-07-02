package circularorbittest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import circularorbit.FunctionTrackGame;
import circularorbit.SocialNetworkCircle;

public class TrackGameErrorHandleTest {
  // 运动员成绩的小数点位数过少
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用田径比赛轨道系统的creatingTrackFromFiles函数
  // 判断该函数能否识别出异常
  @Test
  public void lessFractionTest() {
    FunctionTrackGame temp = new FunctionTrackGame();
    assertFalse(temp.creatingTrackFromFiles("TrackGame_LessFraction."));
  }

  // 运动员成绩的整数位过多
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用田径比赛轨道系统的creatingTrackFromFiles函数
  // 判断该函数能否识别出异常
  @Test
  public void moreIntegerTest() {
    FunctionTrackGame temp = new FunctionTrackGame();
    assertFalse(temp.creatingTrackFromFiles("TrackGame_MoreInteger."));
  }

  // 运动员的国籍采用小写字母
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用田径比赛轨道系统的creatingTrackFromFiles函数
  // 判断该函数能否识别出异常
  @Test
  public void nationailityLowercaseTest() {
    FunctionTrackGame temp = new FunctionTrackGame();
    assertFalse(temp.creatingTrackFromFiles("TrackGame_NationailityLowercase."));
  }

  // 运动员的国籍超过三位字母
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用田径比赛轨道系统的creatingTrackFromFiles函数
  // 判断该函数能否识别出异常
  @Test
  public void nationailityMorethanThreeTest() {
    FunctionTrackGame temp = new FunctionTrackGame();
    assertFalse(temp.creatingTrackFromFiles("TrackGame_NationailityMorethanThree."));
  }

  // 运动员的年龄为负数
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用田径比赛轨道系统的creatingTrackFromFiles函数
  // 判断该函数能否识别出异常
  @Test
  public void negativeAthleteAgeTest() {
    FunctionTrackGame temp = new FunctionTrackGame();
    assertFalse(temp.creatingTrackFromFiles("TrackGame_NegativeAthleteAge."));
  }

  // 运动员的编号为负数
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用田径比赛轨道系统的creatingTrackFromFiles函数
  // 判断该函数能否识别出异常
  @Test
  public void negativeAthleteNumberTest() {
    FunctionTrackGame temp = new FunctionTrackGame();
    assertFalse(temp.creatingTrackFromFiles("TrackGame_NegativeAthleteNumber."));
  }

  // 运动员的最好成绩为负数
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用田径比赛轨道系统的creatingTrackFromFiles函数
  // 判断该函数能否识别出异常
  @Test
  public void negativeResultTest() {
    FunctionTrackGame temp = new FunctionTrackGame();
    assertFalse(temp.creatingTrackFromFiles("TrackGame_NegativeResult."));
  }

  // 出现同名的运动员
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用田径比赛轨道系统的creatingTrackFromFiles函数
  // 判断该函数能否识别出异常
  @Test
  public void sameAthleteTest() {
    FunctionTrackGame temp = new FunctionTrackGame();
    assertFalse(temp.creatingTrackFromFiles("TrackGame_SameAthlete."));
  }

  // 正常的文件
  // Testing strategy for exception
  // 采用正常的文件作为参数调用田径比赛轨道系统的creatingTrackFromFiles函数
  // 判断该函数能否正常返回
  @Test
  public void trackGameTest() {
    FunctionTrackGame temp = new FunctionTrackGame();
    assertTrue(temp.creatingTrackFromFiles("TrackGame."));
  }
}
