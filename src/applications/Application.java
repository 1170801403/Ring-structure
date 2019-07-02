package applications;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.IntToDoubleFunction;
import apis.*;
import centralobject.L1;
import centralobject.AtomL1;
import centralobject.SocialL1;
import circularorbit.AtomCareTaker;
import circularorbit.AtomOriginator;
import circularorbit.AtomState;
import circularorbit.AtomStructure;
import circularorbit.FunctionTrackGame;
import circularorbit.SocialNetworkCircle;
import circularorbit.Tie;
import circularorbit.TrackGame;
import circularorbit.TrackOrganizer;
import mylog.LogSearchMethod;
import physicalobject.AtomE1;
import physicalobject.SocialE1;
import physicalobject.TrackE1;
import strategyio.AtomStrategyOrganizerInput;
import strategyio.AtomStrategyOrganizerOutput;
import strategyio.SocialStrategyOrganizerInput;
import strategyio.SocialStrategyOrganizerOutput;
import strategyio.TrackStrategyOrganizerInput;
import strategyio.TrackStrategyOrganizerOutput;


public class Application {

  /**
   * 将田径轨道写回文件.
   */
  public static boolean trackWriteBack(FunctionTrackGame track) {
    System.out
        .println("Now please choose the method to write files:Stream(a) Writer(b) Printer(c)");
    Scanner choiceOrder = new Scanner(System.in);
    String read = choiceOrder.next();
    boolean choose2 = true;
    boolean fina = false;
    while (choose2) {
      if (read.toLowerCase().equals("a")) {
        TrackStrategyOrganizerOutput organizer = new TrackStrategyOrganizerOutput("a");
        if (!organizer.arrange(track)) {
          System.out.println("The file has problem!Input again?");
          String againYes = choiceOrder.next();
          if (againYes.equals("n")) {
            choose2 = false;
          } else {
            System.out.println("please input:");
            read = choiceOrder.next();
          }
        } else {
          choose2 = false;
          fina = true;
        }

      } else if (read.toLowerCase().equals("b")) {
        TrackStrategyOrganizerOutput organizer = new TrackStrategyOrganizerOutput("b");
        if (!organizer.arrange(track)) {
          System.out.println("The file has problem!Input again?");
          String againYes = choiceOrder.next();
          if (againYes.equals("n")) {
            choose2 = false;
          } else {
            System.out.println("please input:");
            read = choiceOrder.next();
          }
        } else {
          choose2 = false;
          fina = true;
        }
      } else if (read.toLowerCase().equals("c")) {
        TrackStrategyOrganizerOutput organizer = new TrackStrategyOrganizerOutput("c");
        if (!organizer.arrange(track)) {
          System.out.println("The file has problem!Input again?");
          String againYes = choiceOrder.next();
          if (againYes.equals("n")) {
            choose2 = false;
          } else {
            System.out.println("please input:");
            read = choiceOrder.next();
          }
        } else {
          choose2 = false;
          fina = true;
        }
      } else {
        System.out.println("Wrong order!Input again?");
        String yes = choiceOrder.next();
        if (yes.toLowerCase().equals("n")) {
          choose2 = false;
        } else {
          System.out.println("Please input:");
          read = choiceOrder.next();;
        }
      }
    }
    return fina;
  }

  /**
   * 将原子轨道写回文件.
   */
  public static boolean atomWriteBack(AtomStructure atom) {
    System.out
        .println("Now please choose the method to write files:Stream(a) Writer(b) Printer(c)");
    Scanner choiceOrder = new Scanner(System.in);
    String read = choiceOrder.next();
    boolean choose2 = true;
    boolean fina = false;
    while (choose2) {
      if (read.toLowerCase().equals("a")) {
        AtomStrategyOrganizerOutput organizer = new AtomStrategyOrganizerOutput("a");
        if (!organizer.arrange(atom)) {
          System.out.println("The file has problem!Input again?");
          String againYes = choiceOrder.next();
          if (againYes.equals("n")) {
            choose2 = false;
          } else {
            System.out.println("please input:");
            read = choiceOrder.next();
          }
        } else {
          choose2 = false;
          fina = true;
        }

      } else if (read.toLowerCase().equals("b")) {
        AtomStrategyOrganizerOutput organizer = new AtomStrategyOrganizerOutput("b");
        if (!organizer.arrange(atom)) {
          System.out.println("The file has problem!Input again?");
          String againYes = choiceOrder.next();
          if (againYes.equals("n")) {
            choose2 = false;
          } else {
            System.out.println("please input:");
            read = choiceOrder.next();
          }
        } else {
          choose2 = false;
          fina = true;
        }
      } else if (read.toLowerCase().equals("c")) {
        AtomStrategyOrganizerOutput organizer = new AtomStrategyOrganizerOutput("c");
        if (!organizer.arrange(atom)) {
          System.out.println("The file has problem!Input again?");
          String againYes = choiceOrder.next();
          if (againYes.equals("n")) {
            choose2 = false;
          } else {
            System.out.println("please input:");
            read = choiceOrder.next();
          }
        } else {
          choose2 = false;
          fina = true;
        }
      } else {
        System.out.println("Wrong order!Input again?");
        String yes = choiceOrder.next();
        if (yes.toLowerCase().equals("n")) {
          choose2 = false;
        } else {
          System.out.println("Please input:");
          read = choiceOrder.next();;
        }
      }
    }
    return fina;
  }


  /**
   * 将社交网络写回文件.
   */
  public static boolean socailWriteBack(SocialNetworkCircle society) {
    System.out
        .println("Now please choose the method to write files:Stream(a) Writer(b) Printer(c)");
    Scanner choiceOrder = new Scanner(System.in);
    String read = choiceOrder.next();
    boolean choose2 = true;
    boolean fina = false;
    while (choose2) {
      if (read.toLowerCase().equals("a")) {
        SocialStrategyOrganizerOutput organizer = new SocialStrategyOrganizerOutput("a");
        if (!organizer.arrange(society)) {
          System.out.println("The file has problem!Input again?");
          String againYes = choiceOrder.next();
          if (againYes.equals("n")) {
            choose2 = false;
          } else {
            System.out.println("please input:");
            read = choiceOrder.next();
          }
        } else {
          choose2 = false;
          fina = true;
        }

      } else if (read.toLowerCase().equals("b")) {
        SocialStrategyOrganizerOutput organizer = new SocialStrategyOrganizerOutput("b");
        if (!organizer.arrange(society)) {
          System.out.println("The file has problem!Input again?");
          String againYes = choiceOrder.next();
          if (againYes.equals("n")) {
            choose2 = false;
          } else {
            System.out.println("please input:");
            read = choiceOrder.next();
          }
        } else {
          choose2 = false;
          fina = true;
        }
      } else if (read.toLowerCase().equals("c")) {
        SocialStrategyOrganizerOutput organizer = new SocialStrategyOrganizerOutput("c");
        if (!organizer.arrange(society)) {
          System.out.println("The file has problem!Input again?");
          String againYes = choiceOrder.next();
          if (againYes.equals("n")) {
            choose2 = false;
          } else {
            System.out.println("please input:");
            read = choiceOrder.next();
          }
        } else {
          choose2 = false;
          fina = true;
        }
      } else {
        System.out.println("Wrong order!Input again?");
        String yes = choiceOrder.next();
        if (yes.toLowerCase().equals("n")) {
          choose2 = false;
        } else {
          System.out.println("Please input:");
          read = choiceOrder.next();;
        }
      }
    }
    return fina;
  }



  /**
   * 提示用户选择日志搜索的种类，然后调用相应的函数进行搜索.
   */
  public static void logSearch() {
    boolean again = true;
    LogSearchMethod method = new LogSearchMethod();
    Scanner logOrder = new Scanner(System.in);
    System.out.println("Now please choose the search pattern:");
    System.out.println("1  time");
    System.out.println("2  track type");
    System.out.println("3  class");
    System.out.println("4  method");
    System.out.println("5  log type");
    int order = logOrder.nextInt();
    while (!((order >= 1) && (order <= 5))) {
      System.out.println("Wrong order!Input again:");
      order = logOrder.nextInt();
    }
    switch (order) {

      case 1:
        method.timeLogSearch();;
        break;
      case 2:
        method.trackTypeLogSearch();
        break;
      case 3:
        method.classSearch();
        break;
      case 4:
        method.methodSearch();
        break;
      case 5:
        method.logTypeSearch();
        break;
      default:
        break;
    }

  }

  /**
   * 在原子类型中，用户选择读入的文件大小.
   * 
   * @return
   */
  public static String atomcreatingTrackFromFiles() {
    boolean c = false;
    String size = "2";
    Scanner in2 = new Scanner(System.in);
    while (!c) {
      System.out.println("Please input the size of track(s,m,l):");
      size = in2.next();
      if (size.toLowerCase().equals("s") || size.toLowerCase().equals("m")
          || size.toLowerCase().equals("l")) {
        c = true;
      } else {
        System.out.println("Sorry,this size is illegal.Please input again;");
      }
    }
    if (size.equals("s")) {
      // System.out.println("y");
      return "AtomicStructure.";
      // System.out.println("y");
    } else if (size.equals("m")) {
      return "AtomicStructure_Medium.";
    } else {
      return "AtomicStructure_Medium.";
    }
  }

  /**
   * 在原子类型中，用户选择读入的文件大小.
   * 
   * @return
   */
  public static String socialcreatingTrackFromFiles() {
    boolean c = false;
    String size = "2";
    Scanner in2 = new Scanner(System.in);
    while (!c) {
      System.out.println("Please input the size of track(s,m,l):");
      size = in2.next();
      if (size.toLowerCase().equals("s") || size.toLowerCase().equals("m")
          || size.toLowerCase().equals("l")) {
        c = true;
      } else {
        System.out.println("Sorry,this size is illegal.Please input again;");
      }
    }
    if (size.equals("s")) {
      return "SocialNetworkCircle.";
    } else if (size.equals("m")) {
      return "SocialNetworkCircle_Medium.";
    } else {
      return "Lab5SocialNetworkCircle.";
    }
  }

  /**
   * 在田径类型中，用户选择读入的文件大小.
   * 
   * @return
   */
  public static String trackcreatingTrackFromFiles() {
    boolean c = false;
    String size = "2";
    Scanner in2 = new Scanner(System.in);
    while (!c) {
      System.out.println("Please input the size of track(s,m,l):");
      size = in2.next();
      if (size.toLowerCase().equals("s") || size.toLowerCase().equals("m")
          || size.toLowerCase().equals("l")) {
        c = true;
      } else {
        System.out.println("Sorry,this size is illegal.Please input again;");
      }
    }
    if (size.equals("s")) {
      return "TrackGame.";
    } else if (size.equals("m")) {
      return "TrackGame_Medium.";
    } else {
      return "Lab5TrackGame.";
    }
  }

  /**
   * 计算田径轨道系统的熵值，需要分组计算.
   * 
   * @return
   */
  // 计算田径轨道熵值
  public static double trackEntropy(FunctionTrackGame track) {
    Scanner trackEntropyIn = new Scanner(System.in);
    int groupNumber;

    System.out.println("Which group do you want to query for entropy" + "0-"
        + (track.getGroupTrackSystem().size() - 1));
    groupNumber = trackEntropyIn.nextInt();
    while ((groupNumber > track.getGroupTrackSystem().size() - 1) || (groupNumber < 0)) {
      System.out.println("Wrong number!Please input again:");
      groupNumber = trackEntropyIn.nextInt();
    }
    CircularOrbitApis<L1, TrackE1> apiTrack = new CircularOrbitApis<L1, TrackE1>();
    double entropy =
        apiTrack.getObjectDistributionEntropy(track.getGroupTrackSystem().get(groupNumber));
    return entropy;
  }

  /**
   * 更改同一组或不同组运动员的轨道.
   * 
   * @return
   */
  public static void trackChange(FunctionTrackGame track) {
    Scanner changeIn = new Scanner(System.in);
    System.out.println("Please input the two player's group number,track numebr,the first player:");
    System.out.println("group numebr:");
    int group1;
    group1 = changeIn.nextInt();
    System.out.println("track number:");
    int number1;
    number1 = changeIn.nextInt();
    System.out.println("the next player:");
    System.out.println("group numebr:");
    int group2;
    group2 = changeIn.nextInt();
    System.out.println("track number:");
    int number2;
    number2 = changeIn.nextInt();

    if (group1 > (track.getGroupTrackSystem().size() - 1)) {
      System.out.println("first player's group number is out of bound!");
      return;
    }
    if (group2 > (track.getGroupTrackSystem().size() - 1)) {
      System.out.println("second player's group number is out of bound!");
      return;
    }
    if (track.getGroupTrackSystem().get(group1).getTrackObject().size() - 1 < number1) {
      System.out.println("first player's track number is out of bound!");
      return;
    }
    if (track.getGroupTrackSystem().get(group1).getTrackObject().size() - 1 < number2) {
      System.out.println("second player's track number is out of bound!");
      return;
    }

    System.out.println("Adjust");
    track.groupAdjust(group1, number1, group2, number2);
  }



  /**
   * 在某一组田径比赛中增加轨道.
   * 
   * @return
   */
  public static void trackAddTrack(FunctionTrackGame track) {
    Scanner trackEntropyIn = new Scanner(System.in);
    int groupNumber;
    System.out.println(
        "Which group do you want to add track:" + "0-" + (track.getGroupTrackSystem().size() - 1));
    groupNumber = trackEntropyIn.nextInt();
    while ((groupNumber > track.getGroupTrackSystem().size() - 1) || (groupNumber < 0)) {
      System.out.println("Wrong number!Please input again:");
      groupNumber = trackEntropyIn.nextInt();
    }
    track.addTrack(groupNumber);// 操作成功会有提示，操作不成功也会有提示
  }

  /**
   * 在某一组田径比赛中删除轨道.
   * 
   * @return
   */
  public static void trackDeleteTrack(FunctionTrackGame track) {
    Scanner trackEntropyIn = new Scanner(System.in);
    int groupNumber;
    System.out.println("Which group do you want to delete track:" + "0-"
        + (track.getGroupTrackSystem().size() - 1));
    groupNumber = trackEntropyIn.nextInt();
    while ((groupNumber > track.getGroupTrackSystem().size() - 1) || (groupNumber < 0)) {
      System.out.println("Wrong number!Please input again:");
      groupNumber = trackEntropyIn.nextInt();
    }
    int trackNumber;
    System.out.println("Please input the trackNumber:");// 此处不能给数字范围，因为可能是第二次delete
    trackNumber = trackEntropyIn.nextInt();
    track.deleteTrack(groupNumber, trackNumber);
  }

  /**
   * 在某一组田径比赛中增加运动员.
   * 
   * @return
   */
  public static void trackAddObject(FunctionTrackGame track) {
    Scanner trackEntropyIn = new Scanner(System.in);
    int groupNumber;
    System.out.println(
        "Which group do you want to add object:" + "0-" + (track.getGroupTrackSystem().size() - 1));
    groupNumber = trackEntropyIn.nextInt();
    while ((groupNumber > track.getGroupTrackSystem().size() - 1) || (groupNumber < 0)) {
      System.out.println("Wrong number!Please input again:");
      groupNumber = trackEntropyIn.nextInt();
    }

    int trackNumber;
    System.out.println("Please input the trackNumber:");// 此处不能给数字范围，因为可能是第二次delete
    trackNumber = trackEntropyIn.nextInt();

    System.out.println("Please input the player's name,number,nationality,age,best resuts:");
    System.out.println("Name:");
    String name1 = trackEntropyIn.next();
    System.out.println("Number:");
    int number1 = trackEntropyIn.nextInt();
    System.out.println("Nationaility:");
    String nationaility1 = trackEntropyIn.next();
    System.out.println("Age:");
    int age1 = trackEntropyIn.nextInt();
    System.out.println("Best results:");
    double best1 = trackEntropyIn.nextDouble();

    TrackE1 temp1 = new TrackE1(name1, number1, nationaility1, age1, best1);
    track.addObject(groupNumber, trackNumber, temp1);
  }


  /**
   * 在某一组田径比赛中删除轨道.
   * 
   * @return
   */
  public static void trackDeleteObject(FunctionTrackGame track) {
    Scanner trackEntropyIn = new Scanner(System.in);
    int groupNumber;
    System.out.println("Which group do you want to delete object:" + "0-"
        + (track.getGroupTrackSystem().size() - 1));
    groupNumber = trackEntropyIn.nextInt();
    while ((groupNumber > track.getGroupTrackSystem().size() - 1) || (groupNumber < 0)) {
      System.out.println("Wrong number!Please input again:");
      groupNumber = trackEntropyIn.nextInt();
    }
    int trackNumber;
    System.out.println("Please input the trackNumber:");// 此处不能给数字范围，因为可能是第二次delete
    trackNumber = trackEntropyIn.nextInt();

    // System.out.println("Please input the player's name,number,nationality,age,best resuts:");
    // System.out.println("Name:");
    // String name1 = trackEntropyIn.next();
    // System.out.println("Number:");
    // int number1 = trackEntropyIn.nextInt();
    // System.out.println("Nationaility:");
    // String nationaility1 = trackEntropyIn.next();
    // System.out.println("Age:");
    // int age1 = trackEntropyIn.nextInt();
    // System.out.println("Best results:");
    // double best1 = trackEntropyIn.nextDouble();

    // trackE1 temp1 = new trackE1(name1, number1, nationaility1, age1, best1);
    track.deleteObject(groupNumber, trackNumber);
  }

  /**
   * 检查田径比赛轨道系统中每一组比赛的合法性.
   * 
   * @return
   */
  public static boolean trackCheck(FunctionTrackGame track) {
    boolean b;
    TrackGame temp = new TrackGame();
    int trackNumber = track.getTrackNumber();// 从文件中读到的轨道数
    if ((track.getAthlete().size()) % (trackNumber) == 0) {
      b = true;
    } else {
      b = false;
    }
    for (int i = 0; i < track.getGroupTrackSystem().size(); i++) {
      temp = track.getGroupTrackSystem().get(i);// 增加一条指针

      // 不管用户怎么删，最后都要满足这个约束
      if (temp.getTrackObject().size() > 10 || temp.getTrackObject().size() < 4) {
        System.out.println("group " + i + "'s track number is out of bound!");
        return false;
      }
      // 原则上可以正好排满，但用户可以删除轨道，不过也只是删除这一组的轨道，对其他组无影响
      if (b) {
        // 保证轨道数目合法
        if (temp.getTrackObject().size() != trackNumber) {
          System.out.println("Group" + i + "'s trackNumber is illegal!");
          return false;
        }
        for (int j = 0; j < temp.getTrackObject().size(); j++) {
          // 每条轨道上有且仅仅有一个运动员
          // 如果之前删除过一条轨道，此时就会出现空指针错误，就算j<temp.getTrackObject().size()
          if (temp.getTrackObject().containsKey(j)) {
            if (temp.getTrackObject().get(j).size() > 1) {
              System.out.println("Group" + i + " track" + j + " has more than one athlete!");
              return false;
            }
            if (temp.getTrackObject().get(j).size() == 0) {
              System.out.println("Group" + i + " track" + j + " has no athlete!");
              return false;
            }
          }

        }
      } else {
        if (i != track.getGroupTrackSystem().size() - 1) { // 保证轨道数目合法
          if (temp.getTrackObject().size() != trackNumber) {
            System.out.println("Group" + i + "'s trackNumber is illegal!");
            return false;
          }
        }
        for (int j = 0; j < temp.getTrackObject().size(); j++) {
          // 每条轨道上有且仅仅有一个运动员
          if (temp.getTrackObject().get(j).size() > 1) {
            System.out.println("Group" + i + " track" + j + " has more than one athlete!");
            return false;
          }
          if (temp.getTrackObject().get(j).size() == 0) {
            System.out.println("Group" + i + " track" + j + " has no athlete!");
            return false;
          }
        }
      }

    }
    return true;
  }

  /**
   * 田径比赛轨道系统的可视化，包括每一组比赛的可视化.
   * 
   * @return
   */
  // 设置为static，便于让main函数直接调用
  public static void trackVisualize(FunctionTrackGame track) {
    // int group = track.getGroupTrackSystem().size();
    Scanner trackIn = new Scanner(System.in);
    int numGroup = track.getGroupTrackSystem().size();// 能提前代替就提前代替，否则会容易出现错误
    System.out.println("Please input the group number(0-" + (numGroup - 1) + "):");
    System.out.println(track.getAthlete().size());
    int groupFinal = trackIn.nextInt();
    System.out.println();
    if ((numGroup - 1) < groupFinal) {
      System.out.println("the group is out of bound!");
      return;
    }
    CircularOrbitHelper<AtomL1, AtomE1> apiAtom =
        new CircularOrbitHelper<AtomL1, AtomE1>(track.getGroupTrackSystem().get(groupFinal));
    apiAtom.visualize();
    // for(int i=0; i<group; i++)
    // {
    // CircularOrbitHelper<atomL1, atomE1> apiAtom = new CircularOrbitHelper<atomL1, atomE1>(
    // track.getGroupTrackSystem().get(i));
    // apiAtom.visualize();
    // }
    return;
  }

  public static void trackOrganizer1(FunctionTrackGame track) {
    TrackOrganizer or = new TrackOrganizer("r");
    or.arrange(track);
  }

  public static void trackOrganizer2(FunctionTrackGame track) {
    TrackOrganizer or = new TrackOrganizer("g");
    or.arrange(track);
  }

  // if (athlete.size() % trackNumber == 0)
  // {
  // for (int i = 0; i < trackNumber; i++)
  // {
  // assert trackObject.get(i).size() == athlete.size() / trackNumber;
  // }
  // }
  // else
  // {
  // for (int i = 0; i < trackNumber-1; i++)
  // {
  // assert trackObject.get(i).size() == athlete.size() / trackNumber;
  // }
  // int j = trackNumber-1;
  // assert trackObject.get(j).size() == athlete.size() % trackNumber;
  // }


  /**
   * 在原子轨道中增加轨道.
   * 
   * @return
   */
  public static void atomAddTrack(AtomStructure atom) {
    atom.addTrack();
  }

  /**
   * 在原子轨道中删除轨道.
   * 
   * @return
   */
  public static void atomDeleteTrack(AtomStructure atom) {
    Scanner atomTrackIn = new Scanner(System.in);
    int trackNumber;
    System.out.println("Please input the trackNumber!");// 此处不能给数字范围，因为可能是第二次delete
    trackNumber = atomTrackIn.nextInt();
    atom.deleteTrack(trackNumber);
  }

  /**
   * 在原子轨道中删除轨道.
   * 
   * @return
   */
  public static void atomAddObject(AtomStructure atom) {
    Scanner atomTrackIn = new Scanner(System.in);
    int trackNumber;
    System.out.println("Please input the trackNumber!");// 此处不能给数字范围，因为可能是第二次delete
    trackNumber = atomTrackIn.nextInt();
    atom.addTrackObject(new AtomE1("electronic"), trackNumber);
  }

  /**
   * 在原子轨道中删除电子.
   * 
   * @return
   */
  public static void atomDeleteObject(AtomStructure atom) {
    Scanner atomTrackIn = new Scanner(System.in);
    AtomE1 temp = null;

    int trackNumber;
    System.out.println("Please input the trackNumber!");// 此处不能给数字范围，因为可能是第二次delete
    trackNumber = atomTrackIn.nextInt();
    if (!atom.getTrackObject().containsKey(trackNumber)) {
      System.out.println("the track has been deleted!");
      return;
    }
    Iterator<AtomE1> iterator = atom.getTrackObject().get(trackNumber).iterator();
    if (iterator.hasNext()) {
      temp = iterator.next();
    }
    atom.deleteTrackObject(temp, trackNumber);
  }

  /**
   * 计算原子轨道的熵值.
   * 
   * @return
   */
  public static double atomEntropy(AtomStructure atom) { // 计算原子轨道熵值
    CircularOrbitApis<AtomL1, AtomE1> apiAtom = new CircularOrbitApis<AtomL1, AtomE1>();
    double entropy = apiAtom.getObjectDistributionEntropy(atom);
    return entropy;
  }

  /**
   * 原子轨道可视化.
   * 
   * @return
   */
  public static void atomVisualize(AtomStructure atom) { // 原子轨道可视化
    CircularOrbitHelper<AtomL1, AtomE1> apiAtom = new CircularOrbitHelper<AtomL1, AtomE1>(atom);
    apiAtom.visualize();
  }

  /**
   * 原子轨道中电子跃迁.
   * 
   * @return
   */
  public static void atomTransit(AtomStructure atom, AtomCareTaker careTaker,
      AtomOriginator originator) {
    Scanner trIn = new Scanner(System.in);
    System.out.println("Please input the number of the two tracks:");
    int trackNumber1 = trIn.nextInt();
    int trackNumber2 = trIn.nextInt();
    // 记录当前状态
    originator.setState(new AtomState(atom.getTrackObjectNumber()));
    // 将当前状态加入备忘录列表中
    careTaker.addMemento(originator.addMemento());
    atom.electronicTransition(trackNumber1, trackNumber2);
    // //记录当前状态
    // originator.setState(new atomState(atom.getTrackObjectNumber()));
    // //将当前状态加入备忘录列表中
    // careTaker.addMemento(originator.addMemento());
  }

  /**
   * 原子轨道中回到电子跃迁之前的状态.
   * 
   * @return
   */
  public static void atomGoBack(AtomStructure atom, AtomCareTaker careTaker,
      AtomOriginator originator) {
    Scanner backIn = new Scanner(System.in);
    System.out.println("Please input the steps:");
    int step = backIn.nextInt();
    // 恢复到之前的状态
    originator.restore(careTaker.getMemento(step));
    atom.clear();
    atom.creatingTrack(atom.getTrackNumber2(),
        originator.addMemento().getState().getTrackObjectNumber());
  }
  // while (!m)
  // {
  // if (!atom.electronicTransition(trackNumber1, trackNumber2))
  // {
  // System.out.println("again?(y or n)");
  // String atChoice = trIn.next();
  // if (atChoice.toLowerCase().equals("y"))
  // {
  // ;
  // }
  // else
  // {
  // m = true;
  // }
  // }
  // else
  // {
  // System.out.println("transit again?(y or n)");
  // String successAtChoice = trIn.next();
  // if (successAtChoice.toLowerCase().equals("y"))
  // {
  // ;
  // }
  // else
  // {
  // m = true;
  // }
  // }
  // }
  // }

  /**
   * 社交网络中增加轨道.
   * 
   * @return
   */
  public static void socialAddTrack(SocialNetworkCircle society) {
    society.addTrack();
    return;
  }

  /**
   * 社交网络中删除轨道.
   * 
   * @return
   */
  // 删除一条轨道之后，关系也要删除
  public static void socialDeleteTrack(SocialNetworkCircle society) {
    Scanner socialIn = new Scanner(System.in);
    int trackNumber;
    System.out.println("Please input the trackNumber!");// 此处不能给数字范围，因为可能是第二次delete
    trackNumber = socialIn.nextInt();
    society.deleteTrack(trackNumber);
    return;
  }

  /**
   * 社交网络中删除某个人，先与用户进行交互，确认用户的需求后，再调用相应的功能函数.
   * 
   * @return
   */
  // 这个函数中不加return会错
  public static void socialDeleteObject(SocialNetworkCircle society) {
    Scanner socialIn = new Scanner(System.in);
    char sex = 0;
    int trackNumber;
    SocialE1 temp = new SocialE1("a", 1, sex);
    // System.out.println("Please input the trackNumber!");// 此处不能给数字范围，因为可能是第二次delete
    // trackNumber = socialIn.nextInt();
    System.out.println("Please input the name!");
    String name;
    name = socialIn.next();
    System.out.println("Please input the age!");
    int age;
    age = socialIn.nextInt();
    System.out.println("Please input the sex!(M or F)");
    String sex1;
    sex1 = socialIn.next();
    sex = sex1.charAt(0);// 输入字符中的第一个字母
    boolean is = false;
    int i = 0;
    for (i = 0; i < society.getTrackObject().size(); i++) {
      Iterator<SocialE1> iterator = society.getTrackObject().get(i).iterator();
      while (iterator.hasNext()) {
        // 避免重写equals方法
        temp = iterator.next();// 让temp指向内存中的另一块空间，因为socialE1是不可变的
        if (temp.getName().equals(name) && temp.getSex() == sex && temp.getAge() == age) {
          is = true;
          break;
        }
      }
      if (is == true) {
        break;
      }
    }
    // Iterator<socialE1> iterator = society.getTrackObject().get(trackNumber).iterator();
    // while (iterator.hasNext())
    // {
    // //避免重写equals方法
    // temp = iterator.next();// 让temp指向内存中的另一块空间，因为socialE1是不可变的
    // if (temp.getName().equals(name) && temp.getSex() == sex && temp.getAge() == age)
    // {
    // is = true;
    // break;
    // }
    // }
    if (!is) {
      System.out.println("The person is not in the track!");
      return;
    }
    society.deleteTrackObject(temp, i);
    return;
  }

  /**
   * 社交网络中增加某个人，先与用户进行交互，确认用户的需求后，再调用相应的功能函数.
   * 
   * @return
   */
  public static void socialAddObject(SocialNetworkCircle society) {
    Scanner socialIn = new Scanner(System.in);
    // socialE1 temp = new socialE1("a", 1, sex);
    System.out.println("Please input the trackNumber!");// 此处不能给数字范围，因为可能是第二次delete
    int trackNumber;
    trackNumber = socialIn.nextInt();
    System.out.println("Please input the name!");
    String name;
    name = socialIn.next();
    System.out.println("Please input the age!");
    int age;
    age = socialIn.nextInt();
    System.out.println("Please input the sex!(M or F)");
    String sex1;
    sex1 = socialIn.next();
    char sex = 0;
    sex = sex1.charAt(0);// 输入字符中的第一个字母
    SocialE1 tempE = new SocialE1(name, age, sex);
    society.addTrackObject(tempE, trackNumber);
  }

  /**
   * 计算社交网络的熵值.
   * 
   * @return
   */
  // 计算社交网络熵值
  public static double socialEntropy(SocialNetworkCircle society) {
    CircularOrbitApis<SocialL1, SocialE1> apiSociety = new CircularOrbitApis<SocialL1, SocialE1>();
    double entropy = apiSociety.getObjectDistributionEntropy(society);
    return entropy;
    // System.out.println("The entropy of the system is:" + entropy);
  }

  /**
   * 让社交网络可视化.
   * 
   * @return
   */
  public static void socialVisualize(SocialNetworkCircle society) {
    CircularOrbitHelper<SocialL1, SocialE1> apiSociety =
        new CircularOrbitHelper<SocialL1, SocialE1>(society);
    apiSociety.visualize();
  }

  /**
   * 计算社交网络中通过第一层中某个人能间接认识多少人.
   * 
   * @return
   */
  // 计算亲密度
  public static void socialIntimacy(SocialNetworkCircle society) {
    Scanner intimacyIn = new Scanner(System.in);
    System.out.println("Please enter a friend from layer 1");
    String first = intimacyIn.next();
    society.intimacy(first);
  }
  // boolean c = false;
  // while (!c)
  // {
  // if (!society.intimacy(first))
  // {
  // System.out.println("again?(y or n)");
  // String choice = intimacyIn.next();
  // if (choice.toLowerCase().equals("y"))
  // {
  // System.out.println("Please enter a friend from layer 1");
  // first = intimacyIn.next();
  // }
  // else
  // {
  // c = true;
  // }
  // }
  // else
  // {
  // System.out.println("get it again?(y or n)");
  // String successChoice = intimacyIn.next();
  // if (successChoice.toLowerCase().equals("y"))
  // {
  // ;
  // }
  // else
  // {
  // c = true;
  // }
  // }
  // }

  // }

  /**
   * 社交网络中增加或删除一条关系后重新调整图结构.
   * 
   * @return
   */
  // 增加/删除关系后重新调整图结构
  public static void socialAR(SocialNetworkCircle society) {
    Scanner arIn = new Scanner(System.in);
    // System.out.println("Do you want to add or remove a relationship(y or n)?");
    // String yar = arIn.next();
    // if (yar.toLowerCase().equals("y"))

    // boolean rm = false;
    String name1;
    System.out.println("Please input the first man's name:");
    name1 = arIn.next();
    System.out.println("Please input the second man's name:");
    String name2;
    name2 = arIn.next();
    System.out.println("Add or remove(a or r)?");
    String ar;
    ar = arIn.next();
    while (!((ar.toLowerCase().equals("a")) || (ar.toLowerCase().equals("r")))) {
      System.out.println("Illegal,please input again:");
      ar = arIn.next();
    }
    if (ar.toLowerCase().equals("a")) {
      System.out.println("Please input their intimacy:");
      Float ini = arIn.nextFloat();
      // 在addRelationship方法中包含了addEErelationship
      society.addRelationship(name1, name2, ini);
    } else {
      society.deleteRelationship(name1, name2);
    }
  }


  // 检查合法性的时候需要运行API中的一个函数，观察两者是否相等
  // 既可以是轨道物体之间的，也可以是轨道物体与中心物体之间的

  /**
   * 计算社交网络中两个人之间的最短逻辑距离.
   * 
   * @return
   */
  // 获得最短逻辑距离,这个函数在检查合法性的时候也要用到
  public static int socialGetDistance(SocialNetworkCircle society, String name1, String name2) {
    CircularOrbitApis<SocialL1, SocialE1> socialApi = new CircularOrbitApis<SocialL1, SocialE1>();
    Set<SocialE1> tempFirst = society.getTrackObject().get(0);
    // Scanner disIn = new Scanner(System.in);
    // String name1;
    // String name2;
    SocialL1 central = society.getCentral();
    Map<String, SocialE1> friend = society.getFriend();
    // System.out.println("Do you want to get the shortest distance between two person(y or n)?");
    // String dis;
    // dis = disIn.next();
    // boolean sum = false;
    // System.out.println("Please input the first man's name:");
    // name1 = disIn.next();
    // System.out.println("Please input the second man's name:");
    // name2 = disIn.next();
    // society.shortestLogicalDistance(dname1, dname2);
    int distance;
    // CircularOrbitAPIs<socialL1, socialE1> socialApi = new
    // CircularOrbitAPIs<socialL1, socialE1>();
    if ((name1.equals(central.getName())) || (name2.equals(central.getName()))) {
      distance = 10000000;
      int centralDistance = 0;
      if ((name1.equals(central.getName()))) {
        if (!friend.containsKey(name2)) {
          System.out.println("Man two doesn't exist!");
          return -4;
        }
        Iterator<SocialE1> iterator = tempFirst.iterator();
        while (iterator.hasNext()) {
          // socialApi.getLogicalDistance要么return一个正数，要么return一个-2
          centralDistance =
              socialApi.getLogicalDistance(society, friend.get(name2), iterator.next());
          if (centralDistance >= 0 && centralDistance < distance) {
            distance = centralDistance;
          }
        }
        return distance + 1;
      } else {
        if (!friend.containsKey(name1)) {
          System.out.println("Man one doesn't exist!");
          return -4;
        }
        Iterator<SocialE1> iterator2 = tempFirst.iterator();
        while (iterator2.hasNext()) {
          centralDistance =
              socialApi.getLogicalDistance(society, friend.get(name1), iterator2.next());
          if (centralDistance >= 0 && centralDistance < distance) {
            distance = centralDistance;
          }
        }
        return distance + 1;
      }
    } else {

      if (!friend.containsKey(name1)) {
        System.out.println("Man one doesn't exist!");
        return -4;
      }
      if (!friend.containsKey(name2)) {
        System.out.println("Man two doesn't exist!");
        return -4;
      }
      distance = socialApi.getLogicalDistance(society, friend.get(name1), friend.get(name2));
      return distance;
    }
  }

  /**
   * 检查社交网络的合法性.
   * 
   * @return
   */
  public static boolean socialCheck(SocialNetworkCircle society) {
    // CircularOrbitAPIs<socialL1, socialE1> socialApi = new CircularOrbitAPIs<socialL1,
    // socialE1>();
    // Set<socialE1> temp = society.getTrackObject().get(0);
    Map<Integer, Set<SocialE1>> tempTrackObject = society.getTrackObject();
    Iterator<Integer> iterator = tempTrackObject.keySet().iterator();
    int length = 0;
    boolean is = true;
    while (iterator.hasNext()) { // 只关心层数
      int tempNumber = iterator.next();
      length++;
      Set<SocialE1> tempSocialE1Set = tempTrackObject.get(tempNumber);
      Iterator<SocialE1> iterator2 = tempSocialE1Set.iterator();
      while (iterator2.hasNext()) {
        int centralDistance;
        centralDistance =
            socialGetDistance(society, society.getCentral().getName(), iterator2.next().getName());
        if (centralDistance != length) {
          System.out.println("Illegal system!");
          return false;
        }
        // centralDistance = socialApi.getLogicalDistance(society, society.getCentral(),
        // iterator2.next());
        // if(centralDistance>distance)
        // {
        // distance = centralDistance;
        // }
        //
        // return distance;
      }
    }
    // System.out.println("Succeed!");
    return true;
  }

  /**
   * 直接与用户进行交互的主程序.
   * 
   * @param args 字符串
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    String type = "1";
    boolean b2 = false;
    Scanner in = new Scanner(System.in);
    while (!b2) {
      System.out.println("Please input the type of track(track,atom,social):");
      type = in.next();
      if (type.toLowerCase().equals("track") || type.toLowerCase().equals("atom")
          || type.toLowerCase().equals("social")) {
        b2 = true;
      } else {
        System.out.println("Sorry,this type is illegal.Please input again;");
      }
    }
    // 田径比赛是在安排比赛方案的时候建立轨道
    if (type.toLowerCase().equals("track")) {
      Scanner choiceOrder = new Scanner(System.in);
      String name = trackcreatingTrackFromFiles();
      FunctionTrackGame track = new FunctionTrackGame();
      track.concreteLogInitial();
      boolean size = true;
      // 选择读文件的方式
      System.out
          .println("Now please choose the method to read files:Stream(a) Reader(b) Scanner(c)");
      String read = choiceOrder.next();
      boolean choose2 = true;
      boolean fina = false;
      while (choose2) {
        if (read.toLowerCase().equals("a")) {
          TrackStrategyOrganizerInput organizer = new TrackStrategyOrganizerInput("a");
          if (!organizer.arrange(track, name)) {
            System.out.println("The file has problem!Input again?");
            String againYes = choiceOrder.next();
            if (againYes.equals("n")) {
              choose2 = false;
            } else {
              System.out.println("please input:");
              read = choiceOrder.next();
            }
          } else {
            choose2 = false;
            fina = true;
          }

        } else if (read.toLowerCase().equals("b")) {
          TrackStrategyOrganizerInput organizer = new TrackStrategyOrganizerInput("b");
          if (!organizer.arrange(track, name)) {
            System.out.println("The file has problem!Input again?");
            String againYes = choiceOrder.next();
            if (againYes.equals("n")) {
              choose2 = false;
            } else {
              System.out.println("please input:");
              read = choiceOrder.next();
            }
          } else {
            choose2 = false;
            fina = true;
          }
        } else if (read.toLowerCase().equals("c")) {
          TrackStrategyOrganizerInput organizer = new TrackStrategyOrganizerInput("c");
          if (!organizer.arrange(track, name)) {
            System.out.println("The file has problem!Input again?");
            String againYes = choiceOrder.next();
            if (againYes.equals("n")) {
              choose2 = false;
            } else {
              System.out.println("please input:");
              read = choiceOrder.next();
            }
          } else {
            choose2 = false;
            fina = true;
          }
        } else {
          System.out.println("Wrong order!Input again?");
          String yes = choiceOrder.next();
          if (yes.toLowerCase().equals("n")) {
            choose2 = false;
          } else {
            System.out.println("Please input:");
            read = choiceOrder.next();;
          }
        }
      }
      if (!fina) {
        in.close();
        return;
      }
      // while ((!track.creatingTrackFromFiles(name)) && size) {
      // System.out.println("choose again?(Y or N)");
      // String yes = choiceOrder.next();
      // if (yes.equals("n") || yes.equals("N")) {
      // size = false;
      // } else {
      // name = trackcreatingTrackFromFiles();
      // }
      // }
      //
      //
      //
      // if (size == false) {
      // return;
      // }
      int order;
      String another;
      boolean again = true;
      boolean choose = true;
      boolean fina2 = false;
      System.out.println("Now please choose the method to divede group(R indicates"
          + " random,G indicates results ahead):");
      String choice = choiceOrder.next();
      while (choose) {
        if (choice.toLowerCase().equals("r")) {
          trackOrganizer1(track);
          fina2 = true;
          choose = false;
        } else if (choice.toLowerCase().equals("g")) {
          trackOrganizer2(track);
          fina2 = true;
          choose = false;
        } else {
          System.out.println("Wrong order!Input again?");
          String yes = choiceOrder.next();
          if (yes.toLowerCase().equals("n")) {
            choose = false;
          } else {
            System.out.println("Please input:");
            choice = choiceOrder.next();;
          }
        }
      }
      if (fina2) {
        System.out.println("Now please input the number of operations:");
        System.out.println("1  visualization");
        System.out.println("2  add track");
        System.out.println("3  add object");
        System.out.println("4  delete track");
        System.out.println("5  delete object");
        System.out.println("6  entrophy");
        System.out.println("7  exchange athlete's track");// 分同一组和不同组
        System.out.println("8  check the legitimacy of the track system");
        System.out.println("9  search for log");
        System.out.println("10  write it back");
        System.out.println("0  end");
        order = choiceOrder.nextInt();
        while (!((order >= 0) && (order <= 10))) {
          System.out.println("Wrong order!Input again:");
          order = choiceOrder.nextInt();
        }
        while (again) {
          switch (order) {
            case 0:
              // 结束操作
              again = false;
              break;
            case 1:
              // 调用可视化函数
              trackVisualize(track);
              break;
            case 2:
              // 增加轨道
              trackAddTrack(track);
              break;
            case 3:
              // 向特定轨道上增加物体
              trackAddObject(track);
              break;
            case 4:
              // 删除轨道
              trackDeleteTrack(track);
              break;
            case 5:
              // 在特定轨道上删除物体,没有给出正确提示
              trackDeleteObject(track);
              break;
            case 6:
              System.out.println("The entropy of the system is:" + trackEntropy(track));
              break;
            case 7:
              // 更换轨道
              trackChange(track);
              break;
            case 8:
              // 检查轨道系统的合法性
              trackCheck(track);
              break;
            case 9:
              logSearch();
              break;
            case 10:
              trackWriteBack(track);
              break;
            default:
              break;
          }
          if (again) {
            System.out.println("operate again?(y or n)");
            another = choiceOrder.next();
            if (another.toLowerCase().equals("n")) {
              again = false;
            } else {
              System.out.println("Please input the order:");
              order = choiceOrder.nextInt();
              while (!((order >= 0) && (order <= 10))) {
                System.out.println("Wrong order!Input again:");
                order = choiceOrder.nextInt();
              }
            }
          }
        }
      }
      in.close();
      System.out.println("结束了");
    } else if (type.toLowerCase().equals("atom")) {
      AtomCareTaker careTaker = new AtomCareTaker();
      AtomOriginator originator = new AtomOriginator();
      String name = atomcreatingTrackFromFiles();// 没问题
      Scanner choiceOrder = new Scanner(System.in);
      AtomStructure atom = new AtomStructure();// 没问题
      atom.concreteLogInitial();

      // 选择读文件的方式
      System.out
          .println("Now please choose the method to read files:Stream(a) Reader(b) Scanner(c)");
      String read = choiceOrder.next();
      boolean choose2 = true;
      boolean fina = false;
      while (choose2) {
        if (read.toLowerCase().equals("a")) {
          AtomStrategyOrganizerInput organizer = new AtomStrategyOrganizerInput("a");
          if (!organizer.arrange(atom, name)) {
            System.out.println("The file has problem!Input again?");
            String againYes = choiceOrder.next();
            if (againYes.equals("n")) {
              choose2 = false;
            } else {
              System.out.println("please input:");
              read = choiceOrder.next();
            }
          } else {
            choose2 = false;
            fina = true;
          }

        } else if (read.toLowerCase().equals("b")) {
          AtomStrategyOrganizerInput organizer = new AtomStrategyOrganizerInput("b");
          if (!organizer.arrange(atom, name)) {
            System.out.println("The file has problem!Input again?");
            String againYes = choiceOrder.next();
            if (againYes.equals("n")) {
              choose2 = false;
            } else {
              System.out.println("please input:");
              read = choiceOrder.next();
            }
          } else {
            choose2 = false;
            fina = true;
          }
        } else if (read.toLowerCase().equals("c")) {
          AtomStrategyOrganizerInput organizer = new AtomStrategyOrganizerInput("c");
          if (!organizer.arrange(atom, name)) {
            System.out.println("The file has problem!Input again?");
            String againYes = choiceOrder.next();
            if (againYes.equals("n")) {
              choose2 = false;
            } else {
              System.out.println("please input:");
              read = choiceOrder.next();
            }
          } else {
            choose2 = false;
            fina = true;
          }
        } else {
          System.out.println("Wrong order!Input again?");
          String yes = choiceOrder.next();
          if (yes.toLowerCase().equals("n")) {
            choose2 = false;
          } else {
            System.out.println("Please input:");
            read = choiceOrder.next();;
          }
        }
      }
      if (!fina) {
        in.close();
        return;
      }

      // boolean size = true;
      // while ((!atom.creatingTrackFromFiles(name)) && size) {
      // System.out.println("choose again?(Y or N)");
      // String yes = choiceOrder.next();
      // if (yes.equals("n") || yes.equals("N")) {
      // size = false;
      // } else {
      // name = atomcreatingTrackFromFiles();
      // }
      // }
      // if (size == false) {
      // return;
      // }
      atom.creatingTrack(atom.getTrackNumber2(), atom.getTrackObjectNumber());
      System.out.println("Now please input the number of operations:");
      System.out.println("1  visualization");
      System.out.println("2  add track");
      System.out.println("3  add object");
      System.out.println("4  delete track");
      System.out.println("5  delete object");
      System.out.println("6  entrophy");
      System.out.println("7  transit of electronic");
      System.out.println("8  go back to states before");
      System.out.println("9  search for log");
      System.out.println("10 write it back");
      System.out.println("0  end");
      int order;
      order = choiceOrder.nextInt();
      while (!((order >= 0) && (order <= 10))) {
        System.out.println("Wrong order!Input again:");
        order = choiceOrder.nextInt();
      }
      boolean again = true;
      while (again) {
        switch (order) {
          case 0:
            // 操作结束
            again = false;
            break;
          case 1:
            // 调用可视化函数
            atomVisualize(atom);
            break;
          case 2:
            // 增加轨道
            atomAddTrack(atom);
            break;
          case 3:
            // 向特定轨道上增加物体
            atomAddObject(atom);
            break;
          case 4:
            // 删除轨道
            atomDeleteTrack(atom);
            break;
          case 5:
            // 在特定轨道上删除物体
            atomDeleteObject(atom);
            break;
          case 6:
            // 求熵值
            System.out.println("The entropy of the system is:" + atomEntropy(atom));
            break;
          case 7:
            // 电子跃迁
            atomTransit(atom, careTaker, originator);// 没问题
            break;
          case 8:
            // 回溯
            atomGoBack(atom, careTaker, originator);// 有问题
            break;
          case 9:
            logSearch();
            break;
          case 10:
            atomWriteBack(atom);
            break;
          default:
            break;
        }
        if (again) {
          System.out.println("operate again?(y or n)");
          String another;
          another = choiceOrder.next();
          if (another.toLowerCase().equals("n")) {
            again = false;
          } else {
            System.out.println("Please input the order:");
            order = choiceOrder.nextInt();
            while (!((order >= 0) && (order <= 10))) {
              System.out.println("Wrong order!Input again:");
              order = choiceOrder.nextInt();
            }
          }
        }
      }
      in.close();
      System.out.println("结束了");// 以上代码没有问题
    } else if (type.toLowerCase().equals("social")) {

      Scanner choiceOrder = new Scanner(System.in);
      String name = socialcreatingTrackFromFiles();
      SocialNetworkCircle society = new SocialNetworkCircle();
      society.concreteLogInitial();
      // society.creatingTrackFromFiles(name);// 从文件构造轨道
      boolean size = true;

      // 选择读文件的方式
      System.out
          .println("Now please choose the method to read files:Stream(a) Reader(b) Scanner(c)");
      String read = choiceOrder.next();
      boolean choose2 = true;
      boolean fina = false;
      while (choose2) {
        if (read.toLowerCase().equals("a")) {
          SocialStrategyOrganizerInput organizer = new SocialStrategyOrganizerInput("a");
          if (!organizer.arrange(society, name)) {
            System.out.println("The file has problem!Input again?");
            String againYes = choiceOrder.next();
            if (againYes.equals("n")) {
              choose2 = false;
            } else {
              System.out.println("please input:");
              read = choiceOrder.next();
            }
          } else {
            choose2 = false;
            fina = true;
          }

        } else if (read.toLowerCase().equals("b")) {
          SocialStrategyOrganizerInput organizer = new SocialStrategyOrganizerInput("b");
          if (!organizer.arrange(society, name)) {
            System.out.println("The file has problem!Input again?");
            String againYes = choiceOrder.next();
            if (againYes.equals("n")) {
              choose2 = false;
            } else {
              System.out.println("please input:");
              read = choiceOrder.next();
            }
          } else {
            choose2 = false;
            fina = true;
          }
        } else if (read.toLowerCase().equals("c")) {
          SocialStrategyOrganizerInput organizer = new SocialStrategyOrganizerInput("c");
          if (!organizer.arrange(society, name)) {
            System.out.println("The file has problem!Input again?");
            String againYes = choiceOrder.next();
            if (againYes.equals("n")) {
              choose2 = false;
            } else {
              System.out.println("please input:");
              read = choiceOrder.next();
            }
          } else {
            choose2 = false;
            fina = true;
          }
        } else {
          System.out.println("Wrong order!Input again?");
          String yes = choiceOrder.next();
          if (yes.toLowerCase().equals("n")) {
            choose2 = false;
          } else {
            System.out.println("Please input:");
            read = choiceOrder.next();;
          }
        }
      }
      if (!fina) {
        in.close();
        return;
      }


      // 建立轨道和读文件是分开的
      society.creatingTrack();
      System.out.println("Now please input the order of operations:");
      System.out.println("1  visualization");
      System.out.println("2  add track");
      System.out.println("3  add object");
      System.out.println("4  delete track");
      System.out.println("5  delete object");
      System.out.println("6  entrophy");
      System.out.println("7  degree of diffusion");// 信息扩散度
      System.out.println("8  remove/add a relationship and adjust the socialNet");
      System.out.println("9  shortest logical distance between two person");
      System.out.println("10 check the legitimacy of the track system");
      System.out.println("11  search for log");
      System.out.println("12  write it back");
      System.out.println("0  end");
      int order;
      order = choiceOrder.nextInt();
      while (!((order >= 0) && (order <= 12))) {
        System.out.println("Wrong order!Input again:");
        order = choiceOrder.nextInt();
      }
      boolean again = true;
      while (again) {
        switch (order) {
          case 0:
            again = false;
            break;
          case 1:
            // 调用可视化函数
            socialVisualize(society);
            break;
          case 2:
            // 增加轨道,与中心点物体没有关系的人也能出现在轨道上
            socialAddTrack(society);
            break;
          case 3:
            // 向特定轨道上增加物体,与中心点物体没有关系的人也能出现在轨道上
            socialAddObject(society);
            break;
          case 4:
            // 删除轨道,删除第一条轨道,已经重新调整图结构
            // 调整之后的结构不对(已解决)，只要删的是第一条轨道，再复杂的社交网络也退化为一个圆圈中间点一个点
            socialDeleteTrack(society);
            break;
          case 5:
            // 在特定轨道上删除物体,需要重新调整图结构
            // 需要改进，1不应该让用户输入具体的轨道数字(已解决)2调整之后的结构不对(已解决)
            socialDeleteObject(society);
            break;
          case 6:
            // 求熵值
            System.out.println("The entropy of the system is:" + socialEntropy(society));
            break;
          case 7:
            // 求通过第一层的某个好友能间接认识多少个好友
            socialIntimacy(society);// 有问题（已解决）
            break;
          case 8:
            // 增加/删除关系后重新调整图结构
            socialAR(society);// 仅限于轨道物体间的关系增减，未将中心物体包含在内,需要重新调整图结构(已解决)
            break;
          case 9:
            String name1;
            String name2;
            Scanner disIn = new Scanner(System.in);
            System.out.println("Please input the first man's name:");
            name1 = disIn.next();
            System.out.println("Please input the second man's name:");
            name2 = disIn.next();
            System.out.println(
                "The shortest logical distance is：" + socialGetDistance(society, name1, name2));
            // 问题1：方法有问题(已解决)
            // 问题2：仅限于轨道物体，未将中心物体包含在内（已改正）
            break;
          case 10:
            // 检查轨道系统的合法性
            socialCheck(society);// 有问题(已解决)
            break;
          case 11:
            logSearch();
            break;
          case 12:
            socailWriteBack(society);
            break;
          default:
            break;
        }
        if (again) {
          System.out.println("operate again?(y or n)");
          String another;
          another = choiceOrder.next();
          if ((another.toLowerCase().equals("n"))) {
            again = false;
          } else {
            System.out.println("Please input the order:");
            order = choiceOrder.nextInt();
            while (!((order >= 0) && (order <= 12))) {
              System.out.println("Wrong order!Input again:");
              order = choiceOrder.nextInt();
            }

          }
        }
      }
      in.close();
      System.out.println("结束了");// 只有将可视化窗口关闭，程序才会真正结束
      // // 可视化展示
      //
      // // (3) 用户提供必要信息，以增加新轨道、向特定轨道上增加物体；
      //
      // // (4) 用户提供必要信息，以从特定轨道上删除物体、删除整条轨道；
      //
      // // （5）计算熵值
      // System.out.println("The entropy of the system is:" + sociakEntropy(society));
      // // 特有功能：计算好友扩散度
      // socialIntimacy(society);
      // // 特有功能：增加/删除一条关系
      // socialAR(society);
      // // 特有功能：计算逻辑距离
      // socialGetDistance(society);
    }

  }

}
