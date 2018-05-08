package syllabus.com.syllabus.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.zhuangfei.timetable.core.OnSubjectItemClickListener;
import com.zhuangfei.timetable.core.SubjectBean;
import com.zhuangfei.timetable.core.TimetableView;
import com.zhuangfei.timetable.core.grid.SubjectGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import syllabus.com.syllabus.BaseActivity;
import syllabus.com.syllabus.R;
import syllabus.com.syllabus.model.MySubject;
import syllabus.com.syllabus.model.MySubjectModel;

/**
 * Created by Administrator on 2018/5/7.
 */

public class SyllabusActivity extends BaseActivity implements OnSubjectItemClickListener {
    TimetableView mTimetableView;
    private List<SubjectBean> subjectBeans;
    private int curWeek = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_syllabus);
        mTimetableView = (TimetableView) findViewById(R.id.id_timetableView);
        subjectBeans = transform(MySubjectModel.loadDefaultSubjects());
        //使用默认的参数构造一个网格View,默认灰色
        SubjectGridView gridView=new SubjectGridView(this);
        mTimetableView.setDataSource(subjectBeans)
                .setCurTerm("大三上学期")
                .setCurWeek(curWeek)
                .setMax(true)
                .setBottomLayer(gridView)
                .setOnSubjectItemClickListener(this)
                .showTimetableView();
        mTimetableView.changeWeek(curWeek, true);
    }

    /**
     * Item点击处理
     *
     * @param subjectList 该Item处的课程集合
     */
    @Override
    public void onItemClick(View v, List<SubjectBean> subjectList) {
        int size = subjectList.size();
        String subjectStr = "";
        for (int i = 0; i < size; i++) {
            SubjectBean bean = subjectList.get(i);
            subjectStr += bean.getName() + "\n";
            subjectStr += "上课周次:" + bean.getWeekList().toString() + "\n";
            subjectStr += "时间:周" + bean.getDay() + "," + bean.getStart() + "至" + (bean.getStart() + bean.getStep() -
                    1) + "节上";
            if (i != (size - 1)) {
                subjectStr += "\n########\n";
            }
        }
        subjectStr += "\n";
        Toast.makeText(this, "该时段有" + size + "门课\n\n" + subjectStr, Toast.LENGTH_SHORT).show();
    }

    /**
     * 自定义转换规则,将自己的课程对象转换为所需要的对象集合
     *
     * @param mySubjects
     * @return
     */
    public List<SubjectBean> transform(List<MySubject> mySubjects) {
        //待返回的集合
        List<SubjectBean> subjectBeans = new ArrayList<>();
        //保存课程名、颜色的对应关系
        Map<String, Integer> colorMap = new HashMap<>();
        int colorCount = 1;
        //开始转换
        for (int i = 0; i < mySubjects.size(); i++) {
            MySubject mySubject = mySubjects.get(i);
            //计算课程颜色
            int color;
            if (colorMap.containsKey(mySubject.getName())) {
                color = colorMap.get(mySubject.getName());
            } else {
                colorMap.put(mySubject.getName(), colorCount);
                color = colorCount;
                colorCount++;
            }
            //转换
            subjectBeans.add(new SubjectBean(mySubject.getName(), mySubject.getRoom(), mySubject.getTeacher(),
                    mySubject.getWeekList(),
                    mySubject.getStart(), mySubject.getStep(), mySubject.getDay(), color, mySubject.getTime()));
        }
        return subjectBeans;
    }
}
