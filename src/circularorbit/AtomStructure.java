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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import abstractfactory.AtomFactory;
import centralobject.L1;
import flyweight.FlyWeight;
import flyweight.FlyWeightFactory;
import centralobject.AtomL1;
import mylog.MyFormatter;
import physicalobject.E1;
import physicalobject.AtomE1;
import physicalobject.SocialE1;
import track.Track;

public class AtomStructure extends ConcreteCircularObject<AtomL1, AtomE1> {
  // 测不准原理，不需要知道电子在轨道上的哪个位置
  // 并没有用到父类中的数据结构
  private Map<Integer, Integer> trackObjectNumber = new TreeMap<Integer, Integer>();
  // 每个轨道上有多少个电子
  int trackNumber2 = 0;// 有多少个轨道
  // final private static Logger log = java.util.logging.Logger.getLogger("atomStructureLog");//
  // 记录日志
  private AtomFactory factory = new AtomFactory();
  private FlyWeightFactory atomFacctory = new FlyWeightFactory();

  // 具体实现电子轨道系统，从文件中读入并建立轨道，
  // 采用不同的数据结构存储轨道物体、中心物体、轨道物体与中心物体之间的关系、中心物体与轨道物体之间的关系，
  // 并实现增减轨道物体、增减轨道、增减关系、轨道物体跃迁等功能
  // Representation invariant:
  // 轨道物体，轨道，中心点物体都是不可变的，通过具体的数据结构建立它们之间的联系,
  // Safety from rep exposure:
  // 在返回重要数据结构时，做了防御性克隆

  /**
   * 确保AtomStructure类的表示不变性.
   */
  public void checkRep() {
    // assertTrue(trackNumber2 >= 0);
    // assertTrue(trackObjectNumber != null);
  }

  /**
   * 清空集合，便于重新建立轨道.
   */
  public void clear() {
    physical.clear();
    // physical其实是第一个应该删除的，因为它直接决定了轨道的编号，轨道的编号决定了trackObject能否正常使用
    angle.clear();
    objectTrack.clear();
    trackObject.clear();
    lerelationship.clear();
    // relationship.clear();
    Iterator<AtomE1> iterator = relationship.keySet().iterator();
    while (iterator.hasNext()) {
      relationship.get(iterator.next()).clear();
    }
  }

  /**
   * 返回轨道与电子数量的映射.
   * 
   * @return 轨道与电子数量的映射
   */
  public Map<Integer, Integer> getTrackObjectNumber() {
    Map<Integer, Integer> tempTrackObjectNumber = new HashMap<Integer, Integer>();
    Iterator<Integer> iterator = trackObjectNumber.keySet().iterator();
    while (iterator.hasNext()) {
      int temp = iterator.next();
      tempTrackObjectNumber.put(temp, trackObjectNumber.get(temp));
    }
    log.info("atomStructure:assert trackObjectNumber isn't empty,"
        + "in class atomStructure,method getTrackObjectNumber");
    assert tempTrackObjectNumber.size() != 0 : "trackObjectNumber is empty";// 后置条件
    log.info("atomStructure:return trackObjectNumber");
    return tempTrackObjectNumber;
  }

  /**
   * 返回轨道的数量.
   * 
   * @return 轨道数量.
   */
  public int getTrackNumber2() {
    int x;// 传值而非传指针
    x = trackNumber2;
    log.info(
        "atomStructure:assert trackNumber isn't 0,in class atomStructure,method getTrackNumber2");
    assert x != 0;// 后置条件
    return x;
  }

  @Override
  public boolean creatingTrackFromFiles(String name) { // 从外部文件读取数据构造轨道系统对象
    boolean ifCheck = true;
    log.info("atomStructure:assert file name isn't null,"
        + "in class atomStructure,method creatingTrackFromFiles");
    assert name.length() != 0 : "file name is null";// 前置条件
    String txt = new String();
    String fre9 = "(NumberOfTracks)"; // Word 3
    String fre10 = "(\\s+)"; // White Space 4
    String fre11 = "(:)"; // Any Single Character 4
    String fre12 = "(:)"; // Any Single Character 5
    String fre13 = "(=)"; // Any Single Character 6
    String fre14 = "(\\s+)"; // White Space 5
    String fre15 = "(-?\\d+)"; // Integer Number 1
    Pattern fp = Pattern.compile(fre9 + fre10 + fre11 + fre12 + fre13 + fre14 + fre15,
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String re1 = "(ElementName)"; // Word 1
    String re2 = "(\\s+)"; // White Space 1
    String re3 = "(:)"; // Any Single Character 1
    String re4 = "(:)"; // Any Single Character 2
    String re5 = "(=)"; // Any Single Character 3
    String re6 = "(\\s+)"; // White Space 2
    String re7 = "((?:[a-z][a-z]+))"; // Word 2
    Pattern p2 = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7,
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String g1 = "(-?\\d+)"; // Integer Number 10
    String g2 = "(\\/)"; // Any Single Character 18
    String g3 = "(-?\\d+)"; // Integer Number 11
    Pattern p1 = Pattern.compile(g1 + g2 + g3, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    int count = 0;
    try (BufferedReader in =
        new BufferedReader(new InputStreamReader(new FileInputStream("txt/" + name + "txt")))) {
      // 始终记住：不能要求文件中的内容按次序出现
      while ((txt = in.readLine()) != null) {
        Matcher fm = fp.matcher(txt);
        Matcher m1 = p1.matcher(txt);
        Matcher m2 = p2.matcher(txt);
        if (fm.find()) { // 顶多匹配到一次，只需要用if
          trackNumber2 = Integer.parseInt(fm.group(7));
          // 轨道数应该是正整数
          if (ifCheck) {
            try {
              if (trackNumber2 <= 0) {
                throw new Exception("statements do not conform to syntax rules: the tracknumber "
                    + trackNumber2 + " is non-positive");
              }
            } catch (Exception e) {
              log.warning("atomStructure:exception happens in class atomStructure,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
        }
        if (m2.find()) {
          String temp = m2.group(7);
          if (ifCheck) {
            try {
              // 原子名称最多两位字母
              if (!((temp.length() > 1) && (temp.length() < 3))) {
                throw new Exception("statements do not conform to syntax rules:the name " + temp
                    + " is out of bound ");
              }
              // 第一位必须大写
              if (!((temp.charAt(0) <= 90) && (temp.charAt(0) >= 65))) {
                throw new Exception("statements do not conform to syntax rules:the first letter \""
                    + temp.charAt(0) + "\" is not capital");
              }
              // 再保证只有两位的情况下，判断第二个字母是否是小写
              if (temp.length() > 1) {
                if (!((temp.charAt(1) <= 122) && (temp.charAt(1) >= 97))) {
                  throw new Exception(
                      "statements do not conform to syntax rules:the second letter \""
                          + temp.charAt(1) + "\" is not lowercase");
                }
              }
            } catch (Exception e) {
              log.warning("atomStructure:exception happens in class atomStructure,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          central = new AtomL1(m2.group(7));
        }
        while (m1.find()) {
          String a1 = m1.group(1);
          String a2 = m1.group(3);
          if (ifCheck) {
            try {
              // 轨道编号必须大于等于零
              if (Integer.parseInt(a1) < 0) {
                throw new Exception(
                    "statements do not conform to syntax rules:the numebr of track is negative: "
                        + Integer.parseInt(a1));
              }
              // 轨道上的电子数目必须大于零
              if (Integer.parseInt(a2) <= 0) {
                throw new Exception("statements do not conform to syntax rules:"
                    + "the number of electronic is non-positive: " + Integer.parseInt(a2));
              }
            } catch (Exception e) {
              log.warning("atomStructure:exception happens in class atomStructure,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          trackObjectNumber.put(count, Integer.parseInt(a2));
          count++;
        }
      }
      // 相当于是在读完之后才判断两个电子轨道数目是否相等
      if (ifCheck) {
        try {
          if (count != trackNumber2) {
            throw new Exception(
                "incorrect dependencies:the electronic track number is contradictory:" + count);
          }
        } catch (Exception e) {
          log.warning(
              "atomStructure:exception happens in class atomStructure,method creatingTrackFromFiles:"
                  + e.getMessage()
                  + " handling:return false and exception message is printed on console");
          System.out.println(e.getMessage());
          return false;
          // TODO: handle exception
        }
      }
    } catch (IOException e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    checkRep();
    log.info("atomStructure:read a file");
    return true;
  }

  public boolean creatingTrackFromFilesFile(String name) {
    boolean ifCheck = true;
    log.info("atomStructure:assert file name isn't null,"
        + "in class atomStructure,method creatingTrackFromFiles");
    assert name.length() != 0 : "file name is null";// 前置条件
    String txt = new String();
    String fre9 = "(NumberOfTracks)"; // Word 3
    String fre10 = "(\\s+)"; // White Space 4
    String fre11 = "(:)"; // Any Single Character 4
    String fre12 = "(:)"; // Any Single Character 5
    String fre13 = "(=)"; // Any Single Character 6
    String fre14 = "(\\s+)"; // White Space 5
    String fre15 = "(-?\\d+)"; // Integer Number 1
    Pattern fp = Pattern.compile(fre9 + fre10 + fre11 + fre12 + fre13 + fre14 + fre15,
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String re1 = "(ElementName)"; // Word 1
    String re2 = "(\\s+)"; // White Space 1
    String re3 = "(:)"; // Any Single Character 1
    String re4 = "(:)"; // Any Single Character 2
    String re5 = "(=)"; // Any Single Character 3
    String re6 = "(\\s+)"; // White Space 2
    String re7 = "((?:[a-z][a-z]+))"; // Word 2
    Pattern p2 = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7,
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String g1 = "(-?\\d+)"; // Integer Number 10
    String g2 = "(\\/)"; // Any Single Character 18
    String g3 = "(-?\\d+)"; // Integer Number 11
    Pattern p1 = Pattern.compile(g1 + g2 + g3, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    int count = 0;
    try (BufferedReader reader = new BufferedReader(new FileReader("txt/" + name + "txt"))) {
      // 始终记住：不能要求文件中的内容按次序出现
      while ((txt = reader.readLine()) != null) {
        Matcher fm = fp.matcher(txt);
        Matcher m1 = p1.matcher(txt);
        Matcher m2 = p2.matcher(txt);
        if (fm.find()) { // 顶多匹配到一次，只需要用if
          trackNumber2 = Integer.parseInt(fm.group(7));
          // 轨道数应该是正整数
          if (ifCheck) {
            try {
              if (trackNumber2 <= 0) {
                throw new Exception("statements do not conform to syntax rules: the tracknumber "
                    + trackNumber2 + " is non-positive");
              }
            } catch (Exception e) {
              log.warning("atomStructure:exception happens in class atomStructure,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
        }
        if (m2.find()) {
          String temp = m2.group(7);
          if (ifCheck) {
            try {
              // 原子名称最多两位字母
              if (!((temp.length() > 1) && (temp.length() < 3))) {
                throw new Exception("statements do not conform to syntax rules:the name " + temp
                    + " is out of bound ");
              }
              // 第一位必须大写
              if (!((temp.charAt(0) <= 90) && (temp.charAt(0) >= 65))) {
                throw new Exception("statements do not conform to syntax rules:the first letter \""
                    + temp.charAt(0) + "\" is not capital");
              }
              // 再保证只有两位的情况下，判断第二个字母是否是小写
              if (temp.length() > 1) {
                if (!((temp.charAt(1) <= 122) && (temp.charAt(1) >= 97))) {
                  throw new Exception(
                      "statements do not conform to syntax rules:the second letter \""
                          + temp.charAt(1) + "\" is not lowercase");
                }
              }
            } catch (Exception e) {
              log.warning("atomStructure:exception happens in class atomStructure,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          central = new AtomL1(m2.group(7));
        }
        while (m1.find()) {
          String a1 = m1.group(1);
          String a2 = m1.group(3);
          if (ifCheck) {
            try {
              // 轨道编号必须大于等于零
              if (Integer.parseInt(a1) < 0) {
                throw new Exception(
                    "statements do not conform to syntax rules:the numebr of track is negative: "
                        + Integer.parseInt(a1));
              }
              // 轨道上的电子数目必须大于零
              if (Integer.parseInt(a2) <= 0) {
                throw new Exception("statements do not conform to syntax rules:"
                    + "the number of electronic is non-positive: " + Integer.parseInt(a2));
              }
            } catch (Exception e) {
              log.warning("atomStructure:exception happens in class atomStructure,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          trackObjectNumber.put(count, Integer.parseInt(a2));
          count++;
        }
      }
      // 相当于是在读完之后才判断两个电子轨道数目是否相等
      if (ifCheck) {
        try {
          if (count != trackNumber2) {
            throw new Exception(
                "incorrect dependencies:the electronic track number is contradictory:" + count);
          }
        } catch (Exception e) {
          log.warning(
              "atomStructure:exception happens in class atomStructure,method creatingTrackFromFiles:"
                  + e.getMessage()
                  + " handling:return false and exception message is printed on console");
          System.out.println(e.getMessage());
          return false;
          // TODO: handle exception
        }
      }
    } catch (IOException e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    checkRep();
    log.info("atomStructure:read a file");
    return true;
  }

  public boolean creatingTrackFromFilesScanner(String name) {
    boolean ifCheck = true;
    log.info("atomStructure:assert file name isn't null,"
        + "in class atomStructure,method creatingTrackFromFiles");
    assert name.length() != 0 : "file name is null";// 前置条件
    String txt = new String();
    String fre9 = "(NumberOfTracks)"; // Word 3
    String fre10 = "(\\s+)"; // White Space 4
    String fre11 = "(:)"; // Any Single Character 4
    String fre12 = "(:)"; // Any Single Character 5
    String fre13 = "(=)"; // Any Single Character 6
    String fre14 = "(\\s+)"; // White Space 5
    String fre15 = "(-?\\d+)"; // Integer Number 1
    Pattern fp = Pattern.compile(fre9 + fre10 + fre11 + fre12 + fre13 + fre14 + fre15,
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String re1 = "(ElementName)"; // Word 1
    String re2 = "(\\s+)"; // White Space 1
    String re3 = "(:)"; // Any Single Character 1
    String re4 = "(:)"; // Any Single Character 2
    String re5 = "(=)"; // Any Single Character 3
    String re6 = "(\\s+)"; // White Space 2
    String re7 = "((?:[a-z][a-z]+))"; // Word 2
    Pattern p2 = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7,
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String g1 = "(-?\\d+)"; // Integer Number 10
    String g2 = "(\\/)"; // Any Single Character 18
    String g3 = "(-?\\d+)"; // Integer Number 11
    Pattern p1 = Pattern.compile(g1 + g2 + g3, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    int count = 0;
    try  {
      // 始终记住：不能要求文件中的内容按次序出现
      File file = new File("txt/" + name + "txt");
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        txt = sc.nextLine();
        Matcher fm = fp.matcher(txt);
        Matcher m1 = p1.matcher(txt);
        Matcher m2 = p2.matcher(txt);
        if (fm.find()) { // 顶多匹配到一次，只需要用if
          trackNumber2 = Integer.parseInt(fm.group(7));
          // 轨道数应该是正整数
          if (ifCheck) {
            try {
              if (trackNumber2 <= 0) {
                throw new Exception("statements do not conform to syntax rules: the tracknumber "
                    + trackNumber2 + " is non-positive");
              }
            } catch (Exception e) {
              log.warning("atomStructure:exception happens in class atomStructure,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
        }
        if (m2.find()) {
          String temp = m2.group(7);
          if (ifCheck) {
            try {
              // 原子名称最多两位字母
              if (!((temp.length() > 1) && (temp.length() < 3))) {
                throw new Exception("statements do not conform to syntax rules:the name " + temp
                    + " is out of bound ");
              }
              // 第一位必须大写
              if (!((temp.charAt(0) <= 90) && (temp.charAt(0) >= 65))) {
                throw new Exception("statements do not conform to syntax rules:the first letter \""
                    + temp.charAt(0) + "\" is not capital");
              }
              // 再保证只有两位的情况下，判断第二个字母是否是小写
              if (temp.length() > 1) {
                if (!((temp.charAt(1) <= 122) && (temp.charAt(1) >= 97))) {
                  throw new Exception(
                      "statements do not conform to syntax rules:the second letter \""
                          + temp.charAt(1) + "\" is not lowercase");
                }
              }
            } catch (Exception e) {
              log.warning("atomStructure:exception happens in class atomStructure,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          central = new AtomL1(m2.group(7));
        }
        while (m1.find()) {
          String a1 = m1.group(1);
          String a2 = m1.group(3);
          if (ifCheck) {
            try {
              // 轨道编号必须大于等于零
              if (Integer.parseInt(a1) < 0) {
                throw new Exception(
                    "statements do not conform to syntax rules:the numebr of track is negative: "
                        + Integer.parseInt(a1));
              }
              // 轨道上的电子数目必须大于零
              if (Integer.parseInt(a2) <= 0) {
                throw new Exception("statements do not conform to syntax rules:"
                    + "the number of electronic is non-positive: " + Integer.parseInt(a2));
              }
            } catch (Exception e) {
              log.warning("atomStructure:exception happens in class atomStructure,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          trackObjectNumber.put(count, Integer.parseInt(a2));
          count++;
        }
      }
      // 相当于是在读完之后才判断两个电子轨道数目是否相等
      if (ifCheck) {
        try {
          if (count != trackNumber2) {
            throw new Exception(
                "incorrect dependencies:the electronic track number is contradictory:" + count);
          }
        } catch (Exception e) {
          log.warning(
              "atomStructure:exception happens in class atomStructure,method creatingTrackFromFiles:"
                  + e.getMessage()
                  + " handling:return false and exception message is printed on console");
          System.out.println(e.getMessage());
          return false;
          // TODO: handle exception
        }
      }
    } catch (IOException e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    checkRep();
    log.info("atomStructure:read a file");
    return true;
  }

  // 建立轨道的时候是不负责处理异常的
  /**
   * 通过数据结构建立轨道.
   * 
   * @param trackNumber2 核外电子轨道数目.
   * @param rtrackObjectNumber 轨道数到每条轨道上电子数的映射.
   */
  public void creatingTrack(int trackNumber2, Map<Integer, Integer> rtrackObjectNumber) {
    // 通过checkRep检查前置和后置条件
    checkRep();
    for (int i = 0; i < trackNumber2; i++) { // 构造轨道系统
      // 添加轨道
      addTrack();
    }
    for (int i = 0; i < rtrackObjectNumber.size(); i++) {

      int t = rtrackObjectNumber.get(i);
      for (int j = 0; j < t; j++) {
        // 向轨道上添加物体
        // addTrackObject(factory.manufactureE("electrical"), i);// 向轨道上加电子
        addTrackObject(atomFacctory.getRealFlyWeight("e"),i);
        //其实用的是同一个电子
      }
      // new atomE1("electrical")
    }
    trackObjectNumber = rtrackObjectNumber;
    log.info("atomStructure:creating a track");
    checkRep();
  }



  /**
   * 在原子核外增加一条轨道.
   */
  public void addTrack() { // 增加一条轨道,需要首先确定轨道半径，必须大于
    // System.out.println(trackObject.size());
    int rep = physical.size() + 1;// 轨道半径 = 轨道编号+1
    Track temp = realTrackFactory.manufacture(rep);
    physical.add(temp);
    trackObject.put(this.getPhysical().size() - 1, new HashSet<AtomE1>());
    trackObjectNumber.put(this.getPhysical().size() - 1, 0);
    checkRep();// 检查后置条件
    log.info("atomStructure:add a track");
    // System.out.println(trackObject.size());
    // System.out.println("Succeed!");
  }

  // 涉及到具体的电子实例，之所以要重写父类中的transit方法，是为了更好的建立轨道
  /**
   * 完成电子跃迁.
   * 
   * @param source 电子的源轨道.
   * @param target 电子的目标轨道.
   * @return 如果跃迁成功，则返回true,否则返回false并提示错误信息.
   */
  public boolean electronicTransition(int source, int target) {
    // 电子跃迁：给定源轨道、目标轨道，模拟电子跃迁，实质：源轨道上减少一个电子，目标轨道上增加一个电子；轨道编号从0开始
    try {
      if (!(source < physical.size())) {
        throw new Exception("The source orbit is out of bound!");
      }
      if (!(target < physical.size())) {
        throw new Exception("The target orbit is out of bound!");
      }
    } catch (Exception e) {
      // TODO: handle exception
      log.warning(
          "atomStructure:exception happens in class atomStructure,method electronicTransition:"
              + e.getMessage()
              + " handling:return false and exception message is printed on console");
      System.out.println(e.getMessage());
      return false;
    }

    // 一切正常之后
    int t = trackObjectNumber.get(source);

    // 属于程序运行过程中的判断，用if，不用assert或异常处理
    if (t == 0) {
      System.out.println("The source target doesn't has any e!");
      return false;
    }

    Iterator<AtomE1> iterator = trackObject.get(source).iterator();
    AtomE1 temp = new AtomE1("2");
    if (iterator.hasNext()) {
      // 让它指向另一块内存
      temp = iterator.next();
    }

    // trackObject.get(source).remove(temp);
    deleteTrackObject(temp, source);
    trackObjectNumber.put(source, t - 1);

    // trackObject.get(target).add(temp);a
    addTrackObject(temp, target);
    int q = trackObjectNumber.get(target);
    trackObjectNumber.put(target, q + 1);
    checkRep();// 检查后置条件
    log.info("atomStructure:a electron transits from track" + source + " to track" + target);
    // System.out.println("succeed!");
    return true;
  }
  
  
  
  public boolean atomWriteBackA()
  {
    long startTime = System.currentTimeMillis();
    try (BufferedWriter bw = new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream("write-txt/AtomStructure.txt")))) {
      bw.write("ElementName ::= " + central.getName());
      bw.newLine();
      bw.write("NumberOfTracks ::= " + trackNumber2);
      bw.newLine();
      bw.write("NumberOfElectron ::= ");
      Map<Integer, Integer> temp = trackObjectNumber;
      Iterator<Integer> iterator = temp.keySet().iterator();
      // System.out.println(temp.size());
      while (iterator.hasNext()) {
        int e1 = iterator.next();
        if ((e1 + 1) != temp.size()) {
          bw.write((e1 + 1) + "/" + temp.get(e1) + ";");
        } else {
          bw.write((e1 + 1) + "/" + temp.get(e1));
        }
      }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
      return false;
    }
    long endTime = System.currentTimeMillis();
    System.out.println("It takes "+(endTime-startTime)+" millseconds to write it back.");
    System.out.println("write back successfully!");
    return true;
  }
  
  public boolean atomWriteBackB()
  {
    long startTime = System.currentTimeMillis();
    try (BufferedWriter bw =
        new BufferedWriter(new FileWriter("write-txt/SocialNetworkCircle.txt"))) {
      bw.write("ElementName ::= " + central.getName());
      bw.newLine();
      bw.write("NumberOfTracks ::= " + trackNumber2);
      bw.newLine();
      bw.write("NumberOfElectron ::= ");
      Map<Integer, Integer> temp = trackObjectNumber;
      Iterator<Integer> iterator = temp.keySet().iterator();
      // System.out.println(temp.size());
      while (iterator.hasNext()) {
        int e1 = iterator.next();
        if ((e1 + 1) != temp.size()) {
          bw.write((e1 + 1) + "/" + temp.get(e1) + ";");
        } else {
          bw.write((e1 + 1) + "/" + temp.get(e1));
        }
      }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
      return false;
    }
    long endTime = System.currentTimeMillis();
    System.out.println("It takes "+(endTime-startTime)+" millseconds to write it back.");
    System.out.println("write back successfully!");
    return true;
  }
  
  public boolean atomWriteBackC()
  {
    long startTime = System.currentTimeMillis();
    try (PrintWriter bw = new PrintWriter(new FileWriter("write-txt/SocialNetworkCircle.txt"))) {
      bw.write("ElementName ::= " + central.getName());
      bw.write("\r\n");
      bw.write("NumberOfTracks ::= " + trackNumber2);
      bw.write("\r\n");
      bw.write("NumberOfElectron ::= ");
      Map<Integer, Integer> temp = trackObjectNumber;
      Iterator<Integer> iterator = temp.keySet().iterator();
      // System.out.println(temp.size());
      while (iterator.hasNext()) {
        int e1 = iterator.next();
        if ((e1 + 1) != temp.size()) {
          bw.write((e1 + 1) + "/" + temp.get(e1) + ";");
        } else {
          bw.write((e1 + 1) + "/" + temp.get(e1));
        }
      }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
      return false;
    }
    long endTime = System.currentTimeMillis();
    System.out.println("It takes "+(endTime-startTime)+" millseconds to write it back.");
    System.out.println("write back successfully!");
    return true;
  }
}
