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

import syllabus.com.syllabus.BaseActivity;
import syllabus.com.syllabus.R;
import syllabus.com.syllabus.Utilis.Utils;

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
        for (int i = 0; i < 10; i++) {
            JSONObject item = new JSONObject();
            try {
                item.put("content", "寻物启事dddddddddddddd");
                item.put("theme", "寻物启事");
                item.put("create_time", System.currentTimeMillis());
                array.put(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adpater = new GongGaoAdpater();
        listView.setAdapter(adpater);
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
                view.setTag(holder);
            } else {
                holder = (GongGaoHolder) view.getTag();
            }
            JSONObject item = array.optJSONObject(position);
            holder.content.setText(item.optString("content"));
            holder.title.setText(item.optString("theme"));
            holder.time.setText(Utils.stampToDate(item.optLong("create_time")));
            return view;
        }

        public class GongGaoHolder {
            TextView title, content, time;
        }
    }
}
