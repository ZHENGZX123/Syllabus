package syllabus.com.syllabus.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import okhttp3.Request;
import okhttp3.Response;
import syllabus.com.syllabus.BaseActivity;
import syllabus.com.syllabus.R;
import syllabus.com.syllabus.https.IContant;

/**
 * Created by Administrator on 2018/5/8.
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
        for (int i = 0; i < 10; i++) {
            JSONObject item = new JSONObject();
            try {
                item.put("subject_name", "数学");
                item.put("score", "70");
                item.put("create_time", System.currentTimeMillis());
                array.put(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adpater = new ChengjiAdpater();
        listView.setAdapter(adpater);
    }

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
}
