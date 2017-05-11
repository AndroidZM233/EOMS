package speedata.com.eoms.ui.groupcheck;

import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import speedata.com.eoms.application.AppConfig;
import speedata.com.eoms.application.MyApplication;
import speedata.com.eoms.bean.Device;
import speedata.com.eoms.bean.DeviceDao;
import speedata.com.eoms.bean.Package;
import speedata.com.eoms.bean.PackageDao;
import speedata.com.eoms.bean.RVBean;
import speedata.com.eoms.mvp.BasePresenterImpl;
import speedata.com.eoms.utils.BimpUtil;
import speedata.com.eoms.utils.GetTimeUtils;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class GroupCheckPresenter extends BasePresenterImpl<GroupCheckContract.View> implements GroupCheckContract.Presenter{

    @Override
    public List<RVBean> initData(String name) {
        List<RVBean> rvBeanList=new ArrayList<>();
        Package unique = MyApplication.getDaoInstant().getPackageDao().queryBuilder()
                .where(PackageDao.Properties.Name.eq(name)).unique();
        String equipment = unique.getEquipment();
        String[] split = equipment.split("/");
        for (int i = 0; i < split.length; i++) {
            Device device = MyApplication.getDaoInstant().getDeviceDao().queryBuilder()
                    .where(DeviceDao.Properties.Id.eq(split[i])).unique();
            RVBean rvBean=new RVBean();
            rvBean.setName(device.getName());
            rvBean.setType(device.getType());
            rvBean.setPosition(device.getPosition());
            rvBean.setResponsible(device.getResponsibilityOffice());
            rvBean.setRepair(device.getMaintenanceFactory());
            rvBean.setOrderNumber(device.getId());
            rvBeanList.add(rvBean);
        }
        return rvBeanList;
    }

    @Override
    public boolean saveInspectionTXT(String orderNumber, int state, String repairContent) {
        try {
            String oldPath = AppConfig.getTempDataPath() + "/" + orderNumber;
            String newPath = AppConfig.getDataPath() + "/Out";
            String fileListStr = BimpUtil.getFileListStr(oldPath);
            String result = GetTimeUtils.getTimeStyle2() + ","
                    + orderNumber + ","
                    + state + ","
                    + repairContent + ","
                    + MyApplication.realName + ","
                    + fileListStr
                    + "\n";

            String isFind = BimpUtil.findFileName(newPath, "_inspection.log");
            if (TextUtils.isEmpty(isFind)) {
                String fileName = GetTimeUtils.getTimeStyle1() + "_" + MyApplication.deviceId + "_inspection.log";
                BimpUtil.writeContent(newPath + "/" + fileName, result);
            } else {
                BimpUtil.writeContent(newPath + "/" + isFind, result);
            }

            BimpUtil.copyFolder(oldPath, newPath);
            //删除缓存图片
            File file = new File(oldPath);
            BimpUtil.deleteSDFile(file);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
