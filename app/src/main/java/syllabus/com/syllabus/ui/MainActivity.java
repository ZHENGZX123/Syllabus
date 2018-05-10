package syllabus.com.syllabus.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import syllabus.com.syllabus.BaseActivity;
import syllabus.com.syllabus.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void syllabus(View view) {
        startActivity(new Intent(this, SyllabusActivity.class));
    }

    public void homeWork(View view) {
        startActivity(new Intent(this,HomeWorkActivity.class));
    }

    public void chengji(View view) {
        startActivity(new Intent(this,ChengjiActivity.class));
    }


    public void books(View view) {
        startActivity(new Intent(this,BooksActivity.class));
    }

    public void gonggao(View view) {
        startActivity(new Intent(this,GongGaoActivity.class));
    }

    long time = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - time > 2000) {
                time=System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                app.finishAllAct();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
