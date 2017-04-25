package speedata.com.eoms.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import speedata.com.eoms.bean.DaoMaster;
import speedata.com.eoms.bean.DaoSession;
import speedata.com.eoms.bean.User;
import speedata.com.eoms.bean.UserDao;
import speedata.com.eoms.model.ImageItem;

/**
 * Created by 张明_ on 2017/4/19.
 */

public class MyApplication extends Application {

    private static DaoSession daoSession;
    private static MyApplication singleton;
    /**
     * 图片集合
     */
    public List<ImageItem> selectBitmap = new ArrayList<ImageItem>();

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        setupDatabase();
        initData();
    }
    public static MyApplication getInstance() {
        return singleton;
    }
    //模拟登陆数据
    private void initData() {
        UserDao userDao = daoSession.getUserDao();
        User user=new User();
        user.setUser_name("0000");
        user.setPwd("0000");
        user.setReal_name("zhang");
        userDao.insert(user);
    }

    private void setupDatabase() {
        //创建数据库
        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(this,"EOMS.db",null);
        //获得可写数据库
        SQLiteDatabase db=helper.getWritableDatabase();
        //获得数据库对象
        DaoMaster daoMaster=new DaoMaster(db);
        //获得dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant(){
        return daoSession;
    }
}