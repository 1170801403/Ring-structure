package circularorbit;

import java.util.List;
import java.util.Map;
import java.util.Set;
import centralobject.L1;
import physicalobject.E1;
import physicalobject.SocialE1;

public interface CircularOrbit<L, E> {
  public static <L, E> CircularOrbit<L, E> empty() {
    return new ConcreteCircularObject<L, E>();
  }

  /**
   * 增加轨道 新建一个轨道对象(track)，并新建一个轨道到物体的映射.
   */
  public void addTrack();// 增加一条轨道

  /*
   * 删除一条轨道
   * 
   * @param 轨道的编号 在存储轨道的数据结构中删除这条轨道，
   * 同时在轨道系统中删除这条轨道上的所有物体. 这些物体所关联的关系也会被删除.
   */
  public void deleteTrack(int number);// 去除一条轨道

  /**
   * 增加中心点物体.
   * 
   * @param cre 中心物体 以给定的参数新建一个中心点物体类的实例.
   */
  public void addCentralObject(L cre);// 增加中心点物体

  /**
   * 在给定轨道上增加一个给定物体，需要处理异常情况如给定的轨道编号不存在.
   *  如果成功添加物体， 则新建物体到轨道的映射，物体到位置（角度）的映射，以及物体间关系的映射.
   * 
   * @param ob 轨道编号
   * @param t 轨道物体
   * @return 成功添加物体则返回true,否则返回false并给出错误提示
   */
  public boolean addTrackObject(E ob, int t);// 向特定轨道上增加一个物体（不考虑物理位置）

  /**
   * 删除给定轨道上的给定物体，需要处理异常情况如给定的轨道编号不存在.
   * 给定物体不在给定轨道上. 如果成功删除物体，则需同时删除轨道到该物体的映射，
   * 该物体到轨道的映射，该物体到位置（角度）的映射，
   * 该物体与轨道物体关联的关系，该物体与中心点物体关联的关系
   * 
   * @param ob 轨道编号
   * @param t 轨道物体
   * @return 成功删除物体则返回true,否则返回false并给出错误提示
   */
  public boolean deleteTrackObject(E ob, int t);// 向特定轨道上删除一个物体（不考虑物理位置）

  /**
   * 增加给定轨道物体与中心物体之间的关系，需要处理异常情况如给定物体不在系统中. 
   *  如果轨道物体符合要求，则在相应的数据结构Map中新增一对映射即可
   * 
   * @param e2 轨道物体
   * @return 如果增加成功则返回true,否则返回false并给出错误提示
   */
  public boolean addLErelationship(E e2);// 增加中心物体和轨道物体之间的关系

  /**
   * 增加两个给定轨道物体之间的关系，需要处理异常情况如给定物体不在系统中.
   *  如果轨道物体符合要求，则在相应的数据结构Map中新增一对映射即可
   * 
   * @param e1 轨道物体
   * @param e2 轨道物体
   * @return 如果增加成功则返回true,否则返回false并给出错误提示
   */
  public boolean addEErelationship(E e1, E e2);// 增加两个轨道物体之间的关系

  /**
   * 删除两个给定轨道物体之间的关系，需要处理异常情况如给定物体不在系统中.
   * 如果轨道物体符合要求，则在相应的数据结构Map中删除一对映射即可
   * 
   * 
   * @param e1 轨道物体
   * @param e2 轨道物体
   * @return 如果删除成功则返回true,否则返回false并给出错误提示
   */
  public boolean deleteEErelationship(E e1, E e2);

  /**
   * 删除给定轨道物体与中心物体之间的关系，需要处理异常情况如给定物体不在系统中. 
   * 如果轨道物体符合要求，则在相应的数据结构Map中删除一对映射即可
   * 
   * @param e2 轨道物体
   * @return
   */
  public boolean deleteLErelationship(E e2);

  /**
   * 从文件中读取数据，存储到相应的数据结构中，要求文件名不为空.
   * 
   * @param name 文件名
   * @return 读取数据成功则返回true,读取失败则返回false
   */
  public boolean creatingTrackFromFiles(String name);// 从外部文件读取数据构造轨道系统对象

  /**
   * 给定轨道系统和轨道编号，将该物体从原有轨道移动到轨道编号对应的轨道上. 
   * 需要处理异常情况如轨道物体不在轨道系统中，轨道编号超出范围等。移动成功后需要改变相应、
   * 的轨道到物体的映射、物体到轨道的映射
   * 
   * @param object 轨道物体
   * @param t 轨道编号
   * @return 如果跃迁成功则返回true,否则返回false并给出错误提示
   */
  public boolean transit(E object, int t);// 原子结构中的电子可以跃迁，社交网络中的人的地位可以变化

  /**
   * 通过防御性克隆安全返回轨道系统中轨道物体到其所在位置的映射.
   * 
   * @return 轨道物体到其所在位置的映射
   */
  public Map<E, Double> getAngle();

  /**
   * 通过防御性克隆安全返回轨道系统中轨道物体到其所在轨道的映射.
   * 
   * @return 轨道物体到其所在轨道的映射
   */
  public Map<E, Integer> getObjectTrack();

  /**
   * 通过防御性克隆安全返回轨道系统中存储轨道物体之间关系的映射.
   * 
   * @return 存储轨道物体之间关系的映射
   */
  public Map<E, Set<E>> getRelationship();

  /**
   * 通过防御性克隆安全返回轨道系统中轨道到其上所有的物体的映射.
   * 
   * @return 轨道到其上所有的物体的映射
   */
  public Map<Integer, Set<E>> getTrackObject();

  /**
   * 通过防御性克隆安全返回轨道系统中存储轨道物体与中心点物体关系的映射.
   * 
   * @return 存储轨道物体与中心点物体关系的映射
   */
  public Set<E> getLErelationship();

  /**
   * 通过防御性克隆安全返回轨道系统中存储轨道物体之间联系信息映射.
   * 
   * @return 存储轨道物体之间联系信息映射
   */
  public List<Tie> getSocialTie();

  /**
   * 返回轨道系统的中心点物体(不可变类型).
   * 
   * @return 轨道系统的中心点物体
   */
  public L getCentral();

}
