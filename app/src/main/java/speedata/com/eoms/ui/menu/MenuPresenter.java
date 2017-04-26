package speedata.com.eoms.ui.menu;

import android.support.annotation.NonNull;

import speedata.com.eoms.application.MyApplication;
import speedata.com.eoms.bean.Device;
import speedata.com.eoms.bean.DeviceType;
import speedata.com.eoms.bean.FaultType;
import speedata.com.eoms.bean.Package;
import speedata.com.eoms.bean.User;
import speedata.com.eoms.mvp.BasePresenterImpl;
import speedata.com.eoms.utils.FileUtil;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MenuPresenter extends BasePresenterImpl<MenuContract.View> implements
        MenuContract.Presenter {


    //导入基础信息
    @Override
    public void importInfo() {
        try {
            //导入设备文件
            importDevice();
            //导入设备分类
            importDeviceType();
            //导入故障类型
            importFaultType();
            mView.changeImportUi("导入成功！");
        } catch (Exception e) {
            e.printStackTrace();
            mView.changeImportUi("导入失败！");
        }

    }

    @NonNull
    private void importDevice() {
        String read = FileUtil.read("/data/HTYL/In/device.txt");
        String[] split = read.split("\n");
        for (int i = 0; i < split.length; i++) {
            String[] infoSplit = split[i].split(",");
            Device device = new Device();
            device.setId(infoSplit[0]);
            device.setName(infoSplit[1]);
            device.setBarcode(infoSplit[2]);
            device.setClassify(infoSplit[3]);
            device.setPosition(infoSplit[4]);
            device.setBuyDate(infoSplit[5]);
            device.setUseDate(infoSplit[6]);
            device.setKeepYears(infoSplit[7]);
            device.setMaintenanceFactory(infoSplit[8]);
            device.setMaintenanceFactoryId(infoSplit[9]);
            device.setType(infoSplit[10]);
            device.setModel(infoSplit[11]);
            device.setManufacturer(infoSplit[12]);
            device.setSupplier(infoSplit[13]);
            device.setManagementUnit(infoSplit[14]);
            device.setResponsibilityOffice(infoSplit[15]);
            device.setUseStation(infoSplit[16]);
            device.setEquipDepartment(infoSplit[17]);
            device.setNumber(infoSplit[18]);
            device.setState(infoSplit[19]);
            device.setFlagBit(infoSplit[20]);
            device.setCheckPeriod(infoSplit[21]);
            device.setRepairLevel(infoSplit[22]);
            device.setUsedTime(infoSplit[23]);
            device.setRemark(infoSplit[24]);
            device.setReservedOne(infoSplit[25]);
            device.setReservedTwo(infoSplit[26]);
            device.setReservedThree(infoSplit[27]);
            device.setCreationTime(infoSplit[28]);
            MyApplication.getDaoInstant().getDeviceDao().insertOrReplace(device);
        }
    }

    private void importDeviceType() {
        String read = FileUtil.read("/data/HTYL/In/deviceType.txt");
        String[] split = read.split("\n");
        for (int i = 0; i < split.length; i++) {
            String[] infoSplit = split[i].split(",");
            DeviceType deviceType = new DeviceType();
            deviceType.setId(infoSplit[0]);
            deviceType.setName(infoSplit[1]);
            deviceType.setCode(infoSplit[2]);
            deviceType.setRemark(infoSplit[3]);
            MyApplication.getDaoInstant().getDeviceTypeDao().insertOrReplace(deviceType);
        }
    }


    private void importFaultType() {
        String read = FileUtil.read("/data/HTYL/In/faultType.txt");
        String[] split = read.split("\n");
        for (int i = 0; i < split.length; i++) {
            String[] infoSplit = split[i].split(",");
            FaultType faultType = new FaultType();
            faultType.setId(infoSplit[0]);
            faultType.setName(infoSplit[1]);
            faultType.setCode(infoSplit[2]);
            faultType.setRemark(infoSplit[3]);
            MyApplication.getDaoInstant().getFaultTypeDao().insertOrReplace(faultType);
        }
    }


    //有问题-主键问题
    private void importUser() {
        String read = FileUtil.read("/data/HTYL/In/user.txt");
        String[] split = read.split("\n");
        for (int i = 0; i < split.length; i++) {
            String[] infoSplit = split[i].split(",");
            User user = new User();
            user.setUser_name(infoSplit[0]);
            user.setReal_name(infoSplit[1]);
            MyApplication.getDaoInstant().getUserDao().insertOrReplace(user);
        }
    }

    private void importPackage() {
        String read = FileUtil.read("/data/HTYL/In/package.txt");
        String[] split = read.split("\n");
        for (int i = 0; i < split.length; i++) {
            String[] infoSplit = split[i].split(",");
            Package mPackage=new Package();
            mPackage.setId(infoSplit[0]);
            mPackage.setName(infoSplit[1]);
            mPackage.setBarcode(infoSplit[2]);
            mPackage.setEquipment(infoSplit[3]);
            MyApplication.getDaoInstant().getPackageDao().insertOrReplace(mPackage);
        }
    }

}
