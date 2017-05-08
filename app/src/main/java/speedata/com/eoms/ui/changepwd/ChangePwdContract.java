package speedata.com.eoms.ui.changepwd;

import speedata.com.eoms.mvp.BasePresenter;
import speedata.com.eoms.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ChangePwdContract {
    interface View extends BaseView {
        void changeFailed(String string);
        void changeSuccess(String string);
    }

    interface  Presenter extends BasePresenter<View> {
        void changePwd(String oldPwd,String newPwd);
    }
}
