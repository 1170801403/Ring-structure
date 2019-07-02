// ��������ֱ�Ӷ�functionTrackGame���в�����
// ��functionTrackGame��trackGame���в�����trackGame˵����ֻ��һ�����ϵͳ

package circularorbit;

import static org.junit.Assert.assertTrue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import abstractfactory.TrackFactory;
import centralobject.*;
import mylog.MyFormatter;
import physicalobject.TrackE1;
import track.Track;

// Ϊ��ʹ����־���̳�
public class FunctionTrackGame extends ConcreteCircularObject<L1, TrackE1> {
  private int trackNumber;// ���������ֻ���������ļ��ã�����ʵ�־��幦�ܵ�ʱ������physical��ʵ�ʳ���Ϊ׼
  private int playType;
  private List<TrackGame> groupTrackSystem = new ArrayList<TrackGame>();// �洢ÿһ�����
  private Set<TrackE1> athlete = new HashSet<TrackE1>();// �����洢���е�����
  private List<TrackE1> athleteForRandom = new ArrayList<TrackE1>();// �����ϱ�Ϊ�б����ڰ��ű�������
  private Map<TrackE1, Integer> objectGroup = new HashMap<TrackE1, Integer>();// ���嵽����ӳ��
  private TrackFactory factory = new TrackFactory();

  // Abstraction function:
  // ����ʵ���ﾶ����ϵͳ�����ļ��ж��벢���������
  // ���ò�ͬ�����ݽṹ�洢������塢�������塢�����������������֮��Ĺ�ϵ������������������֮��Ĺ�ϵ��
  // ��ʵ������������塢���������������ϵ���������ԾǨ�ȹ���
  // Representation invariant:
  // ������壬��������ĵ����嶼�ǲ��ɱ�ģ�ͨ����������ݽṹ��������֮�����ϵ,
  // ÿ������ϵ��������ᳬ���ܵ���
  // Safety from rep exposure:
  // ���ж����������private final�ģ��ڷ�����Ҫ���ݽṹʱ�����˷����Կ�¡

  /**
   * ȷ��FunctionTrackGame�ı�ʾ������.
   */
  public void checkRep() {

    // for (int i = 0; i < groupTrackSystem.size(); i++) {
    // // assertTrue(groupTrackSystem.get(i).getPhysical().size() <= trackNumber);
    // // ȷ��һ�������ֻ��һ����
    // assertTrue(groupTrackSystem.get(i).getObjectTrack().size() <= groupTrackSystem.get(i)
    //
    // .getPhysical().size());
    // }
  }


  /**
   * ���ر������࣬����д���ļ�.
   * 
   * @return �ﾶ����������
   */
  public int getPlayType() {
    final int temp;// �û������ٸ���
    temp = playType;
    log.info("trackGame:assert playType isn't 0,in class functionTrackGame,method getTrackNumebr");
    assert temp != 0 : "tracknumber is 0";
    log.info("trackGame:return trackNumebr,in class functionTrackGame,method getTrackNumebr");
    return temp;
  }



  /**
   * Ҫ������Ŀ��Ϊ0.
   * 
   * @return ÿһ������еĹ����Ŀ.
   */
  public int getTrackNumber() {
    final int temp;// �û������ٸ���
    temp = trackNumber;
    log.info(
        "trackGame:assert trackNumber isn't 0,in class functionTrackGame,method getTrackNumebr");
    assert temp != 0 : "tracknumber is 0";
    log.info("trackGame:return trackNumebr,in class functionTrackGame,method getTrackNumebr");
    return temp;
  }

  /**
   * ��ǰ������.
   * 
   * @return ��¡��ı���ϵͳ����.
   */
  public List<TrackGame> getGroupTrackSystem() {
    List<TrackGame> temp = new ArrayList<TrackGame>();
    int size = groupTrackSystem.size();
    for (int i = 0; i < size; i++) {
      // trackGame etemp = new trackGame();
      TrackGame etemp = groupTrackSystem.get(i);// ֻ�ܱ�֤���ÿͻ��˶�trackGame
      temp.add(etemp);
    }
    log.info("trackGame:assert groupTrackSystem isn't empty,in class functionTrackGame,"
        + "method getGroupTrackSystem");
    assert temp.size() != 0 : "trackGame is empty";// ��������
    log.info(
        "trackGame:return groupTrackSystem,in class functionTrackGame,method getGroupTrackSystem");
    return temp;
  }

  /**
   * ��ǰ������.
   * 
   * @return ��¡����˶�Ա����.s
   */
  public Set<TrackE1> getAthlete() {
    Set<TrackE1> temp = new HashSet<TrackE1>();
    Iterator<TrackE1> iterator = athlete.iterator();
    while (iterator.hasNext()) {
      TrackE1 etemp = iterator.next();
      temp.add(etemp);// ��������ǲ��ɱ���󣬸Ĳ���
    }
    log.info("trackGame:assert athlete isn't empty,in class functionTrackGame,method getAthlete");
    assert temp.size() != 0 : "athlete is empty";// ��������
    log.info("trackGame:return Athlete,in class functionTrackGame,method getAthlete");
    return temp;
  }

  // �����ݽṹ�������֣���û�н������
  /**
   * �Ӹ����ļ��ж�ȡ���ݣ��������ݽṹ�������֣�Ҫ���ļ�����Ϊ��.
   * 
   * @param name �ļ���.
   * @return �����ȡ���ݳɹ��򷵻�true�����򷵻�false����ʾ������Ϣ.
   */
  public boolean creatingTrackFromFiles(String name) { // ���ⲿ�ļ���ȡ���ݹ�����ϵͳ����
    boolean ifCheck = false;
    log.info("trackGame:assert file name isn't empty,"
        + "in class functionTrackGame,method creatingTrackFromFiles");
    assert name.length() != 0 : "file name is null";// ǰ������
    // String re1 = "(Athlete)"; // Word 1
    // String re2 = "(\\s+)"; // White Space 1
    // String re3 = "(:)"; // Any Single Character 1
    // String re4 = "(:)"; // Any Single Character 2
    // String re5 = "(=)"; // Any Single Character 3
    // String re6 = "(\\s+)"; // White Space 2
    // String re7 = "(<)"; // Any Single Character 4
    // String re8 = "((?:[a-z][a-z]+))"; // Word 2,����
    // String re9 = "(,)"; // Any Single Character 5
    // String re10 = "(-?\\d+)"; // Integer Number 1�����
    // String re11 = "(,)"; // Any Single Character 6
    // String re12 = "((?:[a-z][a-z]+))"; // Word 3������
    // String re13 = "(,)"; // Any Single Character 7
    // String re14 = "(-?\\d+)"; // Integer Number 2������
    // String re15 = "(,)"; // Any Single Character 8
    // String re16 = "([+-]?\\d*\\.\\d+)(?![-+0-9\\.])"; // Float 1����ѳɼ�
    // String re17 = "(>)"; // Any Single Character 9
    Pattern p = Pattern.compile(
        "(Athlete)" + "(\\s+)" + "(:)" + "(:)" + "(=)" + "(\\s+)" + "(<)" + "((?:[a-z][a-z]+))"
            + "(,)" + "(-?\\d+)" + "(,)" + "((?:[a-z][a-z]+))" + "(,)" + "(-?\\d+)" + "(,)"
            + "([+-]?\\d*\\.\\d+)(?![-+0-9\\.])" + "(>)",
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    // Pattern p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7 + re8 + re9 + re10 + re11
    // + re12 + re13 + re14 + re15 + re16 + re17, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    // String re18 = "(Game)"; // Word 1
    // String re19 = "(\\s+)"; // White Space 1
    // String re20 = "(:)"; // Any Single Character 1
    // String re21 = "(:)"; // Any Single Character 2
    // String re22 = "(=)"; // Any Single Character 3
    // String re23 = "(\\s+)"; // White Space 2
    // String re24 = "(\\d+)"; // Integer Number 1
    Pattern p1 = Pattern.compile("(Game)" + "(\\s+)" + "(:)" + "(:)" + "(=)" + "(\\s+)" + "(\\d+)",
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

//    String re25 = "(NumOfTracks)"; // Word 1
//    String re26 = "(\\s+)"; // White Space 1
//    String re27 = "(:)"; // Any Single Character 1
//    String re28 = "(:)"; // Any Single Character 2
//    String re29 = "(=)"; // Any Single Character 3
//    String re30 = "(\\s+)"; // White Space 2
//    String re31 = "(\\d+)"; // Integer Number 1

    Pattern p2 = Pattern.compile("(NumOfTracks)" + "(\\s+)" + "(:)" +"(:)" +"(=)" + "(\\s+)" +"(\\d+)",
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    // Pattern p2 = Pattern.compile(re25 + re26 + re27 + re28 + re29 + re30 + re31,
    // Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    long startTime = System.currentTimeMillis();
    String txt = new String();
    try (BufferedReader in =
        new BufferedReader(new InputStreamReader(new FileInputStream("txt/" + name + "txt")))) {
      while ((txt = in.readLine()) != null) {
        Matcher m = p.matcher(txt);
        Matcher m1 = p1.matcher(txt);
        Matcher m2 = p2.matcher(txt);
        while (m.find()) {
          // System.out.println("Find ahlete");
//          String word1 = m.group(8);// ����
//          String int1 = m.group(10);// ���
//          String word2 = m.group(12);// ����
//          String int2 = m.group(14);// ����
//          String float1 = m.group(16);// ��ѳɼ�
          // System.out.println("Athlete");
          if (ifCheck) {
            try {
              // ���ж��Ƿ����ظ�Ԫ��
              Iterator<TrackE1> iterator = athlete.iterator();
              while (iterator.hasNext()) {
                try {
                  if (iterator.next().getName().equals(m.group(8))) {
                    throw new Exception(
                        "same elements:the athlete " + m.group(8) + " appears more than once.");
                  }

                } catch (Exception e) {
                  log.info("trackGame:exception happens in class functionTrackGame,"
                      + "method creatingTrackFromFiles:" + e.getMessage()
                      + " handling:return false,and exception message is printed on console");
                  System.out.println(e.getMessage());
                  return false;
                  // TODO: handle exception
                }
              }
              // ��������Ϊ3
              if (!(m.group(12).length() == 3)) {
                throw new Exception("statements do not conform to syntax rules: the athlete "
                    + m.group(8) + "'s nationality is not made up by three letters:"+m.group(12));
              }
              // �����ɴ�д��ĸ����
              for (int i = 0; i < 3; i++) {
                if (!((m.group(12).charAt(i) <= 90) && (m.group(12).charAt(i) >= 65))) {
                  throw new Exception("statements do not conform to syntax rules: the athlete "
                      + m.group(8) + "'s nationality's first letter is not capital " +m.group(12));
                }
              }
              // ���Ϊ��
              if (Integer.parseInt(m.group(10)) < 0) {
                throw new Exception(
                    "statements do not conform to syntax rules: the number of athlete " + m.group(8)
                        + " is negative " + Integer.parseInt(m.group(10)));
              }
              // ����Ϊ��
              if (Integer.parseInt(m.group(14)) < 0) {
                throw new Exception("statements do not conform to syntax rules: the age of athlete "
                    + m.group(8) + " is not negative " + Integer.parseInt(m.group(14)));
              }
              // �ɼ�����Ϊ��
              if (Float.parseFloat(m.group(16)) < 0) {
                throw new Exception(
                    "statements do not conform to syntax rules: the result of athlete " + m.group(8)
                        + " is negative:" + Float.parseFloat(m.group(16)));
              }
              // ��óɼ���������λС��
              if (m.group(16).charAt(m.group(16).length() - 1 - 2) != ".".charAt(0)) { // ��������λС��
                throw new Exception("statements do not conform to syntax rules: the athlete "
                    + m.group(8) + " 's result's fractional part's length isn't 2");
              }
              // �Ѿ�ȷ������λС���ˣ�������Ҫȷ���в�������λ����
              if (m.group(16).length() > 5) { // �ڴ�֮ǰ�Ѿ��ж�С�����λ��������λ�ģ�������Ҫ�ж�����λ��
                throw new Exception("statements do not conform to syntax rules: the athlete "
                    + m.group(8) + " 's result's Integral part's length is larger than 2");
              }
            } catch (Exception e) {
              log.warning("trackGame:exception happens in class functionTrackGame,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false,and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }

          TrackE1 temp = factory.manufactureE(m.group(8), Integer.parseInt(m.group(10)),m.group(12),
              Integer.parseInt(m.group(14)), Float.parseFloat(m.group(16)));
          athlete.add(temp);// parseInt��parseFloat���ǹ��쳣��
        }
        while (m1.find()) {
          // System.out.println("Find game");
          String int11 = m1.group(7);
          // �������಻����Ҫ��
          if (ifCheck) {
            try {
              if (!(Integer.parseInt(int11) == 100 || Integer.parseInt(int11) == 200
                  || Integer.parseInt(int11) == 400)) {
                throw new Exception("statements do not conform to syntax rules: " + "the game type "
                    + Integer.parseInt(int11) + " is illegal");
              }
            } catch (Exception e) {
              log.warning("trackGame:exception happens in class functionTrackGame,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false,and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          playType = Integer.parseInt(int11);// �õ���������
        }

        while (m2.find()) {
          // System.out.println("Find track");
          String int12 = m2.group(7);
          // �����Ŀ������Ҫ��
          if (ifCheck) {
            if (!((Integer.parseInt(int12) <= 10) && (Integer.parseInt(int12) >= 4))) {
              try {
                throw new Exception("statements do not conform to syntax rules: "
                    + "the number of track " + Integer.parseInt(int12) + " is out of bound");
              } catch (Exception e) {
                log.warning("trackGame:exception happens in class functionTrackGame,"
                    + "method creatingTrackFromFiles:" + e.getMessage()
                    + " handling:return false,and exception message is printed on console");
                System.out.println(e.getMessage());
                return false;
                // TODO: handle exception
              }
            }
          }
          trackNumber = Integer.parseInt(int12);// �õ������Ŀ

        }
      }
    } catch (IOException e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    long endTime = System.currentTimeMillis();
    System.out.println("It takes " + (endTime - startTime) + " millseconds to read file.");
    log.info("trackGame:read a file");
    return true;
  }
  // ���ļ��Ĺ����У���ʼ���������˶�Ա���󣬵õ��˱������࣬�����Ŀ

  /**
   * ��FileReader�ķ�ʽ��ȡ�ﾶ��������.
   * 
   * @param name ��Ҫ��ȡ���ݵ��ļ���.
   * @return �����ȡ�ɹ����򷵻�true,���򷵻�false����ʾ����ԭ��
   */
  public boolean creatingTrackFromFilesFile(String name) { // ���ⲿ�ļ���ȡ���ݹ�����ϵͳ����
    boolean ifCheck = false;
    log.info("trackGame:assert file name isn't empty,"
        + "in class functionTrackGame,method creatingTrackFromFiles");
    assert name.length() != 0 : "file name is null";// ǰ������
    String re1 = "(Athlete)"; // Word 1
    String re2 = "(\\s+)"; // White Space 1
    String re3 = "(:)"; // Any Single Character 1
    String re4 = "(:)"; // Any Single Character 2
    String re5 = "(=)"; // Any Single Character 3
    String re6 = "(\\s+)"; // White Space 2
    String re7 = "(<)"; // Any Single Character 4
    String re8 = "((?:[a-z][a-z]+))"; // Word 2,����
    String re9 = "(,)"; // Any Single Character 5
    String re10 = "(-?\\d+)"; // Integer Number 1�����
    String re11 = "(,)"; // Any Single Character 6
    String re12 = "((?:[a-z][a-z]+))"; // Word 3������
    String re13 = "(,)"; // Any Single Character 7
    String re14 = "(-?\\d+)"; // Integer Number 2������
    String re15 = "(,)"; // Any Single Character 8
    String re16 = "([+-]?\\d*\\.\\d+)(?![-+0-9\\.])"; // Float 1����ѳɼ�
    String re17 = "(>)"; // Any Single Character 9
    Pattern p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7 + re8 + re9 + re10 + re11
        + re12 + re13 + re14 + re15 + re16 + re17, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String re18 = "(Game)"; // Word 1
    String re19 = "(\\s+)"; // White Space 1
    String re20 = "(:)"; // Any Single Character 1
    String re21 = "(:)"; // Any Single Character 2
    String re22 = "(=)"; // Any Single Character 3
    String re23 = "(\\s+)"; // White Space 2
    String re24 = "(\\d+)"; // Integer Number 1
    Pattern p1 = Pattern.compile(re18 + re19 + re20 + re21 + re22 + re23 + re24,
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String re25 = "(NumOfTracks)"; // Word 1
    String re26 = "(\\s+)"; // White Space 1
    String re27 = "(:)"; // Any Single Character 1
    String re28 = "(:)"; // Any Single Character 2
    String re29 = "(=)"; // Any Single Character 3
    String re30 = "(\\s+)"; // White Space 2
    String re31 = "(\\d+)"; // Integer Number 1

    Pattern p2 = Pattern.compile(re25 + re26 + re27 + re28 + re29 + re30 + re31,
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    long startTime = System.currentTimeMillis();
    String txt = new String();
    try (BufferedReader reader = new BufferedReader(new FileReader("txt/" + name + "txt"))) {
      while ((txt = reader.readLine()) != null) {
        Matcher m = p.matcher(txt);
        Matcher m1 = p1.matcher(txt);
        Matcher m2 = p2.matcher(txt);
        while (m.find()) {
          // System.out.println("Find ahlete");
          String word1 = m.group(8);// ����
          String int1 = m.group(10);// ���
          String word2 = m.group(12);// ����
          String int2 = m.group(14);// ����
          String float1 = m.group(16);// ��ѳɼ�
          // System.out.println("Athlete");
          if (ifCheck) {
            try {
              // ���ж��Ƿ����ظ�Ԫ��
              Iterator<TrackE1> iterator = athlete.iterator();
              while (iterator.hasNext()) {
                try {
                  if (iterator.next().getName().equals(word1)) {
                    throw new Exception(
                        "same elements:the athlete " + word1 + " appears more than once.");
                  }

                } catch (Exception e) {
                  log.info("trackGame:exception happens in class functionTrackGame,"
                      + "method creatingTrackFromFiles:" + e.getMessage()
                      + " handling:return false,and exception message is printed on console");
                  System.out.println(e.getMessage());
                  return false;
                  // TODO: handle exception
                }
              }
              // ��������Ϊ3
              if (!(word2.length() == 3)) {
                throw new Exception("statements do not conform to syntax rules: the athlete "
                    + word1 + "'s nationality is not made up by three letters:" + word2);
              }
              // �����ɴ�д��ĸ����
              for (int i = 0; i < 3; i++) {
                if (!((word2.charAt(i) <= 90) && (word2.charAt(i) >= 65))) {
                  throw new Exception("statements do not conform to syntax rules: the athlete "
                      + word1 + "'s nationality's first letter is not capital " + word2);
                }
              }
              // ���Ϊ��
              if (Integer.parseInt(int1) < 0) {
                throw new Exception(
                    "statements do not conform to syntax rules: the number of athlete " + word1
                        + " is negative " + Integer.parseInt(int1));
              }
              // ����Ϊ��
              if (Integer.parseInt(int2) < 0) {
                throw new Exception("statements do not conform to syntax rules: the age of athlete "
                    + word1 + " is not negative " + Integer.parseInt(int2));
              }
              // �ɼ�����Ϊ��
              if (Float.parseFloat(float1) < 0) {
                throw new Exception(
                    "statements do not conform to syntax rules: the result of athlete " + word1
                        + " is negative:" + Float.parseFloat(float1));
              }
              // ��óɼ���������λС��
              if (float1.charAt(float1.length() - 1 - 2) != ".".charAt(0)) { // ��������λС��
                throw new Exception("statements do not conform to syntax rules: the athlete "
                    + word1 + " 's result's fractional part's length isn't 2");
              }
              // �Ѿ�ȷ������λС���ˣ�������Ҫȷ���в�������λ����
              if (float1.length() > 5) { // �ڴ�֮ǰ�Ѿ��ж�С�����λ��������λ�ģ�������Ҫ�ж�����λ��
                throw new Exception("statements do not conform to syntax rules: the athlete "
                    + word1 + " 's result's Integral part's length is larger than 2");
              }
            } catch (Exception e) {
              log.warning("trackGame:exception happens in class functionTrackGame,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false,and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }

          TrackE1 temp = factory.manufactureE(word1, Integer.parseInt(int1), word2,
              Integer.parseInt(int2), Float.parseFloat(float1));
          athlete.add(temp);// parseInt��parseFloat���ǹ��쳣��
        }
        while (m1.find()) {
          // System.out.println("Find game");
          String int11 = m1.group(7);
          // �������಻����Ҫ��
          if (ifCheck) {
            try {
              if (!(Integer.parseInt(int11) == 100 || Integer.parseInt(int11) == 200
                  || Integer.parseInt(int11) == 400)) {
                throw new Exception("statements do not conform to syntax rules: " + "the game type "
                    + Integer.parseInt(int11) + " is illegal");
              }
            } catch (Exception e) {
              log.warning("trackGame:exception happens in class functionTrackGame,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false,and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          playType = Integer.parseInt(int11);// �õ���������
        }

        while (m2.find()) {
          // System.out.println("Find track");
          String int12 = m2.group(7);
          // �����Ŀ������Ҫ��
          if (ifCheck) {
            if (!((Integer.parseInt(int12) <= 10) && (Integer.parseInt(int12) >= 4))) {
              try {
                throw new Exception("statements do not conform to syntax rules: "
                    + "the number of track " + Integer.parseInt(int12) + " is out of bound");
              } catch (Exception e) {
                log.warning("trackGame:exception happens in class functionTrackGame,"
                    + "method creatingTrackFromFiles:" + e.getMessage()
                    + " handling:return false,and exception message is printed on console");
                System.out.println(e.getMessage());
                return false;
                // TODO: handle exception
              }
            }
          }
          trackNumber = Integer.parseInt(int12);// �õ������Ŀ

        }
      }
    } catch (IOException e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    long endTime = System.currentTimeMillis();
    System.out.println("It takes " + (endTime - startTime) + " millseconds to read file");
    log.info("trackGame:read a file");
    return true;
  }


  /**
   * ��Scanner�ķ�ʽ��ȡ�ﾶ�����������.
   * 
   * @param name ��Ҫ��ȡ���ݵ��ļ�����.
   * @return �����ȡ�ɹ����򷵻�true,���򷵻�false����ʾ����ԭ��.
   */
  public boolean creatingTrackFromFilesScanner(String name) { // ���ⲿ�ļ���ȡ���ݹ�����ϵͳ����
    boolean ifCheck = false;
    log.info("trackGame:assert file name isn't empty,"
        + "in class functionTrackGame,method creatingTrackFromFiles");
    assert name.length() != 0 : "file name is null";// ǰ������
    String re1 = "(Athlete)"; // Word 1
    String re2 = "(\\s+)"; // White Space 1
    String re3 = "(:)"; // Any Single Character 1
    String re4 = "(:)"; // Any Single Character 2
    String re5 = "(=)"; // Any Single Character 3
    String re6 = "(\\s+)"; // White Space 2
    String re7 = "(<)"; // Any Single Character 4
    String re8 = "((?:[a-z][a-z]+))"; // Word 2,����
    String re9 = "(,)"; // Any Single Character 5
    String re10 = "(-?\\d+)"; // Integer Number 1�����
    String re11 = "(,)"; // Any Single Character 6
    String re12 = "((?:[a-z][a-z]+))"; // Word 3������
    String re13 = "(,)"; // Any Single Character 7
    String re14 = "(-?\\d+)"; // Integer Number 2������
    String re15 = "(,)"; // Any Single Character 8
    String re16 = "([+-]?\\d*\\.\\d+)(?![-+0-9\\.])"; // Float 1����ѳɼ�
    String re17 = "(>)"; // Any Single Character 9
    Pattern p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7 + re8 + re9 + re10 + re11
        + re12 + re13 + re14 + re15 + re16 + re17, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String re18 = "(Game)"; // Word 1
    String re19 = "(\\s+)"; // White Space 1
    String re20 = "(:)"; // Any Single Character 1
    String re21 = "(:)"; // Any Single Character 2
    String re22 = "(=)"; // Any Single Character 3
    String re23 = "(\\s+)"; // White Space 2
    String re24 = "(\\d+)"; // Integer Number 1
    Pattern p1 = Pattern.compile(re18 + re19 + re20 + re21 + re22 + re23 + re24,
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String re25 = "(NumOfTracks)"; // Word 1
    String re26 = "(\\s+)"; // White Space 1
    String re27 = "(:)"; // Any Single Character 1
    String re28 = "(:)"; // Any Single Character 2
    String re29 = "(=)"; // Any Single Character 3
    String re30 = "(\\s+)"; // White Space 2
    String re31 = "(\\d+)"; // Integer Number 1

    Pattern p2 = Pattern.compile(re25 + re26 + re27 + re28 + re29 + re30 + re31,
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    long startTime = System.currentTimeMillis();
    String txt = new String();
    try {
      File file = new File("txt/" + name + "txt");
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        txt = sc.nextLine();
        Matcher m = p.matcher(txt);
        Matcher m1 = p1.matcher(txt);
        Matcher m2 = p2.matcher(txt);
        while (m.find()) {
          // System.out.println("Find ahlete");
          String word1 = m.group(8);// ����
          String int1 = m.group(10);// ���
          String word2 = m.group(12);// ����
          String int2 = m.group(14);// ����
          String float1 = m.group(16);// ��ѳɼ�
          // System.out.println("Athlete");
          if (ifCheck) {
            try {
              // ���ж��Ƿ����ظ�Ԫ��
              Iterator<TrackE1> iterator = athlete.iterator();
              while (iterator.hasNext()) {
                try {
                  if (iterator.next().getName().equals(word1)) {
                    throw new Exception(
                        "same elements:the athlete " + word1 + " appears more than once.");
                  }

                } catch (Exception e) {
                  log.info("trackGame:exception happens in class functionTrackGame,"
                      + "method creatingTrackFromFiles:" + e.getMessage()
                      + " handling:return false,and exception message is printed on console");
                  System.out.println(e.getMessage());
                  return false;
                  // TODO: handle exception
                }
              }
              // ��������Ϊ3
              if (!(word2.length() == 3)) {
                throw new Exception("statements do not conform to syntax rules: the athlete "
                    + word1 + "'s nationality is not made up by three letters:" + word2);
              }
              // �����ɴ�д��ĸ����
              for (int i = 0; i < 3; i++) {
                if (!((word2.charAt(i) <= 90) && (word2.charAt(i) >= 65))) {
                  throw new Exception("statements do not conform to syntax rules: the athlete "
                      + word1 + "'s nationality's first letter is not capital " + word2);
                }
              }
              // ���Ϊ��
              if (Integer.parseInt(int1) < 0) {
                throw new Exception(
                    "statements do not conform to syntax rules: the number of athlete " + word1
                        + " is negative " + Integer.parseInt(int1));
              }
              // ����Ϊ��
              if (Integer.parseInt(int2) < 0) {
                throw new Exception("statements do not conform to syntax rules: the age of athlete "
                    + word1 + " is not negative " + Integer.parseInt(int2));
              }
              // �ɼ�����Ϊ��
              if (Float.parseFloat(float1) < 0) {
                throw new Exception(
                    "statements do not conform to syntax rules: the result of athlete " + word1
                        + " is negative:" + Float.parseFloat(float1));
              }
              // ��óɼ���������λС��
              if (float1.charAt(float1.length() - 1 - 2) != ".".charAt(0)) { // ��������λС��
                throw new Exception("statements do not conform to syntax rules: the athlete "
                    + word1 + " 's result's fractional part's length isn't 2");
              }
              // �Ѿ�ȷ������λС���ˣ�������Ҫȷ���в�������λ����
              if (float1.length() > 5) { // �ڴ�֮ǰ�Ѿ��ж�С�����λ��������λ�ģ�������Ҫ�ж�����λ��
                throw new Exception("statements do not conform to syntax rules: the athlete "
                    + word1 + " 's result's Integral part's length is larger than 2");
              }
            } catch (Exception e) {
              log.warning("trackGame:exception happens in class functionTrackGame,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false,and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }

          TrackE1 temp = factory.manufactureE(word1, Integer.parseInt(int1), word2,
              Integer.parseInt(int2), Float.parseFloat(float1));
          athlete.add(temp);// parseInt��parseFloat���ǹ��쳣��
        }
        while (m1.find()) {
          // System.out.println("Find game");
          String int11 = m1.group(7);
          // �������಻����Ҫ��
          if (ifCheck) {
            try {
              if (!(Integer.parseInt(int11) == 100 || Integer.parseInt(int11) == 200
                  || Integer.parseInt(int11) == 400)) {
                throw new Exception("statements do not conform to syntax rules: " + "the game type "
                    + Integer.parseInt(int11) + " is illegal");
              }
            } catch (Exception e) {
              log.warning("trackGame:exception happens in class functionTrackGame,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false,and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          playType = Integer.parseInt(int11);// �õ���������
        }

        while (m2.find()) {
          // System.out.println("Find track");
          String int12 = m2.group(7);
          // �����Ŀ������Ҫ��
          if (ifCheck) {
            if (!((Integer.parseInt(int12) <= 10) && (Integer.parseInt(int12) >= 4))) {
              try {
                throw new Exception("statements do not conform to syntax rules: "
                    + "the number of track " + Integer.parseInt(int12) + " is out of bound");
              } catch (Exception e) {
                log.warning("trackGame:exception happens in class functionTrackGame,"
                    + "method creatingTrackFromFiles:" + e.getMessage()
                    + " handling:return false,and exception message is printed on console");
                System.out.println(e.getMessage());
                return false;
                // TODO: handle exception
              }
            }
          }
          trackNumber = Integer.parseInt(int12);// �õ������Ŀ

        }
      }
    } catch (IOException e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    long endTime = System.currentTimeMillis();
    System.out.println("It takes " + (endTime - startTime) + " to read the file.");
    log.info("trackGame:read a file");
    return true;
  }


  // ���ﲻ�ܼ̳и����еķ���
  /**
   * ����һ�������Ҫ�������������ϵͳ�Ľ��޷�Χ��.
   * 
   * @param groupNumebr Ҫ���ӹ�������.
   * @return �����ӹ���ɹ����򷵻�true�����򷵻�false����ʾ������Ϣ.
   */
  public boolean addTrack(int groupNumebr) {
    // ���쳣���ƴ�����߳������չ��
    try {
      if (groupNumebr >= groupTrackSystem.size()) {
        throw new Exception("The group is out of bound!");
      }
    } catch (Exception e) {
      log.warning("trackGame:exception happens in class functionTrackGame,method addTrack:"
          + e.getMessage() + " handling:return false,and exception message is printed on console");
      System.out.println(e.getMessage());
      return false;
      // TODO: handle exception
    }
    // assert groupNumebr>=0&&groupNumebr<groupTrackSystem.size():"groupNumber is
    // illegal";
    // if (groupNumebr >= groupTrackSystem.size())
    // {
    // System.out.println("The group is out of bound!");
    // return false;
    // }
    groupTrackSystem.get(groupNumebr).addTrack();
    checkRep();// ������ô�ӣ�������checkRep�е�����
    log.info("trackGame:add track " + trackNumber + " in group " + groupNumebr);
    return true;
  }

  /**
   * ɾ��һ�������Ҫ���������ƥ��Ĺ����ϵͳ�����ڣ�����ɾ��athlete����˶�Ա���˶�Ա�͹��������һ��. ���ǻ�ɾ��ӳ��objectTrack��trackObject����˶�Ա
   * 
   * @param groupNumebr ɾ��������ڵ����.
   * @param trackNumber ɾ������ı��.
   * @return ���ɾ���ɹ��򷵻�true�����򷵻�false����ʾ������Ϣ.
   */
  public boolean deleteTrack(int groupNumebr, int trackNumber) {
    // ���������ⲿ
    // assert groupNumebr>=0&&groupNumebr<groupTrackSystem.size():"groupNumber is
    // illegal";
    try {
      if (groupNumebr >= groupTrackSystem.size()) {
        throw new Exception("The group is out of bound!");
      }
    } catch (Exception e) {
      // TODO: handle exception
      log.warning("trackGame:exception happens in class functionTrackGame,method deleteTrack:"
          + e.getMessage() + " handling:return false,and exception message is printed on console");
      System.out.println(e.getMessage());
      return false;
    }

    groupTrackSystem.get(groupNumebr).deleteTrack(trackNumber);
    checkRep();// ��return true֮ǰ��checkRep()
    // ��־��¼����������ʱ����Ҫ˵�������ͷ�����
    log.info("trackGame:delete track " + trackNumber + " in group " + groupNumebr);
    return true;
  }

  /**
   * ����һ���˶�Ա��Ҫ������Ĺ����ź�������ϵͳ�����ڣ���Ŀ������û���˶�Ա.
   * 
   * @param groupNumebr Ŀ�����.
   * @param trackNumebr Ŀ������.
   * @param ob ��Ҫ��ӵ��˶�Ա.
   * @return �����ӳɹ��򷵻�true,���򷵻�false����ʾ������Ϣ.
   */
  public boolean addObject(int groupNumebr, int trackNumebr, TrackE1 ob) {
    // �ڱ����������û�õ��������
    // ���ⲿ���������쳣����
    try {
      if (groupNumebr >= groupTrackSystem.size()) {
        throw new Exception("The group is out of bound!");
      }
      if (!groupTrackSystem.get(groupNumebr).trackObject.containsKey(trackNumebr)) {
        throw new Exception("The track has been deleted!");
      }
      if (groupTrackSystem.get(groupNumebr).trackObject.get(trackNumebr).size() != 0) {

        // �涨һ�����������ֻ��һλ�˶�Ա
        throw new Exception("There is already an athlete in the track!");
      }
    } catch (Exception e) {
      log.warning("trackGame:exception happens in class functionTrackGame,method addObject:"
          + e.getMessage() + " handling:return false,and exception message is printed on console");
      System.out.println(e.getMessage());
      return false;
      // TODO: handle exception
    }

    groupTrackSystem.get(groupNumebr).addTrackObject(ob, trackNumebr);
    checkRep();// ��return true֮ǰ��checkRep()
    log.info("trackGame:add an athlete in track " + trackNumber + " group " + groupNumebr);
    return true;
  }

  /**
   * ɾ��һ���˶�Ա��Ҫ������Ĳ�������������ƥ�䵽�˶�Ա.
   * 
   * @param groupNumebr �˶�Ա���ڵ�����.
   * @param trackNumber �˶�Ա���ڵĹ�����.
   * @return ���ɾ���ɹ��򷵻�true,���򷵻�false������ʾ������Ϣ.
   */
  public boolean deleteObject(int groupNumebr, int trackNumber) {
    try {
      if (groupNumebr >= groupTrackSystem.size()) {
        throw new Exception("The group is out of bound!");
      }
      if (!groupTrackSystem.get(groupNumebr).getTrackObject().containsKey(trackNumber)) {
        throw new Exception("The track doesn't exist!");
      }
      if (groupTrackSystem.get(groupNumebr).getTrackObject().get(trackNumber).size() == 0) {
        throw new Exception("the track is empty!");
      }
    } catch (Exception e) {
      log.warning("trackGame:exception happens in class functionTrackGame,method deleteObject:"
          + e.getMessage() + " handling:return false,and exception message is printed on console");
      System.out.println(e.getMessage());
      return false;
      // TODO: handle exception
    }

    Iterator<TrackE1> iterator =
        groupTrackSystem.get(groupNumebr).getTrackObject().get(trackNumber).iterator();
    // ��return true֮ǰ��checkRep()
    while (iterator.hasNext()) {
      TrackE1 delete = iterator.next();
      groupTrackSystem.get(groupNumebr).deleteTrackObject(delete, trackNumber);
    }
    checkRep();
    log.info("trackGame:delete an athlete in track " + trackNumber + " group " + groupNumebr);
    return true;
    // System.out.println("the track is empty!");
    // return false;

  }

  /**
   * ������ű�������.
   * 
   * @return ����������ųɹ��򷵻�true,���򷵻�false����ʾ������Ϣ.
   * @throws IOException ���������ļ�����
   * @throws SecurityException ��ȫ���쳣
   */
  public boolean autoCompetitionA() {
    long startTime = System.currentTimeMillis();
    Random rand = new Random();
    Iterator<TrackE1> iterator = athlete.iterator();
    // �����ܹ��Զ����ֻ�����
    while (iterator.hasNext()) { // �������е�Ԫ��ת�Ƶ��б���
      athleteForRandom.add(iterator.next());// Ӧ���Ǵ���ַ����
    }
    long endTime2 = System.currentTimeMillis();
    System.out.println("It takes " + (endTime2 - startTime) + " millseconds to transform.");
    Collections.shuffle(athleteForRandom);
    int total = athlete.size();
    // System.out.println(trackNumber);
    int remainder = total % trackNumber;// ���һ��Ӧ�÷ֶ�����
    int group = total / trackNumber;// Ӧ�÷ֶ�����
    if (remainder == 0) {
      // trackE1 groupPlayer[][] = new trackE1[group][trackNumber];
      long time1 = 0;
      long time2 = 0;
      for (int i = 0; i < group; i++) { // groupҲ��ͨ��physical.size()���������
        TrackGame temp = new TrackGame();
        long time11 = System.currentTimeMillis();
        for (int k = 0; k < trackNumber; k++) {
          temp.addTrack();// �ܿ��ܺܺķ�ʱ��
        }
        long time22 = System.currentTimeMillis();
        time1 = time1 + time22 - time11;
        groupTrackSystem.add(temp);
        long time111 = System.currentTimeMillis();
        for (int j = 0; j < trackNumber; j++) {
          // int random = rand.nextInt(athleteForRandom.size());
          TrackE1 tem = athleteForRandom.get(i * trackNumber + j);
          objectGroup.put(tem, i);// ���˶�Ա�������
          groupTrackSystem.get(i).addTrackObject(tem, j);
          // ���ϲ���ԭ����䣬���ܺܺ�ʱ��
          // athleteForRandom.remove(random);// ��������ֻ��Զ���ǰ�ƶ�
        }
        long time222 = System.currentTimeMillis();
        time2 = time2 + time222 - time111;
      }
      System.out.println(time1);
      System.out.println(time2);
    } else {

      for (int i = 0; i < group; i++) { // �Ȱ�֮ǰ�ĵ��ζ�����
        TrackGame temp = new TrackGame();
        for (int k = 0; k < trackNumber; k++) {
          temp.addTrack();
        }
        groupTrackSystem.add(temp);
        for (int j = 0; j < trackNumber; j++) {
          // int random = rand.nextInt(athleteForRandom.size());
          TrackE1 tem = athleteForRandom.get(i * trackNumber + j);
          objectGroup.put(tem, i);// ���˶�Ա�������
          groupTrackSystem.get(i).addTrackObject(tem, j);
          // ���˶�Ա���ù��,��ʱ�����˽Ƕ�
          // athleteForRandom.remove(random);// ��������ֻ��Զ���ǰ�ƶ�
        }
      }
      TrackGame temp = new TrackGame();
      for (int m = 0; m < trackNumber; m++) {
        temp.addTrack();
      }
      groupTrackSystem.add(temp);// ��ʱgroupTrackSystem��size��group+1
      for (int j = 0; j < remainder; j++) {
        // int random = rand.nextInt(athleteForRandom.size());// 0��athleteForRandom.size()-1֮��������
        // trackObject.get(physical.get(j)).add(athleteForRandom.get(random));
        // temp.setTrackNumber(j);������䣬��������ĺ������Ѿ�ʵ���˸Ĺ���
        // addTrackObject(athleteForRandom.get(random), j);// ��������������
        TrackE1 tem2 = athleteForRandom.get(group * trackNumber + j);
        objectGroup.put(tem2, group);// ���˶�Ա�������
        groupTrackSystem.get(group).addTrackObject(tem2, j);// ���˶�Ա���ù��,��ʱ�����˽Ƕ�
        // athleteForRandom.remove(random);// ������˶�Ա����ǰ�ƶ�����֤ͬһ���˶�Աֻ�ܳ�����һ�������
      }
    }
    // checkRep();// ��return true֮ǰ��checkRep()
    // System.out.println("Succeed!");
    long endTime = System.currentTimeMillis();
    System.out.println(
        "It takes " + (endTime - startTime) + " millseconds to arrange competition for random.");
    log.info("trackGame:make a competition by random");
    return true;
  }

  /**
   * ���ճɼ����ű�������.
   * 
   * @return ����������ųɹ��򷵻�true,���򷵻�false����ʾ������Ϣ.
   * @throws IOException ��������쳣.
   * @throws SecurityException ��ȫ���쳣.
   */
  public boolean autoCompetitionB() { // ���ݱ����ɼ�,��óɼ�������������
    long startTime = System.currentTimeMillis();
    Iterator<TrackE1> iterator = athlete.iterator();
    while (iterator.hasNext()) { // �������е�Ԫ��ת�Ƶ��б���
      athleteForRandom.add(iterator.next());// Ӧ���Ǵ���ַ����
    }
    Collections.sort(athleteForRandom, new Comparator<TrackE1>() {
      public int compare(TrackE1 p1, TrackE1 p2) {
        // �Գɼ����н�������
        if (p1.getBest() > p2.getBest()) {
          return -1;
        }
        if (p1.getBest() == p2.getBest()) {
          return 0;
        }
        return 1;
      }
    });
    int total = athlete.size();
    // System.out.println(trackNumber);
    int remainder = total % trackNumber;// ���һ��Ӧ�÷ֶ�����
    int group = total / trackNumber;// Ӧ�÷ֶ�����
    if (remainder == 0) {
      // trackE1 groupPlayer[][] = new trackE1[group][trackNumber];
      for (int i = 0; i < group; i++) { // groupҲ��ͨ��physical.size()���������
        TrackGame temp = new TrackGame();
        for (int k = 0; k < trackNumber; k++) {
          temp.addTrack();
        }
        groupTrackSystem.add(temp);
        int groupTrackNumber = 0;
        int finalGroupTrackNumebr = trackNumber - 1;
        for (int j = 0; j < trackNumber; j++) {
          int gradeNumber;
          if (j % 2 == 0) {
            gradeNumber = (i * trackNumber) + groupTrackNumber;
            groupTrackNumber++;
          } else {
            gradeNumber = (i * trackNumber) + finalGroupTrackNumebr;
            finalGroupTrackNumebr--;
          }
          objectGroup.put(athleteForRandom.get(gradeNumber), i);// ���˶�Ա�������
          groupTrackSystem.get(i).addTrackObject(athleteForRandom.get(gradeNumber), j);
          // ���˶�Ա���ù��,��ʱ�����˽Ƕ�
          // athleteForRandom.remove(random);// ��������ֻ��Զ���ǰ�ƶ�
        }
      }
    } else {

      // trackE1 groupPlayer[][] = new trackE1[group+1][trackNumber];
      for (int i = 0; i < group; i++) { // �Ȱ�֮ǰ�ĵ��ζ�����
        TrackGame temp = new TrackGame();
        for (int k = 0; k < trackNumber; k++) {
          temp.addTrack();
        }
        groupTrackSystem.add(temp);
        int groupTrackNumber = 0;
        int finalGroupTrackNumebr = trackNumber - 1;
        for (int j = 0; j < trackNumber; j++) {
          // int random = rand.nextInt(athleteForRandom.size());
          // trackObject.get(physical.get(j)).add(athleteForRandom.get(random));//
          // Ϊ�˶�Ա�������
          // temp.setTrackNumber(j);������䣬��������ĺ������Ѿ�ʵ���˸Ĺ���
          // addTrackObject(athleteForRandom.get(random), j);// ��������������
          // groupPlayer[i][j] = athleteForRandom.get(random);//��֤��ͬһ���˶�Աֻ����һ�������
          int gradeNumber;
          if (j % 2 == 0) {
            gradeNumber = (i * trackNumber) + groupTrackNumber;
            groupTrackNumber++;
          } else {
            gradeNumber = (i * trackNumber) + finalGroupTrackNumebr;
            finalGroupTrackNumebr--;
          }
          objectGroup.put(athleteForRandom.get(gradeNumber), i);// ���˶�Ա�������
          groupTrackSystem.get(i).addTrackObject(athleteForRandom.get(gradeNumber), j);
          // ���˶�Ա���ù��,��ʱ�����˽Ƕ�
          // athleteForRandom.remove(random);// ��������ֻ��Զ���ǰ�ƶ�
        }
      }
      TrackGame temp = new TrackGame();
      for (int m = 0; m < trackNumber; m++) {
        temp.addTrack();
      }
      groupTrackSystem.add(temp);
      int groupTrackNumber = 0;
      int finalGroupTrackNumebr = remainder - 1;
      for (int j = 0; j < remainder; j++) {
        int gradeNumber;
        if (j % 2 == 0) {
          gradeNumber = (group * trackNumber) + groupTrackNumber;
          groupTrackNumber++;
        } else {
          gradeNumber = (group * trackNumber) + finalGroupTrackNumebr;
          finalGroupTrackNumebr--;
        }
        objectGroup.put(athleteForRandom.get(gradeNumber), group);// ���˶�Ա�������
        temp.addTrackObject(athleteForRandom.get(gradeNumber), j);// ���˶�Ա���ù��,��ʱ�����˽Ƕ�
        // athleteForRandom.remove(random);// ������˶�Ա����ǰ�ƶ�����֤ͬһ���˶�Աֻ�ܳ�����һ�������
      }
    }

    checkRep();// ��return true֮ǰ��checkRep()
    // System.out.println("Succeed!");
    long endTime = System.currentTimeMillis();
    System.out.println(
        "It takes " + (endTime - startTime) + " millseconds to arrange competition for results.");
    log.info("trackGame:make a competition by results");
    return true;
  }

  /**
   * �����������˶�Ա���и�������Ĳ�����Ҫ�������˶�Ա����ͬһ���˶�Ա.
   * 
   * @param group1 �˶�Ա1�����.
   * @param number1 �˶�Ա1�Ĺ�����.
   * @param group2 �˶�Ա2�����.
   * @param number2 �˶�Ա2�Ĺ�����.
   * @return �����������ɹ�������true,���򷵻�false,����ʾ.
   */
  // ��return true֮ǰ��checkRep()
  public boolean groupAdjust(int group1, int number1, int group2, int number2) {
    // ������������ͬ���Ͳ�ͬ���

    // �ⲿ���������쳣����ǰ������
    try {
      if (group1 == group2 && number1 == number2) {
        throw new Exception("They are the same player");
        // System.out.println("They are the same player");
        // return false;
      }
      if (!((groupTrackSystem.get(group1).getTrackObject().get(number1).size() != 0)
          && (groupTrackSystem.get(group2).getTrackObject().get(number2).size() != 0))) {
        throw new Exception("there is no athlete in track");
      }
    } catch (Exception e) {
      // TODO: handle exception
      log.warning("trackGame:exception happens in class functionTrackGame,method groupAdjust:"
          + e.getMessage() + " handling:return false,and exception message is printed on console");
      System.out.println(e.getMessage());
      return false;
    }

    // �������ڳ��������

    TrackE1 f = new TrackE1("a", 1, "b", 2, 4);
    TrackE1 s = new TrackE1("a", 2, "b", 2, 4);
    Iterator<TrackE1> iterator =
        groupTrackSystem.get(group1).getTrackObject().get(number1).iterator();
    if (iterator.hasNext()) {
      System.out.println("f");
      f = iterator.next();
    }
    Iterator<TrackE1> iterator2 =
        groupTrackSystem.get(group2).getTrackObject().get(number2).iterator();
    if (iterator2.hasNext()) {
      System.out.println("s");
      s = iterator2.next();
    }
    if (group1 == group2) {
      groupTrackSystem.get(group1).transit(f, number2);// �ǶȲ���
      groupTrackSystem.get(group1).transit(s, number1);
      checkRep();
      log.info("trackGame:adjust two athlete in group" + group1 + " track" + number1 + ",group"
          + group2 + " track" + number2);
      // System.out.println("Succeed!");
      return true;
    } else { // ������������ͬ���Ͳ�ͬ���
      groupTrackSystem.get(group1).deleteTrackObject(f, number1);
      groupTrackSystem.get(group2).deleteTrackObject(s, number2);
      groupTrackSystem.get(group1).addTrackObject(s, number1);
      groupTrackSystem.get(group2).addTrackObject(f, number2);
      // ���ʱ������transit��transitֻ�����ͬһ�����ϵͳ��������������ͬ�Ĺ��ϵͳ
      // transit(player1, track2);
      // transit(player2, track1);
      checkRep();
      log.info("trackGame:adjust two athlete in group" + group1 + " track" + number1 + ",group"
          + group2 + " track" + number2);
      // System.out.println("Succeed!");
      return true;
    }

  }

  public boolean trackWriteBackA() {
    long startTime = System.currentTimeMillis();
    DecimalFormat df = new DecimalFormat("#.00");
    try (BufferedWriter bw = new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream("write-txt/TrackGame.txt")))) {
      bw.write("Game ::= " + playType);
      bw.newLine();
      bw.write("NumOfTracks ::= " + trackNumber);
      bw.newLine();
      int y = groupTrackSystem.size();
      for (int i = 0; i < y; i++) {
        Map<TrackE1, Integer> temp = groupTrackSystem.get(i).getObjectTrack();
        // Ϊ��д��־����FunctionTrackGame �̳���ConcreteCircularObject���������޴��Ĵ���
        Iterator<TrackE1> iterator = temp.keySet().iterator();
        // System.out.println(temp.size());
        while (iterator.hasNext()) {
          TrackE1 e1 = iterator.next();
          bw.write("Athlete ::= <" + e1.getName() + "," + e1.getNumber() + ","
              + e1.getNationaility() + "," + e1.getAge() + "," + df.format(e1.getBest()) + ">");
          bw.newLine();
        }
      }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
      return false;
    }
    long endTime = System.currentTimeMillis();
    System.out.println("It takes " + (endTime - startTime) + " millseconds to write it back.");
    System.out.println("write back successfully!");
    return true;
  }

  public boolean trackWriteBackB() {
    long startTime = System.currentTimeMillis();
    DecimalFormat df = new DecimalFormat("#.00");
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("write-txt/TrackGame.txt"))) {
      bw.write("Game ::= " + playType);
      bw.newLine();
      bw.write("NumOfTracks ::= " + trackNumber);
      bw.newLine();
      int y = groupTrackSystem.size();
      for (int i = 0; i < y; i++) {
        Map<TrackE1, Integer> temp = groupTrackSystem.get(i).getObjectTrack();
        // Ϊ��д��־����FunctionTrackGame �̳���ConcreteCircularObject���������޴��Ĵ���
        Iterator<TrackE1> iterator = temp.keySet().iterator();
        // System.out.println(temp.size());
        while (iterator.hasNext()) {
          TrackE1 e1 = iterator.next();
          bw.write("Athlete ::= <" + e1.getName() + "," + e1.getNumber() + ","
              + e1.getNationaility() + "," + e1.getAge() + "," + df.format(e1.getBest()) + ">");
          bw.newLine();
        }
      }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
      return false;
    }
    long endTime = System.currentTimeMillis();
    System.out.println("It takes " + (endTime - startTime) + " millseconds to write it back.");
    System.out.println("write back successfully!");
    return true;
  }


  public boolean trackWriteBackC() {
    long startTime = System.currentTimeMillis();
    DecimalFormat df = new DecimalFormat("#.00");
    try (PrintWriter bw = new PrintWriter(new FileWriter("write-txt/TrackGame.txt"))) {
      bw.write("Game ::= " + playType);
      bw.write("\r\n");
      bw.write("NumOfTracks ::= " + trackNumber);
      bw.write("\r\n");
      int y = groupTrackSystem.size();
      for (int i = 0; i < y; i++) {
        Map<TrackE1, Integer> temp = groupTrackSystem.get(i).getObjectTrack();
        // Ϊ��д��־����FunctionTrackGame �̳���ConcreteCircularObject���������޴��Ĵ���
        Iterator<TrackE1> iterator = temp.keySet().iterator();
        // System.out.println(temp.size());
        while (iterator.hasNext()) {
          TrackE1 e1 = iterator.next();
          bw.write("Athlete ::= <" + e1.getName() + "," + e1.getNumber() + ","
              + e1.getNationaility() + "," + e1.getAge() + "," + df.format(e1.getBest()) + ">");
          bw.write("\r\n");
        }
      }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
      return false;
    }
    long endTime = System.currentTimeMillis();
    System.out.println("It takes " + (endTime - startTime) + " millseconds to write it back.");
    System.out.println("write back successfully!");
    return true;
  }
}
