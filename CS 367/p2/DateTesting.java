import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;

public class DateTesting {
  public static void main(String[] args) throws ParseException {
    Date date1, date2;
    DateFormat dateWithoutTime, df;
    String dateStr;

    dateWithoutTime = new SimpleDateFormat("yyyyMMdd"); 

    df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
    dateStr = "Tue, 12 Sep 2015 08:22:32 -0500";

    try {
      date1 = df.parse(dateStr);
      date2 = new Date();

      System.out.println(dateWithoutTime.format(date1));
      System.out.println(dateWithoutTime.format(date2));

      System.out.println("Dates are equal: " + 
          dateWithoutTime.format(date1).equals(dateWithoutTime.format(date2)));
    } catch (ParseException e) {
      System.out.println("Caught an error parsing the date!");
    }
  } 
}

