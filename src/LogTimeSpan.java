import java.util.Date;

public class LogTimeSpan {
    Date mostRecentDate= new Date();
    Date oldestDate = new Date();
    long diff = 0;
    public static void PrintDates(LogTimeSpan lts){
        System.out.println("Last log: "+lts.mostRecentDate);
        System.out.println("First log: "+lts.oldestDate);
    }
}
