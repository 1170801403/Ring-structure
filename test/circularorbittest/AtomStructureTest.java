package circularorbittest;

import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import centralobject.L1;
import centralobject.AtomL1;
import circularorbit.ConcreteCircularObject;
import circularorbit.AtomStructure;
import physicalobject.E1;
import physicalobject.AtomE1;

public class AtomStructureTest {
  // Testing strategy for clear()
  // 新建一个ConcreteCircularObject对象，调用addTrack(),
  // addTrackObject(),addEErelationship(),addLErelationship()
  // 函数增加轨道、物体以及物体间的关系，然后调用clear()函数
  // 清空物体、物体间的关系，最后观察存储轨道和物体的对象大小是否符合要求
  @Test
  public void clearTest() {
    AtomStructure testObject = new AtomStructure();
    AtomE1 ob1 = new AtomE1("a");
    AtomE1 ob2 = new AtomE1("b");
    testObject.addTrack();
    testObject.addTrack();
    testObject.addTrackObject(ob1, 0);
    testObject.addTrackObject(ob2, 1);
    testObject.addEErelationship(ob1, ob2);
    testObject.addLErelationship(ob2);
    testObject.addLErelationship(ob2);
    testObject.clear();
    assertTrue(testObject.getPhysical().size() == 0);
    assertTrue(testObject.getAngle().size() == 0);
    assertTrue(testObject.getObjectTrack().size() == 0);
    assertTrue(testObject.getTrackObject().size() == 0);
    assertTrue(testObject.getLErelationship().size() == 0);
    // addTrackObject的时候就加入了relationship
    assertTrue(testObject.getRelationship().size() == 2);
    assertTrue(testObject.getRelationship().get(ob1).size() == 0);
    assertTrue(testObject.getRelationship().get(ob2).size() == 0);
  }


  // Testing strategy for creatingTrack()
  // 新建一个ConcreteCircularObject对象，用特定的参数调用creatingTrack()
  // 函数，建立轨道系统，最后观察存储轨道和物体的对象大小是否符合要求
  @Test
  public void creatingTrackTest() {
    AtomStructure testObject = new AtomStructure();
    int trackNumber2 = 2;
    Map<Integer, Integer> trackObjectNumber = new HashMap<Integer, Integer>();
    trackObjectNumber.put(0, 1);
    trackObjectNumber.put(1, 1);
    testObject.creatingTrack(trackNumber2, trackObjectNumber);
    assertTrue(testObject.getPhysical().size() == 2);

    assertTrue(testObject.getTrackObject().size() == 2);
    assertTrue(testObject.getTrackObject().get(0).size() == 1);
    assertTrue(testObject.getTrackObject().get(1).size() == 1);

    // addTrackObject的时候就加入了relationship
    assertTrue(testObject.getRelationship().size() == 2);
    assertTrue(testObject.getObjectTrack().size() == 2);
    assertTrue(testObject.getAngle().size() == 2);
  }


  // Testing strategy for electronicTransition()
  // 新建一个ConcreteCircularObject对象，调用addTrack(),
  // addTrackObject(),，建立轨道系统，然后调用electronicTransition，
  // 完成电子跃迁，最后观察存储轨道和物体的对象大小是否符合要求
  @Test
  public void electronicTransitionTest() {
    AtomStructure testObject = new AtomStructure();
    int trackNumber2 = 2;
    Map<Integer, Integer> trackObjectNumber = new HashMap<Integer, Integer>();
    trackObjectNumber.put(0, 1);
    trackObjectNumber.put(1, 1);
    testObject.creatingTrack(trackNumber2, trackObjectNumber);
    testObject.electronicTransition(0, 2);

    testObject.electronicTransition(0, 1);
    assertTrue(testObject.getPhysical().size() == 2);
    assertTrue(testObject.getTrackObject().size() == 2);
    assertTrue(testObject.getTrackObject().get(0).size() == 0);
    assertTrue(testObject.getTrackObject().get(1).size() == 2);

    assertTrue(testObject.getObjectTrack().size() == 2);


    assertTrue(testObject.getTrackObjectNumber().size() == 2);
    assertTrue(testObject.getTrackObjectNumber().get(0) == 0);
    assertTrue(testObject.getTrackObjectNumber().get(1) == 2);
  }



}
