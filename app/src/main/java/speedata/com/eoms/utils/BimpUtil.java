package speedata.com.eoms.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import speedata.com.eoms.application.MyApplication;
import speedata.com.eoms.model.ImageItem;

public class BimpUtil {


    public static int max = 0;


    //拿到目录下文件名并转换成客户指定格式
    public static String getFileListStr(String path) {
        StringBuffer result = null;
        try {
            result = new StringBuffer();
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
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return String.valueOf(result);
    }

    //判读文件是否存在 不存在则创建
    public static boolean isFolderExists(String strFolder) {
        File file = new File(strFolder);
        if (!file.exists()) {
            if (file.mkdirs()) {
                return true;
            } else {
                return false;

            }
        }
        return true;

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

    //查看文件夹下是否有指定文件
    public static String findFileName(String path,String name){
        File file = new File(path);
        String[] list = file.list();
        for (int i = 0; i < list.length; i++) {
            boolean contains = list[i].contains(name);
            if (contains){
                return list[i];
            }
        }
        return null;
    }


    //删除包含_ok.log的指定文件
    public static void delCache(String path,String name){
        File file=new File(path);
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            boolean contains = files[i].getName().contains(name);
            if (contains){
                files[i].delete();
            }
        }
    }
    public static void writeContent(String path, String str) {
        OutputStreamWriter pw = null;//定义一个流
        try {
            pw = new OutputStreamWriter(new FileOutputStream(path,true), "UTF-8");//确认流的输出文件和编码格式，此过程创建了“test.txt”实例
            pw.write(str);//将要写入文件的内容，可以多次write
            pw.close();//关闭流
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void writeOnlyContent(String path, String str) {
        OutputStreamWriter pw = null;//定义一个流
        try {
            pw = new OutputStreamWriter(new FileOutputStream(path,false), "UTF-8");//确认流的输出文件和编码格式，此过程创建了“test.txt”实例
            pw.write(str);//将要写入文件的内容，可以多次write
            pw.close();//关闭流
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
        return getimage(path,200f,120f);
    }

    /**
     * 图片按比例大小压缩方法
     *
     * @param srcPath （根据路径获取图片并压缩）
     * @return
     */
    public static Bitmap getimage(String srcPath,float mH,float mW) {

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = mH;// 这里设置高度为800f
        float ww = mW;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;

        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
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

    //删除整个文件夹方法
    public static boolean deleteSDFile(File file) {

        //file目标文件夹绝对路径
        if (file.exists()) { //指定文件是否存在
            if (file.isFile()) { //该路径名表示的文件是否是一个标准文件
                file.delete(); //删除该文件
            } else if (file.isDirectory()) { //该路径名表示的文件是否是一个目录（文件夹）
                File[] files = file.listFiles(); //列出当前文件夹下的所有文件
                for (File f : files) {
                    deleteSDFile(f); //递归删除
                }
            }
            file.delete(); //删除文件夹（song,art,lyric）
        }
        return true;
    }
}
