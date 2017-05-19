package speedata.com.eoms.ui.menu;

import android.content.Context;

import speedata.com.eoms.mvp.BasePresenter;
import speedata.com.eoms.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MenuContract {
    interface View extends BaseView {
        void changeImportUi(String read);
        void changeText(String s);
        void failed(String s);
    }

    interface  Presenter extends BasePresenter<View> {
        void importInfo();
        void showDialog(Context context);
        void showCacheDialog(Context context);
        void waitCamera(Context context);
    }
}
