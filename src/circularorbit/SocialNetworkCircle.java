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
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.*;
import com.sun.source.tree.Tree;
import abstractfactory.SocialFactory;
import apis.CircularOrbitApis;
import applications.*;
import centralobject.L1;
import centralobject.SocialL1;
import mylog.MyFormatter;
import physicalobject.SocialE1;
import physicalobject.TrackE1;

public class SocialNetworkCircle extends ConcreteCircularObject<SocialL1, SocialE1> {

  // 由于接口和一些测试只针对ConcreteCircularOrbits,所以socialTie写在了父类中
  // 两个人不可能同名，因为socialTie中只写了姓名
  // socialTie的作用是存储社交图中所有的边，包括与中心点人之间的边
  private final Map<String, SocialE1> friend = new HashMap<String, SocialE1>();// 通过名字找到人
  private final Map<String, Map<String, Float>> intimacyFriend =
      new HashMap<String, Map<String, Float>>();// 存储轨道人间的亲密度
  private final Map<String, Float> superIntimacyFriend = new HashMap<String, Float>();
  // 存储与中心人的亲密度
  private final Map<String, Boolean> ifMarked = new HashMap<String, Boolean>();
  private final List<String> temp1 = new ArrayList<String>();// 存储例外情况
  private final List<String> temp2 = new ArrayList<String>();// 存储例外情况
  private final List<Float> temp3 = new ArrayList<Float>();// 存储例外情况
  private SocialFactory factory = new SocialFactory();// 工厂方法
  // private static Logger log = java.util.logging.Logger.getLogger("socialLog");
  /// private final CircularOrbitAPIs<socialL1,socialE1> api = new
  // CircularOrbitAPIs<socialL1,socialE1>();
  // Abstraction function:
  // 具体实现社交网络系统，从文件中读入并建立轨道，
  // 采用不同的数据结构存储轨道物体、中心物体、轨道物体与中心物体之间的关系、中心物体与轨道物体之间的关系，
  // 并实现增减轨道物体、增减轨道、增减关系、轨道物体跃迁等功能
  // Representation invariant:
  // 轨道物体，轨道，中心点物体都是不可变的，通过具体的数据结构建立它们之间的联系,
  // 不管社交关系如何增加或删除，第 i 层轨道上的人与中心点的人之间的最短路径等于 i。
  // Safety from rep exposure:
  // 所有对象的类型是private final的,在返回重要数据结构时，做了防御性克隆

  /**
   * 检查SocialNetworkCircle类的表示不变性.
   */
  public void checkRep() {
    // 应实验3的要求,把检查轨道的合法性部分防在了application中,
    // 而application中的各个函数需要被main()函数调用,因此写成static,
    // 此处正好可以直接调用,这说明把方法写成static有时是有好处的
    // assertTrue(socialTie != null);
  }

  /**
   * 无前置条件.
   * 
   * @return 存储friend的集合.
   */
  public Map<String, SocialE1> getFriend() {
    Map<String, SocialE1> tempFriend = new HashMap<String, SocialE1>();
    Iterator<String> iterator = friend.keySet().iterator();
    while (iterator.hasNext()) {
      String temp = iterator.next();
      tempFriend.put(temp, friend.get(temp));
    }
    log.info("Social:assert returned Friend's size()!=0,in class socialNetwork,method getFriend");
    assert tempFriend.size() != 0 : "friend is empty";
    // 如果没有friend那么社交网络也没有意义
    log.info("Social:return friend set");
    return tempFriend;
  }


  /**
   * 清空该清空的集合.
   */
  public void clear() {
    physical.clear();
    // physical其实是第一个应该删除的，因为它直接决定了轨道的编号，轨道的编号决定了trackObject能否正常使用
    angle.clear();
    objectTrack.clear();
    trackObject.clear();
    lerelationship.clear();
    // relationship.clear();
    Iterator<SocialE1> iterator = relationship.keySet().iterator();
    while (iterator.hasNext()) {
      relationship.get(iterator.next()).clear();
    }
    temp1.clear();
    temp2.clear();
    temp3.clear();
    superIntimacyFriend.clear();
    Iterator<String> iterator2 = intimacyFriend.keySet().iterator();
    while (iterator2.hasNext()) {
      intimacyFriend.get(iterator2.next()).clear();
    }
    log.info("Social:everything is clear up,ready for creating a new track system");
  }

  // 覆盖了父类中的方法
  /**
   * 向特定轨道上增加一个人，要求输入的轨道编号在范围内.
   * 
   * @param ob 要增加的人.
   * @param t 轨道数目.
   * @return 如果添加成功则返回true,否则返回false并提示错误信息.
   */
  public boolean addTrackObject(SocialE1 ob, int t) { // 向特定轨道上增加一个物体（不考虑物理位置）
    // 参数来自外部，通过异常来处理前置条件，不用if是为了提高程序的可扩展性
    try {
      if (t >= physical.size()) {
        throw new Exception("the track is out of bound!");
      }
      if (!trackObject.containsKey(t)) {
        // 必须要考虑到删除轨道之后的情况，轨道的编号是一个萝卜一个坑
        throw new Exception("the track has been deleted!");
      }
    } catch (Exception e) {
      log.warning("Social:exception happens in class socialNetwork,method addTrackObject:"
          + e.getMessage() + " handling:return false,exception message is printed on console");
      System.out.println(e.getMessage());
      return false;
      // TODO: handle exception
    }

    trackObject.get(t).add(ob);// 设置轨道到物体的映射
    objectTrack.put(ob, t);// 设置物体到轨道的映射
    angle.put(ob, 0.00);// 初始时角度为0
    // 加物体的时候也需要对friend进行更改
    friend.put(ob.getName(), ob);
    log.info("Social:" + ob.getName() + "is added to track " + t);
    // addLErelationship(ob);
    // 向轨道上增加物体，该物体与中心物体不一定直接相关，例如社交网络中两层及以上的人与中心物体就没有明显关系
    // System.out.println("Succeed!");
    return true;

  }

  // 覆盖了父类中的方法
  /**
   * 删除一条轨道，要求输入的轨道编号在轨道系统的界限内.
   * 
   * @param number 轨道编号.
   */
  public void deleteTrack(int number) { // 去除一条轨道,输入轨道在列表中的编号
    try {
      if (number >= physical.size()) {
        throw new Exception("The track is out of bound!");
      }
    } catch (Exception e) {
      // TODO: handle exception
      log.warning("Social:exception happens in class socialNetwork,method deleteTrack:"
          + e.getMessage() + " handling:return false,exception message is printed on console");
      System.out.println(e.getMessage());
      return;
    }
    // physical.remove(number);//physical是控制轨道编号的，只能增加轨道，不能减少轨道
    // System.out.println(1);
    // System.out.println(trackObject.size());
    Iterator<SocialE1> iterator = trackObject.get(number).iterator();
    while (iterator.hasNext()) {
      SocialE1 temp = iterator.next();
      if (friend.containsKey(temp.getName())) {
        friend.remove(temp.getName());
      }
      for (int i = 0; i < socialTie.size(); i++) {
        if (socialTie.get(i).getName1().equals(temp.getName())
            || socialTie.get(i).getName2().equals(temp.getName())) {
          socialTie.remove(i);
          i--;// 删除一个元素之后，后面的元素会往前移动
        }
      }
    }

    log.info("Social:track " + number + " is deleted");
    clear();// 清空有关集合(关键步骤)
    creatingTrack();// 重建轨道
    // 此处是重新建图的过程，不能用checkRep()
  }

  // 覆盖了父类中的方法
  /**
   * 向特定轨道上删除一个物体，要求输入的轨道编号在轨道系统的范围之内.
   * 
   * @param ob 轨道上的人
   * @param t 轨道编号
   * @return 如果删除人成功则返回true，否则返回false并提示错误信息
   */
  public boolean deleteTrackObject(SocialE1 ob, int t) { // 向特定轨道上删除一个物体（不考虑物理位置）
    // 参数来自外部，用异常处理前置条件，代替if以提高程序的可扩展性
    try {
      if (t >= physical.size()) {
        throw new Exception("the track is out of bound!");
      }
      if (!trackObject.containsKey(t)) {
        throw new Exception("the track has been deleted!");
      }
      if (!trackObject.get(t).contains(ob)) {
        throw new Exception("The object is not in orbit");
      }
    } catch (Exception e) {
      // TODO: handle exception
      log.warning("Social:exception happens in class socialNetwork,method deleteTrackObject:"
          + e.getMessage() + " handling:return false,exception message is printed on console");
      System.out.println("The object is not in orbit");
      return false;
    }
    if (friend.containsKey(ob.getName())) {
      friend.remove(ob.getName());
    }
    for (int i = 0; i < socialTie.size(); i++) {
      if (socialTie.get(i).getName1().equals(ob.getName())
          || socialTie.get(i).getName2().equals(ob.getName())) {
        socialTie.remove(i);
        i--;// 删除一个元素之后，不可以退出，因为不止一个socialTie里含有它
      }
    }
    log.info("Social:" + ob.getName() + " is deleted in track " + t);
    clear();// 清空有关集合(关键步骤)
    creatingTrack();// 重建轨道
    // 重新求图结构，不能用checkRep
    return true;

  }

  // 已经在接口中声明过
  /**
   * 读取已知文件的内容，获得建立轨道系统所需的数据.
   */
  public boolean creatingTrackFromFiles(String name) { // 曾经忘了关文件
    // 异常本身被处理的越早越好，抛的次数越多，风险越大

    // 先把所有的匹配模式设置好
    boolean ifCheck = false;
    log.info(
        "Social:assert file name isn't null,in class socialNetwork,method creatingTrackFromFiles");
    assert name.length() != 0 : "file name is empty";
    String re1 = "(CentralUser)"; // Word 1
    String re2 = "(\\s*)"; // White Space 1
    String re3 = "(:)"; // Any Single Character 1
    String re4 = "(:)"; // Any Single Character 2
    String re5 = "(=)"; // Any Single Character 3
    String re6 = "(\\s*)"; // White Space 2
    String re7 = "(<)"; // Any Single Character 4
    String re8 = "((?:[a-z][a-z]*[0-9]*[a-z0-9]*))"; // Word 2
    String re9 = "(,)"; // Any Single Character 5
    String re10 = "(\\s*)";
    // String re11 = "([-+]\\d+)"; // Integer Number 1
    String re11 = "(-?\\d+)"; // Integer Number 1
    String re12 = "(,)"; // Any Single Character 6
    String re13 = "(\\s*)";
    String re14 = "([a-z])"; // Any Single Word Character (Not Whitespace) 2
    String re15 = "(>)"; // Any Single Character 7
    Pattern p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7 + re8 + re9 + re10 + re11
        + re12 + re13 + re14 + re15, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String fre1 = "(Friend)"; // Word 1
    String fre2 = "(\\s*)"; // White Space 1
    String fre3 = "(:)"; // Any Single Character 1
    String fre4 = "(:)"; // Any Single Character 2
    String fre5 = "(=)"; // Any Single Character 3
    String fre6 = "(\\s*)"; // White Space 2
    String fre7 = "(<)"; // Any Single Character 4
    String fre8 = "((?:[a-z][a-z]*[0-9]*[a-z0-9]*))"; // Alphanum 1
    String fre9 = "(,)"; // Any Single Character 5
    String fre10 = "(\\s*)"; // White Space 1
    String fre11 = "(-?\\d+)"; // Integer Number 1
    String fre12 = "(,)"; // Any Single Character 6
    String fre13 = "(\\s*)"; // White Space 1
    String fre14 = "([a-z])"; // Any Single Word Character (Not Whitespace) 1
    String fre15 = "(>)"; // Any Single Character 7
    Pattern fp = Pattern.compile(fre1 + fre2 + fre3 + fre4 + fre5 + fre6 + fre7 + fre8 + fre9
        + fre10 + fre11 + fre12 + fre13 + fre14 + fre15, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String sre1 = "(SocialTie)"; // Word 1
    String sre2 = "(\\s*)"; // White Space 1
    String sre3 = "(:)"; // Any Single Character 1
    String sre4 = "(:)"; // Any Single Character 2
    String sre5 = "(=)"; // Any Single Character 3
    String sre6 = "(\\s*)"; // White Space 2
    String sre7 = "(<)"; // Any Single Character 4
    String sre8 = "((?:[a-z][a-z]*[0-9]*[a-z0-9]*))";
    String sre9 = "(,)"; // Any Single Character 5
    String sre10 = "(\\s*)";
    String sre11 = "((?:[a-z][a-z]*[0-9]*[a-z0-9]*))"; // Alphanum 2
    String sre12 = "(,)"; // Any Single Character 6
    String sre13 = "(\\s*)";
    // 放宽了正则表达式的要求，亲密度可正可负
    // String sre14 = "([01](?:\\.{1}\\d{1,3}){0,1})"; // Float 1,限制小数位数
    String sre14 = "([+-]?\\d*\\.\\d+)(?![-+0-9\\.])"; // Float 1
    String sre15 = "(>)"; // Any Single Character 7
    Pattern normalSp = Pattern.compile(sre1 + sre2 + sre3 + sre4 + sre5 + sre6 + sre7 + sre8 + sre9
        + sre10 + sre11 + sre12 + sre13 + sre14 + sre15, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String txt = new String();
    long startTime = System.currentTimeMillis();
    try (BufferedReader in =
        new BufferedReader(new InputStreamReader(new FileInputStream("txt/" + name + "txt")))) {
      while ((txt = in.readLine()) != null) {
        // System.out.println("start again");
        Matcher m = p.matcher(txt);
        Matcher normalSm = normalSp.matcher(txt);
        Matcher fm = fp.matcher(txt);

        // 匹配到中心人
        if (m.find()) { // 只会find一次
          // System.out.println("central find");
          String word1 = m.group(1);
          String c1 = m.group(2);
          String c2 = m.group(3);
          String c3 = m.group(4);
          String ws2 = m.group(5);
          String c4 = m.group(6);
          String word2 = m.group(7);
          String c5 = m.group(8);// 姓名

          String int1 = m.group(9);
          String c6 = m.group(10);
          String w2 = m.group(11);// 年龄
          String c7 = m.group(12);
          String c8 = m.group(14);// 性别
          if (ifCheck) {
            try {
              // 年龄必须为正
              if (Integer.parseInt(w2) < 0) {
                throw new Exception(
                    "the people " + c5 + "'s age " + Integer.parseInt(w2) + " is negative");
                // throw new Exception("age");
              }
              // 性别在m|f中
              if (!(c8.equals("M") || c8.equals("m") || c8.equals("F") || c8.equals("f"))) {

                throw new Exception("the sex " + c8 + " is illegal");
              }

            } catch (Exception e) {
              log.warning(
                  "Social:exception happens in class socialNetwork,method creatingTrackFromFiles:"
                      + e.getMessage()
                      + " handling:return false,exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          central = factory.manufactureL(c5, Integer.parseInt(w2), c8.charAt(0));// 构造中心点物体
        }

        // 匹配到Friend
        if (fm.find()) { // 不止匹配一次
          // System.out.println("friend find");
          String fword1 = fm.group(1);
          String fws1 = fm.group(2);
          String fc1 = fm.group(3);
          String fc2 = fm.group(4);
          String fc3 = fm.group(5);
          String fws2 = fm.group(6);
          String fc4 = fm.group(7);
          String falphanum1 = fm.group(8);// 姓名

          String fc5 = fm.group(9);
          String fint1 = fm.group(10);

          String ffc6 = fm.group(11);// 年龄
          String fw1 = fm.group(12);

          String fc7 = fm.group(14);// 性别
          if (ifCheck) {
            try {
              // 年龄必须为正
              if (Integer.parseInt(ffc6) < 0) {
                throw new Exception("statements do not conform to syntax rules:the age "
                    + Integer.parseInt(ffc6) + " is negative");
              }
              // 性别在m|f中
              if (!(fc7.equals("M") || fc7.equals("m") || fc7.equals("F") || fc7.equals("f"))) {
                throw new Exception(
                    "statements do not conform to syntax rules:the sex " + fc7 + " is illegal");
              }
            } catch (Exception e) {
              log.warning(
                  "Social:exception happens in class socialNetwork,method creatingTrackFromFiles:"
                      + e.getMessage()
                      + " handling:return false,exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          SocialE1 tempFriend =
              factory.manufactureE(falphanum1, Integer.parseInt(ffc6), fc7.charAt(0));
          // new socialE1(falphanum1, Integer.parseInt(ffc6), fc7.charAt(0));
          friend.put(falphanum1, tempFriend);// 通过名字找到人，初始化friend映射
          // socialNetWork重写了父类中的方法
          relationship.put(tempFriend, new HashSet<SocialE1>());// 匹配的同时对“关系”进行初始化,可视化时需要使用
          intimacyFriend.put(falphanum1, new HashMap<String, Float>());// 初始化映射
          // intimacyFriend和socialTie冲突
        }

        // 匹配到socialTie
        if (normalSm.find()) {
          // System.out.println("socialTie find");
          String normalSalphanum1 = normalSm.group(8);// person1
          String normalSalphanum2 = normalSm.group(11);// person2
          if (ifCheck) {
            try {
              if (normalSalphanum1.equals(normalSalphanum2)) {
                throw new Exception("same elements:The same name " + normalSalphanum1);
              }
              // 以下逻辑需要在整个文件都读完后再判断
              // if(!(friend.containsKey(normalSalphanum1)))
              // {
              // throw new Exception("Friend doesn't contains "+normalSalphanum1);
              // }
              // if(!(friend.containsKey(normalSalphanum2)))
              // {
              // throw new Exception("Friend doesn't contains "+normalSalphanum2);
              // }
            } catch (Exception e) {
              log.warning(
                  "Social:exception happens in class socialNetwork,method creatingTrackFromFiles:"
                      + e.getMessage()
                      + " handling:return false,exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }


          String normalSfloat1 = normalSm.group(14);// 亲密度
          if (ifCheck) {
            try {
              if (Float.parseFloat(normalSfloat1) > 1) {
                throw new Exception(
                    "statements do not conform to syntax rules:socialTie is larger than 1:"
                        + normalSfloat1);
              }
              if (Float.parseFloat(normalSfloat1) < 0) {
                throw new Exception(
                    "statements do not conform to syntax rules:socialTie is less than 0:"
                        + normalSfloat1);
              }
              // 已经保证亲密度在01之间
              if (normalSfloat1.length() > 5) {
                throw new Exception("statements do not conform to syntax rules:illegal socialTie: "
                    + normalSfloat1);
              }
            } catch (Exception e) {
              // TODO: handle exception
              log.warning(
                  "Social:exception happens in class socialNetwork,method creatingTrackFromFiles:"
                      + e.getMessage()
                      + " handling:return false,exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
            }
          }

          Tie temp = new Tie(normalSalphanum1, normalSalphanum2, Float.parseFloat(normalSfloat1));
          if (ifCheck) {
            for (int k = 0; k < socialTie.size(); k++) {
              // 出现两条相同的社交关系，这个不需要在把文件都读完之后再处理,但是在
              // 读大文件的时候会让复杂度上升到平方级别,这个是最拖慢速度的一环，只
              // 能在读小文件的时候采用这种检查方法
              try {
                if (socialTie.get(k).tieEquals(temp)) {
                  throw new Exception(
                      "same elements:socialnetwork " + temp.getName1() + "->" + temp.getName2());
                }
              } catch (Exception e) {
                log.warning(
                    "Social:exception happens in class socialNetwork,method creatingTrackFromFiles:"
                        + e.getMessage()
                        + " handling:return false,exception message is printed on console");
                System.out.println(e.getMessage());
                return false;
                // TODO: handle exception
              }
            }
          }
          socialTie.add(temp);// socialTie中存储关系
        }
      }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }

    // 读大文件的时候会拖慢速度
    if (ifCheck) {
      for (int j = 0; j < socialTie.size(); j++) {

        try {
          if (!(friend.containsKey(socialTie.get(j).getName1()))
              && !(socialTie.get(j).getName1().equals(central.getName()))) {
            // 抛出异常之后后面的语句就不会再执行了
            throw new Exception("incorrect dependencies:" + socialTie.get(j).getName1()
                + " appears in socialTie while doesn't appear in Friend");
          }
          if (!(friend.containsKey(socialTie.get(j).getName2()))
              && !(socialTie.get(j).getName2().equals(central.getName()))) {
            throw new Exception("incorrect dependencies:" + socialTie.get(j).getName2()
                + " appears in socialTie while doesn't appear in Friend");
          }
        } catch (Exception e) {
          log.warning(
              "Social:exception happens in class socialNetwork,method creatingTrackFromFiles:"
                  + e.getMessage()
                  + " handling:return false,exception message is printed on console");
          System.out.println(e.getMessage());
          return false;
          // TODO: handle exception
        }
      }
    }
    log.info("Social:file " + name + " is read in");
    long endTime = System.currentTimeMillis();
    System.out.println("It takes " + (endTime - startTime) + " millisecond to read the file.");
    return true;
  }

  public boolean creatingTrackFromFilesFile(String name) {
    boolean ifCheck = false;
    log.info(
        "Social:assert file name isn't null,in class socialNetwork,method creatingTrackFromFiles");
    assert name.length() != 0 : "file name is empty";
    String re1 = "(CentralUser)"; // Word 1
    String re2 = "(\\s*)"; // White Space 1
    String re3 = "(:)"; // Any Single Character 1
    String re4 = "(:)"; // Any Single Character 2
    String re5 = "(=)"; // Any Single Character 3
    String re6 = "(\\s*)"; // White Space 2
    String re7 = "(<)"; // Any Single Character 4
    String re8 = "((?:[a-z][a-z]*[0-9]*[a-z0-9]*))"; // Word 2
    String re9 = "(,)"; // Any Single Character 5
    String re10 = "(\\s*)";
    // String re11 = "([-+]\\d+)"; // Integer Number 1
    String re11 = "(-?\\d+)"; // Integer Number 1
    String re12 = "(,)"; // Any Single Character 6
    String re13 = "(\\s*)";
    String re14 = "([a-z])"; // Any Single Word Character (Not Whitespace) 2
    String re15 = "(>)"; // Any Single Character 7
    Pattern p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7 + re8 + re9 + re10 + re11
        + re12 + re13 + re14 + re15, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String fre1 = "(Friend)"; // Word 1
    String fre2 = "(\\s*)"; // White Space 1
    String fre3 = "(:)"; // Any Single Character 1
    String fre4 = "(:)"; // Any Single Character 2
    String fre5 = "(=)"; // Any Single Character 3
    String fre6 = "(\\s*)"; // White Space 2
    String fre7 = "(<)"; // Any Single Character 4
    String fre8 = "((?:[a-z][a-z]*[0-9]*[a-z0-9]*))"; // Alphanum 1
    String fre9 = "(,)"; // Any Single Character 5
    String fre10 = "(\\s*)"; // White Space 1
    String fre11 = "(-?\\d+)"; // Integer Number 1
    String fre12 = "(,)"; // Any Single Character 6
    String fre13 = "(\\s*)"; // White Space 1
    String fre14 = "([a-z])"; // Any Single Word Character (Not Whitespace) 1
    String fre15 = "(>)"; // Any Single Character 7
    Pattern fp = Pattern.compile(fre1 + fre2 + fre3 + fre4 + fre5 + fre6 + fre7 + fre8 + fre9
        + fre10 + fre11 + fre12 + fre13 + fre14 + fre15, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String sre1 = "(SocialTie)"; // Word 1
    String sre2 = "(\\s*)"; // White Space 1
    String sre3 = "(:)"; // Any Single Character 1
    String sre4 = "(:)"; // Any Single Character 2
    String sre5 = "(=)"; // Any Single Character 3
    String sre6 = "(\\s*)"; // White Space 2
    String sre7 = "(<)"; // Any Single Character 4
    String sre8 = "((?:[a-z][a-z]*[0-9]*[a-z0-9]*))";
    String sre9 = "(,)"; // Any Single Character 5
    String sre10 = "(\\s*)";
    String sre11 = "((?:[a-z][a-z]*[0-9]*[a-z0-9]*))"; // Alphanum 2
    String sre12 = "(,)"; // Any Single Character 6
    String sre13 = "(\\s*)";
    // 放宽了正则表达式的要求，亲密度可正可负
    // String sre14 = "([01](?:\\.{1}\\d{1,3}){0,1})"; // Float 1,限制小数位数
    String sre14 = "([+-]?\\d*\\.\\d+)(?![-+0-9\\.])"; // Float 1
    String sre15 = "(>)"; // Any Single Character 7
    Pattern normalSp = Pattern.compile(sre1 + sre2 + sre3 + sre4 + sre5 + sre6 + sre7 + sre8 + sre9
        + sre10 + sre11 + sre12 + sre13 + sre14 + sre15, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    long startTime = System.currentTimeMillis();
    try (BufferedReader reader = new BufferedReader(new FileReader("txt/" + name + "txt"))) {
      String txt;
      while ((txt = reader.readLine()) != null) {
        Matcher m = p.matcher(txt);
        Matcher normalSm = normalSp.matcher(txt);
        Matcher fm = fp.matcher(txt);

        // 匹配到中心人
        if (m.find()) { // 只会find一次
          // System.out.println("central find");
          String word1 = m.group(1);
          String c1 = m.group(2);
          String c2 = m.group(3);
          String c3 = m.group(4);
          String ws2 = m.group(5);
          String c4 = m.group(6);
          String word2 = m.group(7);
          String c5 = m.group(8);// 姓名

          String int1 = m.group(9);
          String c6 = m.group(10);
          String w2 = m.group(11);// 年龄
          String c7 = m.group(12);
          String c8 = m.group(14);// 性别
          if (ifCheck) {
            try {
              // 年龄必须为正
              if (Integer.parseInt(w2) < 0) {
                throw new Exception(
                    "the people " + c5 + "'s age " + Integer.parseInt(w2) + " is negative");
                // throw new Exception("age");
              }
              // 性别在m|f中
              if (!(c8.equals("M") || c8.equals("m") || c8.equals("F") || c8.equals("f"))) {

                throw new Exception("the sex " + c8 + " is illegal");
              }

            } catch (Exception e) {
              log.warning(
                  "Social:exception happens in class socialNetwork,method creatingTrackFromFiles:"
                      + e.getMessage()
                      + " handling:return false,exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          central = factory.manufactureL(c5, Integer.parseInt(w2), c8.charAt(0));// 构造中心点物体
        }

        // 匹配到Friend

        if (fm.find()) { // 不止匹配一次
          // System.out.println("friend find");
          String fword1 = fm.group(1);
          String fws1 = fm.group(2);
          String fc1 = fm.group(3);
          String fc2 = fm.group(4);
          String fc3 = fm.group(5);
          String fws2 = fm.group(6);
          String fc4 = fm.group(7);
          String falphanum1 = fm.group(8);// 姓名

          String fc5 = fm.group(9);
          String fint1 = fm.group(10);

          String ffc6 = fm.group(11);// 年龄
          String fw1 = fm.group(12);

          String fc7 = fm.group(14);// 性别
          if (ifCheck) {
            try {
              // 年龄必须为正
              if (Integer.parseInt(ffc6) < 0) {
                throw new Exception("statements do not conform to syntax rules:the age "
                    + Integer.parseInt(ffc6) + " is negative");
              }
              // 性别在m|f中
              if (!(fc7.equals("M") || fc7.equals("m") || fc7.equals("F") || fc7.equals("f"))) {
                throw new Exception(
                    "statements do not conform to syntax rules:the sex " + fc7 + " is illegal");
              }
            } catch (Exception e) {
              log.warning(
                  "Social:exception happens in class socialNetwork,method creatingTrackFromFiles:"
                      + e.getMessage()
                      + " handling:return false,exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          SocialE1 tempFriend =
              factory.manufactureE(falphanum1, Integer.parseInt(ffc6), fc7.charAt(0));
          // new socialE1(falphanum1, Integer.parseInt(ffc6), fc7.charAt(0));
          friend.put(falphanum1, tempFriend);// 通过名字找到人，初始化friend映射
          // socialNetWork重写了父类中的方法
          relationship.put(tempFriend, new HashSet<SocialE1>());// 匹配的同时对“关系”进行初始化
          intimacyFriend.put(falphanum1, new HashMap<String, Float>());// 初始化映射
        }

        // 匹配到socialTie
        if (normalSm.find()) {
          // System.out.println("socialTie find");
          String normalSalphanum1 = normalSm.group(8);// person1
          String normalSalphanum2 = normalSm.group(11);// person2
          if (ifCheck) {
            try {
              if (normalSalphanum1.equals(normalSalphanum2)) {
                throw new Exception("same elements:The same name " + normalSalphanum1);
              }

            } catch (Exception e) {
              log.warning(
                  "Social:exception happens in class socialNetwork,method creatingTrackFromFiles:"
                      + e.getMessage()
                      + " handling:return false,exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          // catch (notDefinedInFriendException e) {
          // System.out.println("some people not mentioned in Friend appears in SocialTie,th
          // information is "+e.getMessage());
          // return false;
          // // TODO: handle exception
          // }
          String normalSfloat1 = normalSm.group(14);// 亲密度
          if (ifCheck) {
            try {
              if (Float.parseFloat(normalSfloat1) > 1) {
                throw new Exception(
                    "statements do not conform to syntax rules:socialTie is larger than 1:"
                        + normalSfloat1);
              }
              if (Float.parseFloat(normalSfloat1) < 0) {
                throw new Exception(
                    "statements do not conform to syntax rules:socialTie is less than 0:"
                        + normalSfloat1);
              }
              // 已经保证亲密度在01之间
              if (normalSfloat1.length() > 5) {
                throw new Exception("statements do not conform to syntax rules:illegal socialTie: "
                    + normalSfloat1);
              }
            } catch (Exception e) {
              // TODO: handle exception
              log.warning(
                  "Social:exception happens in class socialNetwork,method creatingTrackFromFiles:"
                      + e.getMessage()
                      + " handling:return false,exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
            }
          }
          Tie temp = new Tie(normalSalphanum1, normalSalphanum2, Float.parseFloat(normalSfloat1));
          if (ifCheck) {
            for (int k = 0; k < socialTie.size(); k++) {
              // 出现两条相同的社交关系，这个不需要在把文件都读完之后再处理
              try {
                if (socialTie.get(k).tieEquals(temp)) {
                  throw new Exception(
                      "same elements:socialnetwork " + temp.getName1() + "->" + temp.getName2());
                }
              } catch (Exception e) {
                log.warning(
                    "Social:exception happens in class socialNetwork,method creatingTrackFromFiles:"
                        + e.getMessage()
                        + " handling:return false,exception message is printed on console");
                System.out.println(e.getMessage());
                return false;
                // TODO: handle exception
              }

            }
          }
          socialTie.add(temp);// socialTie中存储关系
        }
      }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    if (ifCheck) {
      for (int j = 0; j < socialTie.size(); j++) {
        try {
          if (!(friend.containsKey(socialTie.get(j).getName1()))
              && !(socialTie.get(j).getName1().equals(central.getName()))) {
            // 抛出异常之后后面的语句就不会再执行了
            throw new Exception("incorrect dependencies:" + socialTie.get(j).getName1()
                + " appears in socialTie while doesn't appear in Friend");
          }
          if (!(friend.containsKey(socialTie.get(j).getName2()))
              && !(socialTie.get(j).getName2().equals(central.getName()))) {
            throw new Exception("incorrect dependencies:" + socialTie.get(j).getName2()
                + " appears in socialTie while doesn't appear in Friend");
          }
        } catch (Exception e) {
          // TODO: handle exception
          log.warning(
              "Social:exception happens in class socialNetwork,method creatingTrackFromFiles:"
                  + e.getMessage()
                  + " handling:return false,exception message is printed on console");
          System.out.println(e.getMessage());
          return false;
        }
      }
    }
    long endTime = System.currentTimeMillis();
    log.info("Social:file " + name + " is read in");
    System.out.println("It takes " + (endTime - startTime) + " millisecond to read the file.");
    return true;
  }


  public boolean creatingTrackFromFilesScanner(String name) {
    boolean ifCheck = false;
    log.info(
        "Social:assert file name isn't null,in class socialNetwork,method creatingTrackFromFiles");
    assert name.length() != 0 : "file name is empty";
    String re1 = "(CentralUser)"; // Word 1
    String re2 = "(\\s*)"; // White Space 1
    String re3 = "(:)"; // Any Single Character 1
    String re4 = "(:)"; // Any Single Character 2
    String re5 = "(=)"; // Any Single Character 3
    String re6 = "(\\s*)"; // White Space 2
    String re7 = "(<)"; // Any Single Character 4
    String re8 = "((?:[a-z][a-z]*[0-9]*[a-z0-9]*))"; // Word 2
    String re9 = "(,)"; // Any Single Character 5
    String re10 = "(\\s*)";
    // String re11 = "([-+]\\d+)"; // Integer Number 1
    String re11 = "(-?\\d+)"; // Integer Number 1
    String re12 = "(,)"; // Any Single Character 6
    String re13 = "(\\s*)";
    String re14 = "([a-z])"; // Any Single Word Character (Not Whitespace) 2
    String re15 = "(>)"; // Any Single Character 7
    Pattern p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7 + re8 + re9 + re10 + re11
        + re12 + re13 + re14 + re15, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String fre1 = "(Friend)"; // Word 1
    String fre2 = "(\\s*)"; // White Space 1
    String fre3 = "(:)"; // Any Single Character 1
    String fre4 = "(:)"; // Any Single Character 2
    String fre5 = "(=)"; // Any Single Character 3
    String fre6 = "(\\s*)"; // White Space 2
    String fre7 = "(<)"; // Any Single Character 4
    String fre8 = "((?:[a-z][a-z]*[0-9]*[a-z0-9]*))"; // Alphanum 1
    String fre9 = "(,)"; // Any Single Character 5
    String fre10 = "(\\s*)"; // White Space 1
    String fre11 = "(-?\\d+)"; // Integer Number 1
    String fre12 = "(,)"; // Any Single Character 6
    String fre13 = "(\\s*)"; // White Space 1
    String fre14 = "([a-z])"; // Any Single Word Character (Not Whitespace) 1
    String fre15 = "(>)"; // Any Single Character 7
    Pattern fp = Pattern.compile(fre1 + fre2 + fre3 + fre4 + fre5 + fre6 + fre7 + fre8 + fre9
        + fre10 + fre11 + fre12 + fre13 + fre14 + fre15, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String sre1 = "(SocialTie)"; // Word 1
    String sre2 = "(\\s*)"; // White Space 1
    String sre3 = "(:)"; // Any Single Character 1
    String sre4 = "(:)"; // Any Single Character 2
    String sre5 = "(=)"; // Any Single Character 3
    String sre6 = "(\\s*)"; // White Space 2
    String sre7 = "(<)"; // Any Single Character 4
    String sre8 = "((?:[a-z][a-z]*[0-9]*[a-z0-9]*))";
    String sre9 = "(,)"; // Any Single Character 5
    String sre10 = "(\\s*)";
    String sre11 = "((?:[a-z][a-z]*[0-9]*[a-z0-9]*))"; // Alphanum 2
    String sre12 = "(,)"; // Any Single Character 6
    String sre13 = "(\\s*)";
    // 放宽了正则表达式的要求，亲密度可正可负
    // String sre14 = "([01](?:\\.{1}\\d{1,3}){0,1})"; // Float 1,限制小数位数
    String sre14 = "([+-]?\\d*\\.\\d+)(?![-+0-9\\.])"; // Float 1
    String sre15 = "(>)"; // Any Single Character 7
    Pattern normalSp = Pattern.compile(sre1 + sre2 + sre3 + sre4 + sre5 + sre6 + sre7 + sre8 + sre9
        + sre10 + sre11 + sre12 + sre13 + sre14 + sre15, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    long startTime = System.currentTimeMillis();
    try {
      File file = new File("txt/" + name + "txt");
      Scanner sc = new Scanner(file);
      String txt;
      while (sc.hasNextLine()) {
        txt = sc.nextLine();
        Matcher m = p.matcher(txt);
        Matcher normalSm = normalSp.matcher(txt);
        Matcher fm = fp.matcher(txt);

        // 匹配到中心人
        if (m.find()) { // 只会find一次
          // System.out.println("central find");
          String word1 = m.group(1);
          String c1 = m.group(2);
          String c2 = m.group(3);
          String c3 = m.group(4);
          String ws2 = m.group(5);
          String c4 = m.group(6);
          String word2 = m.group(7);
          String c5 = m.group(8);// 姓名

          String int1 = m.group(9);
          String c6 = m.group(10);
          String w2 = m.group(11);// 年龄
          String c7 = m.group(12);
          String c8 = m.group(14);// 性别
          if (ifCheck) {
            try {
              // 年龄必须为正
              if (Integer.parseInt(w2) < 0) {
                throw new Exception(
                    "the people " + c5 + "'s age " + Integer.parseInt(w2) + " is negative");
                // throw new Exception("age");
              }
              // 性别在m|f中
              if (!(c8.equals("M") || c8.equals("m") || c8.equals("F") || c8.equals("f"))) {

                throw new Exception("the sex " + c8 + " is illegal");
              }

            } catch (Exception e) {
              log.warning(
                  "Social:exception happens in class socialNetwork,method creatingTrackFromFiles:"
                      + e.getMessage()
                      + " handling:return false,exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          central = factory.manufactureL(c5, Integer.parseInt(w2), c8.charAt(0));// 构造中心点物体
        }

        // 匹配到Friend
        if (fm.find()) { // 不止匹配一次
          // System.out.println("friend find");
          String fword1 = fm.group(1);
          String fws1 = fm.group(2);
          String fc1 = fm.group(3);
          String fc2 = fm.group(4);
          String fc3 = fm.group(5);
          String fws2 = fm.group(6);
          String fc4 = fm.group(7);
          String falphanum1 = fm.group(8);// 姓名

          String fc5 = fm.group(9);
          String fint1 = fm.group(10);

          String ffc6 = fm.group(11);// 年龄
          String fw1 = fm.group(12);

          String fc7 = fm.group(14);// 性别
          if (ifCheck) {
            try {
              // 年龄必须为正
              if (Integer.parseInt(ffc6) < 0) {
                throw new Exception("statements do not conform to syntax rules:the age "
                    + Integer.parseInt(ffc6) + " is negative");
              }
              // 性别在m|f中
              if (!(fc7.equals("M") || fc7.equals("m") || fc7.equals("F") || fc7.equals("f"))) {
                throw new Exception(
                    "statements do not conform to syntax rules:the sex " + fc7 + " is illegal");
              }
            } catch (Exception e) {
              log.warning(
                  "Social:exception happens in class socialNetwork,method creatingTrackFromFiles:"
                      + e.getMessage()
                      + " handling:return false,exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          SocialE1 tempFriend =
              factory.manufactureE(falphanum1, Integer.parseInt(ffc6), fc7.charAt(0));
          // new socialE1(falphanum1, Integer.parseInt(ffc6), fc7.charAt(0));
          friend.put(falphanum1, tempFriend);// 通过名字找到人，初始化friend映射
          // socialNetWork重写了父类中的方法
          relationship.put(tempFriend, new HashSet<SocialE1>());// 匹配的同时对“关系”进行初始化
          intimacyFriend.put(falphanum1, new HashMap<String, Float>());// 初始化映射
        }

        // 匹配到socialTie
        if (normalSm.find()) {
          // System.out.println("socialTie find");
          String normalSalphanum1 = normalSm.group(8);// person1
          String normalSalphanum2 = normalSm.group(11);// person2
          if (ifCheck) {
            try {
              if (normalSalphanum1.equals(normalSalphanum2)) {
                throw new Exception("same elements:The same name " + normalSalphanum1);
              }

            } catch (Exception e) {
              log.warning(
                  "Social:exception happens in class socialNetwork,method creatingTrackFromFiles:"
                      + e.getMessage()
                      + " handling:return false,exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          // catch (notDefinedInFriendException e) {
          // System.out.println("some people not mentioned in Friend appears in SocialTie,th
          // information is "+e.getMessage());
          // return false;
          // // TODO: handle exception
          // }
          String normalSfloat1 = normalSm.group(14);// 亲密度
          if (ifCheck) {
            try {
              if (Float.parseFloat(normalSfloat1) > 1) {
                throw new Exception(
                    "statements do not conform to syntax rules:socialTie is larger than 1:"
                        + normalSfloat1);
              }
              if (Float.parseFloat(normalSfloat1) < 0) {
                throw new Exception(
                    "statements do not conform to syntax rules:socialTie is less than 0:"
                        + normalSfloat1);
              }
              // 已经保证亲密度在01之间
              if (normalSfloat1.length() > 5) {
                throw new Exception("statements do not conform to syntax rules:illegal socialTie: "
                    + normalSfloat1);
              }
            } catch (Exception e) {
              // TODO: handle exception
              log.warning(
                  "Social:exception happens in class socialNetwork,method creatingTrackFromFiles:"
                      + e.getMessage()
                      + " handling:return false,exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
            }
          }

          Tie temp = new Tie(normalSalphanum1, normalSalphanum2, Float.parseFloat(normalSfloat1));
          if (ifCheck) {
            for (int k = 0; k < socialTie.size(); k++) {
              // 出现两条相同的社交关系，这个不需要在把文件都读完之后再处理
              try {
                if (socialTie.get(k).tieEquals(temp)) {
                  throw new Exception(
                      "same elements:socialnetwork " + temp.getName1() + "->" + temp.getName2());
                }
              } catch (Exception e) {
                log.warning(
                    "Social:exception happens in class socialNetwork,method creatingTrackFromFiles:"
                        + e.getMessage()
                        + " handling:return false,exception message is printed on console");
                System.out.println(e.getMessage());
                return false;
                // TODO: handle exception
              }

            }
          }
          socialTie.add(temp);// socialTie中存储关系
        }
      }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    if (ifCheck) {
      // 出现在socialTie中，却没有出现在friend中
      for (int j = 0; j < socialTie.size(); j++) {
        try {
          if (!(friend.containsKey(socialTie.get(j).getName1()))
              && !(socialTie.get(j).getName1().equals(central.getName()))) {
            // 抛出异常之后后面的语句就不会再执行了
            throw new Exception("incorrect dependencies:" + socialTie.get(j).getName1()
                + " appears in socialTie while doesn't appear in Friend");
          }
          if (!(friend.containsKey(socialTie.get(j).getName2()))
              && !(socialTie.get(j).getName2().equals(central.getName()))) {
            throw new Exception("incorrect dependencies:" + socialTie.get(j).getName2()
                + " appears in socialTie while doesn't appear in Friend");
          }
        } catch (Exception e) {
          // TODO: handle exception
          log.warning(
              "Social:exception happens in class socialNetwork,method creatingTrackFromFiles:"
                  + e.getMessage()
                  + " handling:return false,exception message is printed on console");
          System.out.println(e.getMessage());
          return false;
        }
      }
    }
    long endTime = System.currentTimeMillis();
    log.info("Social:file " + name + " is read in");
    System.out.println("It takes " + (endTime - startTime) + " millisecond to read the file.");
    return true;
  }

  // intimacyFriend也有用，有些函数需要用到它
  // 检查会在读文件的时候进行处理，这里假定所接收的数据都是正确的
  // 就算出错，程序也不会崩溃
  // eerelationship中是不包含中心点物体的
  /**
   * 利用广度优先搜索建立轨道系统
   */
  public void creatingTrack() {
    // 对数据结构的进一步细化处理，由socialTie可以派生出很多东西
    long startTime = System.currentTimeMillis();
    long time1 = 0;
    long time2 = 0;
    long time3 = 0;
    long time4 = 0;
    //一下这一部分用了33秒
    //注：没有办法一边读文件一边把relationship建立起来，因为不知道中心点物体是什么
    int length = socialTie.size();
    for (Tie t:socialTie) {
      if (t.getName1().equals(central.getName())) {
        long startTime11 = System.currentTimeMillis();
        if (friend.containsKey(t.getName2())) {
          String tempName = t.getName2();
          lerelationship.add(friend.get(tempName));
          superIntimacyFriend.put(tempName, t.getIni());
        }
        long endTime11 = System.currentTimeMillis();
        time1 = time1 + endTime11 - startTime11;
      } else if (t.getName2().equals(central.getName())) {
        long startTime22 = System.currentTimeMillis();
        if (friend.containsKey(t.getName1())) {
          String tempName = t.getName1();
          lerelationship.add(friend.get(tempName));
          superIntimacyFriend.put(tempName, t.getIni());
        }
        long endTime22 = System.currentTimeMillis();
        time2 = time2 + endTime22 - startTime22;
      } else if (friend.containsKey(t.getName1())
          && friend.containsKey(t.getName2())) { // friend包含，intimacyFriend就一定包含,这个条件判断几乎不浪费时间
        long startTime33 = System.currentTimeMillis();
        String name1 = t.getName1();
        String name2 = t.getName2();
        float yes = t.getIni();
//        addEErelationship(friend.get(name1), friend.get(name2));
//        if (!relationship.get(friend.get(name1)).contains(friend.get(name2)))
//        {
          //if判断语句完全可以忽略，set会自动不加入相同的元素
          relationship.get(friend.get(name1)).add(friend.get(name2));
//        }
//        if (!relationship.get(friend.get(name2)).contains(friend.get(name1))) {
          relationship.get(friend.get(name2)).add(friend.get(name1));
//        }
        intimacyFriend.get(name1).put(name2, yes);
        intimacyFriend.get(name2).put(name1, yes);
        long endTime33 = System.currentTimeMillis();
        time3 = time3 + endTime33 - startTime33;
      } else {
       ;
      }
    }
    System.out.println("Time1 "+time1);//0.013秒
    System.out.println("Time2 "+time2);//0秒
    System.out.println("Time3 "+time3);//31秒
    long endTime1 = System.currentTimeMillis();
    //一下部分只用到了1.7s不到
    System.out.println("it takes " + (endTime1 - startTime) + " millseconds to creat track.");
    Iterator<String> iterator = superIntimacyFriend.keySet().iterator();
    List<String> people = new ArrayList<String>();
    while (iterator.hasNext()) {
      people.add(iterator.next());
    }
    int i = 0;
    addTrack();
    while (people.size() != 0) {
      String temp = people.get(0);
      people.remove(0);
      List<String> people2 = new ArrayList<String>();
      if (friend.containsKey(temp)) {
        SocialE1 tempPerson = friend.get(temp);
        addTrackObject(tempPerson, i);
        Iterator<SocialE1> iterator2 = relationship.get(tempPerson).iterator();
        while (iterator2.hasNext()) {
          SocialE1 temp2 = iterator2.next();
          if (!objectTrack.containsKey(temp2)) {
            people2.add(temp2.getName());
          }
        }
      } else {
        ;
      }
      if (people.size() == 0) {
        people = people2;
        if (people2.size() > 0) {
          addTrack();
          i++;
        }
      }
    }
    long endTime = System.currentTimeMillis();
    System.out.println("it takes " + (endTime - endTime1) + " millseconds to creat track.");
  }



  /**
   * 根据相应数据结构建立轨道，用checkRep检查后置条件.
   */
  public void creatingTrack2() {
    boolean isCheck = false;
    long startTime = System.currentTimeMillis();
    addTrack();// 建立第一条轨道，哪怕社交网络中一个人没有，也会默认建立一条轨道
    // 对list进行遍历
    int length1 = socialTie.size();
    for (int i = 0; i < length1; i++) {
      String normalSalphanum1 = socialTie.get(i).getName1();
      String normalSalphanum2 = socialTie.get(i).getName2();
      float normalSfloat1 = socialTie.get(i).getIni();

      if ((normalSalphanum1.equals(central.getName()))
          || (normalSalphanum2.equals(central.getName()))) {

        if (normalSalphanum1.equals(central.getName())) {

          SocialE1 person = friend.get(normalSalphanum2);
          if (!objectTrack.containsKey(person)) {
            // System.out.println("Central!");
            addTrackObject(person, 0);// 已经往objectTrack中加入了映射
            addLErelationship(person);
            superIntimacyFriend.put(normalSalphanum2, normalSfloat1);
          } else {
            // System.out.println("Central!");
            transit(person, 0);// 已经往objectTrack中更新了映射
            addLErelationship(person);
            superIntimacyFriend.put(normalSalphanum2, normalSfloat1);
          }
          // addLErelationship(friend.get(normalSalphanum2));
          // addTrackObject(friend.get(normalSalphanum2), 0);// 已知物体，就可以知道它所在的轨道
        } else {
          SocialE1 person = friend.get(normalSalphanum1);
          if (!objectTrack.containsKey(person)) {
            // System.out.println("Central!");
            addTrackObject(person, 0);// 已经往objectTrack中加入了映射
            addLErelationship(person);
            superIntimacyFriend.put(normalSalphanum1, normalSfloat1);
          } else {
            // System.out.println("Central!");
            transit(person, 0);// 已经往objectTrack中更新了映射
            addLErelationship(person);
            superIntimacyFriend.put(normalSalphanum1, normalSfloat1);

          }

        }
      } else {

        SocialE1 person1 = friend.get(normalSalphanum1);
        SocialE1 person2 = friend.get(normalSalphanum2);
        if (objectTrack.containsKey(person1) && objectTrack.containsKey(person2)) {
          // System.out.println("EE!");
          // 亲密度映射
          intimacyFriend.get(normalSalphanum1).put(normalSalphanum2, normalSfloat1);
          intimacyFriend.get(normalSalphanum2).put(normalSalphanum1, normalSfloat1);
          // 两个物体都在轨道上，不用加轨道，加亲密度就可以
          // 关系映射
          addEErelationship(person2, person1);// 调用的是父类中的方法

        } else if (objectTrack.containsKey(person1) && (!objectTrack.containsKey(person2))) {
          // 下面的两个else-if语句是处理双向情况，当要求处理单向情况时，删去一条分支即可
          if ((physical.size() - 1) > objectTrack.get(person1)) {
            // System.out.println("EE!");
            addTrackObject(person2, objectTrack.get(person1) + 1);
            // 亲密度映射
            intimacyFriend.get(normalSalphanum1).put(normalSalphanum2, normalSfloat1);
            intimacyFriend.get(normalSalphanum2).put(normalSalphanum1, normalSfloat1);
            // 两个物体都在轨道上，不用加轨道，加亲密度就可以
            // 关系映射
            addEErelationship(person2, person1);
          } else {
            temp1.add(normalSalphanum1);
            temp2.add(normalSalphanum2);
            temp3.add(normalSfloat1);
          }
        } else if ((objectTrack.containsKey(person2)) && (!objectTrack.containsKey(person1))) {
          if ((physical.size() - 1) > objectTrack.get(person2)) {
            // System.out.println("EE!");
            addTrackObject(person1, objectTrack.get(person2) + 1);
            // 亲密度映射
            intimacyFriend.get(normalSalphanum1).put(normalSalphanum2, normalSfloat1);
            intimacyFriend.get(normalSalphanum2).put(normalSalphanum1, normalSfloat1);
            // 两个物体都在轨道上，不用加轨道，加亲密度就可以
            // 关系映射
            addEErelationship(person2, person1);

          } else {
            temp1.add(normalSalphanum1);
            temp2.add(normalSalphanum2);
            temp3.add(normalSfloat1);

          }
        } else {
          temp1.add(normalSalphanum1);
          temp2.add(normalSalphanum2);
          temp3.add(normalSfloat1);
        }

      }
    }

    // 对剩余部分进行处理
    int length2 = temp1.size();
    for (int i = 0; i < length2; i++) {
      String t1 = temp1.get(i);
      String t2 = temp2.get(i);
      Float ft3 = temp3.get(i);
      SocialE1 person1 = friend.get(t1);
      SocialE1 person2 = friend.get(t2);
      if (objectTrack.containsKey(person1) && objectTrack.containsKey(person2)) {
        intimacyFriend.get(t1).put(t2, ft3);
        intimacyFriend.get(t2).put(t1, ft3);// 两个物体都在轨道上，不用加轨道，加亲密度就可以
        // 关系映射
        addEErelationship(person2, person1);
        // addEERelationship只需调用一次即可，函数内部会加两次关系
      } else if (objectTrack.containsKey(person1)) {
        // 下面的两个else-if语句是处理双向情况，当要求处理单向情况时，删去一条分支即可
        if ((physical.size() - 1) > objectTrack.get(person1)) {

          // System.out.println("EE!");
          addTrackObject(person2, objectTrack.get(person1) + 1);
          // 亲密度映射
          intimacyFriend.get(t1).put(t2, ft3);
          intimacyFriend.get(t2).put(t1, ft3);// 两个物体都在轨道上，不用加轨道，加亲密度就可以
          // 关系映射
          addEErelationship(person2, person1);
          // addEERelationship只需调用一次即可，函数内部会加两次关系
        } else {

          addTrack();
          addTrackObject(person2, objectTrack.get(person1) + 1);
          // 亲密度映射
          intimacyFriend.get(t1).put(t2, ft3);
          intimacyFriend.get(t2).put(t1, ft3);// 两个物体都在轨道上，不用加轨道，加亲密度就可以
          // 关系映射
          addEErelationship(person2, person1);
          // addEERelationship只需调用一次即可，函数内部会加两次关系
        }
      } else if (objectTrack.containsKey(person2)) {
        if ((physical.size() - 1) > objectTrack.get(person2)) {
          // System.out.println("EE!");
          addTrackObject(person1, objectTrack.get(person2) + 1);
          // 亲密度映射
          intimacyFriend.get(t1).put(t2, ft3);
          intimacyFriend.get(t2).put(t1, ft3);// 两个物体都在轨道上，不用加轨道，加亲密度就可以
          // 关系映射
          addEErelationship(person2, person1);
          // addEERelationship只需调用一次即可，函数内部会加两次关系
        } else {
          // System.out.println("EEADD!");
          addTrack();
          addTrackObject(person1, objectTrack.get(person2) + 1);
          // 亲密度映射
          intimacyFriend.get(t1).put(t2, ft3);
          intimacyFriend.get(t2).put(t1, ft3);// 两个物体都在轨道上，不用加轨道，加亲密度就可以
          // 关系映射
          addEErelationship(person2, person1);
          // addEERelationship只需调用一次即可，函数内部会加两次关系
        }
      } else { // 全部加完之后，两个物体还不在轨道上
        // System.out.println("do its again!");
      }
    }

    int length3 = temp1.size();
    for (int i = 0; i < length3; i++) {
      String t1 = temp1.get(i);
      String t2 = temp2.get(i);
      Float ft3 = temp3.get(i);
      SocialE1 person1 = friend.get(t1);
      SocialE1 person2 = friend.get(t2);
      if (!(objectTrack.containsKey(person1) && objectTrack.containsKey(person2))) {
        if (objectTrack.containsKey(person1) && !objectTrack.containsKey(person2)) {
          if ((physical.size() - 1) > objectTrack.get(person1)) {

            // System.out.println("EE!");
            addTrackObject(person2, objectTrack.get(person1) + 1);
            // 亲密度映射
            intimacyFriend.get(t1).put(t2, ft3);
            intimacyFriend.get(t2).put(t1, ft3);// 两个物体都在轨道上，不用加轨道，加亲密度就可以
            // 关系映射
            addEErelationship(person2, person1);
            // addEERelationship只需调用一次即可，函数内部会加两次关系
          } else {

            addTrack();
            addTrackObject(person2, objectTrack.get(person1) + 1);
            // 亲密度映射
            intimacyFriend.get(t1).put(t2, ft3);
            intimacyFriend.get(t2).put(t1, ft3);// 两个物体都在轨道上，不用加轨道，加亲密度就可以
            // 关系映射
            addEErelationship(person2, person1);
            // addEERelationship只需调用一次即可，函数内部会加两次关系
          }
        } else if (objectTrack.containsKey(person2) && !objectTrack.containsKey(person1)) {
          if ((physical.size() - 1) > objectTrack.get(person2)) {
            // System.out.println("EE!");
            addTrackObject(person1, objectTrack.get(person2) + 1);
            // 亲密度映射
            intimacyFriend.get(t1).put(t2, ft3);
            intimacyFriend.get(t2).put(t1, ft3);// 两个物体都在轨道上，不用加轨道，加亲密度就可以
            // 关系映射
            addEErelationship(person2, person1);
            // addEERelationship只需调用一次即可，函数内部会加两次关系
          } else {
            // System.out.println("EEADD!");
            addTrack();
            addTrackObject(person1, objectTrack.get(person2) + 1);
            // 亲密度映射
            intimacyFriend.get(t1).put(t2, ft3);
            intimacyFriend.get(t2).put(t1, ft3);// 两个物体都在轨道上，不用加轨道，加亲密度就可以
            // 关系映射
            addEErelationship(person2, person1);
            // addEERelationship只需调用一次即可，函数内部会加两次关系
          }
        } else {
          System.out.println("sorry!");
        }
      }
    }
    checkRep();// 检查后置条件
    long endTime = System.currentTimeMillis();
    System.out.println("It takes " + (endTime - startTime) + " millisecond to read the file.");
    log.info("Social:track has been created");
  }


  /**
   * 求某个人与中心点人之间的亲密度.
   * 
   * @param name 表示第一层某个人的名字.
   * @return 如果求取成功则返回true，否则返回false并提示错误信息.
   */
  // 求亲密度
  public boolean intimacy(String name) {
    log.info("Social:assert name isn't null,in class socialNetwork,method intimacy");
    assert name.length() != 0 : "name is empty";
    try {
      if (!friend.containsKey(name)) {
        throw new Exception("This person doesn't exist!");
      }
      if (!objectTrack.containsKey(friend.get(name))) {
        throw new Exception("This person is not in the orbital system!");
      }
      if (objectTrack.get(friend.get(name)) != 0) {
        throw new Exception("This person is not in the first orbit!");
      }
    } catch (Exception e) {
      // TODO: handle exception
      log.warning("Social:exception happens in class socialNetwork,method intimacy:"
          + e.getMessage() + " handling:return false,exception message is printed on console");
      System.out.println(e.getMessage());
      return false;
    }
    int potential = 0;
    // 前提条件：每个人的名字都不一样
    List<String> queue = new ArrayList<String>();// 作为搜索队列
    Set<String> flag = new HashSet<String>();// 标记已经被访问过的人
    queue.add(name);
    String tempName = name;
    while (queue.size() != 0) {
      tempName = queue.get(0);
      queue.remove(0);
      Map<String, Float> maptemp = intimacyFriend.get(tempName);// 不包含中心的那个人
      Iterator<String> iterator = maptemp.keySet().iterator();
      while (iterator.hasNext()) {

        String temp = iterator.next();
        // System.out.println(temp);
        if (!friend.containsKey(temp)) {
          log.severe("Social:one person appears in intimacy but is out of Friend,"
              + "handling:return false,error message is printed on console");
          System.out.println("This person doesn't exist!");
          return false;
        }
        if (objectTrack.get(friend.get(temp)) == 0) {
          ;
        } else {
          System.out.println("yes!");
          if (maptemp.get(temp) > 0.1 && !flag.contains(temp)) { // 亲密度满足要求，且没有被访问过
            potential++;
            flag.add(temp);// 做标记
            queue.add(temp);// 入队
          }
        }
      }
      // 遍历的过程中包含了中心用户
    }
    System.out.println("You can meet " + (potential) + " friends indirectly.");
    log.info("Social:the intimacy of " + name + " is " + potential);
    return true;
  }

  // 增加关系后重新建立轨道系统(可以是轨道物体间增加关系，也可以是轨道物体和中心点物体增加关系)
  /**
   * 增加关系后重新建立轨道系统,要求这两个人都在轨道系统上.
   * 
   * @param name1 人名.
   * @param name2 人名.
   * @param ini 亲密度.
   * @return 如果增加关系则返回true，否则返回false并提示错误信息.
   */
  public boolean addRelationship(String name1, String name2, float ini) { // 这个人必须在轨道上
    // 通过名称得到实例
    // 通过异常处理前置条件
    try {
      if (name1.equals(central.getName()) && !friend.containsKey(name2)) {
        throw new Exception(name1 + " is central but " + name2 + " isn't in Friend");
      }
      if (name2.equals(central.getName()) && !friend.containsKey(name1)) {
        throw new Exception(name2 + " is central but " + name1 + " isn't in Friend");
      }
      if (!friend.containsKey(name1) || !friend.containsKey(name2)) {
        throw new Exception("They are not in the orbit");
      }
    } catch (Exception e) {
      log.warning("Social:exception happens in class socialNetwork,method addRelationship:"
          + e.getMessage() + " handling:return false,exception message is printed on console");
      System.out.println(e.getMessage());
      return false;
      // TODO: handle exception
    }
    if (!friend.containsKey(name1) || !friend.containsKey(name2)) {
      System.out.println("They are not in the orbit");
      return false;
    }
    socialTie.add(new Tie(name1, name2, ini));
    log.info("Social:the relationship between " + name1 + " and " + name2 + " is add");
    clear();
    creatingTrack();
    return true;
  }

  // 删除关系（仅限于轨道物体）后重新调整轨道系统
  /**
   * 删除关系后重新建立轨道系统，要求删除关系的两个人都在轨道系统中.
   * 
   * @param name1 人名.
   * @param name2 人名.
   * @return 如果删除关系则返回true，否则返回false并提示错误信息.
   */
  public boolean deleteRelationship(String name1, String name2) {
    // if (!friend.containsKey(name1) || !friend.containsKey(name2))
    // {
    // System.out.println("They are not in the orbit");
    // }
    try {
      if (name1.equals(central.getName()) && !friend.containsKey(name2)) {
        throw new Exception(name1 + " is central but " + name2 + " isn't in Friend");
      }
      if (name2.equals(central.getName()) && !friend.containsKey(name1)) {
        throw new Exception(name2 + " is central but " + name1 + " isn't in Friend");
      }
      if (!friend.containsKey(name1) || !friend.containsKey(name2)) {
        throw new Exception("They are not in the orbit");
      }
    } catch (Exception e) {
      log.warning("Social:exception happens in class socialNetwork,method addRelationship:"
          + e.getMessage() + " handling:return false,exception message is printed on console");
      System.out.println(e.getMessage());
      return false;
      // TODO: handle exception
    }
    boolean is = false;
    for (int i = 0; i < socialTie.size(); i++) {
      if ((socialTie.get(i).getName1().equals(name1) && socialTie.get(i).getName2().equals(name2))
          || (socialTie.get(i).getName1().equals(name2)
              && socialTie.get(i).getName2().equals(name1))) {
        socialTie.remove(i);
        is = true;
        break;
      }
    }
    // 不属于前置条件，属于处理过程，只需要用if语句即可
    if (!is) {
      log.severe("Social:trying to delete relationship between two people that "
          + "doesn't has relationship with each other,"
          + "in class socialNetwork,method deleteRelationship,"
          + "handling:return false,error message is printed on console");
      System.out.println("They have no relationship!");
      return false;
    }
    log.info("Social:the relationship between " + name1 + " and " + name2 + " is deleted");
    clear();
    creatingTrack();
    return true;
  }


  public boolean socialWriteBackA() {
    long startTime = System.currentTimeMillis();
    try (BufferedWriter bw = new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream("write-txt/SocialNetworkCircle.txt")))) {

      bw.write("CentralUser ::= <" + central.getName() + "," + central.getAge() + ","
          + central.getSex() + ">");
      bw.newLine();;
      Iterator<String> iterator = friend.keySet().iterator();
      while (iterator.hasNext()) {
        SocialE1 e = friend.get(iterator.next());
        bw.write("Friend ::= <" + e.getName() + ", " + e.getAge() + ", " + e.getSex() + ">");
        bw.newLine();
      }
      for (int i = 0; i < socialTie.size(); i++) {
        bw.write("SocialTie ::= <" + socialTie.get(i).getName1() + ", "
            + socialTie.get(i).getName2() + ", " + socialTie.get(i).getIni() + ">");
        bw.newLine();
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

  public boolean socialWriteBackB() {
    long startTime = System.currentTimeMillis();
    try (BufferedWriter bw =
        new BufferedWriter(new FileWriter("write-txt/SocialNetworkCircle.txt"))) {

      bw.write("CentralUser ::= <" + central.getName() + "," + central.getAge() + ","
          + central.getSex() + ">");
      bw.newLine();;
      Iterator<String> iterator = friend.keySet().iterator();
      while (iterator.hasNext()) {
        SocialE1 e = friend.get(iterator.next());
        bw.write("Friend ::= <" + e.getName() + ", " + e.getAge() + ", " + e.getSex() + ">");
        bw.newLine();
      }
      for (int i = 0; i < socialTie.size(); i++) {
        bw.write("SocialTie ::= <" + socialTie.get(i).getName1() + ", "
            + socialTie.get(i).getName2() + ", " + socialTie.get(i).getIni() + ">");
        bw.newLine();
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

  //让用户选择写文件的方式
  public boolean socialWriteBackC() {
    long startTime = System.currentTimeMillis();
    try (PrintWriter bw = new PrintWriter(new FileWriter("write-txt/SocialNetworkCircle.txt"))) {
      bw.write("CentralUser ::= <" + central.getName() + "," + central.getAge() + ","
          + central.getSex() + ">");
      bw.write("\r\n");
      Iterator<String> iterator = friend.keySet().iterator();
      while (iterator.hasNext()) {
        SocialE1 e = friend.get(iterator.next());
        bw.write("Friend ::= <" + e.getName() + ", " + e.getAge() + ", " + e.getSex() + ">");
        bw.write("\r\n");
      }
      for (int i = 0; i < socialTie.size(); i++) {
        bw.write("SocialTie ::= <" + socialTie.get(i).getName1() + ", "
            + socialTie.get(i).getName2() + ", " + socialTie.get(i).getIni() + ">");
        bw.write("\r\n");
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
