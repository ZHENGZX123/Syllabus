package syllabus.com.syllabus;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2018/5/4.
 */

public class App extends Application {

    public OkHttpClient okhttp = new OkHttpClient();
    /**
     * 开启的界面视图集合
     */
    public ArrayList<BaseActivity> activities = new ArrayList<BaseActivity>();

    public String userName;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * 结束所有界面
     */
    public void finishAllAct() {
        if (activities != null) {
            for (Activity activity : activities) {
                if (!activity.isFinishing())
                    activity.finish();
            }
        }
    }
}
