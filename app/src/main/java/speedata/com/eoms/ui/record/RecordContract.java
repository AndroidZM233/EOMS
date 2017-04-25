package speedata.com.eoms.ui.record;

import speedata.com.eoms.mvp.BasePresenter;
import speedata.com.eoms.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RecordContract {
    interface View extends BaseView {
        void changeToUpImage();
        void changeUpedImage();
    }

    interface  Presenter extends BasePresenter<View> {
        void openToUpFragment();
        void openUpedFragment();
    }
}
