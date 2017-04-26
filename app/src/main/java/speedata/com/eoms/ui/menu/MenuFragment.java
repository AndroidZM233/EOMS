package speedata.com.eoms.ui.menu;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import speedata.com.eoms.R;
import speedata.com.eoms.mvp.MVPBaseFragment;
import speedata.com.eoms.ui.main.MainActivity;
import speedata.com.eoms.ui.record.RecordActivity;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MenuFragment extends MVPBaseFragment<MenuContract.View, MenuPresenter>
        implements MenuContract.View,View.OnClickListener {
    private TextView tv_menu_name;
    private TextView tv_menu_clean;
    private TextView tv_menu_record;
    private TextView tv_menu_import;
    private TextView tv_menu_auto;
    private TextView tv_menu_version;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = this.getView();
        initView(view);
    }

    private void initView(View view) {
        tv_menu_name= (TextView) view.findViewById(R.id.tv_menu_name);
        tv_menu_clean= (TextView) view.findViewById(R.id.tv_menu_clean);
        tv_menu_record= (TextView) view.findViewById(R.id.tv_menu_record);
        tv_menu_import= (TextView) view.findViewById(R.id.tv_menu_import);
        tv_menu_auto= (TextView) view.findViewById(R.id.tv_menu_auto);
        tv_menu_version= (TextView) view.findViewById(R.id.tv_menu_version);

        tv_menu_version.setText(getVersion());
        tv_menu_clean.setOnClickListener(this);
        tv_menu_record.setOnClickListener(this);
        tv_menu_import.setOnClickListener(this);
        tv_menu_auto.setOnClickListener(this);
        tv_menu_version.setOnClickListener(this);
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
            return "版本号 V"+version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "版本号错误";
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_menu_clean:
                break;
            case R.id.tv_menu_record:
                Intent intent=new Intent((MainActivity) getActivity(), RecordActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_menu_import:
                mPresenter.importInfo();
                break;
            case R.id.tv_menu_auto:
                break;
            case R.id.tv_menu_version:
                break;
        }
    }

    @Override
    public void changeImportUi(String read) {
        Toast.makeText((MainActivity) getActivity(),read,Toast.LENGTH_SHORT).show();
    }
}
