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
import java.util.TreeMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import abstractfactory.AtomFactory;
import centralobject.L1;
import flyweight.FlyWeight;
import flyweight.FlyWeightFactory;
import centralobject.AtomL1;
import mylog.MyFormatter;
import physicalobject.E1;
import physicalobject.AtomE1;
import physicalobject.SocialE1;
import track.Track;

public class AtomStructure extends ConcreteCircularObject<AtomL1, AtomE1> {
  // �ⲻ׼ԭ������Ҫ֪�������ڹ���ϵ��ĸ�λ��
  // ��û���õ������е����ݽṹ
  private Map<Integer, Integer> trackObjectNumber = new TreeMap<Integer, Integer>();
  // ÿ��������ж��ٸ�����
  int trackNumber2 = 0;// �ж��ٸ����
  // final private static Logger log = java.util.logging.Logger.getLogger("atomStructureLog");//
  // ��¼��־
  private AtomFactory factory = new AtomFactory();
  private FlyWeightFactory atomFacctory = new FlyWeightFactory();

  // ����ʵ�ֵ��ӹ��ϵͳ�����ļ��ж��벢���������
  // ���ò�ͬ�����ݽṹ�洢������塢�������塢�����������������֮��Ĺ�ϵ������������������֮��Ĺ�ϵ��
  // ��ʵ������������塢���������������ϵ���������ԾǨ�ȹ���
  // Representation invariant:
  // ������壬��������ĵ����嶼�ǲ��ɱ�ģ�ͨ����������ݽṹ��������֮�����ϵ,
  // Safety from rep exposure:
  // �ڷ�����Ҫ���ݽṹʱ�����˷����Կ�¡

  /**
   * ȷ��AtomStructure��ı�ʾ������.
   */
  public void checkRep() {
    // assertTrue(trackNumber2 >= 0);
    // assertTrue(trackObjectNumber != null);
  }

  /**
   * ��ռ��ϣ��������½������.
   */
  public void clear() {
    physical.clear();
    // physical��ʵ�ǵ�һ��Ӧ��ɾ���ģ���Ϊ��ֱ�Ӿ����˹���ı�ţ�����ı�ž�����trackObject�ܷ�����ʹ��
    angle.clear();
    objectTrack.clear();
    trackObject.clear();
    lerelationship.clear();
    // relationship.clear();
    Iterator<AtomE1> iterator = relationship.keySet().iterator();
    while (iterator.hasNext()) {
      relationship.get(iterator.next()).clear();
    }
  }

  /**
   * ���ع�������������ӳ��.
   * 
   * @return ��������������ӳ��
   */
  public Map<Integer, Integer> getTrackObjectNumber() {
    Map<Integer, Integer> tempTrackObjectNumber = new HashMap<Integer, Integer>();
    Iterator<Integer> iterator = trackObjectNumber.keySet().iterator();
    while (iterator.hasNext()) {
      int temp = iterator.next();
      tempTrackObjectNumber.put(temp, trackObjectNumber.get(temp));
    }
    log.info("atomStructure:assert trackObjectNumber isn't empty,"
        + "in class atomStructure,method getTrackObjectNumber");
    assert tempTrackObjectNumber.size() != 0 : "trackObjectNumber is empty";// ��������
    log.info("atomStructure:return trackObjectNumber");
    return tempTrackObjectNumber;
  }

  /**
   * ���ع��������.
   * 
   * @return �������.
   */
  public int getTrackNumber2() {
    int x;// ��ֵ���Ǵ�ָ��
    x = trackNumber2;
    log.info(
        "atomStructure:assert trackNumber isn't 0,in class atomStructure,method getTrackNumber2");
    assert x != 0;// ��������
    return x;
  }

  @Override
  public boolean creatingTrackFromFiles(String name) { // ���ⲿ�ļ���ȡ���ݹ�����ϵͳ����
    boolean ifCheck = true;
    log.info("atomStructure:assert file name isn't null,"
        + "in class atomStructure,method creatingTrackFromFiles");
    assert name.length() != 0 : "file name is null";// ǰ������
    String txt = new String();
    String fre9 = "(NumberOfTracks)"; // Word 3
    String fre10 = "(\\s+)"; // White Space 4
    String fre11 = "(:)"; // Any Single Character 4
    String fre12 = "(:)"; // Any Single Character 5
    String fre13 = "(=)"; // Any Single Character 6
    String fre14 = "(\\s+)"; // White Space 5
    String fre15 = "(-?\\d+)"; // Integer Number 1
    Pattern fp = Pattern.compile(fre9 + fre10 + fre11 + fre12 + fre13 + fre14 + fre15,
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String re1 = "(ElementName)"; // Word 1
    String re2 = "(\\s+)"; // White Space 1
    String re3 = "(:)"; // Any Single Character 1
    String re4 = "(:)"; // Any Single Character 2
    String re5 = "(=)"; // Any Single Character 3
    String re6 = "(\\s+)"; // White Space 2
    String re7 = "((?:[a-z][a-z]+))"; // Word 2
    Pattern p2 = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7,
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String g1 = "(-?\\d+)"; // Integer Number 10
    String g2 = "(\\/)"; // Any Single Character 18
    String g3 = "(-?\\d+)"; // Integer Number 11
    Pattern p1 = Pattern.compile(g1 + g2 + g3, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    int count = 0;
    try (BufferedReader in =
        new BufferedReader(new InputStreamReader(new FileInputStream("txt/" + name + "txt")))) {
      // ʼ�ռ�ס������Ҫ���ļ��е����ݰ��������
      while ((txt = in.readLine()) != null) {
        Matcher fm = fp.matcher(txt);
        Matcher m1 = p1.matcher(txt);
        Matcher m2 = p2.matcher(txt);
        if (fm.find()) { // ����ƥ�䵽һ�Σ�ֻ��Ҫ��if
          trackNumber2 = Integer.parseInt(fm.group(7));
          // �����Ӧ����������
          if (ifCheck) {
            try {
              if (trackNumber2 <= 0) {
                throw new Exception("statements do not conform to syntax rules: the tracknumber "
                    + trackNumber2 + " is non-positive");
              }
            } catch (Exception e) {
              log.warning("atomStructure:exception happens in class atomStructure,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
        }
        if (m2.find()) {
          String temp = m2.group(7);
          if (ifCheck) {
            try {
              // ԭ�����������λ��ĸ
              if (!((temp.length() > 1) && (temp.length() < 3))) {
                throw new Exception("statements do not conform to syntax rules:the name " + temp
                    + " is out of bound ");
              }
              // ��һλ�����д
              if (!((temp.charAt(0) <= 90) && (temp.charAt(0) >= 65))) {
                throw new Exception("statements do not conform to syntax rules:the first letter \""
                    + temp.charAt(0) + "\" is not capital");
              }
              // �ٱ�ֻ֤����λ������£��жϵڶ�����ĸ�Ƿ���Сд
              if (temp.length() > 1) {
                if (!((temp.charAt(1) <= 122) && (temp.charAt(1) >= 97))) {
                  throw new Exception(
                      "statements do not conform to syntax rules:the second letter \""
                          + temp.charAt(1) + "\" is not lowercase");
                }
              }
            } catch (Exception e) {
              log.warning("atomStructure:exception happens in class atomStructure,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          central = new AtomL1(m2.group(7));
        }
        while (m1.find()) {
          String a1 = m1.group(1);
          String a2 = m1.group(3);
          if (ifCheck) {
            try {
              // �����ű�����ڵ�����
              if (Integer.parseInt(a1) < 0) {
                throw new Exception(
                    "statements do not conform to syntax rules:the numebr of track is negative: "
                        + Integer.parseInt(a1));
              }
              // ����ϵĵ�����Ŀ���������
              if (Integer.parseInt(a2) <= 0) {
                throw new Exception("statements do not conform to syntax rules:"
                    + "the number of electronic is non-positive: " + Integer.parseInt(a2));
              }
            } catch (Exception e) {
              log.warning("atomStructure:exception happens in class atomStructure,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          trackObjectNumber.put(count, Integer.parseInt(a2));
          count++;
        }
      }
      // �൱�����ڶ���֮����ж��������ӹ����Ŀ�Ƿ����
      if (ifCheck) {
        try {
          if (count != trackNumber2) {
            throw new Exception(
                "incorrect dependencies:the electronic track number is contradictory:" + count);
          }
        } catch (Exception e) {
          log.warning(
              "atomStructure:exception happens in class atomStructure,method creatingTrackFromFiles:"
                  + e.getMessage()
                  + " handling:return false and exception message is printed on console");
          System.out.println(e.getMessage());
          return false;
          // TODO: handle exception
        }
      }
    } catch (IOException e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    checkRep();
    log.info("atomStructure:read a file");
    return true;
  }

  public boolean creatingTrackFromFilesFile(String name) {
    boolean ifCheck = true;
    log.info("atomStructure:assert file name isn't null,"
        + "in class atomStructure,method creatingTrackFromFiles");
    assert name.length() != 0 : "file name is null";// ǰ������
    String txt = new String();
    String fre9 = "(NumberOfTracks)"; // Word 3
    String fre10 = "(\\s+)"; // White Space 4
    String fre11 = "(:)"; // Any Single Character 4
    String fre12 = "(:)"; // Any Single Character 5
    String fre13 = "(=)"; // Any Single Character 6
    String fre14 = "(\\s+)"; // White Space 5
    String fre15 = "(-?\\d+)"; // Integer Number 1
    Pattern fp = Pattern.compile(fre9 + fre10 + fre11 + fre12 + fre13 + fre14 + fre15,
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String re1 = "(ElementName)"; // Word 1
    String re2 = "(\\s+)"; // White Space 1
    String re3 = "(:)"; // Any Single Character 1
    String re4 = "(:)"; // Any Single Character 2
    String re5 = "(=)"; // Any Single Character 3
    String re6 = "(\\s+)"; // White Space 2
    String re7 = "((?:[a-z][a-z]+))"; // Word 2
    Pattern p2 = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7,
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String g1 = "(-?\\d+)"; // Integer Number 10
    String g2 = "(\\/)"; // Any Single Character 18
    String g3 = "(-?\\d+)"; // Integer Number 11
    Pattern p1 = Pattern.compile(g1 + g2 + g3, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    int count = 0;
    try (BufferedReader reader = new BufferedReader(new FileReader("txt/" + name + "txt"))) {
      // ʼ�ռ�ס������Ҫ���ļ��е����ݰ��������
      while ((txt = reader.readLine()) != null) {
        Matcher fm = fp.matcher(txt);
        Matcher m1 = p1.matcher(txt);
        Matcher m2 = p2.matcher(txt);
        if (fm.find()) { // ����ƥ�䵽һ�Σ�ֻ��Ҫ��if
          trackNumber2 = Integer.parseInt(fm.group(7));
          // �����Ӧ����������
          if (ifCheck) {
            try {
              if (trackNumber2 <= 0) {
                throw new Exception("statements do not conform to syntax rules: the tracknumber "
                    + trackNumber2 + " is non-positive");
              }
            } catch (Exception e) {
              log.warning("atomStructure:exception happens in class atomStructure,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
        }
        if (m2.find()) {
          String temp = m2.group(7);
          if (ifCheck) {
            try {
              // ԭ�����������λ��ĸ
              if (!((temp.length() > 1) && (temp.length() < 3))) {
                throw new Exception("statements do not conform to syntax rules:the name " + temp
                    + " is out of bound ");
              }
              // ��һλ�����д
              if (!((temp.charAt(0) <= 90) && (temp.charAt(0) >= 65))) {
                throw new Exception("statements do not conform to syntax rules:the first letter \""
                    + temp.charAt(0) + "\" is not capital");
              }
              // �ٱ�ֻ֤����λ������£��жϵڶ�����ĸ�Ƿ���Сд
              if (temp.length() > 1) {
                if (!((temp.charAt(1) <= 122) && (temp.charAt(1) >= 97))) {
                  throw new Exception(
                      "statements do not conform to syntax rules:the second letter \""
                          + temp.charAt(1) + "\" is not lowercase");
                }
              }
            } catch (Exception e) {
              log.warning("atomStructure:exception happens in class atomStructure,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          central = new AtomL1(m2.group(7));
        }
        while (m1.find()) {
          String a1 = m1.group(1);
          String a2 = m1.group(3);
          if (ifCheck) {
            try {
              // �����ű�����ڵ�����
              if (Integer.parseInt(a1) < 0) {
                throw new Exception(
                    "statements do not conform to syntax rules:the numebr of track is negative: "
                        + Integer.parseInt(a1));
              }
              // ����ϵĵ�����Ŀ���������
              if (Integer.parseInt(a2) <= 0) {
                throw new Exception("statements do not conform to syntax rules:"
                    + "the number of electronic is non-positive: " + Integer.parseInt(a2));
              }
            } catch (Exception e) {
              log.warning("atomStructure:exception happens in class atomStructure,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          trackObjectNumber.put(count, Integer.parseInt(a2));
          count++;
        }
      }
      // �൱�����ڶ���֮����ж��������ӹ����Ŀ�Ƿ����
      if (ifCheck) {
        try {
          if (count != trackNumber2) {
            throw new Exception(
                "incorrect dependencies:the electronic track number is contradictory:" + count);
          }
        } catch (Exception e) {
          log.warning(
              "atomStructure:exception happens in class atomStructure,method creatingTrackFromFiles:"
                  + e.getMessage()
                  + " handling:return false and exception message is printed on console");
          System.out.println(e.getMessage());
          return false;
          // TODO: handle exception
        }
      }
    } catch (IOException e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    checkRep();
    log.info("atomStructure:read a file");
    return true;
  }

  public boolean creatingTrackFromFilesScanner(String name) {
    boolean ifCheck = true;
    log.info("atomStructure:assert file name isn't null,"
        + "in class atomStructure,method creatingTrackFromFiles");
    assert name.length() != 0 : "file name is null";// ǰ������
    String txt = new String();
    String fre9 = "(NumberOfTracks)"; // Word 3
    String fre10 = "(\\s+)"; // White Space 4
    String fre11 = "(:)"; // Any Single Character 4
    String fre12 = "(:)"; // Any Single Character 5
    String fre13 = "(=)"; // Any Single Character 6
    String fre14 = "(\\s+)"; // White Space 5
    String fre15 = "(-?\\d+)"; // Integer Number 1
    Pattern fp = Pattern.compile(fre9 + fre10 + fre11 + fre12 + fre13 + fre14 + fre15,
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String re1 = "(ElementName)"; // Word 1
    String re2 = "(\\s+)"; // White Space 1
    String re3 = "(:)"; // Any Single Character 1
    String re4 = "(:)"; // Any Single Character 2
    String re5 = "(=)"; // Any Single Character 3
    String re6 = "(\\s+)"; // White Space 2
    String re7 = "((?:[a-z][a-z]+))"; // Word 2
    Pattern p2 = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7,
        Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    String g1 = "(-?\\d+)"; // Integer Number 10
    String g2 = "(\\/)"; // Any Single Character 18
    String g3 = "(-?\\d+)"; // Integer Number 11
    Pattern p1 = Pattern.compile(g1 + g2 + g3, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    int count = 0;
    try  {
      // ʼ�ռ�ס������Ҫ���ļ��е����ݰ��������
      File file = new File("txt/" + name + "txt");
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        txt = sc.nextLine();
        Matcher fm = fp.matcher(txt);
        Matcher m1 = p1.matcher(txt);
        Matcher m2 = p2.matcher(txt);
        if (fm.find()) { // ����ƥ�䵽һ�Σ�ֻ��Ҫ��if
          trackNumber2 = Integer.parseInt(fm.group(7));
          // �����Ӧ����������
          if (ifCheck) {
            try {
              if (trackNumber2 <= 0) {
                throw new Exception("statements do not conform to syntax rules: the tracknumber "
                    + trackNumber2 + " is non-positive");
              }
            } catch (Exception e) {
              log.warning("atomStructure:exception happens in class atomStructure,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
        }
        if (m2.find()) {
          String temp = m2.group(7);
          if (ifCheck) {
            try {
              // ԭ�����������λ��ĸ
              if (!((temp.length() > 1) && (temp.length() < 3))) {
                throw new Exception("statements do not conform to syntax rules:the name " + temp
                    + " is out of bound ");
              }
              // ��һλ�����д
              if (!((temp.charAt(0) <= 90) && (temp.charAt(0) >= 65))) {
                throw new Exception("statements do not conform to syntax rules:the first letter \""
                    + temp.charAt(0) + "\" is not capital");
              }
              // �ٱ�ֻ֤����λ������£��жϵڶ�����ĸ�Ƿ���Сд
              if (temp.length() > 1) {
                if (!((temp.charAt(1) <= 122) && (temp.charAt(1) >= 97))) {
                  throw new Exception(
                      "statements do not conform to syntax rules:the second letter \""
                          + temp.charAt(1) + "\" is not lowercase");
                }
              }
            } catch (Exception e) {
              log.warning("atomStructure:exception happens in class atomStructure,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          central = new AtomL1(m2.group(7));
        }
        while (m1.find()) {
          String a1 = m1.group(1);
          String a2 = m1.group(3);
          if (ifCheck) {
            try {
              // �����ű�����ڵ�����
              if (Integer.parseInt(a1) < 0) {
                throw new Exception(
                    "statements do not conform to syntax rules:the numebr of track is negative: "
                        + Integer.parseInt(a1));
              }
              // ����ϵĵ�����Ŀ���������
              if (Integer.parseInt(a2) <= 0) {
                throw new Exception("statements do not conform to syntax rules:"
                    + "the number of electronic is non-positive: " + Integer.parseInt(a2));
              }
            } catch (Exception e) {
              log.warning("atomStructure:exception happens in class atomStructure,"
                  + "method creatingTrackFromFiles:" + e.getMessage()
                  + " handling:return false and exception message is printed on console");
              System.out.println(e.getMessage());
              return false;
              // TODO: handle exception
            }
          }
          trackObjectNumber.put(count, Integer.parseInt(a2));
          count++;
        }
      }
      // �൱�����ڶ���֮����ж��������ӹ����Ŀ�Ƿ����
      if (ifCheck) {
        try {
          if (count != trackNumber2) {
            throw new Exception(
                "incorrect dependencies:the electronic track number is contradictory:" + count);
          }
        } catch (Exception e) {
          log.warning(
              "atomStructure:exception happens in class atomStructure,method creatingTrackFromFiles:"
                  + e.getMessage()
                  + " handling:return false and exception message is printed on console");
          System.out.println(e.getMessage());
          return false;
          // TODO: handle exception
        }
      }
    } catch (IOException e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    checkRep();
    log.info("atomStructure:read a file");
    return true;
  }

  // ���������ʱ���ǲ��������쳣��
  /**
   * ͨ�����ݽṹ�������.
   * 
   * @param trackNumber2 ������ӹ����Ŀ.
   * @param rtrackObjectNumber �������ÿ������ϵ�������ӳ��.
   */
  public void creatingTrack(int trackNumber2, Map<Integer, Integer> rtrackObjectNumber) {
    // ͨ��checkRep���ǰ�úͺ�������
    checkRep();
    for (int i = 0; i < trackNumber2; i++) { // ������ϵͳ
      // ��ӹ��
      addTrack();
    }
    for (int i = 0; i < rtrackObjectNumber.size(); i++) {

      int t = rtrackObjectNumber.get(i);
      for (int j = 0; j < t; j++) {
        // �������������
        // addTrackObject(factory.manufactureE("electrical"), i);// �����ϼӵ���
        addTrackObject(atomFacctory.getRealFlyWeight("e"),i);
        //��ʵ�õ���ͬһ������
      }
      // new atomE1("electrical")
    }
    trackObjectNumber = rtrackObjectNumber;
    log.info("atomStructure:creating a track");
    checkRep();
  }



  /**
   * ��ԭ�Ӻ�������һ�����.
   */
  public void addTrack() { // ����һ�����,��Ҫ����ȷ������뾶���������
    // System.out.println(trackObject.size());
    int rep = physical.size() + 1;// ����뾶 = ������+1
    Track temp = realTrackFactory.manufacture(rep);
    physical.add(temp);
    trackObject.put(this.getPhysical().size() - 1, new HashSet<AtomE1>());
    trackObjectNumber.put(this.getPhysical().size() - 1, 0);
    checkRep();// ����������
    log.info("atomStructure:add a track");
    // System.out.println(trackObject.size());
    // System.out.println("Succeed!");
  }

  // �漰������ĵ���ʵ����֮����Ҫ��д�����е�transit��������Ϊ�˸��õĽ������
  /**
   * ��ɵ���ԾǨ.
   * 
   * @param source ���ӵ�Դ���.
   * @param target ���ӵ�Ŀ����.
   * @return ���ԾǨ�ɹ����򷵻�true,���򷵻�false����ʾ������Ϣ.
   */
  public boolean electronicTransition(int source, int target) {
    // ����ԾǨ������Դ�����Ŀ������ģ�����ԾǨ��ʵ�ʣ�Դ����ϼ���һ�����ӣ�Ŀ����������һ�����ӣ������Ŵ�0��ʼ
    try {
      if (!(source < physical.size())) {
        throw new Exception("The source orbit is out of bound!");
      }
      if (!(target < physical.size())) {
        throw new Exception("The target orbit is out of bound!");
      }
    } catch (Exception e) {
      // TODO: handle exception
      log.warning(
          "atomStructure:exception happens in class atomStructure,method electronicTransition:"
              + e.getMessage()
              + " handling:return false and exception message is printed on console");
      System.out.println(e.getMessage());
      return false;
    }

    // һ������֮��
    int t = trackObjectNumber.get(source);

    // ���ڳ������й����е��жϣ���if������assert���쳣����
    if (t == 0) {
      System.out.println("The source target doesn't has any e!");
      return false;
    }

    Iterator<AtomE1> iterator = trackObject.get(source).iterator();
    AtomE1 temp = new AtomE1("2");
    if (iterator.hasNext()) {
      // ����ָ����һ���ڴ�
      temp = iterator.next();
    }

    // trackObject.get(source).remove(temp);
    deleteTrackObject(temp, source);
    trackObjectNumber.put(source, t - 1);

    // trackObject.get(target).add(temp);a
    addTrackObject(temp, target);
    int q = trackObjectNumber.get(target);
    trackObjectNumber.put(target, q + 1);
    checkRep();// ����������
    log.info("atomStructure:a electron transits from track" + source + " to track" + target);
    // System.out.println("succeed!");
    return true;
  }
  
  
  
  public boolean atomWriteBackA()
  {
    long startTime = System.currentTimeMillis();
    try (BufferedWriter bw = new BufferedWriter(
        new OutputStreamWriter(new FileOutputStream("write-txt/AtomStructure.txt")))) {
      bw.write("ElementName ::= " + central.getName());
      bw.newLine();
      bw.write("NumberOfTracks ::= " + trackNumber2);
      bw.newLine();
      bw.write("NumberOfElectron ::= ");
      Map<Integer, Integer> temp = trackObjectNumber;
      Iterator<Integer> iterator = temp.keySet().iterator();
      // System.out.println(temp.size());
      while (iterator.hasNext()) {
        int e1 = iterator.next();
        if ((e1 + 1) != temp.size()) {
          bw.write((e1 + 1) + "/" + temp.get(e1) + ";");
        } else {
          bw.write((e1 + 1) + "/" + temp.get(e1));
        }
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
  
  public boolean atomWriteBackB()
  {
    long startTime = System.currentTimeMillis();
    try (BufferedWriter bw =
        new BufferedWriter(new FileWriter("write-txt/SocialNetworkCircle.txt"))) {
      bw.write("ElementName ::= " + central.getName());
      bw.newLine();
      bw.write("NumberOfTracks ::= " + trackNumber2);
      bw.newLine();
      bw.write("NumberOfElectron ::= ");
      Map<Integer, Integer> temp = trackObjectNumber;
      Iterator<Integer> iterator = temp.keySet().iterator();
      // System.out.println(temp.size());
      while (iterator.hasNext()) {
        int e1 = iterator.next();
        if ((e1 + 1) != temp.size()) {
          bw.write((e1 + 1) + "/" + temp.get(e1) + ";");
        } else {
          bw.write((e1 + 1) + "/" + temp.get(e1));
        }
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
  
  public boolean atomWriteBackC()
  {
    long startTime = System.currentTimeMillis();
    try (PrintWriter bw = new PrintWriter(new FileWriter("write-txt/SocialNetworkCircle.txt"))) {
      bw.write("ElementName ::= " + central.getName());
      bw.write("\r\n");
      bw.write("NumberOfTracks ::= " + trackNumber2);
      bw.write("\r\n");
      bw.write("NumberOfElectron ::= ");
      Map<Integer, Integer> temp = trackObjectNumber;
      Iterator<Integer> iterator = temp.keySet().iterator();
      // System.out.println(temp.size());
      while (iterator.hasNext()) {
        int e1 = iterator.next();
        if ((e1 + 1) != temp.size()) {
          bw.write((e1 + 1) + "/" + temp.get(e1) + ";");
        } else {
          bw.write((e1 + 1) + "/" + temp.get(e1));
        }
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
