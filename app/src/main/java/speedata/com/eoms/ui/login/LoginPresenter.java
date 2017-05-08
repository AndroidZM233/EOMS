package speedata.com.eoms.ui.login;

import java.util.List;

import speedata.com.eoms.application.MyApplication;
import speedata.com.eoms.bean.User;
import speedata.com.eoms.bean.UserDao;
import speedata.com.eoms.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LoginPresenter extends BasePresenterImpl<LoginContract.View>
        implements LoginContract.Presenter{

    //登录
    @Override
    public void login(String user, String pwd) {
        List<User> list = MyApplication.getDaoInstant().getUserDao()
                .queryBuilder().where(UserDao.Properties.User_name.eq(user)).list();
        if (list.size()!=0){
            if (pwd.equals(list.get(0).getPwd())){
                MyApplication.setRealName(list.get(0).getReal_name());
                MyApplication.setUserName(user);
                mView.loginSuccess();
            }else {
                mView.loginPwdFailed();
            }
        }else {
            mView.loginUserFailed();
        }
    }

    //退出
    @Override
    public void exit() {
        //杀掉，这个应用程序的进程，释放 内存
        int id = android.os.Process.myPid();
        if (id != 0) {
            android.os.Process.killProcess(id);
        }
    }



}
