package circularorbittest;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import circularorbit.AtomStructure;
import circularorbit.SocialNetworkCircle;

// 主要针对读文件时的错误
public class AtomStructureErrorHandleTest {
  // 元素名称第一位字母小写
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用原子轨道系统的creatingTrackFromFiles函数
  // 判断该函数是否能够识别出这些异常
  @Test
  public void firstLowercaseTest() {
    AtomStructure temp = new AtomStructure();
    assertFalse(temp.creatingTrackFromFiles("AtomStructure_FirstLowercase."));
  }

  // 第二位字母大写
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用原子轨道系统的creatingTrackFromFiles函数
  // 判断该函数是否能够识别出这些异常
  @Test
  public void secondCapitalTest() {
    AtomStructure temp = new AtomStructure();
    assertFalse(temp.creatingTrackFromFiles("AtomStructure_SecondCapital."));
  }

  // 原子名称超过两位
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用原子轨道系统的creatingTrackFromFiles函数
  // 判断该函数是否能够识别出这些异常
  @Test
  public void morethanThreeTest() {
    AtomStructure temp = new AtomStructure();
    assertFalse(temp.creatingTrackFromFiles("AtomStructure_MorethanThree."));
  }

  // 轨道数目为负数
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用原子轨道系统的creatingTrackFromFiles函数
  // 判断该函数是否能够识别出这些异常
  @Test
  public void negativeElectronicNumberTest() {
    AtomStructure temp = new AtomStructure();
    assertFalse(temp.creatingTrackFromFiles("AtomStructure_NegativeElectronicNumber."));
  }

  // 轨道上的电子数目为负数
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用原子轨道系统的creatingTrackFromFiles函数
  // 判断该函数是否能够识别出这些异常
  @Test
  public void negativeTrackNumberTest() {
    AtomStructure temp = new AtomStructure();
    assertFalse(temp.creatingTrackFromFiles("AtomStructure_NegativeTrackNumber."));
  }

  // 轨道编号为负
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用原子轨道系统的creatingTrackFromFiles函数
  // 判断该函数是否能够识别出这些异常
  @Test
  public void negativeTrackTest() {
    AtomStructure temp = new AtomStructure();
    assertFalse(temp.creatingTrackFromFiles("AtomStructure_NegativeTrack."));
  }

  // 两个轨道数目矛盾
  // Testing strategy for exception
  // 采用带有特定异常的文件作为参数调用原子轨道系统的creatingTrackFromFiles函数
  // 判断该函数是否能够识别出这些异常
  @Test
  public void contradictionTest() {
    AtomStructure temp = new AtomStructure();
    assertFalse(temp.creatingTrackFromFiles("AtomStructure_Contradiction."));
  }

  // 正常测试
  // Testing strategy for exception
  // 采用正常的文件作为参数调用原子轨道系统的creatingTrackFromFiles函数
  // 判断该函数能否正常返回
  @Test
  public void atomStructureTest() {
    AtomStructure temp = new AtomStructure();
    assertTrue(temp.creatingTrackFromFiles("AtomicStructure."));
  }
}
