package syllabus.com.syllabus.Utilis;

import android.util.Log;

/**
 * Created by Administrator on 2018/5/10.
 */

public class Logger {
    static boolean isLog = true;
    static String syllabus = "syllabus";

    public static void log(Object s) {
        if (isLog) {
            Log.e(syllabus, s.toString());
        }
    }
}
