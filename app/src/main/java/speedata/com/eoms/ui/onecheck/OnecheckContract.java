package speedata.com.eoms.ui.onecheck;

import speedata.com.eoms.mvp.BasePresenter;
import speedata.com.eoms.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class OnecheckContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        //获取当前单号下的照片
        void getOrderNumPhoto(String orderNumber);
    }
}
