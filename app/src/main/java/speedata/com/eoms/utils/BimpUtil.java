package speedata.com.eoms.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import speedata.com.eoms.application.MyApplication;
import speedata.com.eoms.model.ImageItem;

public class BimpUtil {


    public static int max = 0;


    //拿到目录下文件名并转换成客户指定格式
    public static String getFileListStr(String path) {
        StringBuffer result = new StringBuffer();
        File file = new File(path);
        String[] list = file.list();
        for (int i = 0; i < list.length; i++) {
            String pathStr = list[i];
            if (i + 1 == list.length) {
                result.append(pathStr);
            } else {
                result.append(pathStr + "/");
            }
        }
        return String.valueOf(result);
    }

    /**
     * 整个复制文件夹里的文件
     *
     * @param oldPath 原文件路径 如：c:
     * @param newPath 复制后路径 如：f:
     */
    public static void copyFileToNewFile(String oldPath, String newPath) {
        String path = "";
        File file = new File(oldPath);
        File[] listFiles = file.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            path = listFiles[i].getPath();
            String[] split = path.split("/");
            String newPathResult = newPath + split[split.length - 1];
            File newFile = new File(newPathResult);// 创建目录
            if (!newFile.exists()) {// 目录存在返回false
                newFile.mkdirs();// 创建一个目录
            }
            copyFile(path, newPathResult);
        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        File oldfile = new File(oldPath);
        if (oldfile.exists()) { // 文件存在时
            InputStream inStream = null;
            FileOutputStream fs = null;
            // 读入原文件
            try {
                inStream = new FileInputStream(oldPath);
                fs = new FileOutputStream(newPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                byte[] buffer = new byte[1444];
                int bytesum = 0;
                int byteread = 0;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */

    public static void copyFolder(String oldPath, String newPath) {
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 修改文件或文件夹的权限
     **/
    public static void initChmod(String command) {
//        Process process = null;
//        DataOutputStream os = null;
//        try {
//            process = Runtime.getRuntime().exec("su");
//            os = new DataOutputStream(process.getOutputStream());
////            os.writeBytes("adb shell\n");
////            os.writeBytes("mkdir /data/HTYL/Out\n");
//            os.writeBytes(command + "\n");
//            os.writeBytes("exit\n");
//            os.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        finally {
//            try {
//                if (os != null) {
//                    os.close();
//                }
//                process.destroy();
//            } catch (Exception e) {
//            }
//        }


        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            int read;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();
            process.waitFor();
//            return output.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 图片压缩变换
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 1000)
                    && (options.outHeight >> i <= 1000)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }

    /**
     * 保存图片
     *
     * @param bm          图片
     * @param orderNumber 条码单号
     * @param picName     图片名
     * @return
     */
    public static boolean saveBitmap(Bitmap bm, String orderNumber, String picName) {
        try {
            String dirpath = FileUtil.getOrderNumberPath(orderNumber);
            File f = new File(dirpath, picName + ".png");
            if (f.exists()) {//文件存在先删除
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean delFile(String filePath, String code) {
        File file = new File(filePath);
        if (file.exists()) {

        } else {
            return false;
        }


        if (file.isFile()) {
            file.delete();
        }
        if (!TextUtils.isEmpty(code)) {
            File file2 = new File(FileUtil.getTempDataPath() + "/" + code);
            if (file2.isDirectory()) {
                file2.delete();
            }
        }
        return file.exists();
    }

    /**
     * 清空照片
     */
    public static void clearBitmap() {
        for (ImageItem ii : MyApplication.getInstance().selectBitmap) {
            if (ii.getBitmap() != null) {
                ii.recycleBitmap();
                FileUtil.deleteDir(new File(ii.getImagePath()));
            }
        }
        MyApplication.getInstance().selectBitmap.clear();
        BimpUtil.max = 0;
    }
}
