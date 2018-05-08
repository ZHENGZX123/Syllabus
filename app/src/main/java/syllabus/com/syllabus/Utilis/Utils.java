package syllabus.com.syllabus.Utilis;

import android.util.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2018/5/8.
 */

public class Utils {
    public static void e(String tag, String msg) {  //信息太长,分段打印
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - tag.length();
        //大于4000时
        while (msg.length() > max_str_length) {
            Log.i(tag, msg.substring(0, max_str_length));
            msg = msg.substring(max_str_length);
        }
        //剩余部分
        Log.e(tag, msg);
    }

    /*
    * 将时间戳转换为时间
    */
    public static String stampToDate(long s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(s);
        res = simpleDateFormat.format(date);
        return res;
    }
}
