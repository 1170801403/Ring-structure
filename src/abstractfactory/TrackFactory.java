package abstractfactory;

import centralobject.L1;
import physicalobject.TrackE1;

public class TrackFactory {

  public L1 manufactureL(String name) {
    return new L1(name);
  }

  public TrackE1 manufactureE(String name, int number, String nationaility, int age, double best) {
    return new TrackE1(name, number, nationaility, age, best);
  }
}
