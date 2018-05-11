package syllabus.com.syllabus.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
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
 * Created by Administrator on 2018/5/8.
 */

public class GongGaoActivity extends BaseActivity {
    ListView listView;
    JSONArray array = new JSONArray();
    GongGaoAdpater adpater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gonggao);
        listView = (ListView) findViewById(R.id.listview);
        adpater = new GongGaoAdpater();
        listView.setAdapter(adpater);
        loadData();
    }


    public void check(View view) {
        try {
            JSONObject data = new JSONObject();
            data.put("theme", "尋物啟事");
            data.put("content", "默默上課是卡路徑上課啦是放假設計師是的克里斯的防守打法");
            data.put("user_name", getSharedPreferences("syllabus", 0).getString
                    ("userName", ""));
            RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                    , data.toString());
            Request request = new Request.Builder()
                    .url(IContant.CREATE_CAMPUSBULLETIN)
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
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        super.loadData();
        Request request = new Request.Builder()
                .url(IContant.CAMPUSBULLETIN)
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
                        array = data.optJSONArray("data");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adpater.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public class GongGaoAdpater extends BaseAdapter {
        GongGaoHolder holder;

        public GongGaoAdpater() {
        }

        @Override
        public int getCount() {
            return array.length();
        }

        @Override
        public Object getItem(int position) {
            return array.optJSONObject(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(GongGaoActivity.this).inflate(R.layout.item_gonggao, null);
                holder = new GongGaoHolder();
                holder.content = (TextView) view.findViewById(R.id.content);
                holder.title = (TextView) view.findViewById(R.id.title);
                holder.time = (TextView) view.findViewById(R.id.time);
                holder.userName= (TextView) view.findViewById(R.id.userName);
                view.setTag(holder);
            } else {
                holder = (GongGaoHolder) view.getTag();
            }
            JSONObject item = array.optJSONObject(position);
            holder.content.setText(item.optString("content"));
            holder.title.setText(item.optString("theme"));
            holder.time.setText("发布时间:"+item.optString("create_time").split("T")[0]);
            holder.userName.setText("发布者: "+item.optString("user_name"));
            return view;
        }

        public class GongGaoHolder {
            TextView title, content, time,userName;
        }
    }
    public void back(View view) {
        finish();
    }
}
