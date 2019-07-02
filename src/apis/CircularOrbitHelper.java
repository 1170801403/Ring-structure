package apis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JPanel;
import centralobject.L1;
import circularorbit.CircularOrbit;
import circularorbit.Tie;
import physicalobject.E1;
import physicalobject.SocialE1;

public class CircularOrbitHelper<L extends L1, E extends E1> {
  int trackNumber;
  Map<Integer, Set<E>> temptrackObject;
  Map<Integer, Integer> thingsNumberPerTrack = new HashMap<Integer, Integer>();// ����Ҫ����һ�οռ�
  Map<E, Position> thingsPosition = new HashMap<E, Position>();
  Map<E, Set<E>> eerelationship;
  Set<E> lerelationship;
  List<Tie> tempsocialTie;
  L cretral;

  // ���캯��
  public CircularOrbitHelper(CircularOrbit c) {
    cretral = (L) c.getCentral();
    temptrackObject = c.getTrackObject();
    trackNumber = temptrackObject.size();
    eerelationship = c.getRelationship();
    lerelationship = c.getLErelationship();
    // System.out.println("y");// ����û������
    Iterator<Integer> iterator = temptrackObject.keySet().iterator();
    tempsocialTie = c.getSocialTie();
    // friend = c.getFriend();
    while (iterator.hasNext()) {
      int i = iterator.next();
      // System.out.println(i);
      thingsNumberPerTrack.put(i, temptrackObject.get(i).size());// ��ʱ�����Ӧ��û������
    }

  }

  // ����Ҫ���õĺ���
  public void visualize() {
    myFrame frame = new myFrame();
    frame.setVisible(true);
  }

  class myPanel extends JPanel {
    private myFrame frame;

    public myPanel(myFrame frame) {
      super();
      this.frame = frame;
    }

    public void paintComponent(Graphics g) {
      // g.drawRoundRect(10, 10, 100, 100, 100, 100);
      int length = 50;
      int doubleLength = 2 * length;
      super.paintComponent(g);
      int width = getWidth();
      int height = getHeight();
      g.fillOval(width / 2 - 16, height / 2 - 16, 32, 32);// �����ĵ�����
      Iterator<Integer> iterator = temptrackObject.keySet().iterator();
      int m = 0;
      while (iterator.hasNext()) {
        // ���������
        int realNumber = iterator.next();
        // System.out.println("realNumber" + realNumber);
        g.drawOval(width / 2 - (m + 1) * length, height / 2 - (m + 1) * length,
            doubleLength + doubleLength * m, doubleLength + doubleLength * m);
        int thingsNumber = thingsNumberPerTrack.get(realNumber);
        // System.out.println("thingsNumber" + thingsNumber);
        double partition;
        // ����һ�����֮���¹������û�������
        if (thingsNumber != 0) {
          partition = (Math.PI) / (double) (thingsNumber);
          // ���㲿��
          double a = 0.00;
          // ��øù���ϵ���������
          List<E> things = new ArrayList<E>(temptrackObject.get(realNumber));
          for (int j = 0; j < thingsNumber; j++) {

            a = a + partition;
            int x = width / 2 - (m + 1) * length + (length * (m + 1))
                + (int) (((m + 1) * length) * Math.cos(a + j * partition));
            int y = height / 2 - (m + 1) * length + (length * (m + 1))
                + (int) (((m + 1) * length) * Math.sin(a + j * partition));
            // thingsPosition.put(temptrackObject.get(i), new position(x, y));
            thingsPosition.put(things.get(j), new Position(x, y));// ����ϵ�ÿ�������Ӧ��һ������
            g.setFont(new Font("����", Font.PLAIN, 12));
            g.fillOval(x - 8, y - 8, 16, 16);
            g.setColor(Color.GREEN);
            g.drawString(things.get(j).getName(), x - 12, y - 12);
            g.setColor(Color.BLACK);
          }
        }
        m++;
      }
      g.setColor(Color.RED);
      // System.out.println("i is " + m);
      // System.out.println(temptrackObject.size());
      // ���������������
      // System.out.println("׼������");// ���ϴ���û������
      Iterator<E> iterator1 = eerelationship.keySet().iterator();
      g.setColor(Color.RED);
      while (iterator1.hasNext()) {
        // System.out.println("��ʼ��������");
        E r1 = iterator1.next();

        Set<E> rr1 = eerelationship.get(r1);
        Iterator<E> iterator2 = rr1.iterator();
        // System.out.println("����");
        // ɾ����һ����֮�����ѭ������û����
        while (iterator2.hasNext()) {
          g.setColor(Color.RED);
          // System.out.println("xxxxxxxxxx");
          E m1 = iterator2.next();
          g.drawLine(thingsPosition.get(r1).getX(), thingsPosition.get(r1).getY(),
              thingsPosition.get(m1).getX(), thingsPosition.get(m1).getY());
          // ��������ע�����ܶ�,���ܶȵ���ɫ����ɫ
          g.setColor(Color.blue);
          for (int mn = 0; mn < tempsocialTie.size(); mn++) {

            if ((r1.getName().equals(tempsocialTie.get(mn).getName1())
                && m1.getName().equals(tempsocialTie.get(mn).getName2()))
                || (r1.getName().equals(tempsocialTie.get(mn).getName2())
                    && m1.getName().equals(tempsocialTie.get(mn).getName1()))) {
              int numberx = (thingsPosition.get(r1).getX() + thingsPosition.get(m1).getX()) / 2;
              int numbery = (thingsPosition.get(r1).getY() + thingsPosition.get(m1).getY()) / 2;
              g.drawString(Float.toString(tempsocialTie.get(mn).getIni()), numberx, numbery);
            }

          }
        }
      }

      // �����ĵ��������������
      Iterator<E> iterator2 = lerelationship.iterator();
      // System.out.println("eee" + LErelationship.size());
      while (iterator2.hasNext()) {
        g.setColor(Color.RED);
        E r2 = iterator2.next();
        g.drawLine(width / 2, height / 2, thingsPosition.get(r2).getX(),
            thingsPosition.get(r2).getY());
        g.setColor(Color.blue);
        for (int v = 0; v < tempsocialTie.size(); v++) {
          // ����ж�ʵ���ϱȽ����࣬�����friend�ӵ�concretecircularOrbit����ͻ�úܶ�
          if ((r2.getName().equals(tempsocialTie.get(v).getName1())
              && cretral.getName().equals(tempsocialTie.get(v).getName2()))
              || (r2.getName().equals(tempsocialTie.get(v).getName2())
                  && cretral.getName().equals(tempsocialTie.get(v).getName1()))) {
            int numberx = (thingsPosition.get(r2).getX() + width / 2) / 2;
            int numbery = (thingsPosition.get(r2).getY() + height / 2) / 2;
            g.drawString(Float.toString(tempsocialTie.get(v).getIni()), numberx, numbery);
          }

        }
      }

    }
  }

  class myFrame extends JFrame {
    public static final String TITLE = "Javaͼ�λ���";
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 618;

    public myFrame() {
      super();
      initFrame();
    }

    private void initFrame() {
      // ���� ���ڱ��� �� ���ڴ�С
      setTitle(TITLE);
      setSize(WIDTH, HEIGHT);
      // ���ô��ڹرհ�ť��Ĭ�ϲ���(����ر�ʱ�˳�����)
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      // �Ѵ���λ�����õ���Ļ������
      setLocationRelativeTo(null);
      // ���ô��ڵ��������
      myPanel panel = new myPanel(this);
      setContentPane(panel);

    }
  }

  class Position {
    private final int x1;
    private final int y1;

    public Position(int x, int y) {
      this.x1 = x;
      this.y1 = y;
    }

    public int getX() {
      return this.x1;
    }

    public int getY() {
      return this.y1;
    }
  }

}

// public static void main(String[] args)
// {
// JFrame jf = new JFrame();
// MyPanel jp = new MyPanel();
//
// jf.setBounds(200, 200, 500, 500);
// jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// jf.add(jp);
// jf.setVisible(true);
//
// while(true)
// {
// //��ͣ���ػ�JPanel,ʵ�ֶ�����Ч��
// jp.display();
//
// try
// {
// Thread.sleep(300);
// }
// catch (InterruptedException e)
// {
// e.printStackTrace();
// }
// }
// }

// }//
///
// Graphics2D g2d = (Graphics2D)g;
//
// g2d.setColor(Color.RED);//���û�ͼ����ɫ
// g2d.fill3DRect(x, y, 100, 100, true);//���һ������
// }//
