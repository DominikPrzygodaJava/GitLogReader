import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Convert {
    public static String GetConvertedTime(long time) {
        time = time / 1000;
        long years = time / (365 * 24 * 60 * 60);
        time = time % (365 * 24 * 60 * 60);
        long months = time / (30 * 24 * 60 * 60);
        time = time % (30 * 24 * 60 * 60);
        long days = time / (24 * 60 * 60);
        time = time % (24 * 60 * 60);
        long hours = time / (60 * 60);
        time = time % (60 * 60);
        long minutes = time / (60);
        time = time % 60;
        long seconds = time;
        return String.format("%d years, %d months, %d days, %d hours, %d minutes, %d seconds", years, months, days, hours, minutes, seconds);
    }

    public static Map<String, Integer> GetMap(ArrayList<String> list) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < list.size(); i++) {
            map.put(list.get(i), i);
        }
        //System.out.println(map);
        return map;
    }

    public static double GetSeverityScore(Map<String, Integer> map) {
        double errors = map.get("ERROR");
        double warns = map.get("WARN");
        double infos = map.get("INFO");
        double debugs = map.get("DEBUG");
        double fatals = map.get("FATAL");
        return ((errors + fatals) / (errors + fatals + warns + infos + debugs));
    }
}