package syllabus.com.syllabus.model;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import syllabus.com.syllabus.Utilis.Utils;

/**
 * Created by Administrator on 2018/5/7.
 */

public class MySubjectParser {
    public List<MySubject> parse(String parseString) {
        List<MySubject> courses = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(parseString);
            for (int i = 0; i < array.length(); i++) {
                JSONArray array2 = array.getJSONArray(i);
                String term = array2.getString(0);
                String name = array2.getString(3);
                String teacher = array2.getString(8);
                String string = array2.getString(17);
                if (string != null) {
                    string = string.replaceAll("\\(.*?\\)", "");
                }
                String room = array2.getString(16) + string;
                String weeks = array2.getString(11);
                int day, start, step;
                try {
                    day = Integer.parseInt(array2.getString(12));
                    start = Integer.parseInt(array2.getString(13));
                    step = Integer.parseInt(array2.getString(14));
                } catch (Exception e) {
                    day = -1;
                    start = -1;
                    step = -1;
                }
                courses.add(new MySubject(term, name, room, teacher, getWeekList(weeks), start, step, day, -1, null));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Utils.e("---", new Gson().toJson(courses));
        return courses;
    }

    public static List<Integer> getWeekList(String weeksString) {
        List<Integer> weekList = new ArrayList<>();
        if (weeksString == null || weeksString.length() == 0) return weekList;
        weeksString = weeksString.replaceAll("[^\\d\\-\\,]", "");
        if (weeksString.indexOf(",") != -1) {
            String[] arr = weeksString.split(",");
            for (int i = 0; i < arr.length; i++) {
                weekList.addAll(getWeekList2(arr[i]));
            }
        } else {
            weekList.addAll(getWeekList2(weeksString));
        }
        return weekList;
    }

    public static List<Integer> getWeekList() {
        List<Integer> weekList = new ArrayList<>();
        weekList.add(1);
        weekList.add(2);
        weekList.add(3);
        weekList.add(4);
        weekList.add(5);
        weekList.add(6);
        weekList.add(7);
        weekList.add(8);
        weekList.add(9);
        weekList.add(10);
        weekList.add(12);
        weekList.add(13);
        weekList.add(14);
        weekList.add(15);
        weekList.add(16);
        weekList.add(17);
        weekList.add(18);
        weekList.add(19);
        weekList.add(20);

        return weekList;
    }


    public static List<Integer> getWeekList2(String weeksString) {
        List<Integer> weekList = new ArrayList<>();
        int first = -1, end = -1, index = -1;
        if ((index = weeksString.indexOf("-")) != -1) {
            first = Integer.parseInt(weeksString.substring(0, index));
            end = Integer.parseInt(weeksString.substring(index + 1));
        } else {
            first = Integer.parseInt(weeksString);
            end = first;
        }
        for (int i = first; i <= end; i++)
            weekList.add(i);
        return weekList;
    }
}
