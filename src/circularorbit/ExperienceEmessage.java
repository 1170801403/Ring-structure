package circularorbit;



public class ExperienceEmessage {
  /**
   * 试验main函数打印e.getMessage()功能.
   * @param args 字符串
   */
  public static void main(String[] args) {
    int a = 5;
    if (a > 0) {
      try {
        throw new Exception("The same name!");
      } catch (Exception e) {
        System.out.println(e.getMessage());
        return;
        // TODO: handle exception
      }
    }
  }
}
