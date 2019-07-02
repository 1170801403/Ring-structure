package circularorbit;

import track.*;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import centralobject.*;
import mylog.MyFormatter;
import physicalobject.*;
import track.*;

// 接口中的泛型是object的，ConcreteCircularObject中的泛型是有限定的，
//ConcreteCircularObject子类中的泛型也是有限定的
// 如果接口和ConcreteCircularObject中的泛型都是<L extends L1,E extends E1>，就会出错
//接口后面必须加尖括号，否则会错，而且不能加extends，但接口本身的定义里可以加extends
public class ConcreteCircularObject<L, E> implements CircularOrbit<L, E> {
  L central;
  //默认状态，同一个包内可见，没有get函数，对于客户端代码来讲反而更加安全
  final List<Track> physical = new ArrayList<Track>();
  //列表编号表示轨道层号，轨道对象中包含物体E,未把Track当成占位符
  final Map<Integer, Set<E>> trackObject = new HashMap<Integer, Set<E>>();// 轨道映射物体的列表
  final Map<E, Integer> objectTrack = new HashMap<E, Integer>();// 物体映射轨道的列表
  final Map<E, Set<E>> relationship = new HashMap<E, Set<E>>();// 存储轨道物体之间的关系
  final Set<E> lerelationship = new HashSet<E>();// 存储中心点物体和轨道物体之间的关系，在社交网络中是指第一层人
  final Map<E, Double> angle = new HashMap<E, Double>();// 存储每个物体的角度
  // final Map<E, Integer> objectGroup = new HashMap<E, Integer>();// 物体到组别的映射
  final PhysicalShelf shelf = new PhysicalShelf();
  final TrackFactory realTrackFactory = new TrackFactory();
  final List<Tie> socialTie = new ArrayList<Tie>();
  //非private,便于子类获取,但是客户端想要获取只能通过getXXX函数,而getXXX函数是做了防御性克隆的
  final Logger log = java.util.logging.Logger.getLogger("concreteLog");

  /**
   * 作为构造函数，新建一个轨道系统.
   */
  public void concreteLogInitial() {
    try {
      this.log.setUseParentHandlers(false); // 禁用日志原本处理类
      FileHandler fileHandler = new FileHandler("log/log");
      fileHandler.setFormatter(new MyFormatter());
      this.log.addHandler(fileHandler); // 添加Handler
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }

  // Abstraction function:
  // 具体实现一个circularOrbit接口，从文件中读入并建立轨道，
  // 采用不同的数据结构存储轨道物体、中心物体、轨道物体与中心物体之间的关系、中心物体与轨道物体之间的关系，
  // 并实现增减轨道物体、增减轨道、增减关系、轨道物体跃迁等功能
  // Representation invariant:
  // 轨道物体，轨道，中心点物体都是不可变的，通过具体的数据结构建立它们之间的联系
  // Safety from rep exposure:
  // 在返回重要数据结构时，做了防御性克隆

  /**
   * 保证ConcreteCircularObject类的表示不变性.
   */
  public void checkRep() {
//    assertTrue(physical != null);
    // assertTrue(relationship.size() > 0);

  }

  /**
   * 无前置条件.
   * 
   * @return 克隆之后的存储轨道的列表
   */
  public List<Track> getPhysical() {
    List<Track> tempphysical = new ArrayList<Track>();
    for (int i = 0; i < physical.size(); i++) {
      tempphysical.add(physical.get(i));
    }
    // log.info("Concrete:assert physical is not empty,in class ConcreteCircularObject,method
    // getPhysical");
    // assert tempphysical.size() != 0 : "physical is empty";// 后置条件
    log.info("Concrete:return physical list,in class ConcreteCircularObject,method getPhysical");
    return tempphysical;
  }

  @Override

  public List<Tie> getSocialTie() {
    List<Tie> tempSocialTie = new ArrayList<Tie>();
    for (int i = 0; i < socialTie.size(); i++) {
      tempSocialTie.add(socialTie.get(i));
    }
    // 不同轨道情况不一样，可以为0
    // assert tempSocialTie.size() != 0 : "socialTie is empty";//后置条件
    log.info("Concrete:return socialTie list,in class ConcreteCircularObject,method getSocialTie");
    return tempSocialTie;
  }

  @Override
  public L getCentral() { // L是不可变的
    // 田径比赛中是没有中心点物体的
    // assert central != null : "central is null";//前置条件
    log.info("Concrete:return central,in class ConcreteCircularObject,method getCentral");
    return central;
  }

  @Override 
  public Set<E> getLErelationship() { // 返回轨道上所有的物体,理论上讲轨道上的所有物体与中心物体都有关系
    Iterator<E> iterator = lerelationship.iterator();
    Set<E> etemp = new HashSet<E>();
    E temp;
    while (iterator.hasNext()) {
      temp = iterator.next();
      etemp.add(temp);
    }
    // 可以等于0
    // assert etemp.size() != 0 : "LErelationship is null";//后置条件
    log.info("Concrete:return LErelationship set");
    return etemp;
  }

  @Override
  public Map<Integer, Set<E>> getTrackObject() {
    Map<Integer, Set<E>> temp = new HashMap<Integer, Set<E>>();
    Iterator<Integer> iterator = trackObject.keySet().iterator();
    while (iterator.hasNext()) {
      Integer etemp = iterator.next();
      Set<E> stemp = new HashSet<E>();
      temp.put(etemp, stemp);
      Iterator<E> iterator2 = trackObject.get(etemp).iterator();
      while (iterator2.hasNext()) {
        temp.get(etemp).add(iterator2.next());
      }
    }
    log.info(
        "Concrete:assert trackObject is not empty,"
        + "in class ConcreteCircularObject,method getTrackObject");
    // assert temp.size() != 0 : "trackObject is empty";// 后置条件
    log.info(
        "Concrete:return trackObject map,in class ConcreteCircularObject,method getTrackObject");
    return temp;
  }

  @Override
  public Map<E, Set<E>> getRelationship() {
    Map<E, Set<E>> temp = new HashMap<E, Set<E>>();
    Iterator<E> iterator = relationship.keySet().iterator();
    while (iterator.hasNext()) {
      E etemp = iterator.next();// 轨道物体，中心物体，轨道都是不可变的好处
      Set<E> stemp = new HashSet<E>();
      temp.put(etemp, stemp);
      Iterator<E> iterator2 = relationship.get(etemp).iterator();
      while (iterator2.hasNext()) {
        temp.get(etemp).add(iterator2.next());
      }
      // 不同轨道情况不一样，可以为0
      // assert temp.size() != 0 : "relationship is empty";//后置条件
    }
    log.info("Concrete:return reltionship,in class ConcreteCircularObject,method getRelationship");
    return temp;
  }

  @Override
  public Map<E, Integer> getObjectTrack() {
    //System.out.println("real size"+objectTrack.size());
    Map<E, Integer> temp = new HashMap<E, Integer>();
    Iterator<E> iterator = objectTrack.keySet().iterator();
    while (iterator.hasNext()) {
      E tempe = iterator.next();
      int m = objectTrack.get(tempe);
      temp.put(tempe, m);
    }
    log.info(
        "Concrete:assert objectTrack is not empty,in class ConcreteCircularObject,"
        + "method getObjectTrack");
    // assert temp.size() != 0 : "objectTrack is empty";// 后置条件
    log.info("Concrete:return objecttrack,in class ConcreteCircularObject,method getObjectTrack");
    return temp;
  }

  @Override
  public Map<E, Double> getAngle() {
    Map<E, Double> temp = new HashMap<E, Double>();
    Iterator<E> iterator = angle.keySet().iterator();
    while (iterator.hasNext()) {
      E tempe = iterator.next();// 客户端代码就算删，也只能删指针，影响不到原来的angle
      double m = angle.get(tempe);
      temp.put(tempe, m);// 自动包装
    }
    log.info(
        "Concrete:assert Angle is not empty,in class ConcreteCircularObject,method getObjectTrack");
    // assert temp.size() != 0 : "angle is empty";// 后置条件
    log.info("Concrete:return Angle,in class ConcreteCircularObject,method getAngle");
    return temp;
  }

  @Override
  public void addTrack() { // 增加一条轨道,需要首先确定轨道半径，必须大于physical的size
    int rep = physical.size() + 1;// 轨道半径 = 轨道编号+1
    Track temp = realTrackFactory.manufacture(rep);
    physical.add(temp);
    trackObject.put(physical.size() - 1, new HashSet<E>());
    log.info(
        "Concrete:assert physical is not empty,in class ConcreteCircularObject,method addTrack");
    //assert physical.size() != 0 : "physical is empty";// 后置条件
    log.info("Concrete:add a track,in class ConcreteCircularObject,method addTrack");
    // System.out.println("Succeed!");
  }

  @Override
  public void deleteTrack(int number) { // 去除一条轨道,输入轨道在列表中的编号
    // if (number >= physical.size()) 
    // {
    // System.out.println("The track is out of bound!");
    // return;
    // }
    // 用断言代替if语句，好处是可以打开和关闭断言
    // 参数来自外部，其实应该用异常来编写
    // log.info("Concrete:assert track number is legal,in class ConcreteCircularObject,method
    // deleteTrack");
    // assert number >= 0 && number < physical.size() : "The track is out of bound";
    // physical.remove(number);//physical是控制轨道编号的，只能增加轨道，不能减少轨道
    // System.out.println(1);
    // System.out.println(trackObject.size());
    try {
      if (!trackObject.containsKey(number)) {
        throw new Exception("the track doesn't exist!");
      }
      if (!(number >= 0 && number < physical.size())) {
        throw new Exception("The track is out of bound");

      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return;
      // TODO: handle exception
    }

    Iterator<E> iterator = trackObject.get(number).iterator();
    while (iterator.hasNext()) {
      E temp = iterator.next();
      if (objectTrack.containsKey(temp)) {
        objectTrack.remove(temp);
      }
      
      if (relationship.containsKey(temp)) {
        Iterator<E> iterator2 = relationship.get(temp).iterator();
        while (iterator2.hasNext()) {
          E temp1 = iterator2.next();
          if (relationship.get(temp1).contains(temp)) {
            relationship.get(temp1).remove(temp);
          }
        }
        relationship.remove(temp);// 删除该物体与其他物体间的关系
      }
      
      if (lerelationship.contains(temp)) {
        lerelationship.remove(temp);
      }
    } 
    // 物体不再出现在该轨道中，在田径比赛中相当于出局
    trackObject.remove(number);// 删除轨道对物体的映射
    log.info(
        "Concrete:assert physical number is legal,"
        + "in class ConcreteCircularObject,method deleteTrack");
    assert physical.size() != 0 : "physical is empty";// physical是控制轨道编号的，只能增加轨道，不能减少轨道
    log.info(
        "Concrete:delete track " + number + ",in class ConcreteCircularObject,method deleteTrack");
    // System.out.println(trackObject.size());
    // System.out.println("Succeed!");
  }

  @Override
  public boolean addTrackObject(E ob, int t) { // 向特定轨道上增加一个物体（不考虑物理位置）
    // 参数来自外部，不适合用assert处理
    try {
      if (t >= physical.size()) {
        throw new Exception("the track is out of bound!");
      }
      if (!trackObject.containsKey(t)) {
        throw new Exception("the track has been deleted!");
      }
    } catch (Exception e) {
      log.info("Concrete:exception happens in class ConcreteCircularObject,method addTrackObject:"
          + e.getMessage()
          + " handling:return false,and the exception message is printed on console");
      System.out.println(e.getMessage());
      return false;
      // TODO: handle exception
    }

    trackObject.get(t).add(ob);// 设置轨道到物体的映射
    objectTrack.put(ob, t);// 设置物体到轨道的映射
    angle.put(ob, 0.00);// 初始时角度为0
    log.info(
        "Concrete:assert physical isn't empty,in class ConcreteCircular,method addTrackObject");
    //assert physical.size() != 0 : "physical is empty";// 后置条件
    log.info("Concrete:another object is added to track " + t);
    return true;
  }

  @Override
  public boolean deleteTrackObject(E ob, int t) { // 向特定轨道上删除一个物体（不考虑物理位置）
    // 参数来自外部，不适合用assert处理
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
      log.warning(
          "Concrete:exception happens in class ConcreteCircularObject,method deleteTrackObject:"
              + e.getMessage()
              + " handling:return false,and the exception message is printed on console");
      System.out.println(e.getMessage());
      return false;
      // TODO: handle exception
    }

    // 删除关系一定要删的彻底
    trackObject.get(t).remove(ob);// 删除轨道到物体的映射
    if (objectTrack.containsKey(ob)) {
      objectTrack.remove(ob, t);// 删除物体到轨道的映射
    }
    if (angle.containsKey(ob)) {
      angle.remove(ob);// 删除物体到角度的映射
    }
    if (relationship.containsKey(ob)) {
      Iterator<E> iterator = relationship.get(ob).iterator();
      while (iterator.hasNext()) {
        E temp1 = iterator.next();
        if (relationship.get(temp1).contains(ob)) {
          relationship.get(temp1).remove(ob);
        }
      }
      relationship.remove(ob);// 删除该物体与其他物体间的关系
    }
    if (lerelationship.contains(ob)) {
      lerelationship.remove(ob);// 删除该物体与中心物体的关系
    }
    log.info(
        "Concrete:assert physical isn't empty,in class ConcreteCircular,method deleteTrackObject");
    assert physical.size() != 0 : "physical is empty";// 后置条件
    // System.out.println("Succeed!");
    log.info("Concrete:another object is deleted from track " + t);
    return true;

  }

  @Override
  public void addCentralObject(L cre) { // 增加中心点物体
    central = cre;
    log.info(
        "Concrete:assert central object isn't null,"
        + "in class ConcreteCircularObject,method addCentralObject");
    assert central != null : "central is null";
    log.info("Concrete:add a central object");
  }

  @Override
  public boolean creatingTrackFromFiles(String name) { // 从外部文件读取数据构造轨道系统对象
    // 留到子类中实现
    return true;
  }

  @Override
  public boolean transit(E ob, int t) { 
    // 原子结构中的电子可以跃迁(需要重新实现transit方法)，运动员更换赛道需要用到跃迁，
    //app更换轨道需要用到跃迁
    // 参数来自外部，不适合用assert处理，用exception处理
    //这些东西不能通过if语句不去做，这不是对文件的检查，这涉及到能否成功建立轨道
    try {
      if (t >= physical.size()) {
        throw new Exception("the target track is out of bound!");
      }
      if (!objectTrack.containsKey(ob)) {
        throw new Exception("The object is not in orbit!");
      }
    } catch (Exception e) {
      log.warning("Concrete:exception happens in class ConcreteCircularObject,method transit:"
          + e.getMessage() + " handling:return false,and exception message is printed on console");
      System.out.println(e.getMessage());
      return false;
      // TODO: handle exception
    }

    // 只改变物体在轨道上的位置，不涉及关系的改变
    int tempTrack = objectTrack.get(ob);
    trackObject.get(t).add(ob);// 目标轨道增加物体
    trackObject.get(tempTrack).remove(ob);// 原轨道删除物体
    objectTrack.put(ob, t);// 物体对应的轨道值改变
    log.info("Concrete:assert physical isn't empty,in class ConcreteCircularObject,method transit");
    assert physical.size() != 0 : "physical is empty";// 后置条件
    log.info("Concrete:transit a object to track " + t);
    return true;

  }

  @Override
  public boolean addEErelationship(E e1, E e2) {  
    
    // 参数来自外部，不适合用assert处理，考虑到程序的可扩展性，用异常处理
    // 在轨道上就说明不是中心物体，因为中心物体不在轨道上
    // 在广度优先搜索建立轨道的方案中，这个操作没有前置条件
    //    try {
//      if (!objectTrack.containsKey(e1)) {
//        throw new Exception("object1 is not in the orbit system!");
//      }
//      if (!objectTrack.containsKey(e2)) {
//        throw new Exception("object2 is not in the orbit system!");
//      }
//    } catch (Exception e) {
//      // TODO: handle exception
//      System.out.println(e.getMessage());
//      return false;
//    }
 
    if (!relationship.get(e1).contains(e2)) {
      relationship.get(e1).add(e2);
    }
    if (!relationship.get(e2).contains(e1)) {
      relationship.get(e2).add(e1);
    }
    log.info(
        "Concrete:assert physical isn't empty,in class ConcreteCircularObject,"
        + "method addEErelationship");
    assert physical.size() != 0 : "physical is empty";// 后置条件
    log.info("Concrete:add another relationship");
    return true;
  }

  // 删除关系
  @Override
  public boolean deleteEErelationship(E e1, E e2) {
    // 参数来自外部，不适合用assert处理，考虑到程序的可扩展性，用异常处理
    // 在轨道上就说明不是中心物体，因为中心物体不在轨道上
    // 在轨道上就说明不是中心物体，因为中心物体不在轨道上
    try {
      if (!objectTrack.containsKey(e1)) {
        throw new Exception("object1 is not in the orbit system!");
      }
      if (!objectTrack.containsKey(e2)) {
        throw new Exception("object2 is not in the orbit system!");
      }
    } catch (Exception e) {
      // TODO: handle exception
      System.out.println(e.getMessage());
      return false;
    }
    // if (!objectTrack.containsKey(e1))
    // {
    // System.out.println("object1 is not in the orbit system!");
    // return false;
    // }
    // if (!objectTrack.containsKey(e2))
    // {
    // System.out.println("object2 is not in the orbit system!");
    // return false;
    // }
    if (relationship.get(e1).contains(e2)) {
      relationship.get(e1).remove(e2);
    }
    if (relationship.get(e2).contains(e1)) {
      relationship.get(e2).remove(e1);
    }
    log.info(
        "Concrete:assert physical isn't empty,"
        + "in class ConcreteCircularObject,method addEErelationship");
    assert physical.size() != 0 : "physical is empty";// 后置条件
    log.info("Concrete:delete another relationship");
    return true;
  }

  @Override
  public boolean addLErelationship(E e2) {
    // 参数来自外部，不适合用assert处理，考虑到程序的可扩展性，用异常处理
//    try {
//      if (!objectTrack.containsKey(e2)) {
//        throw new Exception("The object1 is not in the orbit system!");
//      }
//    } catch (Exception e) {
//      // TODO: handle exception
//      System.out.println(e.getMessage());
//      return false;
//    }
    try {
      if(lerelationship.contains(e2))
      {
        throw new Exception("the person already in LErelationship");
      }
    } catch (Exception e) {
      // TODO: handle exception
      System.out.println(e.getMessage());
      return false;
    }
    lerelationship.add(e2);
    log.info(
        "Concrete:assert physical isn't empty,"
        + "in class ConcreteCircularObject,method addEErelationship");
    assert physical.size() != 0 : "physical is empty";// 后置条件
    log.info("Concrete:add another LErelationship");
    return true;
  }

  @Override
  public boolean deleteLErelationship(E e2) {
    // 参数来自外部，不适合用assert处理，考虑到程序的可扩展性，用异常处理
    try {
      if (!objectTrack.containsKey(e2)) {
        throw new Exception("object1 is not in the orbit system!");
      }
    } catch (Exception e) {
      // TODO: handle exception
      System.out.println(e.getMessage());
      return false;
    }
    if (lerelationship.contains(e2)) {
      lerelationship.remove(e2);
    }
    log.info(
        "Concrete:assert physical isn't empty,"
        + "in class ConcreteCircularObject,method addEErelationship");
    assert physical.size() != 0 : "physical is empty";// 后置条件
    log.info("Concrete:delete another LErelationship");
    return true;
  }

}
