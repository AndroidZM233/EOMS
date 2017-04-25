package speedata.com.eoms.ui.record;

import speedata.com.eoms.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RecordPresenter extends BasePresenterImpl<RecordContract.View> implements RecordContract.Presenter{

    @Override
    public void openToUpFragment() {
        mView.changeToUpImage();
    }

    @Override
    public void openUpedFragment() {
        mView.changeUpedImage();
    }
}
