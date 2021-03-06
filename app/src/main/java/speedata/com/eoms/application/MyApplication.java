package speedata.com.eoms.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.SoundPool;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import speedata.com.eoms.bean.DaoMaster;
import speedata.com.eoms.bean.DaoSession;
import speedata.com.eoms.model.ImageItem;
import speedata.com.eoms.utils.BimpUtil;

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
    public static String deviceId;
    public static String realName = "测试";
    public static String userName = "测试";
    public static SoundPool soundPool;
    public static int failedSound;
    public static int successSound;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        setupDatabase();
        deviceId = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId();
        BimpUtil.isFolderExists("/storage/emulated/0/data/HTYL/Out/");
        BimpUtil.isFolderExists("/storage/emulated/0/data/HTYL/In/");
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        failedSound = soundPool.load("/system/media/audio/ui/VideoRecord.ogg", 0);
        successSound = soundPool.load("/system/media/audio/ui/WirelessChargingStarted.ogg", 0);

        if (!TextUtils.isEmpty(deviceId)) {
            BimpUtil.writeOnlyContent("/storage/emulated/0/data/HTYL/IMEI.txt", deviceId);
        }

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        soundPool.release();
    }

    public static MyApplication getInstance() {
        return singleton;
    }


    private void setupDatabase() {
        //创建数据库
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "EOMS.db", null);
        //获得可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获得数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获得dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }

    public static void setRealName(String name) {
        realName = name;
    }

    public static void setUserName(String name) {
        userName = name;
    }
}
