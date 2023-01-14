package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Task2 {

    public static void main(String[] args) {
        Map<Integer, List<RocketStory>> myDataMap = readLog();
        sortRocketStoryLists(myDataMap);
        List<Integer> ids = getListOfIds(myDataMap);
        printTime(ids, myDataMap);

    }

    static Map<Integer, List<RocketStory>> readLog() {
        Map<Integer, List<RocketStory>> myDataMap = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine(); // переход на новую строку
        for (int i = 0; i < n ; i++) {
            RocketStory rocketStory = getRocketStoryFromLineOfLog(scanner.nextLine());
            if (!myDataMap.containsKey(rocketStory.id)) {
                List<RocketStory> rocketStoryList = new ArrayList<>();
                rocketStoryList.add(rocketStory);
                myDataMap.put(rocketStory.getId(), rocketStoryList);
            } else {
                List<RocketStory> rocketStoryList = myDataMap.get(rocketStory.id);
                rocketStoryList.add(rocketStory);
            }
        }
        return myDataMap;
    }

    static RocketStory getRocketStoryFromLineOfLog(String line) {
        String[] rocketData = line.split(" ");
        return new RocketStory(Integer.parseInt(rocketData[0]) , Integer.parseInt(rocketData[1]),
                Integer.parseInt(rocketData[2]), Integer.parseInt(rocketData[3]), rocketData[4]);
    }

    static void sortRocketStoryLists(Map<Integer, List<RocketStory>> myDataMap) {
        myDataMap.keySet().stream().forEach(key -> {
            List<RocketStory> rocketStoryList = myDataMap.get(key);
            Collections.sort(rocketStoryList);
        });
    }

    static List<Integer> getListOfIds(Map<Integer, List<RocketStory>> myDataMap) {
        return myDataMap.keySet().stream().sorted().collect(Collectors.toList());
    }

    static void printTime(List<Integer> ids, Map<Integer, List<RocketStory>> myDataMap) {
        for (int i = 0; i < ids.size(); i++) {
            System.out.print(computeTime(myDataMap.get(ids.get(i))) + " ");
        }
    }
    static int computeTime(List<RocketStory> rocketStoryList) {
        int time = 0;
        int start = 0;
        int end = 0;
        for (int i = 0; i < rocketStoryList.size(); i++) {
            if (rocketStoryList.get(i).getStatusCode().equals("A")){
                start = rocketStoryList.get(i).getMinute() + rocketStoryList.get(i).getHour() * 60
                        + rocketStoryList.get(i).getDay() * 60 * 24;
            }
            if (rocketStoryList.get(i).getStatusCode().equals("S") || rocketStoryList.get(i).getStatusCode().equals("C")){
                end = rocketStoryList.get(i).getMinute() + rocketStoryList.get(i).getHour() * 60
                        + rocketStoryList.get(i).getDay() * 60 * 24;
                time += end - start;
            }
        }
        return time;
    }

}
class RocketStory implements  Comparable<RocketStory>{
    int day;
    int hour;
    int minute;
    int id;
    String statusCode;

    public RocketStory(int day, int hour, int minute, int id, String statusCode) {
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.id = id;
        this.statusCode = statusCode;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getId() {
        return id;
    }


    public String getStatusCode() {
        return statusCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RocketStory that = (RocketStory) o;
        return day == that.day && hour == that.hour && minute == that.minute && id == that.id && Objects.equals(statusCode, that.statusCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, hour, minute, id, statusCode);
    }

    @Override
    public int compareTo(RocketStory o) {
        if (this.getDay() != o.getDay()) {
            return (this.getDay() - o.getDay()) * 24 * 60;
        } else {
            if (this.getHour() != o.getHour()) {
                return (this.getHour() - o.getHour())*60;
            } else {
                if (this.minute != o.minute) {
                    return this.getMinute() - o.getMinute();
                }
            }

        }
        return 0;
    }
}