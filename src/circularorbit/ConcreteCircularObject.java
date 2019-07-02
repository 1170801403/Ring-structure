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

// �ӿ��еķ�����object�ģ�ConcreteCircularObject�еķ��������޶��ģ�
//ConcreteCircularObject�����еķ���Ҳ�����޶���
// ����ӿں�ConcreteCircularObject�еķ��Ͷ���<L extends L1,E extends E1>���ͻ����
//�ӿں������Ӽ����ţ����������Ҳ��ܼ�extends�����ӿڱ���Ķ�������Լ�extends
public class ConcreteCircularObject<L, E> implements CircularOrbit<L, E> {
  L central;
  //Ĭ��״̬��ͬһ�����ڿɼ���û��get���������ڿͻ��˴��������������Ӱ�ȫ
  final List<Track> physical = new ArrayList<Track>();
  //�б��ű�ʾ�����ţ���������а�������E,δ��Track����ռλ��
  final Map<Integer, Set<E>> trackObject = new HashMap<Integer, Set<E>>();// ���ӳ��������б�
  final Map<E, Integer> objectTrack = new HashMap<E, Integer>();// ����ӳ�������б�
  final Map<E, Set<E>> relationship = new HashMap<E, Set<E>>();// �洢�������֮��Ĺ�ϵ
  final Set<E> lerelationship = new HashSet<E>();// �洢���ĵ�����͹������֮��Ĺ�ϵ�����罻��������ָ��һ����
  final Map<E, Double> angle = new HashMap<E, Double>();// �洢ÿ������ĽǶ�
  // final Map<E, Integer> objectGroup = new HashMap<E, Integer>();// ���嵽����ӳ��
  final PhysicalShelf shelf = new PhysicalShelf();
  final TrackFactory realTrackFactory = new TrackFactory();
  final List<Tie> socialTie = new ArrayList<Tie>();
  //��private,���������ȡ,���ǿͻ�����Ҫ��ȡֻ��ͨ��getXXX����,��getXXX���������˷����Կ�¡��
  final Logger log = java.util.logging.Logger.getLogger("concreteLog");

  /**
   * ��Ϊ���캯�����½�һ�����ϵͳ.
   */
  public void concreteLogInitial() {
    try {
      this.log.setUseParentHandlers(false); // ������־ԭ��������
      FileHandler fileHandler = new FileHandler("log/log");
      fileHandler.setFormatter(new MyFormatter());
      this.log.addHandler(fileHandler); // ���Handler
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }

  // Abstraction function:
  // ����ʵ��һ��circularOrbit�ӿڣ����ļ��ж��벢���������
  // ���ò�ͬ�����ݽṹ�洢������塢�������塢�����������������֮��Ĺ�ϵ������������������֮��Ĺ�ϵ��
  // ��ʵ������������塢���������������ϵ���������ԾǨ�ȹ���
  // Representation invariant:
  // ������壬��������ĵ����嶼�ǲ��ɱ�ģ�ͨ����������ݽṹ��������֮�����ϵ
  // Safety from rep exposure:
  // �ڷ�����Ҫ���ݽṹʱ�����˷����Կ�¡

  /**
   * ��֤ConcreteCircularObject��ı�ʾ������.
   */
  public void checkRep() {
//    assertTrue(physical != null);
    // assertTrue(relationship.size() > 0);

  }

  /**
   * ��ǰ������.
   * 
   * @return ��¡֮��Ĵ洢������б�
   */
  public List<Track> getPhysical() {
    List<Track> tempphysical = new ArrayList<Track>();
    for (int i = 0; i < physical.size(); i++) {
      tempphysical.add(physical.get(i));
    }
    // log.info("Concrete:assert physical is not empty,in class ConcreteCircularObject,method
    // getPhysical");
    // assert tempphysical.size() != 0 : "physical is empty";// ��������
    log.info("Concrete:return physical list,in class ConcreteCircularObject,method getPhysical");
    return tempphysical;
  }

  @Override

  public List<Tie> getSocialTie() {
    List<Tie> tempSocialTie = new ArrayList<Tie>();
    for (int i = 0; i < socialTie.size(); i++) {
      tempSocialTie.add(socialTie.get(i));
    }
    // ��ͬ��������һ��������Ϊ0
    // assert tempSocialTie.size() != 0 : "socialTie is empty";//��������
    log.info("Concrete:return socialTie list,in class ConcreteCircularObject,method getSocialTie");
    return tempSocialTie;
  }

  @Override
  public L getCentral() { // L�ǲ��ɱ��
    // �ﾶ��������û�����ĵ������
    // assert central != null : "central is null";//ǰ������
    log.info("Concrete:return central,in class ConcreteCircularObject,method getCentral");
    return central;
  }

  @Override 
  public Set<E> getLErelationship() { // ���ع�������е�����,�����Ͻ�����ϵ������������������嶼�й�ϵ
    Iterator<E> iterator = lerelationship.iterator();
    Set<E> etemp = new HashSet<E>();
    E temp;
    while (iterator.hasNext()) {
      temp = iterator.next();
      etemp.add(temp);
    }
    // ���Ե���0
    // assert etemp.size() != 0 : "LErelationship is null";//��������
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
    // assert temp.size() != 0 : "trackObject is empty";// ��������
    log.info(
        "Concrete:return trackObject map,in class ConcreteCircularObject,method getTrackObject");
    return temp;
  }

  @Override
  public Map<E, Set<E>> getRelationship() {
    Map<E, Set<E>> temp = new HashMap<E, Set<E>>();
    Iterator<E> iterator = relationship.keySet().iterator();
    while (iterator.hasNext()) {
      E etemp = iterator.next();// ������壬�������壬������ǲ��ɱ�ĺô�
      Set<E> stemp = new HashSet<E>();
      temp.put(etemp, stemp);
      Iterator<E> iterator2 = relationship.get(etemp).iterator();
      while (iterator2.hasNext()) {
        temp.get(etemp).add(iterator2.next());
      }
      // ��ͬ��������һ��������Ϊ0
      // assert temp.size() != 0 : "relationship is empty";//��������
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
    // assert temp.size() != 0 : "objectTrack is empty";// ��������
    log.info("Concrete:return objecttrack,in class ConcreteCircularObject,method getObjectTrack");
    return temp;
  }

  @Override
  public Map<E, Double> getAngle() {
    Map<E, Double> temp = new HashMap<E, Double>();
    Iterator<E> iterator = angle.keySet().iterator();
    while (iterator.hasNext()) {
      E tempe = iterator.next();// �ͻ��˴������ɾ��Ҳֻ��ɾָ�룬Ӱ�첻��ԭ����angle
      double m = angle.get(tempe);
      temp.put(tempe, m);// �Զ���װ
    }
    log.info(
        "Concrete:assert Angle is not empty,in class ConcreteCircularObject,method getObjectTrack");
    // assert temp.size() != 0 : "angle is empty";// ��������
    log.info("Concrete:return Angle,in class ConcreteCircularObject,method getAngle");
    return temp;
  }

  @Override
  public void addTrack() { // ����һ�����,��Ҫ����ȷ������뾶���������physical��size
    int rep = physical.size() + 1;// ����뾶 = ������+1
    Track temp = realTrackFactory.manufacture(rep);
    physical.add(temp);
    trackObject.put(physical.size() - 1, new HashSet<E>());
    log.info(
        "Concrete:assert physical is not empty,in class ConcreteCircularObject,method addTrack");
    //assert physical.size() != 0 : "physical is empty";// ��������
    log.info("Concrete:add a track,in class ConcreteCircularObject,method addTrack");
    // System.out.println("Succeed!");
  }

  @Override
  public void deleteTrack(int number) { // ȥ��һ�����,���������б��еı��
    // if (number >= physical.size()) 
    // {
    // System.out.println("The track is out of bound!");
    // return;
    // }
    // �ö��Դ���if��䣬�ô��ǿ��Դ򿪺͹رն���
    // ���������ⲿ����ʵӦ�����쳣����д
    // log.info("Concrete:assert track number is legal,in class ConcreteCircularObject,method
    // deleteTrack");
    // assert number >= 0 && number < physical.size() : "The track is out of bound";
    // physical.remove(number);//physical�ǿ��ƹ����ŵģ�ֻ�����ӹ�������ܼ��ٹ��
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
        relationship.remove(temp);// ɾ�������������������Ĺ�ϵ
      }
      
      if (lerelationship.contains(temp)) {
        lerelationship.remove(temp);
      }
    } 
    // ���岻�ٳ����ڸù���У����ﾶ�������൱�ڳ���
    trackObject.remove(number);// ɾ������������ӳ��
    log.info(
        "Concrete:assert physical number is legal,"
        + "in class ConcreteCircularObject,method deleteTrack");
    assert physical.size() != 0 : "physical is empty";// physical�ǿ��ƹ����ŵģ�ֻ�����ӹ�������ܼ��ٹ��
    log.info(
        "Concrete:delete track " + number + ",in class ConcreteCircularObject,method deleteTrack");
    // System.out.println(trackObject.size());
    // System.out.println("Succeed!");
  }

  @Override
  public boolean addTrackObject(E ob, int t) { // ���ض����������һ�����壨����������λ�ã�
    // ���������ⲿ�����ʺ���assert����
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

    trackObject.get(t).add(ob);// ���ù���������ӳ��
    objectTrack.put(ob, t);// �������嵽�����ӳ��
    angle.put(ob, 0.00);// ��ʼʱ�Ƕ�Ϊ0
    log.info(
        "Concrete:assert physical isn't empty,in class ConcreteCircular,method addTrackObject");
    //assert physical.size() != 0 : "physical is empty";// ��������
    log.info("Concrete:another object is added to track " + t);
    return true;
  }

  @Override
  public boolean deleteTrackObject(E ob, int t) { // ���ض������ɾ��һ�����壨����������λ�ã�
    // ���������ⲿ�����ʺ���assert����
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

    // ɾ����ϵһ��Ҫɾ�ĳ���
    trackObject.get(t).remove(ob);// ɾ������������ӳ��
    if (objectTrack.containsKey(ob)) {
      objectTrack.remove(ob, t);// ɾ�����嵽�����ӳ��
    }
    if (angle.containsKey(ob)) {
      angle.remove(ob);// ɾ�����嵽�Ƕȵ�ӳ��
    }
    if (relationship.containsKey(ob)) {
      Iterator<E> iterator = relationship.get(ob).iterator();
      while (iterator.hasNext()) {
        E temp1 = iterator.next();
        if (relationship.get(temp1).contains(ob)) {
          relationship.get(temp1).remove(ob);
        }
      }
      relationship.remove(ob);// ɾ�������������������Ĺ�ϵ
    }
    if (lerelationship.contains(ob)) {
      lerelationship.remove(ob);// ɾ������������������Ĺ�ϵ
    }
    log.info(
        "Concrete:assert physical isn't empty,in class ConcreteCircular,method deleteTrackObject");
    assert physical.size() != 0 : "physical is empty";// ��������
    // System.out.println("Succeed!");
    log.info("Concrete:another object is deleted from track " + t);
    return true;

  }

  @Override
  public void addCentralObject(L cre) { // �������ĵ�����
    central = cre;
    log.info(
        "Concrete:assert central object isn't null,"
        + "in class ConcreteCircularObject,method addCentralObject");
    assert central != null : "central is null";
    log.info("Concrete:add a central object");
  }

  @Override
  public boolean creatingTrackFromFiles(String name) { // ���ⲿ�ļ���ȡ���ݹ�����ϵͳ����
    // ����������ʵ��
    return true;
  }

  @Override
  public boolean transit(E ob, int t) { 
    // ԭ�ӽṹ�еĵ��ӿ���ԾǨ(��Ҫ����ʵ��transit����)���˶�Ա����������Ҫ�õ�ԾǨ��
    //app���������Ҫ�õ�ԾǨ
    // ���������ⲿ�����ʺ���assert������exception����
    //��Щ��������ͨ��if��䲻ȥ�����ⲻ�Ƕ��ļ��ļ�飬���漰���ܷ�ɹ��������
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

    // ֻ�ı������ڹ���ϵ�λ�ã����漰��ϵ�ĸı�
    int tempTrack = objectTrack.get(ob);
    trackObject.get(t).add(ob);// Ŀ������������
    trackObject.get(tempTrack).remove(ob);// ԭ���ɾ������
    objectTrack.put(ob, t);// �����Ӧ�Ĺ��ֵ�ı�
    log.info("Concrete:assert physical isn't empty,in class ConcreteCircularObject,method transit");
    assert physical.size() != 0 : "physical is empty";// ��������
    log.info("Concrete:transit a object to track " + t);
    return true;

  }

  @Override
  public boolean addEErelationship(E e1, E e2) {  
    
    // ���������ⲿ�����ʺ���assert�������ǵ�����Ŀ���չ�ԣ����쳣����
    // �ڹ���Ͼ�˵�������������壬��Ϊ�������岻�ڹ����
    // �ڹ������������������ķ����У��������û��ǰ������
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
    assert physical.size() != 0 : "physical is empty";// ��������
    log.info("Concrete:add another relationship");
    return true;
  }

  // ɾ����ϵ
  @Override
  public boolean deleteEErelationship(E e1, E e2) {
    // ���������ⲿ�����ʺ���assert�������ǵ�����Ŀ���չ�ԣ����쳣����
    // �ڹ���Ͼ�˵�������������壬��Ϊ�������岻�ڹ����
    // �ڹ���Ͼ�˵�������������壬��Ϊ�������岻�ڹ����
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
    assert physical.size() != 0 : "physical is empty";// ��������
    log.info("Concrete:delete another relationship");
    return true;
  }

  @Override
  public boolean addLErelationship(E e2) {
    // ���������ⲿ�����ʺ���assert�������ǵ�����Ŀ���չ�ԣ����쳣����
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
    assert physical.size() != 0 : "physical is empty";// ��������
    log.info("Concrete:add another LErelationship");
    return true;
  }

  @Override
  public boolean deleteLErelationship(E e2) {
    // ���������ⲿ�����ʺ���assert�������ǵ�����Ŀ���չ�ԣ����쳣����
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
    assert physical.size() != 0 : "physical is empty";// ��������
    log.info("Concrete:delete another LErelationship");
    return true;
  }

}
