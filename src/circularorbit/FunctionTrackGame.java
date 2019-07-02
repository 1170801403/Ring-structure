// 其他函数直接对functionTrackGame进行操作，
// 而functionTrackGame对trackGame进行操作，trackGame说白了只是一个轨道系统

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

// 为了使用日志而继承
public class FunctionTrackGame extends ConcreteCircularObject<L1, TrackE1> {
  private int trackNumber;// 轨道数量，只是用来读文件用，真正实现具体功能的时候还是以physical的实际长度为准
  private int playType;
  private List<TrackGame> groupTrackSystem = new ArrayList<TrackGame>();// 存储每一组比赛
  private Set<TrackE1> athlete = new HashSet<TrackE1>();// 用来存储所有的物体
  private List<TrackE1> athleteForRandom = new ArrayList<TrackE1>();// 将集合变为列表，便于安排比赛方案
  private Map<TrackE1, Integer> objectGroup = new HashMap<TrackE1, Integer>();// 物体到组别的映射
  private TrackFactory factory = new TrackFactory();

  // Abstraction function:
  // 具体实现田径比赛系统，从文件中读入并建立轨道，
  // 采用不同的数据结构存储轨道物体、中心物体、轨道物体与中心物体之间的关系、中心物体与轨道物体之间的关系，
  // 并实现增减轨道物体、增减轨道、增减关系、轨道物体跃迁等功能
  // Representation invariant:
  // 轨道物体，轨道，中心点物体都是不可变的，通过具体的数据结构建立它们之间的联系,
  // 每个轨道上的人数不会超过跑道数
  // Safety from rep exposure:
  // 所有对象的类型是private final的，在返回重要数据结构时，做了防御性克隆

  /**
   * 确保FunctionTrackGame的表示不变性.
   */
  public void checkRep() {

    // for (int i = 0; i < groupTrackSystem.size(); i++) {
    // // assertTrue(groupTrackSystem.get(i).getPhysical().size() <= trackNumber);
    // // 确保一条轨道上只有一个人
    // assertTrue(groupTrackSystem.get(i).getObjectTrack().size() <= groupTrackSystem.get(i)
    //
    // .getPhysical().size());
    // }
  }


  /**
   * 返回比赛种类，用于写回文件.
   * 
   * @return 田径比赛的种类
   */
  public int getPlayType() {
    final int temp;// 用户不能再更改
    temp = playType;
    log.info("trackGame:assert playType isn't 0,in class functionTrackGame,method getTrackNumebr");
    assert temp != 0 : "tracknumber is 0";
    log.info("trackGame:return trackNumebr,in class functionTrackGame,method getTrackNumebr");
    return temp;
  }



  /**
   * 要求轨道数目不为0.
   * 
   * @return 每一组比赛中的轨道数目.
   */
  public int getTrackNumber() {
    final int temp;// 用户不能再更改
    temp = trackNumber;
    log.info(
        "trackGame:assert trackNumber isn't 0,in class functionTrackGame,method getTrackNumebr");
    assert temp != 0 : "tracknumber is 0";
    log.info("trackGame:return trackNumebr,in class functionTrackGame,method getTrackNumebr");
    return temp;
  }

  /**
   * 无前置条件.
   * 
   * @return 克隆后的比赛系统集合.
   */
  public List<TrackGame> getGroupTrackSystem() {
    List<TrackGame> temp = new ArrayList<TrackGame>();
    int size = groupTrackSystem.size();
    for (int i = 0; i < size; i++) {
      // trackGame etemp = new trackGame();
      TrackGame etemp = groupTrackSystem.get(i);// 只能保证不让客户端动trackGame
      temp.add(etemp);
    }
    log.info("trackGame:assert groupTrackSystem isn't empty,in class functionTrackGame,"
        + "method getGroupTrackSystem");
    assert temp.size() != 0 : "trackGame is empty";// 后置条件
    log.info(
        "trackGame:return groupTrackSystem,in class functionTrackGame,method getGroupTrackSystem");
    return temp;
  }

  /**
   * 无前置条件.
   * 
   * @return 克隆后的运动员集合.s
   */
  public Set<TrackE1> getAthlete() {
    Set<TrackE1> temp = new HashSet<TrackE1>();
    Iterator<TrackE1> iterator = athlete.iterator();
    while (iterator.hasNext()) {
      TrackE1 etemp = iterator.next();
      temp.add(etemp);// 轨道物体是不可变对象，改不了
    }
    log.info("trackGame:assert athlete isn't empty,in class functionTrackGame,method getAthlete");
    assert temp.size() != 0 : "athlete is empty";// 后置条件
    log.info("trackGame:return Athlete,in class functionTrackGame,method getAthlete");
    return temp;
  }

  // 往数据结构里填数字，并没有建立轨道
  /**
   * 从给定文件中读取数据，并往数据结构中填数字，要求文件名不为空.
   * 
   * @param name 文件名.
   * @return 如果读取数据成功则返回true，否则返回false并提示错误信息.
   */
  public boolean creatingTrackFromFiles(String name) { // 从外部文件读取数据构造轨道系统对象
    boolean ifCheck = false;
    log.info("trackGame:assert file name isn't empty,"
        + "in class functionTrackGame,method creatingTrackFromFiles");
    assert name.length() != 0 : "file name is null";// 前置条件
    // String re1 = "(Athlete)"; // Word 1
    // String re2 = "(\\s+)"; // White Space 1
    // String re3 = "(:)"; // Any Single Character 1
    // String re4 = "(:)"; // Any Single Character 2
    // String re5 = "(=)"; // Any Single Character 3
    // String re6 = "(\\s+)"; // White Space 2
    // String re7 = "(<)"; // Any Single Character 4
    // String re8 = "((?:[a-z][a-z]+))"; // Word 2,姓名
    // String re9 = "(,)"; // Any Single Character 5
    // String re10 = "(-?\\d+)"; // Integer Number 1，编号
    // String re11 = "(,)"; // Any Single Character 6
    // String re12 = "((?:[a-z][a-z]+))"; // Word 3，国籍
    // String re13 = "(,)"; // Any Single Character 7
    // String re14 = "(-?\\d+)"; // Integer Number 2，年龄
    // String re15 = "(,)"; // Any Single Character 8
    // String re16 = "([+-]?\\d*\\.\\d+)(?![-+0-9\\.])"; // Float 1，最佳成绩
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
//          String word1 = m.group(8);// 姓名
//          String int1 = m.group(10);// 编号
//          String word2 = m.group(12);// 国籍
//          String int2 = m.group(14);// 年龄
//          String float1 = m.group(16);// 最佳成绩
          // System.out.println("Athlete");
          if (ifCheck) {
            try {
              // 先判断是否有重复元素
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
              // 国籍长度为3
              if (!(m.group(12).length() == 3)) {
                throw new Exception("statements do not conform to syntax rules: the athlete "
                    + m.group(8) + "'s nationality is not made up by three letters:"+m.group(12));
              }
              // 国籍由大写字母构成
              for (int i = 0; i < 3; i++) {
                if (!((m.group(12).charAt(i) <= 90) && (m.group(12).charAt(i) >= 65))) {
                  throw new Exception("statements do not conform to syntax rules: the athlete "
                      + m.group(8) + "'s nationality's first letter is not capital " +m.group(12));
                }
              }
              // 编号为正
              if (Integer.parseInt(m.group(10)) < 0) {
                throw new Exception(
                    "statements do not conform to syntax rules: the number of athlete " + m.group(8)
                        + " is negative " + Integer.parseInt(m.group(10)));
              }
              // 年龄为正
              if (Integer.parseInt(m.group(14)) < 0) {
                throw new Exception("statements do not conform to syntax rules: the age of athlete "
                    + m.group(8) + " is not negative " + Integer.parseInt(m.group(14)));
              }
              // 成绩必须为正
              if (Float.parseFloat(m.group(16)) < 0) {
                throw new Exception(
                    "statements do not conform to syntax rules: the result of athlete " + m.group(8)
                        + " is negative:" + Float.parseFloat(m.group(16)));
              }
              // 最好成绩必须有两位小数
              if (m.group(16).charAt(m.group(16).length() - 1 - 2) != ".".charAt(0)) { // 必须有两位小数
                throw new Exception("statements do not conform to syntax rules: the athlete "
                    + m.group(8) + " 's result's fractional part's length isn't 2");
              }
              // 已经确保有两位小数了，现在需要确保有不超过两位整数
              if (m.group(16).length() > 5) { // 在此之前已经判断小数点的位数是有两位的，现在需要判断整数位数
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
          athlete.add(temp);// parseInt和parseFloat都是构造常量
        }
        while (m1.find()) {
          // System.out.println("Find game");
          String int11 = m1.group(7);
          // 比赛种类不符合要求
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
          playType = Integer.parseInt(int11);// 得到比赛种类
        }

        while (m2.find()) {
          // System.out.println("Find track");
          String int12 = m2.group(7);
          // 轨道数目不符合要求
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
          trackNumber = Integer.parseInt(int12);// 得到轨道数目

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
  // 读文件的过程中，初始化了所有运动员对象，得到了比赛种类，轨道数目

  /**
   * 以FileReader的方式读取田径比赛数据.
   * 
   * @param name 所要读取数据的文件名.
   * @return 如果读取成功，则返回true,否则返回false并提示错误原因
   */
  public boolean creatingTrackFromFilesFile(String name) { // 从外部文件读取数据构造轨道系统对象
    boolean ifCheck = false;
    log.info("trackGame:assert file name isn't empty,"
        + "in class functionTrackGame,method creatingTrackFromFiles");
    assert name.length() != 0 : "file name is null";// 前置条件
    String re1 = "(Athlete)"; // Word 1
    String re2 = "(\\s+)"; // White Space 1
    String re3 = "(:)"; // Any Single Character 1
    String re4 = "(:)"; // Any Single Character 2
    String re5 = "(=)"; // Any Single Character 3
    String re6 = "(\\s+)"; // White Space 2
    String re7 = "(<)"; // Any Single Character 4
    String re8 = "((?:[a-z][a-z]+))"; // Word 2,姓名
    String re9 = "(,)"; // Any Single Character 5
    String re10 = "(-?\\d+)"; // Integer Number 1，编号
    String re11 = "(,)"; // Any Single Character 6
    String re12 = "((?:[a-z][a-z]+))"; // Word 3，国籍
    String re13 = "(,)"; // Any Single Character 7
    String re14 = "(-?\\d+)"; // Integer Number 2，年龄
    String re15 = "(,)"; // Any Single Character 8
    String re16 = "([+-]?\\d*\\.\\d+)(?![-+0-9\\.])"; // Float 1，最佳成绩
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
          String word1 = m.group(8);// 姓名
          String int1 = m.group(10);// 编号
          String word2 = m.group(12);// 国籍
          String int2 = m.group(14);// 年龄
          String float1 = m.group(16);// 最佳成绩
          // System.out.println("Athlete");
          if (ifCheck) {
            try {
              // 先判断是否有重复元素
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
              // 国籍长度为3
              if (!(word2.length() == 3)) {
                throw new Exception("statements do not conform to syntax rules: the athlete "
                    + word1 + "'s nationality is not made up by three letters:" + word2);
              }
              // 国籍由大写字母构成
              for (int i = 0; i < 3; i++) {
                if (!((word2.charAt(i) <= 90) && (word2.charAt(i) >= 65))) {
                  throw new Exception("statements do not conform to syntax rules: the athlete "
                      + word1 + "'s nationality's first letter is not capital " + word2);
                }
              }
              // 编号为正
              if (Integer.parseInt(int1) < 0) {
                throw new Exception(
                    "statements do not conform to syntax rules: the number of athlete " + word1
                        + " is negative " + Integer.parseInt(int1));
              }
              // 年龄为正
              if (Integer.parseInt(int2) < 0) {
                throw new Exception("statements do not conform to syntax rules: the age of athlete "
                    + word1 + " is not negative " + Integer.parseInt(int2));
              }
              // 成绩必须为正
              if (Float.parseFloat(float1) < 0) {
                throw new Exception(
                    "statements do not conform to syntax rules: the result of athlete " + word1
                        + " is negative:" + Float.parseFloat(float1));
              }
              // 最好成绩必须有两位小数
              if (float1.charAt(float1.length() - 1 - 2) != ".".charAt(0)) { // 必须有两位小数
                throw new Exception("statements do not conform to syntax rules: the athlete "
                    + word1 + " 's result's fractional part's length isn't 2");
              }
              // 已经确保有两位小数了，现在需要确保有不超过两位整数
              if (float1.length() > 5) { // 在此之前已经判断小数点的位数是有两位的，现在需要判断整数位数
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
          athlete.add(temp);// parseInt和parseFloat都是构造常量
        }
        while (m1.find()) {
          // System.out.println("Find game");
          String int11 = m1.group(7);
          // 比赛种类不符合要求
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
          playType = Integer.parseInt(int11);// 得到比赛种类
        }

        while (m2.find()) {
          // System.out.println("Find track");
          String int12 = m2.group(7);
          // 轨道数目不符合要求
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
          trackNumber = Integer.parseInt(int12);// 得到轨道数目

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
   * 以Scanner的方式读取田径比赛轨道数据.
   * 
   * @param name 所要读取数据的文件名称.
   * @return 如果读取成功，则返回true,否则返回false并提示错误原因.
   */
  public boolean creatingTrackFromFilesScanner(String name) { // 从外部文件读取数据构造轨道系统对象
    boolean ifCheck = false;
    log.info("trackGame:assert file name isn't empty,"
        + "in class functionTrackGame,method creatingTrackFromFiles");
    assert name.length() != 0 : "file name is null";// 前置条件
    String re1 = "(Athlete)"; // Word 1
    String re2 = "(\\s+)"; // White Space 1
    String re3 = "(:)"; // Any Single Character 1
    String re4 = "(:)"; // Any Single Character 2
    String re5 = "(=)"; // Any Single Character 3
    String re6 = "(\\s+)"; // White Space 2
    String re7 = "(<)"; // Any Single Character 4
    String re8 = "((?:[a-z][a-z]+))"; // Word 2,姓名
    String re9 = "(,)"; // Any Single Character 5
    String re10 = "(-?\\d+)"; // Integer Number 1，编号
    String re11 = "(,)"; // Any Single Character 6
    String re12 = "((?:[a-z][a-z]+))"; // Word 3，国籍
    String re13 = "(,)"; // Any Single Character 7
    String re14 = "(-?\\d+)"; // Integer Number 2，年龄
    String re15 = "(,)"; // Any Single Character 8
    String re16 = "([+-]?\\d*\\.\\d+)(?![-+0-9\\.])"; // Float 1，最佳成绩
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
          String word1 = m.group(8);// 姓名
          String int1 = m.group(10);// 编号
          String word2 = m.group(12);// 国籍
          String int2 = m.group(14);// 年龄
          String float1 = m.group(16);// 最佳成绩
          // System.out.println("Athlete");
          if (ifCheck) {
            try {
              // 先判断是否有重复元素
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
              // 国籍长度为3
              if (!(word2.length() == 3)) {
                throw new Exception("statements do not conform to syntax rules: the athlete "
                    + word1 + "'s nationality is not made up by three letters:" + word2);
              }
              // 国籍由大写字母构成
              for (int i = 0; i < 3; i++) {
                if (!((word2.charAt(i) <= 90) && (word2.charAt(i) >= 65))) {
                  throw new Exception("statements do not conform to syntax rules: the athlete "
                      + word1 + "'s nationality's first letter is not capital " + word2);
                }
              }
              // 编号为正
              if (Integer.parseInt(int1) < 0) {
                throw new Exception(
                    "statements do not conform to syntax rules: the number of athlete " + word1
                        + " is negative " + Integer.parseInt(int1));
              }
              // 年龄为正
              if (Integer.parseInt(int2) < 0) {
                throw new Exception("statements do not conform to syntax rules: the age of athlete "
                    + word1 + " is not negative " + Integer.parseInt(int2));
              }
              // 成绩必须为正
              if (Float.parseFloat(float1) < 0) {
                throw new Exception(
                    "statements do not conform to syntax rules: the result of athlete " + word1
                        + " is negative:" + Float.parseFloat(float1));
              }
              // 最好成绩必须有两位小数
              if (float1.charAt(float1.length() - 1 - 2) != ".".charAt(0)) { // 必须有两位小数
                throw new Exception("statements do not conform to syntax rules: the athlete "
                    + word1 + " 's result's fractional part's length isn't 2");
              }
              // 已经确保有两位小数了，现在需要确保有不超过两位整数
              if (float1.length() > 5) { // 在此之前已经判断小数点的位数是有两位的，现在需要判断整数位数
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
          athlete.add(temp);// parseInt和parseFloat都是构造常量
        }
        while (m1.find()) {
          // System.out.println("Find game");
          String int11 = m1.group(7);
          // 比赛种类不符合要求
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
          playType = Integer.parseInt(int11);// 得到比赛种类
        }

        while (m2.find()) {
          // System.out.println("Find track");
          String int12 = m2.group(7);
          // 轨道数目不符合要求
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
          trackNumber = Integer.parseInt(int12);// 得到轨道数目

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


  // 这里不能继承父类中的方法
  /**
   * 增加一条轨道，要求输入的组编号在系统的界限范围内.
   * 
   * @param groupNumebr 要增加轨道的组号.
   * @return 如果添加轨道成功，则返回true，否则返回false并提示错误信息.
   */
  public boolean addTrack(int groupNumebr) {
    // 用异常机制处理，提高程序可扩展性
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
    checkRep();// 不管怎么加，都满足checkRep中的条件
    log.info("trackGame:add track " + trackNumber + " in group " + groupNumebr);
    return true;
  }

  /**
   * 删除一条轨道，要求输入参数匹配的轨道在系统界限内，并不删除athlete里的运动员，运动员和轨道不绑定在一起. 但是会删除映射objectTrack和trackObject里的运动员
   * 
   * @param groupNumebr 删除轨道所在的组号.
   * @param trackNumber 删除轨道的编号.
   * @return 如果删除成功则返回true，否则返回false并提示错误信息.
   */
  public boolean deleteTrack(int groupNumebr, int trackNumber) {
    // 参数来自外部
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
    checkRep();// 在return true之前加checkRep()
    // 日志记录正常操作的时候不需要说明类名和方法名
    log.info("trackGame:delete track " + trackNumber + " in group " + groupNumebr);
    return true;
  }

  /**
   * 增加一个运动员，要求输入的轨道编号和组编号在系统界限内，且目标轨道上没有运动员.
   * 
   * @param groupNumebr 目标组号.
   * @param trackNumebr 目标轨道号.
   * @param ob 将要添加的运动员.
   * @return 如果添加成功则返回true,否则返回false并提示错误信息.
   */
  public boolean addObject(int groupNumebr, int trackNumebr, TrackE1 ob) {
    // 在本函数里根本没用到这个功能
    // 对外部参数采用异常处理
    try {
      if (groupNumebr >= groupTrackSystem.size()) {
        throw new Exception("The group is out of bound!");
      }
      if (!groupTrackSystem.get(groupNumebr).trackObject.containsKey(trackNumebr)) {
        throw new Exception("The track has been deleted!");
      }
      if (groupTrackSystem.get(groupNumebr).trackObject.get(trackNumebr).size() != 0) {

        // 规定一条轨道上至多只有一位运动员
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
    checkRep();// 在return true之前加checkRep()
    log.info("trackGame:add an athlete in track " + trackNumber + " group " + groupNumebr);
    return true;
  }

  /**
   * 删除一个运动员，要求输入的参数必须满足能匹配到运动员.
   * 
   * @param groupNumebr 运动员所在的组编号.
   * @param trackNumber 运动员所在的轨道编号.
   * @return 如果删除成功则返回true,否则返回false，并提示错误信息.
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
    // 在return true之前加checkRep()
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
   * 随机安排比赛方案.
   * 
   * @return 如果方案编排成功则返回true,否则返回false并提示错误信息.
   * @throws IOException 输入和输出文件错误
   * @throws SecurityException 安全性异常
   */
  public boolean autoCompetitionA() {
    long startTime = System.currentTimeMillis();
    Random rand = new Random();
    Iterator<TrackE1> iterator = athlete.iterator();
    // 集合能够自动保持互异性
    while (iterator.hasNext()) { // 将集合中的元素转移到列表中
      athleteForRandom.add(iterator.next());// 应该是传地址调用
    }
    long endTime2 = System.currentTimeMillis();
    System.out.println("It takes " + (endTime2 - startTime) + " millseconds to transform.");
    Collections.shuffle(athleteForRandom);
    int total = athlete.size();
    // System.out.println(trackNumber);
    int remainder = total % trackNumber;// 最后一组应该分多少人
    int group = total / trackNumber;// 应该分多少组
    if (remainder == 0) {
      // trackE1 groupPlayer[][] = new trackE1[group][trackNumber];
      long time1 = 0;
      long time2 = 0;
      for (int i = 0; i < group; i++) { // group也是通过physical.size()计算出来的
        TrackGame temp = new TrackGame();
        long time11 = System.currentTimeMillis();
        for (int k = 0; k < trackNumber; k++) {
          temp.addTrack();// 很可能很耗费时间
        }
        long time22 = System.currentTimeMillis();
        time1 = time1 + time22 - time11;
        groupTrackSystem.add(temp);
        long time111 = System.currentTimeMillis();
        for (int j = 0; j < trackNumber; j++) {
          // int random = rand.nextInt(athleteForRandom.size());
          TrackE1 tem = athleteForRandom.get(i * trackNumber + j);
          objectGroup.put(tem, i);// 对运动员设置组别
          groupTrackSystem.get(i).addTrackObject(tem, j);
          // 以上不是原子语句，可能很耗时间
          // athleteForRandom.remove(random);// 后面的数字会自动往前移动
        }
        long time222 = System.currentTimeMillis();
        time2 = time2 + time222 - time111;
      }
      System.out.println(time1);
      System.out.println(time2);
    } else {

      for (int i = 0; i < group; i++) { // 先把之前的道次都排满
        TrackGame temp = new TrackGame();
        for (int k = 0; k < trackNumber; k++) {
          temp.addTrack();
        }
        groupTrackSystem.add(temp);
        for (int j = 0; j < trackNumber; j++) {
          // int random = rand.nextInt(athleteForRandom.size());
          TrackE1 tem = athleteForRandom.get(i * trackNumber + j);
          objectGroup.put(tem, i);// 对运动员设置组别
          groupTrackSystem.get(i).addTrackObject(tem, j);
          // 对运动员设置轨道,此时设置了角度
          // athleteForRandom.remove(random);// 后面的数字会自动往前移动
        }
      }
      TrackGame temp = new TrackGame();
      for (int m = 0; m < trackNumber; m++) {
        temp.addTrack();
      }
      groupTrackSystem.add(temp);// 此时groupTrackSystem的size是group+1
      for (int j = 0; j < remainder; j++) {
        // int random = rand.nextInt(athleteForRandom.size());// 0到athleteForRandom.size()-1之间的随机数
        // trackObject.get(physical.get(j)).add(athleteForRandom.get(random));
        // temp.setTrackNumber(j);多余语句，增加物体的函数中已经实现了改功能
        // addTrackObject(athleteForRandom.get(random), j);// 向轨道上增加物体
        TrackE1 tem2 = athleteForRandom.get(group * trackNumber + j);
        objectGroup.put(tem2, group);// 对运动员设置组别
        groupTrackSystem.get(group).addTrackObject(tem2, j);// 对运动员设置轨道,此时设置了角度
        // athleteForRandom.remove(random);// 后面的运动员会向前移动，保证同一个运动员只能出现在一组比赛中
      }
    }
    // checkRep();// 在return true之前加checkRep()
    // System.out.println("Succeed!");
    long endTime = System.currentTimeMillis();
    System.out.println(
        "It takes " + (endTime - startTime) + " millseconds to arrange competition for random.");
    log.info("trackGame:make a competition by random");
    return true;
  }

  /**
   * 按照成绩安排比赛方案.
   * 
   * @return 如果方案编排成功则返回true,否则返回false并提示错误信息.
   * @throws IOException 输入输出异常.
   * @throws SecurityException 安全性异常.
   */
  public boolean autoCompetitionB() { // 根据比赛成绩,最好成绩从慢到快排序
    long startTime = System.currentTimeMillis();
    Iterator<TrackE1> iterator = athlete.iterator();
    while (iterator.hasNext()) { // 将集合中的元素转移到列表中
      athleteForRandom.add(iterator.next());// 应该是传地址调用
    }
    Collections.sort(athleteForRandom, new Comparator<TrackE1>() {
      public int compare(TrackE1 p1, TrackE1 p2) {
        // 对成绩进行降序排序
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
    int remainder = total % trackNumber;// 最后一组应该分多少人
    int group = total / trackNumber;// 应该分多少组
    if (remainder == 0) {
      // trackE1 groupPlayer[][] = new trackE1[group][trackNumber];
      for (int i = 0; i < group; i++) { // group也是通过physical.size()计算出来的
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
          objectGroup.put(athleteForRandom.get(gradeNumber), i);// 对运动员设置组别
          groupTrackSystem.get(i).addTrackObject(athleteForRandom.get(gradeNumber), j);
          // 对运动员设置轨道,此时设置了角度
          // athleteForRandom.remove(random);// 后面的数字会自动往前移动
        }
      }
    } else {

      // trackE1 groupPlayer[][] = new trackE1[group+1][trackNumber];
      for (int i = 0; i < group; i++) { // 先把之前的道次都排满
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
          // 为运动员设置组别
          // temp.setTrackNumber(j);多余语句，增加物体的函数中已经实现了改功能
          // addTrackObject(athleteForRandom.get(random), j);// 向轨道上增加物体
          // groupPlayer[i][j] = athleteForRandom.get(random);//保证了同一个运动员只出现一组比赛中
          int gradeNumber;
          if (j % 2 == 0) {
            gradeNumber = (i * trackNumber) + groupTrackNumber;
            groupTrackNumber++;
          } else {
            gradeNumber = (i * trackNumber) + finalGroupTrackNumebr;
            finalGroupTrackNumebr--;
          }
          objectGroup.put(athleteForRandom.get(gradeNumber), i);// 对运动员设置组别
          groupTrackSystem.get(i).addTrackObject(athleteForRandom.get(gradeNumber), j);
          // 对运动员设置轨道,此时设置了角度
          // athleteForRandom.remove(random);// 后面的数字会自动往前移动
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
        objectGroup.put(athleteForRandom.get(gradeNumber), group);// 对运动员设置组别
        temp.addTrackObject(athleteForRandom.get(gradeNumber), j);// 对运动员设置轨道,此时设置了角度
        // athleteForRandom.remove(random);// 后面的运动员会向前移动，保证同一个运动员只能出现在一组比赛中
      }
    }

    checkRep();// 在return true之前加checkRep()
    // System.out.println("Succeed!");
    long endTime = System.currentTimeMillis();
    System.out.println(
        "It takes " + (endTime - startTime) + " millseconds to arrange competition for results.");
    log.info("trackGame:make a competition by results");
    return true;
  }

  /**
   * 对任意两个运动员进行更换轨道的操作，要求两个运动员不是同一个运动员.
   * 
   * @param group1 运动员1的组别.
   * @param number1 运动员1的轨道编号.
   * @param group2 运动员2的组别.
   * @param number2 运动员2的轨道编号.
   * @return 如果更换轨道成功，返回true,否则返回false,并提示.
   */
  // 在return true之前加checkRep()
  public boolean groupAdjust(int group1, int number1, int group2, int number2) {
    // 更换赛道，分同组别和不同组别

    // 外部参数采用异常处理前置条件
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

    // 以下属于程序的主体

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
      groupTrackSystem.get(group1).transit(f, number2);// 角度不变
      groupTrackSystem.get(group1).transit(s, number1);
      checkRep();
      log.info("trackGame:adjust two athlete in group" + group1 + " track" + number1 + ",group"
          + group2 + " track" + number2);
      // System.out.println("Succeed!");
      return true;
    } else { // 更换赛道，分同组别和不同组别
      groupTrackSystem.get(group1).deleteTrackObject(f, number1);
      groupTrackSystem.get(group2).deleteTrackObject(s, number2);
      groupTrackSystem.get(group1).addTrackObject(s, number1);
      groupTrackSystem.get(group2).addTrackObject(f, number2);
      // 这个时候不能用transit，transit只针对于同一个轨道系统，现在是两个不同的轨道系统
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
        // 为了写日志，让FunctionTrackGame 继承了ConcreteCircularObject，导致了愚蠢的错误
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
        // 为了写日志，让FunctionTrackGame 继承了ConcreteCircularObject，导致了愚蠢的错误
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
        // 为了写日志，让FunctionTrackGame 继承了ConcreteCircularObject，导致了愚蠢的错误
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
