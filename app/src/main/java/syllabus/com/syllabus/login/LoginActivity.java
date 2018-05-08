package syllabus.com.syllabus.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import syllabus.com.syllabus.BaseActivity;
import syllabus.com.syllabus.R;
import syllabus.com.syllabus.https.IContant;
import syllabus.com.syllabus.ui.MainActivity;

/**
 * Created by Administrator on 2018/5/4.
 */

public class LoginActivity extends BaseActivity {
    EditText editText2, editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
    }

    public void Login(View view) {
        if (editText2.getText().toString().equals("")) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (editText.getText().toString().equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Request request = new Request.Builder()
                .url(IContant.LOGIN)
                .build();
        app.okhttp.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                finish();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }

    public void register(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
