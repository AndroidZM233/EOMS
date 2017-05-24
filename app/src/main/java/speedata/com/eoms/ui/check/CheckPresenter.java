package speedata.com.eoms.ui.check;

import com.unnamed.b.atv.model.TreeNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import speedata.com.eoms.R;
import speedata.com.eoms.adapter.IconTreeItemHolder;
import speedata.com.eoms.application.MyApplication;
import speedata.com.eoms.bean.Device;
import speedata.com.eoms.bean.DeviceDao;
import speedata.com.eoms.bean.DeviceType;
import speedata.com.eoms.bean.DeviceTypeDao;
import speedata.com.eoms.bean.Package;
import speedata.com.eoms.bean.PackageDao;
import speedata.com.eoms.mvp.BasePresenterImpl;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CheckPresenter extends BasePresenterImpl<CheckContract.View> implements CheckContract.Presenter {

    @Override
    public void getTreeData() {
        //增加树形数据
        TreeNode root = TreeNode.root();
        TreeNode parent = null;
        List<TreeNode> parentList = new ArrayList<>();
        List<TreeNode> groupTreeList = new ArrayList<>();
        List<Device> mList = MyApplication.getDaoInstant().getDeviceDao().queryBuilder().list();
        List<String> classifyList = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            classifyList.add(mList.get(i).getClassify());
        }
        HashSet hashSet = new HashSet(classifyList);
        classifyList.clear();
        classifyList.addAll(hashSet);

        if (classifyList.size() == 0) {
            mView.showFailedInfo("请先导入基础信息");
            return;
        }
        TreeNode group = new TreeNode(new IconTreeItemHolder
                .IconTreeItem(R.string.ic_folder, "组巡"));
        TreeNode one = new TreeNode(new IconTreeItemHolder
                .IconTreeItem(R.string.ic_folder, "单巡"));

        List<Package> packageList = MyApplication.getDaoInstant().getPackageDao().queryBuilder()
                .distinct().orderDesc(PackageDao.Properties.Name).list();
        for (int i = 0; i < packageList.size(); i++) {
            String name = packageList.get(i).getName();
            TreeNode groupChild = new TreeNode(new IconTreeItemHolder
                    .IconTreeItem(R.string.ic_folder, name));
            groupTreeList.add(groupChild);
        }
        group.addChildren(groupTreeList);

        for (int i = 0; i < classifyList.size(); i++) {
            List<TreeNode> childTreeList = new ArrayList<>();
            String classify = classifyList.get(i);
            List<DeviceType> list = MyApplication.getDaoInstant().getDeviceTypeDao().queryBuilder()
                    .where(DeviceTypeDao.Properties.Id.eq(classify)).list();
            if (list.size()==0){
                continue;
            }
            String name = list.get(0).getName();
            parent = new TreeNode(new IconTreeItemHolder
                    .IconTreeItem(R.string.ic_folder, name));
            parentList.add(parent);
            List<Device> deviceList = MyApplication.getDaoInstant().getDeviceDao().queryBuilder()
                    .where(DeviceDao.Properties.Classify.eq(classifyList.get(i))).list();
            for (int j = 0; j < deviceList.size(); j++) {
                TreeNode child = new TreeNode(new IconTreeItemHolder
                        .IconTreeItem(R.string.ic_folder, deviceList.get(j).getName()));
                childTreeList.add(child);
            }
            parent.addChildren(childTreeList);
        }

        one.addChildren(parentList);
        root.addChildren(group, one);

        mView.returnTreeNode(root);
    }
}
