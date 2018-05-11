package syllabus.com.syllabus.https;

/**
 * Created by Administrator on 2018/5/8.
 */

public class IContant {
    public static final String BASE_URL = "http://192.168.8.51:8080/";
    public static final String LOGIN = BASE_URL + "auser/login";//登录
    public static final String REGISTER = BASE_URL + "auser/register";//注册


    public static final String SYLLABUS = BASE_URL + "syllabus";//课程表


    public static final String HOMEWORK = BASE_URL + "homework";//作业


    public static final String CAMPUSBULLETIN = BASE_URL + "campusBulletion/list";//校园公告
    public static final String CREATE_CAMPUSBULLETIN = BASE_URL + "campusBulletion/add";

    public static final String SCORE = BASE_URL + "scores/list";//成绩
    public static final String CREATE_SCORE=BASE_URL+"scores/add";


    public static final String CREATE_BOOKSINFO = BASE_URL + "books/add";//书本信息
    public static final String BOOKSINFO = BASE_URL + "books/list";//书本信息
}
