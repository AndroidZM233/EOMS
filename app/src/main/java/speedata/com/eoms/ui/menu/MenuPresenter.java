package speedata.com.eoms.ui.menu;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemProperties;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import speedata.com.eoms.application.MyApplication;
import speedata.com.eoms.bean.Device;
import speedata.com.eoms.bean.DeviceType;
import speedata.com.eoms.bean.FaultType;
import speedata.com.eoms.bean.Package;
import speedata.com.eoms.bean.User;
import speedata.com.eoms.mvp.BasePresenterImpl;
import speedata.com.eoms.service.AutoImportService;
import speedata.com.eoms.utils.BimpUtil;
import speedata.com.eoms.utils.FileUtil;
import speedata.com.eoms.utils.SharedXmlUtil;

import static android.content.ContentValues.TAG;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MenuPresenter extends BasePresenterImpl<MenuContract.View> implements
        MenuContract.Presenter {


    //导入基础信息
    @Override
    public void importInfo() {
        final String isExists = fileIsExists();
        if (TextUtils.isEmpty(isExists)){
            try {
                MyApplication.getDaoInstant().getDeviceDao().deleteAll();
                //导入设备文件
                importDevice();
            } catch (Exception e) {
                e.printStackTrace();
                mView.changeImportUi("导入设备文件失败！");
            }
            try {
                MyApplication.getDaoInstant().getDeviceTypeDao().deleteAll();
                //导入设备分类
                importDeviceType();
            } catch (Exception e) {
                e.printStackTrace();
                mView.changeImportUi("导入设备分类文件失败！");
            }
            try {
                MyApplication.getDaoInstant().getFaultTypeDao().deleteAll();
                //导入故障类型
                importFaultType();
            } catch (Exception e) {
                e.printStackTrace();
                mView.changeImportUi("导入故障类型文件失败！");
            }
            try {
                MyApplication.getDaoInstant().getPackageDao().deleteAll();
                //导入组巡文件
                importPackage();
            } catch (Exception e) {
                e.printStackTrace();
                mView.changeImportUi("导入组巡文件失败！");
            }
            try {
                MyApplication.getDaoInstant().getUserDao().deleteAll();
                //导入用户信息
                importUser();
            } catch (Exception e) {
                e.printStackTrace();
                mView.changeImportUi("导入用户信息失败！");
            }


            mView.changeImportUi("导入成功！");
            File file = new File("/storage/emulated/0/data/HTYL/In");
            BimpUtil.deleteFiles(file);
        }else {
            mView.changeImportUi(isExists);
        }

    }
    //判断文件是否存在
    private String fileIsExists(){
        StringBuffer stringBuffer=new StringBuffer();
        if (!BimpUtil.fileIsExists("/storage/emulated/0/data/HTYL/In/device.txt")){
            stringBuffer.append("device.txt文件不存在\n");
        }
        if (!BimpUtil.fileIsExists("/storage/emulated/0/data/HTYL/In/deviceType.txt")){
            stringBuffer.append("deviceType.txt文件不存在\n");
        }
        if (!BimpUtil.fileIsExists("/storage/emulated/0/data/HTYL/In/faultType.txt")){
            stringBuffer.append("faultType.txt文件不存在\n");
        }
        if (!BimpUtil.fileIsExists("/storage/emulated/0/data/HTYL/In/user.txt")){
            stringBuffer.append("user.txt文件不存在\n");
        }
        if (!BimpUtil.fileIsExists("/storage/emulated/0/data/HTYL/In/package.txt")){
            stringBuffer.append("package.txt文件不存在");
        }
        return String.valueOf(stringBuffer);
    }


    //设置上传间隔-功能去除
    @Override
    public void showDialog(final Context context) {
        boolean serviceWork = isServiceWork(context, "speedata.com.eoms.service.AutoImportService");
        if (serviceWork){
            Intent intent=new Intent(context, AutoImportService.class);
            context.stopService(intent);
            context.startService(intent);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("设置上传间隔（min）");
        final EditText editText = new EditText(context);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setHint("不能低于10min");
        builder.setView(editText);
        builder.setCancelable(false);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                InputMethodManager imm = (InputMethodManager) context
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                int etTime = Integer.parseInt(editText.getText().toString());
                if (etTime < 10) {
                    Toast.makeText(context, "不能低于10min", Toast.LENGTH_SHORT).show();
                    imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
                    preventDismissDialog(dialog);
                }else {
                    SharedXmlUtil.getInstance(context).write("scheduleTime", etTime);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
                    dismissDialog(dialog);
                }

            }
            /**
             * 通过反射 阻止关闭对话框
             */
            private void preventDismissDialog(DialogInterface dialog) {
                try {
                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                    field.setAccessible(true);
                    //设置mShowing值，欺骗android系统
                    field.set(dialog, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            /**
             * 关闭对话框
             */
            private void dismissDialog(DialogInterface dialog) {
                try {
                    Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                    field.setAccessible(true);
                    field.set(dialog, true);
                } catch (Exception e) {
                }
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //设置缓存天数
    @Override
    public void showCacheDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("设置缓存天数");
        final EditText editText = new EditText(context);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(editText);
        builder.setCancelable(false);
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                InputMethodManager imm = (InputMethodManager) context
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                String string = editText.getText().toString();
                if (TextUtils.isEmpty(string)){
                    mView.failed("天数不能为空");
                    return;
                }
                int etTime = Integer.parseInt(string);
                SharedXmlUtil.getInstance(context).write("cacheTime",etTime);
                long value = System.currentTimeMillis();
                SharedXmlUtil.getInstance(context).write("cacheDate", value);
                imm.hideSoftInputFromWindow(editText.getWindowToken(),0);
                mView.changeText(String.valueOf(etTime));
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    /**
     * 发送系统广播，并等待相机释放。sending system broadcast，and waiting for camera release
     */
    @Override
    public void waitCamera(Context context) {
        context.sendBroadcast(new Intent().setAction("com.se4500.opencamera"));
        if (SystemProperties.get("persist.sys.keyreport").equals("true")) {
            if (SystemProperties.get("persist.sys.se4500").equals("true")) {
                int waitCount;
                for (waitCount = 0; waitCount < 20; waitCount++) {
                    try {
                        Thread.sleep(200);
                    } catch (Exception e) {
                        Log.d(TAG, "waitCamera: ");
                    }
                    if (SystemProperties.get("persist.sys.iscamera").equals("open")) {
                        break;
                    }
                }
            }
        }
    }

    //判断服务是否启动
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    @NonNull
    private void importDevice() {
        String read = FileUtil.read("/storage/emulated/0/data/HTYL/In/device.txt");
        String[] split = read.split("\\*\\#\n");
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
            try {
                device.setCreationTime(infoSplit[28]);
            } catch (Exception e) {
                e.printStackTrace();
                device.setCreationTime("");
            }
            MyApplication.getDaoInstant().getDeviceDao().insertOrReplace(device);
        }
    }

    private void importDeviceType() {
        String read = FileUtil.read("/storage/emulated/0/data/HTYL/In/deviceType.txt");
        String[] split = read.split("\\*\\#\n");
        for (int i = 0; i < split.length; i++) {
            String[] infoSplit = split[i].split(",");
            DeviceType deviceType = new DeviceType();
            deviceType.setId(infoSplit[0]);
            deviceType.setName(infoSplit[1]);
            deviceType.setCode(infoSplit[2]);
            try {
                deviceType.setRemark(infoSplit[3]);
            } catch (Exception e) {
                e.printStackTrace();
                deviceType.setRemark("");
            }

            MyApplication.getDaoInstant().getDeviceTypeDao().insertOrReplace(deviceType);
        }
    }


    private void importFaultType() {
        String read = FileUtil.read("/storage/emulated/0/data/HTYL/In/faultType.txt");
        String[] split = read.split("\\*\\#\n");
        for (int i = 0; i < split.length; i++) {
            String[] infoSplit = split[i].split(",");
            FaultType faultType = new FaultType();
            faultType.setId(infoSplit[0]);
            faultType.setName(infoSplit[1]);
            faultType.setCode(infoSplit[2]);
            try {
                faultType.setRemark(infoSplit[3]);
            } catch (Exception e) {
                e.printStackTrace();
                faultType.setRemark("");
            }
            MyApplication.getDaoInstant().getFaultTypeDao().insertOrReplace(faultType);
        }
    }


    //有问题-主键问题
    private void importUser() {
        String read = FileUtil.read("/storage/emulated/0/data/HTYL/In/user.txt");
        String[] split = read.split("\\*\\#\n");
        for (int i = 0; i < split.length; i++) {
            String[] infoSplit = split[i].split(",");
            User user = new User();
            user.setUser_name(infoSplit[0]);
            try {
                user.setReal_name(infoSplit[1]);
            } catch (Exception e) {
                e.printStackTrace();
                user.setReal_name("");
            }
            user.setPwd("0000");
            MyApplication.getDaoInstant().getUserDao().insertOrReplace(user);
        }
    }

    private void importPackage() {
        String read = FileUtil.read("/storage/emulated/0/data/HTYL/In/package.txt");
        String[] split = read.split("\\*\\#\n");
        for (int i = 0; i < split.length; i++) {
            String[] infoSplit = split[i].split(",");
            Package mPackage = new Package();
            mPackage.setId(infoSplit[0]);
            mPackage.setName(infoSplit[1]);
            mPackage.setBarcode(infoSplit[2]);
            try {
                mPackage.setEquipment(infoSplit[3]);
            } catch (Exception e) {
                e.printStackTrace();
                mPackage.setEquipment("");
            }
            MyApplication.getDaoInstant().getPackageDao().insertOrReplace(mPackage);
        }
    }

}
