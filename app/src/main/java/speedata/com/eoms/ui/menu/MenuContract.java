package speedata.com.eoms.ui.menu;

import speedata.com.eoms.mvp.BasePresenter;
import speedata.com.eoms.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MenuContract {
    interface View extends BaseView {
        void changeImportUi(String read);
    }

    interface  Presenter extends BasePresenter<View> {
        void importInfo();
    }
}
