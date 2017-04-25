package speedata.com.eoms.ui.login;

import speedata.com.eoms.mvp.BasePresenter;
import speedata.com.eoms.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LoginContract {
    interface View extends BaseView {
        void loginSuccess();
        void loginUserFailed();
        void loginPwdFailed();
    }

    interface  Presenter extends BasePresenter<View> {
        void login(String user,String pwd);
        void exit();
    }
}
