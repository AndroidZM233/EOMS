package speedata.com.eoms.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import speedata.com.eoms.application.MyApplication;
import speedata.com.eoms.bean.Device;
import speedata.com.eoms.bean.DeviceType;
import speedata.com.eoms.bean.FaultType;
import speedata.com.eoms.bean.Package;
import speedata.com.eoms.bean.User;
import speedata.com.eoms.utils.FileUtil;

/**
 * Created by 张明_ on 2017/5/5.
 */

public class AutoImportService extends Service {
    private Timer timer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startTimer(3000);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    private void stopTimer() {
        timer.cancel();
        timer = null;
    }

    private void startTimer(int time) {
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new ImportTimerTask(), 4000, time);

    }

    private class ImportTimerTask extends TimerTask {

        @Override
        public void run() {
            importInfo();
        }
    }

    public void importInfo() {
        final StringBuffer stringBuffer=new StringBuffer();
        try {
            MyApplication.getDaoInstant().getDeviceDao().deleteAll();
            //导入设备文件
            importDevice();
        } catch (Exception e) {
            e.printStackTrace();
            stringBuffer.append("导入设备文件失败\n");
        }
        try {
            MyApplication.getDaoInstant().getDeviceTypeDao().deleteAll();
            //导入设备分类
            importDeviceType();
        } catch (Exception e) {
            e.printStackTrace();
            stringBuffer.append("导入设备分类失败\n");
        }
        try {
            MyApplication.getDaoInstant().getFaultTypeDao().deleteAll();
            //导入故障类型
            importFaultType();
        } catch (Exception e) {
            e.printStackTrace();
            stringBuffer.append("导入故障类型失败\n");
        }
        try {
            MyApplication.getDaoInstant().getPackageDao().deleteAll();
            //导入组巡文件
            importPackage();
        } catch (Exception e) {
            e.printStackTrace();
            stringBuffer.append("导入组巡文件失败\n");
        }
        try {
            MyApplication.getDaoInstant().getUserDao().deleteAll();
            //导入用户信息
            importUser();
        } catch (Exception e) {
            e.printStackTrace();
            stringBuffer.append("导入用户信息失败\n");
        }
        final String str= String.valueOf(stringBuffer);
        Handler handler=new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AutoImportService.this,
                        str+"导入成功！", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void importDevice() {
        String read = FileUtil.read("/storage/emulated/0/data/HTYL/In/device.txt");
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
        String read = FileUtil.read("/storage/emulated/0/data/HTYL/In/deviceType.txt");
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
        String read = FileUtil.read("/storage/emulated/0/data/HTYL/In/faultType.txt");
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
        String read = FileUtil.read("/storage/emulated/0/data/HTYL/In/user.txt");
        String[] split = read.split("\n");
        for (int i = 0; i < split.length; i++) {
            String[] infoSplit = split[i].split(",");
            User user = new User();
            user.setUser_name(infoSplit[0]);
            user.setReal_name(infoSplit[1]);
            user.setPwd("0000");
            MyApplication.getDaoInstant().getUserDao().insertOrReplace(user);
        }
    }

    private void importPackage() {
        String read = FileUtil.read("/storage/emulated/0/data/HTYL/In/package.txt");
        String[] split = read.split("\n");
        for (int i = 0; i < split.length; i++) {
            String[] infoSplit = split[i].split(",");
            Package mPackage = new Package();
            mPackage.setId(infoSplit[0]);
            mPackage.setName(infoSplit[1]);
            mPackage.setBarcode(infoSplit[2]);
            mPackage.setEquipment(infoSplit[3]);
            MyApplication.getDaoInstant().getPackageDao().insertOrReplace(mPackage);
        }
    }
}
