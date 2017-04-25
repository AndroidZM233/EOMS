package speedata.com.eoms.ui.onecheck;

import android.graphics.Bitmap;

import java.io.File;
import java.io.IOException;

import speedata.com.eoms.application.MyApplication;
import speedata.com.eoms.model.ImageItem;
import speedata.com.eoms.mvp.BasePresenterImpl;
import speedata.com.eoms.utils.BimpUtil;
import speedata.com.eoms.utils.FileUtil;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class OnecheckPresenter extends BasePresenterImpl<OnecheckContract.View> implements OnecheckContract.Presenter{

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
}
