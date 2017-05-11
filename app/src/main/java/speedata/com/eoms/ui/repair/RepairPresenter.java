package speedata.com.eoms.ui.repair;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.unnamed.b.atv.model.TreeNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import speedata.com.eoms.R;
import speedata.com.eoms.adapter.IconTreeItemHolder;
import speedata.com.eoms.application.AppConfig;
import speedata.com.eoms.application.MyApplication;
import speedata.com.eoms.bean.Device;
import speedata.com.eoms.bean.DeviceDao;
import speedata.com.eoms.bean.DeviceType;
import speedata.com.eoms.bean.DeviceTypeDao;
import speedata.com.eoms.model.ImageItem;
import speedata.com.eoms.mvp.BasePresenterImpl;
import speedata.com.eoms.utils.BimpUtil;
import speedata.com.eoms.utils.FileUtil;
import speedata.com.eoms.utils.GetTimeUtils;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RepairPresenter extends BasePresenterImpl<RepairContract.View> implements RepairContract.Presenter {

    @Override
    public boolean saveRepairTXT(String orderNumber, String repairContent) {

        try {
            String oldPath = AppConfig.getTempDataPath() + "/" + orderNumber;
            String newPath = AppConfig.getDataPath() + "/Out";
            String fileListStr = BimpUtil.getFileListStr(oldPath);
            String result = GetTimeUtils.getTimeStyle2() + ","
                    + orderNumber + ","
                    + " " + ","
                    + repairContent + ","
                    + MyApplication.realName + ","
                    + fileListStr
                    + "\n";
            BimpUtil.copyFolder(oldPath, newPath);

            String isFind = BimpUtil.findFileName(newPath, "__repair.log");
            if (TextUtils.isEmpty(isFind)) {
                String fileName = GetTimeUtils.getTimeStyle1() + "_" + MyApplication.deviceId + "__repair.log";
                BimpUtil.writeContent(newPath + "/" + fileName, result);
            } else {
                BimpUtil.writeContent(newPath + "/" + isFind, result);
            }

            //删除缓存图片
            File file = new File(oldPath);
            BimpUtil.deleteSDFile(file);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void getOrderNumPhoto(String orderNumber) {
        File[] files = FileUtil.getFiles(orderNumber);
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                Bitmap bm;
                try {
                    bm = BimpUtil.revitionImageSize(files[i].getAbsolutePath());
                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setBitmap(bm);
                    takePhoto.setImagePath(files[i].getAbsolutePath());
                    MyApplication.getInstance().selectBitmap.add(takePhoto);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void getTreeData() {
        //增加树形数据
        TreeNode root = TreeNode.root();
        TreeNode parent = null;
        List<TreeNode> parentList = new ArrayList<>();
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
        for (int i = 0; i < classifyList.size(); i++) {
            List<TreeNode> childTreeList = new ArrayList<>();
            String classify = classifyList.get(i);
            DeviceType deviceType = MyApplication.getDaoInstant().getDeviceTypeDao().queryBuilder()
                    .where(DeviceTypeDao.Properties.Id.eq(classify)).unique();
            String name = deviceType.getName();
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

        root.addChildren(parentList);

        mView.returnTreeNode(root);
    }
}
