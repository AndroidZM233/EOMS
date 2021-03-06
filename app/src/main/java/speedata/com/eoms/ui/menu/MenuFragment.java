package speedata.com.eoms.ui.menu;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import speedata.com.eoms.R;
import speedata.com.eoms.application.MyApplication;
import speedata.com.eoms.mvp.MVPBaseFragment;
import speedata.com.eoms.ui.aboutus.AboutUsActivity;
import speedata.com.eoms.ui.changepwd.ChangePwdActivity;
import speedata.com.eoms.ui.main.MainActivity;
import speedata.com.eoms.ui.record.RecordActivity;
import speedata.com.eoms.utils.SharedXmlUtil;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MenuFragment extends MVPBaseFragment<MenuContract.View, MenuPresenter>
        implements MenuContract.View, View.OnClickListener {
    private TextView tv_realName;
    private TextView tv_menu_name;
    private TextView tv_menu_clean;
    private TextView tv_menu_record;
    private TextView tv_menu_import;
    private TextView tv_menu_auto;
    private TextView tv_menu_pwd;
    private TextView tv_menu_flashlight;
    private TextView tv_menu_aboutus;
    private TextView tv_menu_version;
    Camera camera = null;// = Camera.open();
    Camera.Parameters parameter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = this.getView();
        initView(view);
    }

    private void initCamera() {
        mPresenter.waitCamera(getActivity());
        if (camera == null) {
            try {
                camera = Camera.open();
                parameter = camera.getParameters();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return;
        }
    }

    private void initView(View view) {
        tv_realName = (TextView) view.findViewById(R.id.tv_realName);
        tv_menu_name = (TextView) view.findViewById(R.id.tv_menu_name);
        tv_menu_clean = (TextView) view.findViewById(R.id.tv_menu_clean);
        tv_menu_record = (TextView) view.findViewById(R.id.tv_menu_record);
        tv_menu_import = (TextView) view.findViewById(R.id.tv_menu_import);
        tv_menu_auto = (TextView) view.findViewById(R.id.tv_menu_auto);
        tv_menu_pwd = (TextView) view.findViewById(R.id.tv_menu_pwd);
        tv_menu_flashlight = (TextView) view.findViewById(R.id.tv_menu_flashlight);
        tv_menu_version = (TextView) view.findViewById(R.id.tv_menu_version);
        tv_menu_aboutus= (TextView) view.findViewById(R.id.tv_menu_aboutus);

        tv_menu_version.setText(getVersion());
        tv_menu_clean.setOnClickListener(this);
        tv_menu_record.setOnClickListener(this);
        tv_menu_import.setOnClickListener(this);
        tv_menu_auto.setOnClickListener(this);
        tv_menu_pwd.setOnClickListener(this);
        tv_menu_flashlight.setOnClickListener(this);
        tv_menu_version.setOnClickListener(this);
        tv_menu_aboutus.setOnClickListener(this);

        tv_realName.setText(MyApplication.realName);
        try {
            String phone = SharedXmlUtil.getInstance(getActivity()).read("phone", "");
            tv_menu_name.setText(phone);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int time = SharedXmlUtil.getInstance(getActivity()).read("cacheTime", 10);
        tv_menu_clean.setText("本地缓存天数" + time + "天");
    }


    @Override
    public int getLayout() {
        return R.layout.fragment_menu;
    }

    /**
     * 获取当前应用程序的版本号
     */
    public String getVersion() {
        PackageManager pm = ((MainActivity) getActivity()).getPackageManager();
        try {
            PackageInfo packinfo = pm.getPackageInfo(((MainActivity) getActivity()).getPackageName(), 0);
            String version = packinfo.versionName;
            return "版本号 V" + version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "版本号错误";
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_menu_clean:
                mPresenter.showCacheDialog(getActivity());
                break;
            case R.id.tv_menu_record:
                Intent intent = new Intent((MainActivity) getActivity(), RecordActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_menu_import:
                showLoading(getActivity(), "导入中...");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.importInfo();
                        hideLoading();
                    }
                }).start();

                break;
            case R.id.tv_menu_auto:
                //去除这个功能
//                mPresenter.showDialog(getActivity());
//                hideInputMethod();
                break;
            case R.id.tv_menu_pwd:
                Intent intent1 = new Intent(getActivity(), ChangePwdActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_menu_flashlight:
                initCamera();
                String string = tv_menu_flashlight.getText().toString();
                if (string.equals("打开手电筒")) {
                    tv_menu_flashlight.setText("关闭手电筒");
                    openLight();
                } else {
                    tv_menu_flashlight.setText("打开手电筒");
                    closeLight();
                }
                break;
            case R.id.tv_menu_version:
                break;
            case R.id.tv_menu_aboutus:
                Intent intent2=new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    public void changeImportUi(final String read) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText((MainActivity) getActivity(), read, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void changeText(String s) {
        tv_menu_clean.setText("本地缓存天数" + s + "天");
        Toast.makeText((MainActivity) getActivity(), "设置成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failed(String s) {
        Toast.makeText((MainActivity) getActivity(), s, Toast.LENGTH_SHORT).show();
    }


    public void openLight() {
        // 打开闪光灯关键代码
        camera.startPreview();
        parameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameter);
        camera.startPreview();
    }

    public void closeLight() {// 关闭闪关灯关键代码
        parameter = camera.getParameters();
        parameter.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameter);// 关闭闪关灯关键代码
        parameter = camera.getParameters();
        parameter.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameter);
    }


    @Override
    public void onPause() {
        super.onPause();
//        if (judgeSe4500()) {
//            Intent intent = new Intent();
//            intent.setAction("com.se4500.closecamera");
//            getActivity().sendBroadcast(intent);
//        }
//        getActivity().sendBroadcast(new Intent().setAction("com.se4500.closecamera"));
//        if (camera != null) {
//            camera.stopPreview();
//            camera.release();
//            camera = null;
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().sendBroadcast(new Intent().setAction("com.se4500.closecamera"));
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }
}
