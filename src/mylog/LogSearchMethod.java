package mylog;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogSearchMethod {
  /**
   * 按照时间来搜索日志.
   */
  public void timeLogSearch() {
    Scanner time = new Scanner(System.in);
    String txt;

    String re1 = "(\\d+)"; // Integer Number 1
    String re2 = "(:)"; // Any Single Character 1
    String re3 = "(\\d+)"; // Integer Number 2
    String re4 = "(:)"; // Any Single Character 2
    String re5 = "(\\d+)"; // Integer Number 3

    Pattern p =
        Pattern.compile(re1 + re2 + re3 + re4 + re5, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    int startHour;
    int startMinute;
    int endMinute;
    System.out.println("Please input the start hour:");
    startHour = time.nextInt();
    System.out.println("Please input the start minute:");
    startMinute = time.nextInt();
    System.out.println("Please input the end hour:");
    int endHour;
    endHour = time.nextInt();
    System.out.println("Please input the end minute:");
    endMinute = time.nextInt();
    try (BufferedReader in =
        new BufferedReader(new InputStreamReader(new FileInputStream("log/log")))) {
      while ((txt = in.readLine()) != null) {
        Matcher m = p.matcher(txt);
        if (m.find()) {
          // System.out.println("find");
          String int1 = m.group(1);// 小时
          String c1 = m.group(2);
          String int2 = m.group(3);// 分钟
          String c2 = m.group(4);
          String int3 = m.group(5);// 秒
          // System.out.println(Integer.parseInt(int1));
          // System.out.println(Integer.parseInt(int2));
          // System.out.println(Integer.parseInt(int3));
          // 这里对分钟没有要求
          if (Integer.parseInt(int1) <= endHour && Integer.parseInt(int1) >= startHour) {
            System.out.println(txt);
          }
        }

      }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }

  }

  /**
   * 按照轨道类型来搜索日志.
   */
  public void trackTypeLogSearch() {
    Scanner track = new Scanner(System.in);
    String txt;
    System.out.println(
        "Please input the trackType(T indicates trackGame,"
        + "A indicates atomStructure,S indicates socialNetwork)：");
    String type = track.next();
    while (!(type.toLowerCase().equals("t") || type.toLowerCase().equals("a")
        || type.toLowerCase().equals("s"))) {
      System.out.println("wrong order,input again?(y or n)");
      String yes = track.next();
      if (!yes.toLowerCase().equals("n")) {
        System.out.println(
            "Please input the log type(I indicates info,W indicates warning,S indicates severe)：");
        type = track.next();
      } else {
        return;
      }
    }
    String re1 = "(\\[)"; // Any Single Character 1
    String re2 = "((?:[a-z][a-z]+))"; // Word 1
    String re3 = "(\\.)"; // Any Single Character 2
    String re4 = "((?:[a-z][a-z]+))"; // Word 2
    String re5 = "(\\.)"; // Any Single Character 3
    String re6 = "((?:[a-z][a-z]+))"; // Word 3
    String re7 = "(\\])"; // Any Single Character 4

    Pattern p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7,
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    try (BufferedReader in =
        new BufferedReader(new InputStreamReader(new FileInputStream("log/log")))) {
      while ((txt = in.readLine()) != null) {
        Matcher m = p.matcher(txt);
        if (m.find()) {
          String c1 = m.group(1);
          String word1 = m.group(2);
          String c2 = m.group(3);
          String word2 = m.group(4);// 类名
          String c3 = m.group(5);
          String word3 = m.group(6);
          String c4 = m.group(7);
          switch (type.toLowerCase()) {
            case "t":
              if (word2.equals("functionTrackGame") || word2.equals("ConcreteCircularObject")) {
                System.out.println(txt);
              }
              break;
            case "a":

              if (word2.equals("atomStructure") || word2.equals("ConcreteCircularObject")) {
                System.out.println(txt);
              }
              break;
            case "s":

              if (word2.equals("socialNetworkCircle") || word2.equals("ConcreteCircularObject")) {
                System.out.println(txt);
              }
              break;
            default:
              break;
          }
        }
      }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }

  // 与trackTypeSearch本质上一样，但比trackTypeSearch简单
  /**
   * 按照类名来搜索日志.
   */
  public void classSearch() {
    Scanner track = new Scanner(System.in);
    String txt;
    System.out.println("Please input the class name：");
    String type = track.next();

    String re1 = "(\\[)"; // Any Single Character 1
    String re2 = "((?:[a-z][a-z]+))"; // Word 1
    String re3 = "(\\.)"; // Any Single Character 2
    String re4 = "((?:[a-z][a-z]+))"; // Word 2
    String re5 = "(\\.)"; // Any Single Character 3
    String re6 = "((?:[a-z][a-z]+))"; // Word 3
    String re7 = "(\\])"; // Any Single Character 4

    Pattern p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7,
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    try (BufferedReader in =
        new BufferedReader(new InputStreamReader(new FileInputStream("log/log")))) {
      while ((txt = in.readLine()) != null) {
        Matcher m = p.matcher(txt);
        if (m.find()) {
          String c1 = m.group(1);
          String word1 = m.group(2);
          String c2 = m.group(3);
          String word2 = m.group(4);// 类名
          String c3 = m.group(5);
          String word3 = m.group(6);
          String c4 = m.group(7);
          if (type.equals(word2)) {
            System.out.println(txt);
          }
          // switch (type.toLowerCase())
          // {
          // case "t":
          // if(word2.equals("functionTrackGame")||word2.equals("ConcreteCircularObject"))
          // {
          // System.out.println(txt);
          // }
          // break;
          // case "a":
          // // 调用可视化函数
          // if(word2.equals("atomStructure")||word2.equals("ConcreteCircularObject"))
          // {
          // System.out.println(txt);
          // }
          // break;
          // case "s":
          // // 增加轨道,与中心点物体没有关系的人也能出现在轨道上
          // if(word2.equals("socialNetworkCircle")||word2.equals("ConcreteCircularObject"))
          // {
          // System.out.println(txt);
          // }
          // break;
          // default:
          // break;
          // }
        }
      }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }

  // 本质上与classSearch没有区别
  /**
   * 按照方法类型来搜索日志.
   */
  public void methodSearch() {
    Scanner track = new Scanner(System.in);
    String txt;
    System.out.println("Please input the method name：");
    String type = track.next();

    String re1 = "(\\[)"; // Any Single Character 1
    String re2 = "((?:[a-z][a-z]+))"; // Word 1
    String re3 = "(\\.)"; // Any Single Character 2
    String re4 = "((?:[a-z][a-z]+))"; // Word 2
    String re5 = "(\\.)"; // Any Single Character 3
    String re6 = "((?:[a-z][a-z]+))"; // Word 3
    String re7 = "(\\])"; // Any Single Character 4

    Pattern p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7,
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    try (BufferedReader in =
        new BufferedReader(new InputStreamReader(new FileInputStream("log/log")))) {
      while ((txt = in.readLine()) != null) {
        Matcher m = p.matcher(txt);
        if (m.find()) {
          String c1 = m.group(1);
          String word1 = m.group(2);
          String c2 = m.group(3);
          String word2 = m.group(4);// 类名
          String c3 = m.group(5);
          String word3 = m.group(6);// 方法名
          String c4 = m.group(7);
          if (type.equals(word3)) {
            System.out.println(txt);
          }
          // switch (type.toLowerCase())
          // {
          // case "t":
          // if(word2.equals("functionTrackGame")||word2.equals("ConcreteCircularObject"))
          // {
          // System.out.println(txt);
          // }
          // break;
          // case "a":
          // // 调用可视化函数
          // if(word2.equals("atomStructure")||word2.equals("ConcreteCircularObject"))
          // {
          // System.out.println(txt);
          // }
          // break;
          // case "s":
          // // 增加轨道,与中心点物体没有关系的人也能出现在轨道上
          // if(word2.equals("socialNetworkCircle")||word2.equals("ConcreteCircularObject"))
          // {
          // System.out.println(txt);
          // }
          // break;
          // default:
          // break;
          // }
        }
      }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }

  /**
   * 按照日志的严重等级来搜索日志.
   */
  public void logTypeSearch() {
    Scanner track = new Scanner(System.in);
    String txt;
    System.out.println(
        "Please input the log type(I indicates info,W indicates warning,S indicates severe)：");
    String type = track.next();
    while (!(type.toLowerCase().equals("i") || type.toLowerCase().equals("w")
        || type.toLowerCase().equals("s"))) {
      System.out.println("wrong order,input again?(y or n)");
      String yes = track.next();
      if (!yes.toLowerCase().equals("n")) {
        System.out.println(
            "Please input the log type(I indicates info,W indicates warning,S indicates severe)：");
        type = track.next();
      } else {
        return;
      }
    }
    String re1 = "(\\])"; // Any Single Character 1
    String re2 = "((?:[a-z][a-z]+))"; // Word 1
    String re3 = "(:)"; // Any Single Character 2
    Pattern p = Pattern.compile(re1 + re2 + re3, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    try (BufferedReader in =
        new BufferedReader(new InputStreamReader(new FileInputStream("log/log")))) {
      while ((txt = in.readLine()) != null) {
        Matcher m = p.matcher(txt);
        if (m.find()) {
          String c1 = m.group(1);
          String word1 = m.group(2);
          String c2 = m.group(3);// log类型
          switch (type.toLowerCase()) {
            case "i":
              if (word1.equals("INFO")) {
                System.out.println(txt);
              }
              break;
            case "w":

              if (word1.equals("WARNING")) {
                System.out.println(txt);
              }
              break;
            case "s":

              if (word1.equals("SEVERE")) {
                System.out.println(txt);
              }
              break;
            default:
              break;
          }
        }
      }

    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }


  }
}
