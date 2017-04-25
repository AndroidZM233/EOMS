package speedata.com.eoms.ui.main;

import speedata.com.eoms.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MainPresenter extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter{

    @Override
    public void openCheckFragment() {
        mView.changeTvCheckImage();

    }

    @Override
    public void openRepairFragment() {
        mView.changeTvRepairImage();
    }

    @Override
    public void openMenuFragment() {
        mView.changeTvMenuImage();
    }

}
