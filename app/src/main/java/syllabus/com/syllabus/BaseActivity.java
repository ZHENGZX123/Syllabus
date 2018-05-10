package syllabus.com.syllabus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/5/4.
 */

public class BaseActivity extends AppCompatActivity {
    /**
     * 应有程序
     */
    public App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (App) getApplicationContext();
        app.activities.add(this);
    }

    public void back(View view) {
        finish();
    }
    public void loadData(){}
    public void toast(final String s){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this,s,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
