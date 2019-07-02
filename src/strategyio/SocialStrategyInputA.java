package strategyio;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import circularorbit.SocialNetworkCircle;
import circularorbit.Tie;
import physicalobject.SocialE1;

public class SocialStrategyInputA extends SocialStrategyInput {
  @Override
  public boolean socialInput(SocialNetworkCircle society, String name) {
    return society.creatingTrackFromFiles(name);
  }
}

