
package abstractfactory;

import centralobject.SocialL1;

import physicalobject.SocialE1;

public class SocialFactory {
  public SocialL1 manufactureL(String name, int age, char sex) {
    return new SocialL1(name, age, sex);
  }

  public SocialE1 manufactureE(String name, int realAge, char realSex) {
    return new SocialE1(name, realAge, realSex);
  }
}
