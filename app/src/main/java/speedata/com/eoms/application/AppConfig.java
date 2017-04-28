package speedata.com.eoms.application;

import android.os.Environment;

import java.io.File;

/**
 * Created by 张明_ on 2017/4/21.
 */

public class AppConfig {
    /**
     * 获取临时图片目录
     *
     * @return
     */
    public static String getTempDataPath() {
        // create directory full path
        File path = new File(Environment.getExternalStorageDirectory(), "tempData");// 创建目录
        if (!path.exists()) {// 目录存在返回false
            path.mkdirs();// 创建一个目录
        }
        return path.getPath();
    }

    /**
     * 获取存储信息目录
     *
     * @return
     */
    public static String getDataPath() {
        // create directory full path
        File path = new File(Environment.getExternalStorageDirectory(), "data/HTYL");// 创建目录
        if (!path.exists()) {// 目录存在返回false
            path.mkdirs();// 创建一个目录
        }
        return path.getPath();
    }
}
