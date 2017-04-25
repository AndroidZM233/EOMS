package speedata.com.eoms.ui.showphoto;

import java.util.ArrayList;

import speedata.com.eoms.mvp.BasePresenter;
import speedata.com.eoms.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ShowPhotoContract {
    interface View extends BaseView {
        void finishAct();
        void notifyDataSetChanged(ArrayList<android.view.View> listViews);
    }

    interface  Presenter extends BasePresenter<View> {
        void delPhoto(String orderNumber,ArrayList<android.view.View> listViews,int count);
    }
}
