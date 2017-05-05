package speedata.com.eoms.ui.repair;

import com.unnamed.b.atv.model.TreeNode;

import speedata.com.eoms.mvp.BasePresenter;
import speedata.com.eoms.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RepairContract {
    interface View extends BaseView {
        void returnTreeNode(TreeNode root);

        void showFailedInfo(String msg);
    }

    interface  Presenter extends BasePresenter<View> {
        //保存报修记录文件
        boolean saveRepairTXT(String orderNumber,String repairContent);
        //获得单号下图片
        void getOrderNumPhoto(String orderNumber);
        //增加树形控件数据
        void getTreeData();
    }
}
