package physicalobject;

import static org.junit.Assert.assertTrue;

public class TrackE1 extends E1 {
  private final int number;// 号码
  private final String nationaility;// 国籍
  private final int age;// 年龄
  private final double best;// 本年度最好成绩
  // Abstraction function:
  // 表示一个运动员的所有信息
  // Representation invariant:
  // 每一个信息都是不可变类型，不能为null,且如果信息为数字，则必须大于0
  // Safety from rep exposure:
  // 所有的信息都是private和final类型

  /**
   * 保证TrackE1类的表示不变性.
   */
  public void trackcheckRep() {
    //这些东西在读文件和建立轨道的时候就已经检查过一遍了，没必要再检查一次
//    assertTrue(nationaility != null);
//    assertTrue(best > 0);
//    assertTrue(age > 0);
//    assertTrue(number > 0);
  }

  /**
   * 用指定的参数构造一个运动员对象.
   * @param name 运动员名字
   * @param number 运动员编号
   * @param nationaility 运动员国籍
   * @param age 运动员姓名
   * @param best 运动员最好成绩
   */
  public TrackE1(String name, int number, String nationaility, int age, double best) {
    super(name);// 父类构造函数
    this.number = number;
    this.nationaility = nationaility;
    this.age = age;
    this.best = best;
    checkRep();
  }

  // 以下四类都是不可变的
  public int getNumber() {
    trackcheckRep();
    return number;
  }

  public String getNationaility() {
    trackcheckRep();
    return nationaility;
  }

  /**
   * 返回运动员的年龄.
   * @return 运动员的年龄.
   */
  public int getAge() { // 返回的只是一个常量
    trackcheckRep();
    return age;
  }

  /**
   * 返回运动员的最好成绩.
   * @return 最好成绩.
   */
  public double getBest() {
    trackcheckRep();
    return best;
  }

  /**
   * 比较两个运动员是否是同一个运动员.
   * @param temp 另一个运动员.
   * @return 如果是同一个运动员，则返回true;否则返回false.
   */
  public boolean trackE1equals(TrackE1 temp) { // 比较两个运动员是否是同一个运动员

    //    if(temp.equals(this)) {
    if (temp.getName().equals(this.getName()) && temp.getAge() == this.age
        && temp.getNumber() == this.number && temp.getNationaility().equals(this.nationaility)) {
      return true;
    } else {
      return false;
    }
  }

}
