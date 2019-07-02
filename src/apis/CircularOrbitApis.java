package apis;

import java.util.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import circularorbit.AtomStructure;
import circularorbit.CircularOrbit;
import circularorbit.SocialNetworkCircle;
import circularorbit.TrackGame;
import physicalobject.AtomE1;
import physicalobject.SocialE1;
import physicalobject.TrackE1;

// ���API��һ����¶����������
public class CircularOrbitApis<L, E> {

  //�������public�Ļ�������ͬһ������ɼ�����ͬ����Ͳ��ɼ�����ʹnew��һ��������ʹ��Ҳ����
  public double getObjectDistributionEntropy(CircularOrbit c) {
    double entropy = 0.00;
    int num = 0;
    Map<Integer, Set<E>> temp = c.getTrackObject();
    int trackNumber = temp.size();
    List<Double> probability = new ArrayList<Double>();
    Iterator<Integer> iterator = temp.keySet().iterator();
    while (iterator.hasNext()) {
      Set<E> etemp = temp.get(iterator.next());
      num = num + temp.size();
    }
    Iterator<Integer> iterator2 = temp.keySet().iterator();
    while (iterator2.hasNext()) {
      Set<E> etemp2 = temp.get(iterator2.next());
      double e = (double) etemp2.size() / (double) num;
      probability.add(e);
    }
    for (int i = 0; i < probability.size(); i++) {
      double e1 = probability.get(i);
      entropy = entropy + e1 * Math.log(e1);
    }
    return (-1) * entropy;
  }

  //  public CircularOrbitApis() {
  //    // TODO Auto-generated constructor stub
  //  }

  // ����������Ӧ�õ�socialNetWork�У���ֵ��������Ĺ����֮��Ӧ������ͬ��
  // ֻ��social���������˹�ϵ
  //�޷��ж�e1��e2��ͬ�����
  
  public int getLogicalDistance(CircularOrbit c, E e1, E e2) {
    // �������
    if (e1.equals(e2)) {
      return 0;
    }
    int length = 1;
    // Map<E, Integer> grade = new HashMap<E, Integer>();
    Map<E, Set<E>> real = c.getRelationship();
    List<E> queue = new ArrayList<E>();
    Set<E> flag = new HashSet<E>();// �����ڵ��˶��Ѿ������ʹ���
    queue.add(e1);

    while (queue.size() != 0) {
      int size = queue.size();
      List<E> cqueue = new ArrayList<E>();
      for (int i = 0; i < size; i++) {
        E e = queue.get(0);// ����Ԫ�س���
        queue.remove(0);

        if (real.containsKey(e)) {
          Set<E> tempReal = real.get(e);// ��ȡ��ǰ������й�����ļ���
          Iterator<E> iterator = tempReal.iterator();// �Ըü��Ͻ��б���

          while (iterator.hasNext()) {
            E te = iterator.next();
            // ��һ����ζ�ű�����д����Ӧ�����е�equals����
            if (te.equals(e2)) {
              return length;
            } else {
              if (!flag.contains(te)) {
                flag.add(te);
                cqueue.add(te);
              }
            }

          }
        } else {
          System.out.println("the person shouldn't appear in the orbit system!");
          return -8;
        }

      }
      queue = cqueue;
      length++;
    }
    // System.out.println("There is no path between the two person!");
    return -2;// ȫ������֮��û��return,˵������֮��û��·��

  }


  public double getPhysicalDistance(CircularOrbit c, E e1, E e2) {
    double distance;
    Map<E, Double> temp = c.getAngle();
    Map<E, Integer> repTemp = c.getObjectTrack();
    double angle1 = temp.get(e2);
    double angle2 = temp.get(e2);
    int rep1 = repTemp.get(e1) + 1;
    int rep2 = repTemp.get(e2) + 1;
    double x1 = rep1 * Math.cos(angle1);
    double x2 = rep2 * Math.cos(angle2);
    double y1 = rep1 * Math.sin(angle1);
    double y2 = rep2 * Math.sin(angle2);
    distance = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    return distance;
  }

  
  //c1��c2������ͬ���͵Ĳ��ܱȽϣ���ô�ж���
  public Difference getDifference(CircularOrbit c1, CircularOrbit c2) {

    int basicSize;
    int numberDifference;
    int trackNumber1 = c1.getTrackObject().size();
    int trackNumber2 = c2.getTrackObject().size();
    numberDifference = trackNumber1 - trackNumber2;
    Difference d = new Difference<L, E>(numberDifference);
    // d.differenceNumber = numberDifference;
    boolean trackType1 = c1 instanceof TrackGame;
    boolean trackType2 = c1 instanceof TrackGame;
    boolean atomType1 = c1 instanceof AtomStructure;
    boolean atomType2 = c2 instanceof AtomStructure;
    boolean socialType1 = c1 instanceof SocialNetworkCircle;
    boolean socialType2 = c2 instanceof SocialNetworkCircle;
    
    // ��������Ĵ���:
    // �Ȱ����Һã��ٰ������˳��Ƚϣ���Ҫ�Ķ�trackGame�з���Ĺ���
    if (trackType1 == true && trackType2 == true) {
      Map<Integer, Set<TrackE1>> temp1 = c1.getTrackObject();// Ϊʲô��Ҫ�ĳ�socialE1����ΪE��object����û��name
      Map<Integer, Set<TrackE1>> temp2 = c2.getTrackObject();
      if (trackNumber1 == trackNumber2) {
        for (int i = 0; i < trackNumber1; i++) {
          String first = new String();
          String second = new String();
          Set<TrackE1> stemp1 = temp1.get(i);
          Set<TrackE1> stemp2 = temp2.get(i);
          Iterator<TrackE1> iterator = stemp1.iterator();
          while (iterator.hasNext()) {
            first = first + iterator.next().getName();
          }
          Iterator<TrackE1> iterator2 = stemp2.iterator();
          while (iterator2.hasNext()) {
            second = second + iterator2.next().getName();
          }
          d.numberTrack.put(i, temp1.get(i).size() - temp2.get(i).size());
          d.stringTrack.put(i, first + "-" + second);
        }
        // 1�Ĺ��������
      } else if (trackNumber1 > trackNumber2) {
        for (int i = 0; i < trackNumber2; i++) {
          String first = new String();
          String second = new String();
          Set<TrackE1> stemp1 = temp1.get(i);
          Set<TrackE1> stemp2 = temp2.get(i);
          Iterator<TrackE1> iterator = stemp1.iterator();
          while (iterator.hasNext()) {
            first = first + iterator.next().getName();
          }
          Iterator<TrackE1> iterator2 = stemp2.iterator();
          while (iterator2.hasNext()) {
            second = second + iterator2.next().getName();
          }
          d.numberTrack.put(i, temp1.get(i).size() - temp2.get(i).size());
          d.stringTrack.put(i, first + "-" + second);
        }
        for (int j = trackNumber2; j < trackNumber1; j++) {
          String first = new String();
          Set<TrackE1> stemp1 = temp1.get(j);
          Iterator<TrackE1> iterator = stemp1.iterator();
          while (iterator.hasNext()) {
            first = first + iterator.next().getName();
          }
          d.numberTrack.put(j, temp1.get(j).size());
          d.stringTrack.put(j, first + "-");
        }
        // 2�Ĺ��������
      } else {
        for (int i = 0; i < trackNumber1; i++) {
          String first = new String();
          String second = new String();
          Set<TrackE1> stemp1 = temp1.get(i);
          Set<TrackE1> stemp2 = temp2.get(i);
          Iterator<TrackE1> iterator = stemp1.iterator();
          while (iterator.hasNext()) {
            first = first + iterator.next().getName();
          }
          Iterator<TrackE1> iterator2 = stemp2.iterator();
          while (iterator2.hasNext()) {
            second = second + iterator2.next().getName();
          }
          d.numberTrack.put(i, temp1.get(i).size() - temp2.get(i).size());
          d.stringTrack.put(i, first + "-" + second);
        }
        for (int j = trackNumber1; j < trackNumber2; j++) {
          String second = new String();
          Set<TrackE1> stemp2 = temp2.get(j);
          Iterator<TrackE1> iterator = stemp2.iterator();
          while (iterator.hasNext()) {
            second = second + iterator.next().getName();
          }
          d.numberTrack.put(j, -temp1.get(j).size());
          d.stringTrack.put(j, "-" + second);
        }
      }
    } else if (atomType1 == true && atomType2 == true) {
      if (trackNumber1 == trackNumber2) {
        Map<Integer, Set<AtomE1>> atemp = c1.getTrackObject();
        Map<Integer, Set<AtomE1>> atemp2 = c2.getTrackObject();
        for (int i = 0; i < trackNumber1; i++) {
          // ������׻���Ҫ�����ӹ���ϼӵ���
          Set<AtomE1> stemp = atemp.get(i);
          Set<AtomE1> stemp2 = atemp2.get(i);
          int dnumber = stemp.size() - stemp2.size();
          d.numberTrack.put(i, dnumber);
        }
      } else if (trackNumber1 > trackNumber2) {
        Map<Integer, Set<AtomE1>> atemp = c1.getTrackObject();
        Map<Integer, Set<AtomE1>> atemp2 = c2.getTrackObject();
        for (int i = 0; i < trackNumber2; i++) {
          Set<AtomE1> stemp = atemp.get(i);
          Set<AtomE1> stemp2 = atemp2.get(i);
          int dnumber = stemp.size() - stemp2.size();
          d.numberTrack.put(i, dnumber);
        }
        for (int j = trackNumber2; j < trackNumber1; j++) {
          Set<AtomE1> stemp = atemp.get(j);
          d.numberTrack.put(j, stemp.size());
        }
      } else {
        Map<Integer, Set<AtomE1>> atemp = c1.getTrackObject();
        Map<Integer, Set<AtomE1>> atemp2 = c2.getTrackObject();
        for (int i = 0; i < trackNumber1; i++) {
          Set<AtomE1> stemp = atemp.get(i);
          Set<AtomE1> stemp2 = atemp2.get(i);
          int dnumber = stemp.size() - stemp2.size();
          d.numberTrack.put(i, dnumber);
        }
        for (int j = trackNumber1; j < trackNumber2; j++) {
          Set<AtomE1> stemp = atemp2.get(j);
          d.numberTrack.put(j, -stemp.size());
        }
      }
    } else if (socialType1 == true && socialType2 == true) {

      if (trackNumber1 == trackNumber2) {
        Map<Integer, Set<SocialE1>> temp1 = c1.getTrackObject();
        Map<Integer, Set<SocialE1>> temp2 = c2.getTrackObject();
        for (int i = 0; i < trackNumber1; i++) {
          Set<SocialE1> stemp1 = temp1.get(i);
          Set<SocialE1> stemp2 = temp2.get(i);
          List<SocialE1> firstHave = new ArrayList<SocialE1>();
          List<SocialE1> secondHave = new ArrayList<SocialE1>();

          Iterator<SocialE1> iterator = stemp1.iterator();
          while (iterator.hasNext()) {
            SocialE1 person1 = iterator.next();
            if (!stemp2.contains(person1)) {
              firstHave.add(person1);
            }
          }
          Iterator<SocialE1> iterator2 = stemp2.iterator();
          while (iterator2.hasNext()) {
            SocialE1 person2 = iterator.next();
            if (!stemp1.contains(person2)) {
              secondHave.add(person2);
            }
          }

          String first = new String();
          for (int j = 0; j < firstHave.size(); j++) {
            first = first + "," + firstHave.get(j).getName();
          }
          String second = new String();
          for (int k = 0; k < secondHave.size(); k++) {
            second = second + "," + secondHave.get(k).getName();
          }

          d.stringTrack.put(i, "{" + first + "}" + "-" + "{" + second + "}");
          // ������������������Ĳ���
          int dnumber = stemp1.size() - stemp2.size();
          d.numberTrack.put(i, dnumber);
        }
        // 1ϵͳ�Ĺ����Ŀ����
      } else if (trackNumber1 > trackNumber2) {
        Map<Integer, Set<SocialE1>> temp1 = c1.getTrackObject();
        Map<Integer, Set<SocialE1>> temp2 = c2.getTrackObject();
        for (int i = 0; i < trackNumber2; i++) {
          List<SocialE1> firstHave = new ArrayList<SocialE1>();
          List<SocialE1> secondHave = new ArrayList<SocialE1>();
          Set<SocialE1> stemp1 = temp1.get(i);
          Set<SocialE1> stemp2 = temp2.get(i);

          Iterator<SocialE1> iterator = stemp1.iterator();
          while (iterator.hasNext()) {
            SocialE1 person1 = iterator.next();
            if (!stemp2.contains(person1)) {
              firstHave.add(person1);
            }
          }
          Iterator<SocialE1> iterator2 = stemp2.iterator();
          while (iterator2.hasNext()) {
            SocialE1 person2 = iterator.next();
            if (!stemp1.contains(person2)) {
              secondHave.add(person2);
            }
          }

          String first = new String();
          for (int j = 0; j < firstHave.size(); j++) {
            first = first + "," + firstHave.get(j).getName();
          }
          String second = new String();
          for (int k = 0; k < secondHave.size(); k++) {
            second = second + "," + secondHave.get(k).getName();
          }
          d.stringTrack.put(i, "{" + first + "}" + "-" + "{" + second + "}");
          // ������������������Ĳ���
          int dnumber = stemp1.size() - stemp2.size();
          d.numberTrack.put(i, dnumber);
        }
        for (int i = trackNumber2; i < trackNumber1; i++) {
          List<SocialE1> firstHave = new ArrayList<SocialE1>();
          Set<SocialE1> stemp1 = temp1.get(i);

          Iterator<SocialE1> iterator = stemp1.iterator();
          while (iterator.hasNext()) {
            SocialE1 person1 = iterator.next();
            firstHave.add(person1);
          }

          String first = new String();
          for (int j = 0; j < firstHave.size(); j++) {
            first = first + "," + firstHave.get(j).getName();
          }
          d.stringTrack.put(i, "{" + first + "}" + "-" + "0");
        }
        // 2ϵͳ�Ĺ����Ŀ����
      } else {
        Map<Integer, Set<SocialE1>> temp1 = c1.getTrackObject();
        Map<Integer, Set<SocialE1>> temp2 = c2.getTrackObject();
        for (int i = 0; i < trackNumber1; i++) {
          Set<SocialE1> stemp1 = temp1.get(i);
          Set<SocialE1> stemp2 = temp2.get(i);
          List<SocialE1> firstHave = new ArrayList<SocialE1>();
          List<SocialE1> secondHave = new ArrayList<SocialE1>();
          Iterator<SocialE1> iterator = stemp1.iterator();

          while (iterator.hasNext()) {
            SocialE1 person1 = iterator.next();
            if (!stemp2.contains(person1)) {
              firstHave.add(person1);
            }
          }
          Iterator<SocialE1> iterator2 = stemp2.iterator();
          while (iterator2.hasNext()) {
            SocialE1 person2 = iterator.next();
            if (!stemp1.contains(person2)) {
              secondHave.add(person2);
            }
          }

          String first = new String();
          for (int j = 0; j < firstHave.size(); j++) {
            first = first + "," + firstHave.get(j).getName();
          }
          String second = new String();
          for (int k = 0; k < secondHave.size(); k++) {
            second = second + "," + secondHave.get(k).getName();
          }

          d.stringTrack.put(i, "{" + first + "}" + "-" + "{" + second + "}");
          // ������������������Ĳ���
          int dnumber = stemp1.size() - stemp2.size();
          d.numberTrack.put(i, dnumber);
        }
        for (int i = trackNumber1; i < trackNumber2; i++) {
          Set<SocialE1> stemp2 = temp2.get(i);
          Iterator<SocialE1> iterator = stemp2.iterator();
          List<SocialE1> secondHave = new ArrayList<SocialE1>();

          while (iterator.hasNext()) {
            SocialE1 person2 = iterator.next();
            secondHave.add(person2);
          }

          String second = new String();
          for (int j = 0; j < secondHave.size(); j++) {
            second = second + "," + secondHave.get(j).getName();
          }
          d.stringTrack.put(i, "0" + "-" + "{" + second + "}");
        }
      }
    } else {
      System.out.println("The two orbit systems are not the same type!");
    }
    return d;
  }

}
