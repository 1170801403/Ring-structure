// mutable

package circularorbit;

// 遗漏点：用户删除一条轨道，或增加一条轨道时，如何重新进行初始化(已解决)
// 遗漏点：每条轨道上没有记录轨道上的物体，熵值怎么计算
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import centralobject.L1;
import physicalobject.E1;
import physicalobject.TrackE1;
import track.*;

// parameter n. 参数；系数；参量
// 具体的子类中可以用具体的L,E
public class TrackGame extends ConcreteCircularObject<L1, TrackE1> { 
  // ConcreteCircularObject后面可以不写任何东西，要写的话只能写<L,E>
  ;
}
