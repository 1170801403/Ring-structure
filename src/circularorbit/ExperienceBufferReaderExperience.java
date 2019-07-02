package circularorbit;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ExperienceBufferReaderExperience {
 
  /**
      * 从文本文件中读取内容.
   * @param txt 要读取的文本文件.
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
