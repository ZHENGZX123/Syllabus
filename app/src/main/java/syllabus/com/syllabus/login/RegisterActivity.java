package syllabus.com.syllabus.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import syllabus.com.syllabus.views.poplib.PopCommon;
import syllabus.com.syllabus.views.poplib.PopModel;

/**
 * Created by Administrator on 2018/5/7.
 * 注册页
 */

public class RegisterActivity extends BaseActivity {
    EditText editText, editText2;
JSONArray colleges=new JSONArray();
    JSONArray oaClasses=new JSONArray();
    JSONArray roles=new JSONArray();
    JSONObject college;
    JSONObject oaClass;
    JSONObject role;
TextView collegeTxt,oaClassTxt,roleTxt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText = (EditText) findViewById(R.id.editText);
        collegeTxt= (TextView) findViewById(R.id.xueyuan);
        oaClassTxt= (TextView) findViewById(R.id.zhuangyuan);
        roleTxt= (TextView) findViewById(R.id.juese);
        loadData();
    }

    @Override
    public void loadData() {
        Request request = new Request.Builder()
                .url(IContant.GET_REGISTER)
                .build();
        app.okhttp.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject data = new JSONObject(response.body().string());
                    Logger.log(data.toString());
                    if (data.optInt("code") == 200) {
                        colleges=data.optJSONObject("data").optJSONArray("college");
                        oaClasses=data.optJSONObject("data").optJSONArray("oaClasses");
                        roles=data.optJSONObject("data").optJSONArray("roles");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void juese(View view){
        /** 初始化数据源 **/
        final List<PopModel> list = new ArrayList<>();
        for (int i=0;i<roles.length();i++){
            PopModel item = new PopModel();
            item.setItemDesc(roles.optJSONObject(i).optString("name"));
            list.add(item);
        }
        PopCommon popCommon = new PopCommon(this, list, new PopCommon.OnPopCommonListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                role=roles.optJSONObject(position);
                roleTxt.setText(role.optString("name"));
            }
            @Override
            public void onDismiss() {

            }
        });
        popCommon.showPop(roleTxt, dp2px(getApplicationContext(), 5), roleTxt.getHeight() / 4 * 5);

    }
    public void college(View view){

        /** 初始化数据源 **/
        final List<PopModel> list = new ArrayList<>();
        for (int i=0;i<colleges.length();i++){
            PopModel item = new PopModel();
            item.setItemDesc(colleges.optJSONObject(i).optString("name"));
            list.add(item);
        }
        PopCommon popCommon = new PopCommon(this, list, new PopCommon.OnPopCommonListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               college=colleges.optJSONObject(position);
                collegeTxt.setText(college.optString("name"));
            }
            @Override
            public void onDismiss() {

            }
        });
        popCommon.showPop(collegeTxt, dp2px(getApplicationContext(), 5), collegeTxt.getHeight() / 4 * 5);
    }

    public void oaClasses(View view){
        /** 初始化数据源 **/
        final List<PopModel> list = new ArrayList<>();
        for (int i=0;i<oaClasses.length();i++){
            PopModel item = new PopModel();
            item.setItemDesc(oaClasses.optJSONObject(i).optString("name"));
            list.add(item);
        }

        PopCommon popCommon = new PopCommon(this, list, new PopCommon.OnPopCommonListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       oaClass=oaClasses.optJSONObject(position);
                oaClassTxt.setText(oaClass.optString("name"));
         }
            @Override
            public void onDismiss() {

            }
        });
        popCommon.showPop(oaClassTxt, dp2px(getApplicationContext(), 5), oaClassTxt.getHeight() / 4 * 5);
    }

    public void Register(View view) {
        if (editText2.getText().toString().equals("")) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (editText.getText().toString().equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (college==null){
            Toast.makeText(this, "请选择学院", Toast.LENGTH_SHORT).show();
        return;
    }
        if (oaClass==null){
            Toast.makeText(this, "请选择专业", Toast.LENGTH_SHORT).show();
        return;
    }
        if (role==null){
            Toast.makeText(this, "请选择角色", Toast.LENGTH_SHORT).show();
        return;
    }
        try {
            JSONObject data = new JSONObject();
            data.put("username", editText2.getText().toString());
            data.put("password", editText.getText().toString());
            data.put("class_id",oaClass.opt("id"));
            data.put("class_name",oaClass.opt("name"));
            data.put("college_id",college.opt("id"));
            data.put("college_name",college.opt("name"));
            data.put("role_id",role.opt("id"));
            data.put("role_name",role.opt("name"));
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

    private static int dp2px(Context context, float value) {
        return (int) (context.getResources().getDisplayMetrics().density * value + 0.5);
    }
}
