package circularorbittest;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;
import centralobject.L1;
import circularorbit.ConcreteCircularObject;
import physicalobject.E1;

// jUNITest中不能使用泛型
public class ConcreteCircularObjectTest {

  // Testing strategy for Edge.getSource()
  // 新建一个ConcreteCircularObject对象，调用addTrack()函数向系统中增加
  // 轨道，观察存储轨道的对象大小是否符合要求
  @Test
  public void testAddTrack() {
    ConcreteCircularObject<L1, E1> testObject = new ConcreteCircularObject<L1, E1>();
    testObject.addTrack();
    assertTrue(testObject.getTrackObject().size() == 1);
    assertTrue(testObject.getPhysical().size() == 1);
  }

  // Testing strategy for Edge.getSource()
  // 新建一个ConcreteCircularObject对象，调用addTrack()函数向系统中增加
  // 轨道，然后调用deleteTrack()函数删除轨道，分别用不同的参数调用deleteTrack()
  // 以观察是否能处理异常情况，最后观察存储轨道的对象大小是否符合要求
  // 异常情况由专门的异常文件处理
  @Test
  public void testDeleteTrack() {
    ConcreteCircularObject<L1, E1> testObject = new ConcreteCircularObject<L1, E1>();
    testObject.addTrack();
    testObject.addTrack();

    // testObject.deleteTrack(2);
    // assertTrue(testObject.getTrackObject().size() == 2);
    // assertTrue(testObject.getPhysical().size() == 2);
    testObject.deleteTrack(1);
    assertTrue(testObject.getTrackObject().size() == 1);
    assertTrue(testObject.getPhysical().size() == 2);// physical从来没有删除过
  }

  // Testing strategy for Edge.getSource()
  // 新建一个ConcreteCircularObject对象，调用addTrack()函数向系统中增加
  // 轨道，然后调用addTrackObject()函数向轨道上增加物体，分别用不同的参数调用addTrackObject()
  // 以观察是否能处理异常情况，最后观察存储轨道和物体的对象大小是否符合要求
  @Test
  public void testAddTrackObject() {
    ConcreteCircularObject<L1, E1> testObject = new ConcreteCircularObject<L1, E1>();
    E1 ob1 = new E1("a");
    testObject.addTrack();
    testObject.addTrack();
    // testObject.addTrackObject(ob1, 2);
    // assertTrue(testObject.getObjectTrack().size() == 0);
    // assertTrue(testObject.getAngle().size() == 0);
    // assertTrue(testObject.getRelationship().size() == 0);
    testObject.addTrackObject(ob1, 1);
    assertTrue(testObject.getObjectTrack().size() == 1);
    assertTrue(testObject.getAngle().size() == 1);
    assertTrue(testObject.getTrackObject().get(1).size() == 1);
    assertTrue(testObject.getTrackObject().get(0).size() == 0);
    assertTrue(testObject.getRelationship().size() == 1);
    E1 ob2 = new E1("b");
    testObject.addTrackObject(ob2, 1);
    assertTrue(testObject.getObjectTrack().size() == 2);
    assertTrue(testObject.getAngle().size() == 2);
    assertTrue(testObject.getTrackObject().get(1).size() == 2);
    assertTrue(testObject.getTrackObject().get(0).size() == 0);
    assertTrue(testObject.getRelationship().size() == 2);
  }

  // Testing strategy for Edge.getSource()
  // 新建一个ConcreteCircularObject对象，调用addTrack()函数向系统中增加
  // 轨道，然后调用addTrackObject()函数向轨道上增加物体，接着调用deleteTrackObject()
  // 函数，采用不同的参数调用deleteTrackObject()函数，以观察是否能处理异常情况，
  // 最后观察存储轨道和物体的对象大小是否符合要求
  @Test
  public void testDeleteTrackObject() {
    ConcreteCircularObject<L1, E1> testObject = new ConcreteCircularObject<L1, E1>();
    E1 ob1 = new E1("a");
    E1 ob2 = new E1("b");
    E1 ob3 = new E1("c");
    testObject.addTrack();
    testObject.addTrack();
    testObject.addTrackObject(ob1, 0);
    testObject.addTrackObject(ob2, 1);
    testObject.addTrackObject(ob3, 1);
    // 轨道越界
    testObject.deleteTrackObject(ob3, 2);
    assertTrue(testObject.getObjectTrack().size() == 3);
    assertTrue(testObject.getAngle().size() == 3);
    // 物体不在轨道上
    testObject.deleteTrackObject(ob3, 0);
    assertTrue(testObject.getObjectTrack().size() == 3);
    assertTrue(testObject.getAngle().size() == 3);
    testObject.deleteTrackObject(ob3, 1);
    assertTrue(testObject.getObjectTrack().size() == 2);
    assertTrue(testObject.getAngle().size() == 2);
    assertTrue(testObject.getTrackObject().get(1).size() == 1);
  }

  // Testing strategy for Edge.getSource()
  // 新建一个ConcreteCircularObject对象，调用addCentralObject函数向系统中增加
  // 中心，然后判断从轨道系统返回的中心物体是否是预期物体
  @Test
  public void addCentralObject() {
    ConcreteCircularObject<L1, E1> testObject = new ConcreteCircularObject<L1, E1>();
    L1 central = new L1("YES");
    testObject.addCentralObject(central);
    assertTrue(testObject.getCentral().equals(central));
  }


  // Testing strategy for Edge.getSource()
  // 新建一个ConcreteCircularObject对象，调用addTrack()函数向系统中增加
  // 轨道，然后调用addTrackObject()函数向轨道上增加物体，接着调用transit()
  // 函数，采用不同的参数调用transit()函数，以观察是否能处理异常情况，
  // 最后观察存储轨道和物体的对象大小是否符合要求
  @Test
  public void testTransit() {
    ConcreteCircularObject<L1, E1> testObject = new ConcreteCircularObject<L1, E1>();
    E1 ob1 = new E1("a");
    E1 ob2 = new E1("b");
    E1 ob3 = new E1("c");
    E1 ob4 = new E1("d");
    testObject.addTrack();
    testObject.addTrack();
    testObject.addTrack();
    testObject.addTrackObject(ob1, 0);
    testObject.addTrackObject(ob2, 1);
    testObject.addTrackObject(ob3, 1);
    testObject.addTrackObject(ob4, 2);
    testObject.transit(ob1, 3);
    assertTrue(testObject.getObjectTrack().size() == 4);
    assertTrue(testObject.getTrackObject().get(0).size() == 1);
    E1 ob5 = new E1("e");
    testObject.transit(ob5, 1);
    assertTrue(testObject.getObjectTrack().size() == 4);
    assertTrue(testObject.getTrackObject().get(1).size() == 2);
    testObject.transit(ob1, 2);
    assertTrue(testObject.getObjectTrack().size() == 4);
    assertTrue(testObject.getTrackObject().get(0).size() == 0);
    assertTrue(testObject.getTrackObject().get(2).size() == 2);
  }

  // Testing strategy for Edge.getSource()
  // 新建一个ConcreteCircularObject对象，调用addTrack()函数向系统中增加
  // 轨道，然后调用addTrackObject()函数向轨道上增加物体，接着调用addEErelationship()
  // 函数，在轨道物体间增加关系，采用不同的参数调用addEErelationship()函数，以观察是否
  // 能处理异常情况，最后观察存储轨道和物体的对象大小是否符合要求
  @Test
  public void testAddEErelationship() {
    ConcreteCircularObject<L1, E1> testObject = new ConcreteCircularObject<L1, E1>();
    E1 ob1 = new E1("a");
    E1 ob2 = new E1("b");
    E1 ob3 = new E1("c");
    E1 ob4 = new E1("d");
    E1 ob5 = new E1("e");
    testObject.addTrack();
    testObject.addTrack();
    testObject.addTrack();
    testObject.addTrackObject(ob1, 0);
    testObject.addTrackObject(ob2, 1);
    testObject.addTrackObject(ob3, 1);
    testObject.addTrackObject(ob4, 2);
    testObject.addEErelationship(ob1, ob5);
    assertTrue(testObject.getRelationship().size() == 4);
    assertTrue(testObject.getRelationship().get(ob1).size() == 0);
    testObject.addEErelationship(ob1, ob2);
    assertTrue(testObject.getRelationship().size() == 4);
    assertTrue(testObject.getRelationship().get(ob1).size() == 1);
    assertTrue(testObject.getRelationship().get(ob2).size() == 1);
    testObject.addEErelationship(ob1, ob4);
    assertTrue(testObject.getRelationship().size() == 4);
    assertTrue(testObject.getRelationship().get(ob1).size() == 2);
    assertTrue(testObject.getRelationship().get(ob4).size() == 1);

  }


  // Testing strategy for Edge.getSource()
  // 新建一个ConcreteCircularObject对象，调用addTrack()函数向系统中增加
  // 轨道，然后调用addTrackObject(),addEErelationship()函数添加物体和关系
  // 最后用不同的参数调用deleteEErelationship()函数删除轨道物体间的关系，
  // 并观察是否能够处理异常情况，同时观察relationship的情况是否符合预期。

  @Test
  public void testDeleteEErelationship() {
    ConcreteCircularObject<L1, E1> testObject = new ConcreteCircularObject<L1, E1>();
    E1 ob1 = new E1("a");
    E1 ob2 = new E1("b");
    E1 ob3 = new E1("c");
    E1 ob4 = new E1("d");
    E1 ob5 = new E1("e");
    testObject.addTrack();
    testObject.addTrack();
    testObject.addTrack();
    testObject.addTrackObject(ob1, 0);
    testObject.addTrackObject(ob2, 1);
    testObject.addTrackObject(ob3, 1);
    testObject.addTrackObject(ob4, 2);
    testObject.addEErelationship(ob1, ob2);
    testObject.addEErelationship(ob1, ob4);
    testObject.addEErelationship(ob2, ob3);
    testObject.deleteEErelationship(ob5, ob1);
    assertTrue(testObject.getRelationship().size() == 4);
    assertTrue(testObject.getRelationship().get(ob1).size() == 2);
    assertTrue(testObject.getRelationship().get(ob2).size() == 2);
    assertTrue(testObject.getRelationship().get(ob3).size() == 1);
    assertTrue(testObject.getRelationship().get(ob4).size() == 1);


    testObject.deleteEErelationship(ob1, ob2);
    assertTrue(testObject.getRelationship().size() == 4);
    assertTrue(testObject.getRelationship().get(ob1).size() == 1);
    assertTrue(testObject.getRelationship().get(ob2).size() == 1);
    assertTrue(testObject.getRelationship().get(ob3).size() == 1);
    assertTrue(testObject.getRelationship().get(ob4).size() == 1);


    testObject.deleteEErelationship(ob2, ob3);
    assertTrue(testObject.getRelationship().size() == 4);
    assertTrue(testObject.getRelationship().get(ob1).size() == 1);
    assertTrue(testObject.getRelationship().get(ob2).size() == 0);
    assertTrue(testObject.getRelationship().get(ob3).size() == 0);
    assertTrue(testObject.getRelationship().get(ob4).size() == 1);
  }


  // Testing strategy for Edge.getSource()
  // 新建一个ConcreteCircularObject对象，调用addTrack()函数向系统中增加
  // 轨道，然后调用addTrackObject(),addEErelationship()函数添加物体和关系
  // 最后用不同的参数调用addLErelationship()函数添加轨道物体与中心物体间的关系，
  // 并观察是否能够处理异常情况，同时观察LErelationship的情况是否符合预期。

  @Test
  public void testAddLErelationship() {
    ConcreteCircularObject<L1, E1> testObject = new ConcreteCircularObject<L1, E1>();
    E1 ob1 = new E1("a");
    E1 ob2 = new E1("b");
    E1 ob3 = new E1("c");
    E1 ob4 = new E1("d");
    E1 ob5 = new E1("e");
    testObject.addTrack();
    testObject.addTrack();
    testObject.addTrack();
    testObject.addTrackObject(ob1, 0);
    testObject.addTrackObject(ob2, 1);
    testObject.addTrackObject(ob3, 1);
    testObject.addTrackObject(ob4, 2);
    testObject.addLErelationship(ob5);
    assertTrue(testObject.getLErelationship().size() == 0);
    testObject.addLErelationship(ob4);
    assertTrue(testObject.getLErelationship().size() == 1);
    testObject.addLErelationship(ob3);
    assertTrue(testObject.getLErelationship().size() == 2);
  }

  // Testing strategy for Edge.getSource()
  // 新建一个ConcreteCircularObject对象，调用addTrack()函数向系统中增加
  // 轨道，然后调用addTrackObject(),addEErelationship(),addLErelationship()
  // 函数添加物体和关系最后用不同的参数调用deleteLErelationship()函数删除轨道物体与中心物体间的关系，
  // 并观察是否能够处理异常情况，同时观察LErelationship的情况是否符合预期。

  @Test
  public void testDeleteLErelationship() {
    ConcreteCircularObject<L1, E1> testObject = new ConcreteCircularObject<L1, E1>();
    E1 ob1 = new E1("a");
    E1 ob2 = new E1("b");
    E1 ob3 = new E1("c");
    E1 ob4 = new E1("d");
    E1 ob5 = new E1("e");
    // E ob2 = (E) new Object();
    // E ob3 = (E) new Object();
    // E ob4 = (E) new Object();
    // E ob5 = (E) new Object();
    testObject.addTrack();
    testObject.addTrack();
    testObject.addTrack();
    testObject.addTrackObject(ob1, 0);
    testObject.addTrackObject(ob2, 1);
    testObject.addTrackObject(ob3, 1);
    testObject.addTrackObject(ob4, 2);
    testObject.addLErelationship(ob4);
    testObject.addLErelationship(ob3);

    testObject.deleteLErelationship(ob5);
    assertTrue(testObject.getLErelationship().size() == 2);
    testObject.deleteLErelationship(ob3);
    assertTrue(testObject.getLErelationship().size() == 1);
    testObject.deleteLErelationship(ob4);
    assertTrue(testObject.getLErelationship().size() == 0);
  }



}
