package speedata.com.eoms.ui.showphoto;

import android.widget.ImageView;

import java.util.ArrayList;

import speedata.com.eoms.application.MyApplication;
import speedata.com.eoms.mvp.BasePresenterImpl;
import speedata.com.eoms.utils.BimpUtil;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ShowPhotoPresenter extends BasePresenterImpl<ShowPhotoContract.View> implements ShowPhotoContract.Presenter{


    @Override
    public void delPhoto(String orderNumber,ArrayList<android.view.View> listViews,int count) {
        if (listViews.size() == 1) {
            if (!BimpUtil.delFile(((ImageView) listViews.get(count)).getTag().toString(), orderNumber)) {
                MyApplication.getInstance().selectBitmap.clear();
                BimpUtil.max = 0;
                mView.finishAct();
            }
        } else {
            if (!BimpUtil.delFile(((ImageView) listViews.get(count)).getTag().toString(), null)) {
                MyApplication.getInstance().selectBitmap.remove(count);
                BimpUtil.max--;
                listViews.remove(count);
                mView.notifyDataSetChanged(listViews);
            }
        }
    }
}
