package flyweight;

import java.util.HashMap;
import physicalobject.AtomE1;

public class FlyWeightFactory {
private HashMap<String, AtomE1> data = new HashMap<String, AtomE1>();
public AtomE1 getRealFlyWeight(String e3)
{
  if(data.containsKey(e3))
  {
    return data.get(e3);
  }
  else 
  {
    AtomE1 flyWeight = new AtomE1(e3);
    data.put(e3, flyWeight);
    return flyWeight;
  }
}


}
