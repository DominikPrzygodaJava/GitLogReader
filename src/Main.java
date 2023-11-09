import java.io.*;
import java.util.*;

import static java.lang.System.currentTimeMillis;

public class Main {
    public static void main(String[] args) {
        File dir = new File("D:\\\\logs\\\\");//Otwiera katalog logs
        File[] files = dir.listFiles((d, name) -> name.endsWith(".log"));//Tworzy listę plików .log
        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());//układa listę w poprawnej kolejności
        for (File file : files) {//iteruje przez wszystkie pliki z listy
            long timerStart = currentTimeMillis();//rozpoczyna pomiar czasu dla pojedyńczego pliku
            System.out.println(file.getName());
            System.out.println(Scan.ScanForLibs(file));//Wypisuje biblioteki i to ile razy wystąpiły
            System.out.println(Scan.ScanForLogs(file));//Wypisuje rodzaje logów i ile razy wystąpiły
            System.out.println("Severity score: " + Convert.GetSeverityScore(Scan.ScanForLogs(file)));//Wypisuje stosunek logów ERROR i FATAL do całości
            LogTimeSpan lts = Scan.ScanForDates(file);//Podaje do obiektu daty pierwszego i ostatniego logu oraz czas pomiędzy logami
            //LogTimeSpan.PrintDates(lts);//Wypisuje daty pierwszego i ostatniego logu
            System.out.println("Last log: "+lts.mostRecentDate);
            System.out.println("First log: "+lts.oldestDate);
            System.out.println("Total log time: "+Convert.GetConvertedTime(lts.diff));
            long timerStop = currentTimeMillis() - timerStart;//kończy pomiar czasu dla pojedyńczego pliku
            System.out.println("Read time in millis: "+timerStop);
        }
    }
}