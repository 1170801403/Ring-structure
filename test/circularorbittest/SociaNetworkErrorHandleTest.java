package circularorbittest;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import circularorbit.SocialNetworkCircle;

public class SociaNetworkErrorHandleTest {
  // 年龄为负
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用社交网络轨道系统的creatingTrackFromFiles函数
  // 判断该函数是否能够识别出这些异常
  @Test
  public void negativeAgeTest() {
    SocialNetworkCircle temp = new SocialNetworkCircle();
    assertFalse(temp.creatingTrackFromFiles("SocialNetworkCircle_NegativeAge."));
  }

  // 性别不为m或f
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用社交网络轨道系统的creatingTrackFromFiles函数
  // 判断该函数是否能够识别出这些异常
  @Test
  public void sexTest() {
    SocialNetworkCircle temp = new SocialNetworkCircle();
    assertFalse(temp.creatingTrackFromFiles("SocialNetworkCircle_SocialSex."));
  }

  // 亲密度的小数位超过限制
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用社交网络轨道系统的creatingTrackFromFiles函数
  // 判断该函数是否能够识别出这些异常
  @Test
  public void socialTieFractionTest() {
    SocialNetworkCircle temp = new SocialNetworkCircle();
    assertFalse(temp.creatingTrackFromFiles("SocialNetworkCircle_SocialTieFraction."));
  }

  // 亲密度过大
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用社交网络轨道系统的creatingTrackFromFiles函数
  // 判断该函数是否能够识别出这些异常
  @Test
  public void socialTieLargerTest() {
    SocialNetworkCircle temp = new SocialNetworkCircle();
    assertFalse(temp.creatingTrackFromFiles("SocialNetworkCircle_SocialTieLarger."));
  }

  // 亲密度过小
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用社交网络轨道系统的creatingTrackFromFiles函数
  // 判断该函数是否能够识别出这些异常
  @Test
  public void socialTieLessTest() {
    SocialNetworkCircle temp = new SocialNetworkCircle();
    assertFalse(temp.creatingTrackFromFiles("SocialNetworkCircle_SocialTieLess."));
  }

  // 某人未在Friend中定义就出现在socialTie中
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用社交网络轨道系统的creatingTrackFromFiles函数
  // 判断该函数是否能够识别出这些异常
  @Test
  public void socialTieFriendTest() {
    SocialNetworkCircle temp = new SocialNetworkCircle();
    assertFalse(temp.creatingTrackFromFiles("SocialNetworkCircle_SocialTieFriend."));
  }

  // 出现两个相同的socialTie
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用社交网络轨道系统的creatingTrackFromFiles函数
  // 判断该函数是否能够识别出这些异常
  @Test
  public void socialTieSameTest() {
    SocialNetworkCircle temp = new SocialNetworkCircle();
    assertFalse(temp.creatingTrackFromFiles("SocialNetworkCircle_SocialTieSame."));
  }

  // 出现指向自身的socialTie
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用社交网络轨道系统的creatingTrackFromFiles函数
  // 判断该函数是否能够识别出这些异常
  @Test
  public void socialTieSelfTest() {
    SocialNetworkCircle temp = new SocialNetworkCircle();
    assertFalse(temp.creatingTrackFromFiles("SocialNetworkCircle_SocialTieSelf."));
  }

  // 正常的轨道文件
  // Testing strategy for exception
  // 采用正常的文件作为参数调用社交网络轨道系统的creatingTrackFromFiles函数
  // 判断该函数能否正常返回
  @Test
  public void socialTest() {
    SocialNetworkCircle temp = new SocialNetworkCircle();
    assertTrue(temp.creatingTrackFromFiles("SocialNetworkCircle."));
  }

}
