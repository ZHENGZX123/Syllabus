package syllabus.com.syllabus.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import syllabus.com.syllabus.BaseActivity;
import syllabus.com.syllabus.R;
import syllabus.com.syllabus.https.IContant;
import syllabus.com.syllabus.ui.MainActivity;

/**
 * Created by Administrator on 2018/5/4.
 * 登陆页
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
        try {
            JSONObject data = new JSONObject();
            data.put("username", editText2.getText().toString());
            data.put("password", editText.getText().toString());
            RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                    , data.toString());
            Request request = new Request.Builder()
                    .url(IContant.LOGIN)
                    .post(body)
                    .build();
            app.okhttp.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("---", e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String s = response.body().string().toString();
                    Log.e("---", s);

                    try {
                        JSONObject data = new JSONObject(s);
                        if (data.optInt("code") == 200) {
                            toast("登录成功");
                            finish();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            getSharedPreferences("syllabus", 0).edit().putString("userName", editText2.getText().toString())
                                    .commit();
                            getSharedPreferences("syllabus", 0).edit().putInt("college", data.optJSONObject("data").optInt("college_id"))
                                    .commit();
                        } else {
                            toast("登录失败，请检查用户名密码");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void register(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
