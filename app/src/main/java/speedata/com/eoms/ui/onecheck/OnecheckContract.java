package speedata.com.eoms.ui.onecheck;

import speedata.com.eoms.bean.Device;
import speedata.com.eoms.mvp.BasePresenter;
import speedata.com.eoms.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class OnecheckContract {
    interface View extends BaseView {
        //返回device对象
        void backDevice(Device device);
    }

    interface  Presenter extends BasePresenter<View> {
        //获取当前单号下的照片
        void getOrderNumPhoto(String orderNumber);
        //查数据库更新界面信息
        void initData(String deviceName);
        //保存巡检记录文件
        boolean saveInspectionTXT(String orderNumber,int state, String repairContent);
    }
}
