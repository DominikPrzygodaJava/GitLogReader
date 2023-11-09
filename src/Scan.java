import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scan {
    public static Map<String, Integer> ScanForLogs(File file) {
        int errors = 0;
        int warns = 0;
        int infos = 0;
        int debugs = 0;
        int fatals = 0;
        String error = "ERROR";
        String warn = "WARN";
        String info = "INFO";
        String debug = "DEBUG";
        String fatal = "FATAL";
        try {//skanowanie pliku linijka po linijce
            FileReader fileReader = new FileReader(file.getAbsolutePath());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(error)) errors++;
                if (line.contains(warn)) warns++;
                if (line.contains(info)) infos++;
                if (line.contains(debug)) debugs++;
                if (line.contains(fatal)) fatals++;
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + file.getName() + "'");
        } catch (IOException ex) {
            System.out.println("Error reading file '" + file.getName() + "'");
        }
        Map<String, Integer> map = new HashMap<String, Integer>();//tworzenie mapy z danymi na temat typu logów
        map.put(error, errors);
        map.put(warn, warns);
        map.put(info, infos);
        map.put(debug, debugs);
        map.put(fatal, fatals);
        //map.put("Severity Score",(double)(errors+fatals)/(errors+fatals+warns+infos+debugs));//mapa może zawierać severity score ale wymaga to zmiany typu z int na double
        return map;
    }

    public static Object ScanForLibs(File file) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String line;
        ArrayList<String> list = new ArrayList<String>();
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            int start = line.indexOf("[");
            int end = line.indexOf("]");
            if (start != -1 && end != -1) {
                list.add(line.substring(start, end + 1));
            }
        }
        return Convert.GetMap(list);
    }

    public static LogTimeSpan ScanForDates(File file) {
        String line = null;
        Date oldestDate = null;
        Date mostRecentDate = null;
        String pattern = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";//format daty
        Pattern r = Pattern.compile(pattern);
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                Matcher m = r.matcher(line);
                if (m.find()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
                    Date date = sdf.parse(m.group(0));
                    //System.out.println(date);//
                    //System.out.println(m.group(0));//
                    if (oldestDate == null || date.before(oldestDate)) {
                        oldestDate = date;
                    }
                    if (mostRecentDate == null || date.after(mostRecentDate)) {
                        mostRecentDate = date;
                    }
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        long diff = mostRecentDate.getTime() - oldestDate.getTime();
        LogTimeSpan lts = new LogTimeSpan();
        lts.mostRecentDate=mostRecentDate;
        lts.oldestDate=oldestDate;
        lts.diff=diff;
        return lts;
        //System.out.println(mostRecentDate);
        //System.out.println(oldestDate);
        //System.out.println("time in millis " + diff + " normal time " + Convert.GetConvertedTime(diff));
    }
}