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
import syllabus.com.syllabus.Utilis.Utils;
import syllabus.com.syllabus.https.IContant;

/**
 * Created by Administrator on 2018/5/8.
 */

public class BooksActivity extends BaseActivity {
    JSONArray array = new JSONArray();
    ListView listView;
    BooksAdpater adpater;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        listView = (ListView) findViewById(R.id.listview);
        for (int i = 0; i < 10; i++) {
            JSONObject item = new JSONObject();
            try {
                item.put("book_name", "佛列巡游记");
                item.put("book_code", "斯柯达房间卡萨荆防颗粒建安费几十块老地方就开始放假寄顺丰卡拉胶风口浪尖");
                item.put("borrow_name", "郭煜");
                item.put("borrow_time", System.currentTimeMillis());
                item.put("borrow_num", "2000万");
                array.put(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adpater = new BooksAdpater();
        listView.setAdapter(adpater);
    }

    @Override
    public void loadData() {
        super.loadData();
        Request request = new Request.Builder()
                .url(IContant.BOOKSINFO)
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

    public class BooksAdpater extends BaseAdapter {
        BooksHolder holder;

        public BooksAdpater() {
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
                view = LayoutInflater.from(BooksActivity.this).inflate(R.layout.item_books, null);
                holder = new BooksHolder();
                holder.name = (TextView) view.findViewById(R.id.name);
                holder.aour = (TextView) view.findViewById(R.id.aour);
                holder.num = (TextView) view.findViewById(R.id.num);
                holder.time = (TextView) view.findViewById(R.id.time);
                holder.content = (TextView) view.findViewById(R.id.content);
                view.setTag(holder);
            } else {
                holder = (BooksHolder) view.getTag();
            }
            JSONObject item = array.optJSONObject(position);
            holder.name.setText(item.optString("book_name"));
            holder.aour.setText("作者:"+item.optString("borrow_name"));
            holder.num.setText("销量："+item.optString("borrow_num"));
            holder.time.setText("出版时间"+Utils.stampToDate(item.optLong("borrow_time")));
            holder.content.setText(item.optString("book_code"));
            return view;
        }

        public class BooksHolder {
            TextView name, aour, num, time, content;
        }
    }
}
