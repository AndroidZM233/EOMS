package speedata.com.eoms.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import speedata.com.eoms.application.MyApplication;
import speedata.com.eoms.model.ImageItem;

public class BimpUtil {


    public static int max = 0;


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
        if (file.exists()){

        }else {
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
