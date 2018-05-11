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

    public void check(View view) {
        try {
            JSONObject data = new JSONObject();
            data.put("book_name", "格列佛遊記");
            data.put("book_code", "1233");
            data.put("borrow_name","格列佛");
            data.put("borrow_num","200");
            data.put("give_name","zz");
            RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                    , data.toString());
            Request request = new Request.Builder()
                    .url(IContant.CREATE_BOOKSINFO)
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
                        array  = data.optJSONArray("data");
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
                holder.content = (TextView) view.findViewById(R.id.content);
                view.setTag(holder);
            } else {
                holder = (BooksHolder) view.getTag();
            }
            JSONObject item = array.optJSONObject(position);
            holder.name.setText(item.optString("book_name"));
            holder.aour.setText("作者:"+item.optString("borrow_name"));
            holder.num.setText("销量："+item.optString("borrow_num"));
            holder.time.setText("出版時間:"+item.optString("borrow_time").split("T")[0]);
            holder.content.setText(item.optString("book_code"));
            return view;
        }

        public class BooksHolder {
            TextView name, aour, num, time, content;
        }
    }
    public void back(View view) {
        finish();
    }
}
