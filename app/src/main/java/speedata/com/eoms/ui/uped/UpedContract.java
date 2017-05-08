package speedata.com.eoms.ui.uped;

import java.util.List;

import speedata.com.eoms.bean.ToUpRVBean;
import speedata.com.eoms.mvp.BasePresenter;
import speedata.com.eoms.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class UpedContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        //查找记录文件
        List<ToUpRVBean> findLogFile();
    }
}
