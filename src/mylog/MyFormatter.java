package mylog;

import java.sql.Date;
import java.util.Calendar;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MyFormatter extends Formatter {
  @Override
  public String format(LogRecord record) {
    return new java.util.Date() + "-[" + record.getSourceClassName() + "."
        + record.getSourceMethodName() + "]" + record.getLevel() + ":" + record.getMessage() + "\n";
  }


}
