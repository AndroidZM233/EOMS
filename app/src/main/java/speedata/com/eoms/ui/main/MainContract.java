package speedata.com.eoms.ui.main;

import speedata.com.eoms.mvp.BasePresenter;
import speedata.com.eoms.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MainContract {
    interface View extends BaseView {
        void changeTvCheckImage();
        void changeTvRepairImage();
        void changeTvMenuImage();
    }

    interface  Presenter extends BasePresenter<View> {
        void openCheckFragment();
        void openRepairFragment();
        void openMenuFragment();
    }
}
