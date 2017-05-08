package speedata.com.eoms.ui.changepwd;

import speedata.com.eoms.application.MyApplication;
import speedata.com.eoms.bean.User;
import speedata.com.eoms.bean.UserDao;
import speedata.com.eoms.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ChangePwdPresenter extends BasePresenterImpl<ChangePwdContract.View> implements ChangePwdContract.Presenter{

    @Override
    public void changePwd(String oldPwd, String newPwd) {
        User unique = MyApplication.getDaoInstant().getUserDao().queryBuilder()
                .where(UserDao.Properties.User_name.eq(MyApplication.userName)).unique();
        String pwd = unique.getPwd();
        if (pwd.equals(oldPwd)){
            unique.setPwd(newPwd);
            try {
                MyApplication.getDaoInstant().getUserDao().update(unique);
                mView.changeSuccess("密码修改成功");
            } catch (Exception e) {
                e.printStackTrace();
                mView.changeFailed("密码修改失败");
            }

        }else {
            mView.changeFailed("原密码错误");
        }

    }
}
