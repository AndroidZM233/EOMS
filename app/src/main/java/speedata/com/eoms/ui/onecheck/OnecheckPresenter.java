package speedata.com.eoms.ui.onecheck;

import android.graphics.Bitmap;
import android.text.TextUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.io.IOException;

import speedata.com.eoms.application.AppConfig;
import speedata.com.eoms.application.MyApplication;
import speedata.com.eoms.bean.Device;
import speedata.com.eoms.bean.DeviceDao;
import speedata.com.eoms.model.ImageItem;
import speedata.com.eoms.mvp.BasePresenterImpl;
import speedata.com.eoms.utils.BimpUtil;
import speedata.com.eoms.utils.FileUtil;
import speedata.com.eoms.utils.GetTimeUtils;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class OnecheckPresenter extends BasePresenterImpl<OnecheckContract.View> implements OnecheckContract.Presenter {

    @Override
    public void getOrderNumPhoto(String orderNumber) {
        File[] files = FileUtil.getFiles(orderNumber);
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                Bitmap bm;
                try {
                    bm = BimpUtil.revitionImageSize(files[i].getAbsolutePath());
                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setBitmap(bm);
                    takePhoto.setImagePath(files[i].getAbsolutePath());
                    MyApplication.getInstance().selectBitmap.add(takePhoto);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void initData(String deviceName) {
        QueryBuilder<Device> where = MyApplication.getDaoInstant().getDeviceDao().queryBuilder().where(
                DeviceDao.Properties.Name.eq(deviceName));
        Device device = where.unique();
        mView.backDevice(device);
    }

    @Override
    public boolean saveInspectionTXT(String orderNumber, int state, String repairContent) {
        try {
            String oldPath = AppConfig.getTempDataPath() + "/" + orderNumber;
            String newPath = AppConfig.getDataPath() + "/Out";
            String fileListStr = BimpUtil.getFileListStr(oldPath);
            String result = GetTimeUtils.getTimeStyle2() + ","
                    + orderNumber + ","
                    + state + ","
                    + repairContent + ","
                    + MyApplication.realName + ","
                    + fileListStr
                    + "\n";
            BimpUtil.copyFolder(oldPath, newPath);
            String isFind = BimpUtil.findFileName(newPath, "_inspection.log");
            if (TextUtils.isEmpty(isFind)) {
                String fileName = GetTimeUtils.getTimeStyle1() + "_" + MyApplication.deviceId + "_inspection.log";
                BimpUtil.writeContent(newPath + "/" + fileName, result);
            } else {
                BimpUtil.writeContent(newPath + "/" + isFind, result);
            }

            //删除缓存图片
            File file = new File(oldPath);
            boolean del = BimpUtil.deleteSDFile(file);
            if (del) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
