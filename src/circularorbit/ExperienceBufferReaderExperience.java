package circularorbit;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ExperienceBufferReaderExperience {
 
  /**
      * ���ı��ļ��ж�ȡ����.
   * @param txt Ҫ��ȡ���ı��ļ�.
   */
  public void readFile(String txt) {
    try (BufferedReader in = new BufferedReader(
        new InputStreamReader(new FileInputStream("txt/SocialNetworkCircle_Larger.txt")))) {
      ;
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }
}
