package syllabus.com.syllabus.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.zhuangfei.timetable.core.SubjectBean;
import com.zhuangfei.timetable.core.TimetableView;
import com.zhuangfei.timetable.core.grid.SubjectGridView;

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
import syllabus.com.syllabus.https.IContant;

/**
 * Created by Administrator on 2018/5/7.
 * 课程表
 */

public class SyllabusActivity extends BaseActivity {
    TimetableView mTimetableView;
    private List<SubjectBean> subjectBeans;
    private int curWeek = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_syllabus);
        mTimetableView = (TimetableView) findViewById(R.id.id_timetableView);

        loadData();
    }


    public void loadData() {
        super.loadData();
        Request request = new Request.Builder()
                .url(IContant.SYLLABUS)
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
                        JSONArray array = data.optJSONArray("data");
                        subjectBeans = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject item = array.optJSONObject(i);
                            if (item.optInt("college_id") == getSharedPreferences("syllabus", 0).getInt("college", 0)) {
                                int part = item.optInt("part");
                                if (!item.optString("mon_class_name").equals("")) {
                                    subjectBeans.add(new SubjectBean(item.optString("mon_class_name"), part, 1));
                                }
                                if (!item.optString("tues_class_name").equals("")) {
                                    subjectBeans.add(new SubjectBean(item.optString("tues_class_name"), part, 2));
                                }
                                if (!item.optString("wed_class_name").equals("")) {
                                    subjectBeans.add(new SubjectBean(item.optString("wed_class_name"), part, 3));
                                }
                                if (!item.optString("thurs_class_name").equals("")) {
                                    subjectBeans.add(new SubjectBean(item.optString("thurs_class_name"), part, 4));
                                }
                                if (!item.optString("fir_class_name").equals("")) {
                                    subjectBeans.add(new SubjectBean(item.optString("fir_class_name"), part, 5));
                                }
                                if (!item.optString("sat_class_name").equals("")) {
                                    subjectBeans.add(new SubjectBean(item.optString("sat_class_name"), part, 6));
                                }
                                if (!item.optString("sun_class_name").equals("")) {
                                    subjectBeans.add(new SubjectBean(item.optString("sun_class_name"), part, 7));
                                }
                            }
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //使用默认的参数构造一个网格View,默认灰色
                            SubjectGridView gridView = new SubjectGridView(SyllabusActivity.this);
                            mTimetableView.setDataSource(subjectBeans)
                                    .setCurTerm("大三上学期")
                                    .setCurWeek(curWeek)
                                    .setMax(true)
                                    .setBottomLayer(gridView)
                                    .showTimetableView();
                            mTimetableView.changeWeek(curWeek, true);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
