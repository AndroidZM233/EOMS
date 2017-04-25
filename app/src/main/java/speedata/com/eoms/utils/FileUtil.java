package speedata.com.eoms.utils;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by YJ on 16/4/27.
 */
public class FileUtil {
    /**
     * 获取图片存放的根路径
     *
     * @return
     */
    public static String getTempDataPath() {
        File path = new File(Environment.getExternalStorageDirectory(), "tempData");// 创建目录
        if (!path.exists()) {
            path.mkdirs();// 创建一个目录
        }
        return path.getPath();
    }

    /**
     * 获取sd卡路径
     * @return
     */
    public static String getsdPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 根据单号获取图片存放文件夹路径
     *
     * @param orderNumber
     * @return
     */
    public static String getOrderNumberPath(String orderNumber) {
        File file = new File(getTempDataPath() + "/" + orderNumber);
        if (!file.exists()) {
            file.mkdirs();// 创建一个目录
        }
        return file.getPath();
    }

    /**
     * 获取单号文件夹下所有的图片
     *
     * @param orderNumber
     * @return
     */
    public static File[] getFiles(String orderNumber) {
        File file = new File(getTempDataPath() + "/" + orderNumber);
        if (file.exists()) {
            file.list();
            return file.listFiles();
        }
        return null;
    }

    /**
     * 获取单号文件夹下所有的图片路径名称
     *
     * @param orderNumber
     * @return
     */
    public static String[] getFilePaths(String orderNumber) {
        File file = new File(getTempDataPath() + "/" + orderNumber);
        if (file.exists()) {
            return file.list();
        }
        return null;
    }

    /**
     * 删除文件
     *
     * @param filePath
     * @param code
     * @return
     */
    public static boolean delFile(String filePath, String code) {
        File file = new File(filePath);
        if (file.isFile()) {
            file.delete();
        }
        if (!TextUtils.isEmpty(code)) {
            File file2 = new File(getTempDataPath() + "/" + code);
            if (file2.isDirectory()) {
                file2.delete();
            }
        }
        return file.exists();
    }


    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful. If a
     * deletion fails, the method stops attempting to delete and returns
     * "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

}
