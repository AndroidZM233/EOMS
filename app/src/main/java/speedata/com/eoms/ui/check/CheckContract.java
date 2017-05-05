package speedata.com.eoms.ui.check;

import com.unnamed.b.atv.model.TreeNode;

import speedata.com.eoms.mvp.BasePresenter;
import speedata.com.eoms.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CheckContract {
    interface View extends BaseView {
        void returnTreeNode(TreeNode root);

        void showFailedInfo(String msg);
    }

    interface  Presenter extends BasePresenter<View> {
        //增加树形控件数据
        void getTreeData();
    }
}
