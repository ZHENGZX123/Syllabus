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
import syllabus.com.syllabus.https.IContant;

/**
 * Created by Administrator on 2018/5/8.
 * 成绩
 */

public class ChengjiActivity extends BaseActivity {
    ListView listView;
    JSONArray array = new JSONArray();
    ChengjiAdpater adpater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chengji);
        listView = (ListView) findViewById(R.id.listview);
        adpater = new ChengjiAdpater();
        listView.setAdapter(adpater);
        loadData();
    }




    @Override
    public void loadData() {
        super.loadData();
        Request request = new Request.Builder()
                .url(IContant.SCORE)
                .build();
        app.okhttp.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject data = new JSONObject(response.body().string());
                    if (data.optInt("code") == 200) {
                        JSONArray array1 = data.optJSONArray("data");
                        array = new JSONArray();
                        for (int i = 0; i < array1.length(); i++) {//过了不是自己的成绩
                            JSONObject item = array1.optJSONObject(i);
                            if (item.optString("user_name").equals(getSharedPreferences("syllabus", 0).getString
                                    ("userName", ""))) {
                                array.put(item);
                            }
                        }
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

    public class ChengjiAdpater extends BaseAdapter {
        GongGaoHolder holder;

        public ChengjiAdpater() {
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
                view = LayoutInflater.from(ChengjiActivity.this).inflate(R.layout.item_chengji, null);
                holder = new GongGaoHolder();
                holder.name = (TextView) view.findViewById(R.id.name);
                holder.score = (TextView) view.findViewById(R.id.score);
                view.setTag(holder);
            } else {
                holder = (GongGaoHolder) view.getTag();
            }
            JSONObject item = array.optJSONObject(position);
            holder.name.setText(item.optString("subject_name"));
            holder.score.setText(item.optString("score"));
            return view;
        }

        public class GongGaoHolder {
            TextView name, score;
        }
    }


    public void check(View view) {
        try {
            JSONObject data = new JSONObject();
            data.put("subject_name", "語文");
            data.put("score", 12);
            data.put("user_name", getSharedPreferences("syllabus", 0).getString
                    ("userName", ""));
            RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                    , data.toString());
            Request request = new Request.Builder()
                    .url(IContant.CREATE_SCORE)
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
}
