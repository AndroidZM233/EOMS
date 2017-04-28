package speedata.com.eoms.ui.repair;

import speedata.com.eoms.mvp.BasePresenter;
import speedata.com.eoms.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RepairContract {
    interface View extends BaseView {
    }

    interface  Presenter extends BasePresenter<View> {
        //保存报修记录文件
        boolean saveRepairTXT(String orderNumber,String repairContent);

        void getOrderNumPhoto(String orderNumber);
    }
}
