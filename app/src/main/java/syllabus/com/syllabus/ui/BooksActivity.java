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
 * 书籍
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
        adpater = new BooksAdpater();
        listView.setAdapter(adpater);
        loadData();
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
                try {
                    JSONObject data = new JSONObject(response.body().string());
                    if (data.optInt("code") == 200) {
                        array = data.optJSONArray("data");
                        Log.e("---", array.toString());
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
                holder.staute= (TextView) view.findViewById(R.id.staute);
                holder.content = (TextView) view.findViewById(R.id.content);
                view.setTag(holder);
            } else {
                holder = (BooksHolder) view.getTag();
            }
            JSONObject item = array.optJSONObject(position);
            holder.name.setText(item.optString("book_name"));
            holder.num.setText("借阅次数:" + item.optInt("borrow_num"));
            holder.content.setText("编号:"+item.optString("book_code"));
            if (item.optInt("state")==1){
                holder.staute.setText("待借阅");
                holder.aour.setText("还书人:" + item.optString("borrow_name"));
                holder.time.setText("还书时间:" + item.optString("give_time").replace("T","").replace(".000+0000",""));
            }else {
                holder.staute.setText("已借阅");
                holder.aour.setText("借阅人:" + item.optString("borrow_name"));
                holder.time.setText("借阅时间:" + item.optString("borrow_time").replace("T","").replace(".000+0000",""));
            }
            return view;
        }

        public class BooksHolder {
            TextView name, aour, num, time, content,staute;
        }
    }


}
