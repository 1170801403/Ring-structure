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

  // ���ڽӿں�һЩ����ֻ���ConcreteCircularOrbits,����socialTieд���˸�����
  // �����˲�����ͬ������ΪsocialTie��ֻд������
  // socialTie�������Ǵ洢�罻ͼ�����еıߣ����������ĵ���֮��ı�
  private final Map<String, SocialE1> friend = new HashMap<String, SocialE1>();// ͨ�������ҵ���
  private final Map<String, Map<String, Float>> intimacyFriend =
      new HashMap<String, Map<String, Float>>();// �洢����˼�����ܶ�
  private final Map<String, Float> superIntimacyFriend = new HashMap<String, Float>();
  // �洢�������˵����ܶ�
  private final Map<String, Boolean> ifMarked = new HashMap<String, Boolean>();
  private final List<String> temp1 = new ArrayList<String>();// �洢�������
  private final List<String> temp2 = new ArrayList<String>();// �洢�������
  private final List<Float> temp3 = new ArrayList<Float>();// �洢�������
  private SocialFactory factory = new SocialFactory();// ��������
  // private static Logger log = java.util.logging.Logger.getLogger("socialLog");
  /// private final CircularOrbitAPIs<socialL1,socialE1> api = new
  // CircularOrbitAPIs<socialL1,socialE1>();
  // Abstraction function:
  // ����ʵ���罻����ϵͳ�����ļ��ж��벢���������
  // ���ò�ͬ�����ݽṹ�洢������塢�������塢�����������������֮��Ĺ�ϵ������������������֮��Ĺ�ϵ��
  // ��ʵ������������塢���������������ϵ���������ԾǨ�ȹ���
  // Representation invariant:
  // ������壬��������ĵ����嶼�ǲ��ɱ�ģ�ͨ����������ݽṹ��������֮�����ϵ,
  // �����罻��ϵ������ӻ�ɾ������ i �����ϵ��������ĵ����֮������·������ i��
  // Safety from rep exposure:
  // ���ж����������private final��,�ڷ�����Ҫ���ݽṹʱ�����˷����Կ�¡

  /**
   * ���SocialNetworkCircle��ı�ʾ������.
   */
  public void checkRep() {
    // Ӧʵ��3��Ҫ��,�Ѽ�����ĺϷ��Բ��ַ�����application��,
    // ��application�еĸ���������Ҫ��main()��������,���д��static,
    // �˴����ÿ���ֱ�ӵ���,��˵���ѷ���д��static��ʱ���кô���
    // assertTrue(socialTie != null);
  }

  /**
   * ��ǰ������.
   * 
   * @return �洢friend�ļ���.
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
    // ���û��friend��ô�罻����Ҳû������
    log.info("Social:return friend set");
    return tempFriend;
  }


  /**
   * ��ո���յļ���.
   */
  public void clear() {
    physical.clear();
    // physical��ʵ�ǵ�һ��Ӧ��ɾ���ģ���Ϊ��ֱ�Ӿ����˹���ı�ţ�����ı�ž�����trackObject�ܷ�����ʹ��
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

  // �����˸����еķ���
  /**
   * ���ض����������һ���ˣ�Ҫ������Ĺ������ڷ�Χ��.
   * 
   * @param ob Ҫ���ӵ���.
   * @param t �����Ŀ.
   * @return �����ӳɹ��򷵻�true,���򷵻�false����ʾ������Ϣ.
   */
  public boolean addTrackObject(SocialE1 ob, int t) { // ���ض����������һ�����壨����������λ�ã�
    // ���������ⲿ��ͨ���쳣������ǰ������������if��Ϊ����߳���Ŀ���չ��
    try {
      if (t >= physical.size()) {
        throw new Exception("the track is out of bound!");
      }
      if (!trackObject.containsKey(t)) {
        // ����Ҫ���ǵ�ɾ�����֮������������ı����һ���ܲ�һ����
        throw new Exception("the track has been deleted!");
      }
    } catch (Exception e) {
      log.warning("Social:exception happens in class socialNetwork,method addTrackObject:"
          + e.getMessage() + " handling:return false,exception message is printed on console");
      System.out.println(e.getMessage());
      return false;
      // TODO: handle exception
    }

    trackObject.get(t).add(ob);// ���ù���������ӳ��
    objectTrack.put(ob, t);// �������嵽�����ӳ��
    angle.put(ob, 0.00);// ��ʼʱ�Ƕ�Ϊ0
    // �������ʱ��Ҳ��Ҫ��friend���и���
    friend.put(ob.getName(), ob);
    log.info("Social:" + ob.getName() + "is added to track " + t);
    // addLErelationship(ob);
    // �������������壬���������������岻һ��ֱ����أ������罻���������㼰���ϵ��������������û�����Թ�ϵ
    // System.out.println("Succeed!");
    return true;

  }

  // �����˸����еķ���
  /**
   * ɾ��һ�������Ҫ������Ĺ������ڹ��ϵͳ�Ľ�����.
   * 
   * @param number ������.
   */
  public void deleteTrack(int number) { // ȥ��һ�����,���������б��еı��
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
    // physical.remove(number);//physical�ǿ��ƹ����ŵģ�ֻ�����ӹ�������ܼ��ٹ��
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
          i--;// ɾ��һ��Ԫ��֮�󣬺����Ԫ�ػ���ǰ�ƶ�
        }
      }
    }

    log.info("Social:track " + number + " is deleted");
    clear();// ����йؼ���(�ؼ�����)
    creatingTrack();// �ؽ����
    // �˴������½�ͼ�Ĺ��̣�������checkRep()
  }

  // �����˸����еķ���
  /**
   * ���ض������ɾ��һ�����壬Ҫ������Ĺ������ڹ��ϵͳ�ķ�Χ֮��.
   * 
   * @param ob ����ϵ���
   * @param t ������
   * @return ���ɾ���˳ɹ��򷵻�true�����򷵻�false����ʾ������Ϣ
   */
  public boolean deleteTrackObject(SocialE1 ob, int t) { // ���ض������ɾ��һ�����壨����������λ�ã�
    // ���������ⲿ�����쳣����ǰ������������if����߳���Ŀ���չ��
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
        i--;// ɾ��һ��Ԫ��֮�󣬲������˳�����Ϊ��ֹһ��socialTie�ﺬ����
      }
    }
    log.info("Social:" + ob.getName() + " is deleted in track " + t);
    clear();// ����йؼ���(�ؼ�����)
    creatingTrack();// �ؽ����
    // ������ͼ�ṹ��������checkRep
    return true;

  }

  // �Ѿ��ڽӿ���������
  /**
   * ��ȡ��֪�ļ������ݣ���ý������ϵͳ���������.
   */
  public boolean creatingTrackFromFiles(String name) { // �������˹��ļ�
    // �쳣���������Խ��Խ�ã��׵Ĵ���Խ�࣬����Խ��

    // �Ȱ����е�ƥ��ģʽ���ú�
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
    // �ſ���������ʽ��Ҫ�����ܶȿ����ɸ�
    // String sre14 = "([01](?:\\.{1}\\d{1,3}){0,1})"; // Float 1,����С��λ��
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

        // ƥ�䵽������
        if (m.find()) { // ֻ��findһ��
          // System.out.println("central find");
          String word1 = m.group(1);
          String c1 = m.group(2);
          String c2 = m.group(3);
          String c3 = m.group(4);
          String ws2 = m.group(5);
          String c4 = m.group(6);
          String word2 = m.group(7);
          String c5 = m.group(8);// ����

          String int1 = m.group(9);
          String c6 = m.group(10);
          String w2 = m.group(11);// ����
          String c7 = m.group(12);
          String c8 = m.group(14);// �Ա�
          if (ifCheck) {
            try {
              // �������Ϊ��
              if (Integer.parseInt(w2) < 0) {
                throw new Exception(
                    "the people " + c5 + "'s age " + Integer.parseInt(w2) + " is negative");
                // throw new Exception("age");
              }
              // �Ա���m|f��
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
          central = factory.manufactureL(c5, Integer.parseInt(w2), c8.charAt(0));// �������ĵ�����
        }

        // ƥ�䵽Friend
        if (fm.find()) { // ��ֹƥ��һ��
          // System.out.println("friend find");
          String fword1 = fm.group(1);
          String fws1 = fm.group(2);
          String fc1 = fm.group(3);
          String fc2 = fm.group(4);
          String fc3 = fm.group(5);
          String fws2 = fm.group(6);
          String fc4 = fm.group(7);
          String falphanum1 = fm.group(8);// ����

          String fc5 = fm.group(9);
          String fint1 = fm.group(10);

          String ffc6 = fm.group(11);// ����
          String fw1 = fm.group(12);

          String fc7 = fm.group(14);// �Ա�
          if (ifCheck) {
            try {
              // �������Ϊ��
              if (Integer.parseInt(ffc6) < 0) {
                throw new Exception("statements do not conform to syntax rules:the age "
                    + Integer.parseInt(ffc6) + " is negative");
              }
              // �Ա���m|f��
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
          friend.put(falphanum1, tempFriend);// ͨ�������ҵ��ˣ���ʼ��friendӳ��
          // socialNetWork��д�˸����еķ���
          relationship.put(tempFriend, new HashSet<SocialE1>());// ƥ���ͬʱ�ԡ���ϵ�����г�ʼ��,���ӻ�ʱ��Ҫʹ��
          intimacyFriend.put(falphanum1, new HashMap<String, Float>());// ��ʼ��ӳ��
          // intimacyFriend��socialTie��ͻ
        }

        // ƥ�䵽socialTie
        if (normalSm.find()) {
          // System.out.println("socialTie find");
          String normalSalphanum1 = normalSm.group(8);// person1
          String normalSalphanum2 = normalSm.group(11);// person2
          if (ifCheck) {
            try {
              if (normalSalphanum1.equals(normalSalphanum2)) {
                throw new Exception("same elements:The same name " + normalSalphanum1);
              }
              // �����߼���Ҫ�������ļ�����������ж�
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


          String normalSfloat1 = normalSm.group(14);// ���ܶ�
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
              // �Ѿ���֤���ܶ���01֮��
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
              // ����������ͬ���罻��ϵ���������Ҫ�ڰ��ļ�������֮���ٴ���,������
              // �����ļ���ʱ����ø��Ӷ�������ƽ������,������������ٶȵ�һ����ֻ
              // ���ڶ�С�ļ���ʱ��������ּ�鷽��
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
          socialTie.add(temp);// socialTie�д洢��ϵ
        }
      }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }

    // �����ļ���ʱ��������ٶ�
    if (ifCheck) {
      for (int j = 0; j < socialTie.size(); j++) {

        try {
          if (!(friend.containsKey(socialTie.get(j).getName1()))
              && !(socialTie.get(j).getName1().equals(central.getName()))) {
            // �׳��쳣֮���������Ͳ�����ִ����
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
    // �ſ���������ʽ��Ҫ�����ܶȿ����ɸ�
    // String sre14 = "([01](?:\\.{1}\\d{1,3}){0,1})"; // Float 1,����С��λ��
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

        // ƥ�䵽������
        if (m.find()) { // ֻ��findһ��
          // System.out.println("central find");
          String word1 = m.group(1);
          String c1 = m.group(2);
          String c2 = m.group(3);
          String c3 = m.group(4);
          String ws2 = m.group(5);
          String c4 = m.group(6);
          String word2 = m.group(7);
          String c5 = m.group(8);// ����

          String int1 = m.group(9);
          String c6 = m.group(10);
          String w2 = m.group(11);// ����
          String c7 = m.group(12);
          String c8 = m.group(14);// �Ա�
          if (ifCheck) {
            try {
              // �������Ϊ��
              if (Integer.parseInt(w2) < 0) {
                throw new Exception(
                    "the people " + c5 + "'s age " + Integer.parseInt(w2) + " is negative");
                // throw new Exception("age");
              }
              // �Ա���m|f��
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
          central = factory.manufactureL(c5, Integer.parseInt(w2), c8.charAt(0));// �������ĵ�����
        }

        // ƥ�䵽Friend

        if (fm.find()) { // ��ֹƥ��һ��
          // System.out.println("friend find");
          String fword1 = fm.group(1);
          String fws1 = fm.group(2);
          String fc1 = fm.group(3);
          String fc2 = fm.group(4);
          String fc3 = fm.group(5);
          String fws2 = fm.group(6);
          String fc4 = fm.group(7);
          String falphanum1 = fm.group(8);// ����

          String fc5 = fm.group(9);
          String fint1 = fm.group(10);

          String ffc6 = fm.group(11);// ����
          String fw1 = fm.group(12);

          String fc7 = fm.group(14);// �Ա�
          if (ifCheck) {
            try {
              // �������Ϊ��
              if (Integer.parseInt(ffc6) < 0) {
                throw new Exception("statements do not conform to syntax rules:the age "
                    + Integer.parseInt(ffc6) + " is negative");
              }
              // �Ա���m|f��
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
          friend.put(falphanum1, tempFriend);// ͨ�������ҵ��ˣ���ʼ��friendӳ��
          // socialNetWork��д�˸����еķ���
          relationship.put(tempFriend, new HashSet<SocialE1>());// ƥ���ͬʱ�ԡ���ϵ�����г�ʼ��
          intimacyFriend.put(falphanum1, new HashMap<String, Float>());// ��ʼ��ӳ��
        }

        // ƥ�䵽socialTie
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
          String normalSfloat1 = normalSm.group(14);// ���ܶ�
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
              // �Ѿ���֤���ܶ���01֮��
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
              // ����������ͬ���罻��ϵ���������Ҫ�ڰ��ļ�������֮���ٴ���
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
          socialTie.add(temp);// socialTie�д洢��ϵ
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
            // �׳��쳣֮���������Ͳ�����ִ����
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
    // �ſ���������ʽ��Ҫ�����ܶȿ����ɸ�
    // String sre14 = "([01](?:\\.{1}\\d{1,3}){0,1})"; // Float 1,����С��λ��
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

        // ƥ�䵽������
        if (m.find()) { // ֻ��findһ��
          // System.out.println("central find");
          String word1 = m.group(1);
          String c1 = m.group(2);
          String c2 = m.group(3);
          String c3 = m.group(4);
          String ws2 = m.group(5);
          String c4 = m.group(6);
          String word2 = m.group(7);
          String c5 = m.group(8);// ����

          String int1 = m.group(9);
          String c6 = m.group(10);
          String w2 = m.group(11);// ����
          String c7 = m.group(12);
          String c8 = m.group(14);// �Ա�
          if (ifCheck) {
            try {
              // �������Ϊ��
              if (Integer.parseInt(w2) < 0) {
                throw new Exception(
                    "the people " + c5 + "'s age " + Integer.parseInt(w2) + " is negative");
                // throw new Exception("age");
              }
              // �Ա���m|f��
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
          central = factory.manufactureL(c5, Integer.parseInt(w2), c8.charAt(0));// �������ĵ�����
        }

        // ƥ�䵽Friend
        if (fm.find()) { // ��ֹƥ��һ��
          // System.out.println("friend find");
          String fword1 = fm.group(1);
          String fws1 = fm.group(2);
          String fc1 = fm.group(3);
          String fc2 = fm.group(4);
          String fc3 = fm.group(5);
          String fws2 = fm.group(6);
          String fc4 = fm.group(7);
          String falphanum1 = fm.group(8);// ����

          String fc5 = fm.group(9);
          String fint1 = fm.group(10);

          String ffc6 = fm.group(11);// ����
          String fw1 = fm.group(12);

          String fc7 = fm.group(14);// �Ա�
          if (ifCheck) {
            try {
              // �������Ϊ��
              if (Integer.parseInt(ffc6) < 0) {
                throw new Exception("statements do not conform to syntax rules:the age "
                    + Integer.parseInt(ffc6) + " is negative");
              }
              // �Ա���m|f��
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
          friend.put(falphanum1, tempFriend);// ͨ�������ҵ��ˣ���ʼ��friendӳ��
          // socialNetWork��д�˸����еķ���
          relationship.put(tempFriend, new HashSet<SocialE1>());// ƥ���ͬʱ�ԡ���ϵ�����г�ʼ��
          intimacyFriend.put(falphanum1, new HashMap<String, Float>());// ��ʼ��ӳ��
        }

        // ƥ�䵽socialTie
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
          String normalSfloat1 = normalSm.group(14);// ���ܶ�
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
              // �Ѿ���֤���ܶ���01֮��
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
              // ����������ͬ���罻��ϵ���������Ҫ�ڰ��ļ�������֮���ٴ���
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
          socialTie.add(temp);// socialTie�д洢��ϵ
        }
      }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    if (ifCheck) {
      // ������socialTie�У�ȴû�г�����friend��
      for (int j = 0; j < socialTie.size(); j++) {
        try {
          if (!(friend.containsKey(socialTie.get(j).getName1()))
              && !(socialTie.get(j).getName1().equals(central.getName()))) {
            // �׳��쳣֮���������Ͳ�����ִ����
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

  // intimacyFriendҲ���ã���Щ������Ҫ�õ���
  // �����ڶ��ļ���ʱ����д�������ٶ������յ����ݶ�����ȷ��
  // �����������Ҳ�������
  // eerelationship���ǲ��������ĵ������
  /**
   * ���ù�����������������ϵͳ
   */
  public void creatingTrack() {
    // �����ݽṹ�Ľ�һ��ϸ��������socialTie�����������ܶණ��
    long startTime = System.currentTimeMillis();
    long time1 = 0;
    long time2 = 0;
    long time3 = 0;
    long time4 = 0;
    //һ����һ��������33��
    //ע��û�а취һ�߶��ļ�һ�߰�relationship������������Ϊ��֪�����ĵ�������ʲô
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
          && friend.containsKey(t.getName2())) { // friend������intimacyFriend��һ������,��������жϼ������˷�ʱ��
        long startTime33 = System.currentTimeMillis();
        String name1 = t.getName1();
        String name2 = t.getName2();
        float yes = t.getIni();
//        addEErelationship(friend.get(name1), friend.get(name2));
//        if (!relationship.get(friend.get(name1)).contains(friend.get(name2)))
//        {
          //if�ж������ȫ���Ժ��ԣ�set���Զ���������ͬ��Ԫ��
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
    System.out.println("Time1 "+time1);//0.013��
    System.out.println("Time2 "+time2);//0��
    System.out.println("Time3 "+time3);//31��
    long endTime1 = System.currentTimeMillis();
    //һ�²���ֻ�õ���1.7s����
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
   * ������Ӧ���ݽṹ�����������checkRep����������.
   */
  public void creatingTrack2() {
    boolean isCheck = false;
    long startTime = System.currentTimeMillis();
    addTrack();// ������һ������������罻������һ����û�У�Ҳ��Ĭ�Ͻ���һ�����
    // ��list���б���
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
            addTrackObject(person, 0);// �Ѿ���objectTrack�м�����ӳ��
            addLErelationship(person);
            superIntimacyFriend.put(normalSalphanum2, normalSfloat1);
          } else {
            // System.out.println("Central!");
            transit(person, 0);// �Ѿ���objectTrack�и�����ӳ��
            addLErelationship(person);
            superIntimacyFriend.put(normalSalphanum2, normalSfloat1);
          }
          // addLErelationship(friend.get(normalSalphanum2));
          // addTrackObject(friend.get(normalSalphanum2), 0);// ��֪���壬�Ϳ���֪�������ڵĹ��
        } else {
          SocialE1 person = friend.get(normalSalphanum1);
          if (!objectTrack.containsKey(person)) {
            // System.out.println("Central!");
            addTrackObject(person, 0);// �Ѿ���objectTrack�м�����ӳ��
            addLErelationship(person);
            superIntimacyFriend.put(normalSalphanum1, normalSfloat1);
          } else {
            // System.out.println("Central!");
            transit(person, 0);// �Ѿ���objectTrack�и�����ӳ��
            addLErelationship(person);
            superIntimacyFriend.put(normalSalphanum1, normalSfloat1);

          }

        }
      } else {

        SocialE1 person1 = friend.get(normalSalphanum1);
        SocialE1 person2 = friend.get(normalSalphanum2);
        if (objectTrack.containsKey(person1) && objectTrack.containsKey(person2)) {
          // System.out.println("EE!");
          // ���ܶ�ӳ��
          intimacyFriend.get(normalSalphanum1).put(normalSalphanum2, normalSfloat1);
          intimacyFriend.get(normalSalphanum2).put(normalSalphanum1, normalSfloat1);
          // �������嶼�ڹ���ϣ����üӹ���������ܶȾͿ���
          // ��ϵӳ��
          addEErelationship(person2, person1);// ���õ��Ǹ����еķ���

        } else if (objectTrack.containsKey(person1) && (!objectTrack.containsKey(person2))) {
          // ���������else-if����Ǵ���˫���������Ҫ���������ʱ��ɾȥһ����֧����
          if ((physical.size() - 1) > objectTrack.get(person1)) {
            // System.out.println("EE!");
            addTrackObject(person2, objectTrack.get(person1) + 1);
            // ���ܶ�ӳ��
            intimacyFriend.get(normalSalphanum1).put(normalSalphanum2, normalSfloat1);
            intimacyFriend.get(normalSalphanum2).put(normalSalphanum1, normalSfloat1);
            // �������嶼�ڹ���ϣ����üӹ���������ܶȾͿ���
            // ��ϵӳ��
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
            // ���ܶ�ӳ��
            intimacyFriend.get(normalSalphanum1).put(normalSalphanum2, normalSfloat1);
            intimacyFriend.get(normalSalphanum2).put(normalSalphanum1, normalSfloat1);
            // �������嶼�ڹ���ϣ����üӹ���������ܶȾͿ���
            // ��ϵӳ��
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

    // ��ʣ�ಿ�ֽ��д���
    int length2 = temp1.size();
    for (int i = 0; i < length2; i++) {
      String t1 = temp1.get(i);
      String t2 = temp2.get(i);
      Float ft3 = temp3.get(i);
      SocialE1 person1 = friend.get(t1);
      SocialE1 person2 = friend.get(t2);
      if (objectTrack.containsKey(person1) && objectTrack.containsKey(person2)) {
        intimacyFriend.get(t1).put(t2, ft3);
        intimacyFriend.get(t2).put(t1, ft3);// �������嶼�ڹ���ϣ����üӹ���������ܶȾͿ���
        // ��ϵӳ��
        addEErelationship(person2, person1);
        // addEERelationshipֻ�����һ�μ��ɣ������ڲ�������ι�ϵ
      } else if (objectTrack.containsKey(person1)) {
        // ���������else-if����Ǵ���˫���������Ҫ���������ʱ��ɾȥһ����֧����
        if ((physical.size() - 1) > objectTrack.get(person1)) {

          // System.out.println("EE!");
          addTrackObject(person2, objectTrack.get(person1) + 1);
          // ���ܶ�ӳ��
          intimacyFriend.get(t1).put(t2, ft3);
          intimacyFriend.get(t2).put(t1, ft3);// �������嶼�ڹ���ϣ����üӹ���������ܶȾͿ���
          // ��ϵӳ��
          addEErelationship(person2, person1);
          // addEERelationshipֻ�����һ�μ��ɣ������ڲ�������ι�ϵ
        } else {

          addTrack();
          addTrackObject(person2, objectTrack.get(person1) + 1);
          // ���ܶ�ӳ��
          intimacyFriend.get(t1).put(t2, ft3);
          intimacyFriend.get(t2).put(t1, ft3);// �������嶼�ڹ���ϣ����üӹ���������ܶȾͿ���
          // ��ϵӳ��
          addEErelationship(person2, person1);
          // addEERelationshipֻ�����һ�μ��ɣ������ڲ�������ι�ϵ
        }
      } else if (objectTrack.containsKey(person2)) {
        if ((physical.size() - 1) > objectTrack.get(person2)) {
          // System.out.println("EE!");
          addTrackObject(person1, objectTrack.get(person2) + 1);
          // ���ܶ�ӳ��
          intimacyFriend.get(t1).put(t2, ft3);
          intimacyFriend.get(t2).put(t1, ft3);// �������嶼�ڹ���ϣ����üӹ���������ܶȾͿ���
          // ��ϵӳ��
          addEErelationship(person2, person1);
          // addEERelationshipֻ�����һ�μ��ɣ������ڲ�������ι�ϵ
        } else {
          // System.out.println("EEADD!");
          addTrack();
          addTrackObject(person1, objectTrack.get(person2) + 1);
          // ���ܶ�ӳ��
          intimacyFriend.get(t1).put(t2, ft3);
          intimacyFriend.get(t2).put(t1, ft3);// �������嶼�ڹ���ϣ����üӹ���������ܶȾͿ���
          // ��ϵӳ��
          addEErelationship(person2, person1);
          // addEERelationshipֻ�����һ�μ��ɣ������ڲ�������ι�ϵ
        }
      } else { // ȫ������֮���������廹���ڹ����
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
            // ���ܶ�ӳ��
            intimacyFriend.get(t1).put(t2, ft3);
            intimacyFriend.get(t2).put(t1, ft3);// �������嶼�ڹ���ϣ����üӹ���������ܶȾͿ���
            // ��ϵӳ��
            addEErelationship(person2, person1);
            // addEERelationshipֻ�����һ�μ��ɣ������ڲ�������ι�ϵ
          } else {

            addTrack();
            addTrackObject(person2, objectTrack.get(person1) + 1);
            // ���ܶ�ӳ��
            intimacyFriend.get(t1).put(t2, ft3);
            intimacyFriend.get(t2).put(t1, ft3);// �������嶼�ڹ���ϣ����üӹ���������ܶȾͿ���
            // ��ϵӳ��
            addEErelationship(person2, person1);
            // addEERelationshipֻ�����һ�μ��ɣ������ڲ�������ι�ϵ
          }
        } else if (objectTrack.containsKey(person2) && !objectTrack.containsKey(person1)) {
          if ((physical.size() - 1) > objectTrack.get(person2)) {
            // System.out.println("EE!");
            addTrackObject(person1, objectTrack.get(person2) + 1);
            // ���ܶ�ӳ��
            intimacyFriend.get(t1).put(t2, ft3);
            intimacyFriend.get(t2).put(t1, ft3);// �������嶼�ڹ���ϣ����üӹ���������ܶȾͿ���
            // ��ϵӳ��
            addEErelationship(person2, person1);
            // addEERelationshipֻ�����һ�μ��ɣ������ڲ�������ι�ϵ
          } else {
            // System.out.println("EEADD!");
            addTrack();
            addTrackObject(person1, objectTrack.get(person2) + 1);
            // ���ܶ�ӳ��
            intimacyFriend.get(t1).put(t2, ft3);
            intimacyFriend.get(t2).put(t1, ft3);// �������嶼�ڹ���ϣ����üӹ���������ܶȾͿ���
            // ��ϵӳ��
            addEErelationship(person2, person1);
            // addEERelationshipֻ�����һ�μ��ɣ������ڲ�������ι�ϵ
          }
        } else {
          System.out.println("sorry!");
        }
      }
    }
    checkRep();// ����������
    long endTime = System.currentTimeMillis();
    System.out.println("It takes " + (endTime - startTime) + " millisecond to read the file.");
    log.info("Social:track has been created");
  }


  /**
   * ��ĳ���������ĵ���֮������ܶ�.
   * 
   * @param name ��ʾ��һ��ĳ���˵�����.
   * @return �����ȡ�ɹ��򷵻�true�����򷵻�false����ʾ������Ϣ.
   */
  // �����ܶ�
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
    // ǰ��������ÿ���˵����ֶ���һ��
    List<String> queue = new ArrayList<String>();// ��Ϊ��������
    Set<String> flag = new HashSet<String>();// ����Ѿ������ʹ�����
    queue.add(name);
    String tempName = name;
    while (queue.size() != 0) {
      tempName = queue.get(0);
      queue.remove(0);
      Map<String, Float> maptemp = intimacyFriend.get(tempName);// ���������ĵ��Ǹ���
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
          if (maptemp.get(temp) > 0.1 && !flag.contains(temp)) { // ���ܶ�����Ҫ����û�б����ʹ�
            potential++;
            flag.add(temp);// �����
            queue.add(temp);// ���
          }
        }
      }
      // �����Ĺ����а����������û�
    }
    System.out.println("You can meet " + (potential) + " friends indirectly.");
    log.info("Social:the intimacy of " + name + " is " + potential);
    return true;
  }

  // ���ӹ�ϵ�����½������ϵͳ(�����ǹ����������ӹ�ϵ��Ҳ�����ǹ����������ĵ��������ӹ�ϵ)
  /**
   * ���ӹ�ϵ�����½������ϵͳ,Ҫ���������˶��ڹ��ϵͳ��.
   * 
   * @param name1 ����.
   * @param name2 ����.
   * @param ini ���ܶ�.
   * @return ������ӹ�ϵ�򷵻�true�����򷵻�false����ʾ������Ϣ.
   */
  public boolean addRelationship(String name1, String name2, float ini) { // ����˱����ڹ����
    // ͨ�����Ƶõ�ʵ��
    // ͨ���쳣����ǰ������
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

  // ɾ����ϵ�������ڹ�����壩�����µ������ϵͳ
  /**
   * ɾ����ϵ�����½������ϵͳ��Ҫ��ɾ����ϵ�������˶��ڹ��ϵͳ��.
   * 
   * @param name1 ����.
   * @param name2 ����.
   * @return ���ɾ����ϵ�򷵻�true�����򷵻�false����ʾ������Ϣ.
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
    // ������ǰ�����������ڴ�����̣�ֻ��Ҫ��if��伴��
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

  //���û�ѡ��д�ļ��ķ�ʽ
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
