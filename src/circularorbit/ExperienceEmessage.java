package circularorbit;



public class ExperienceEmessage {
  /**
   * ����main������ӡe.getMessage()����.
   * @param args �ַ���
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
