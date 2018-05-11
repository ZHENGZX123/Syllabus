package syllabus.com.syllabus.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import syllabus.com.syllabus.Utilis.Logger;
import syllabus.com.syllabus.https.IContant;

/**
 * Created by Administrator on 2018/5/7.
 * 注册页
 */

public class RegisterActivity extends BaseActivity {
    EditText editText3, editText2, editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
    }


    public void Register(View view) {
        if (editText2.getText().toString().equals("")) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (editText.getText().toString().equals("") || editText3.getText().toString().equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!editText.getText().toString().equals(editText3.getText().toString())) {
            Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            JSONObject data = new JSONObject();
            data.put("username", editText2.getText().toString());
            data.put("password", editText.getText().toString());
            RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                    , data.toString());
            Request request = new Request.Builder()
                    .url(IContant.REGISTER)
                    .post(body)
                    .build();
            app.okhttp.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String s = response.body().string().toString();
                    Logger.log(s);
                    getSharedPreferences("syllabus", 0).edit().putString("userName", editText2.getText().toString())
                            .commit();
                    try {
                        JSONObject data = new JSONObject(s);
                        if (data.optInt("code") == 200) {
                            toast("注册成功，请重新登录");
                            app.finishAllAct();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        } else {
                            toast("注册失败");
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
}
