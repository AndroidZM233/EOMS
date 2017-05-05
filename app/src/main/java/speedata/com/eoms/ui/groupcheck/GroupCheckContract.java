package speedata.com.eoms.ui.groupcheck;

import java.util.List;

import speedata.com.eoms.bean.RVBean;
import speedata.com.eoms.mvp.BasePresenter;
import speedata.com.eoms.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class GroupCheckContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        //更新界面数据
        List<RVBean> initData(String name);

        boolean saveInspectionTXT(String orderNumber, int state, String repairContent);
    }
}
