package speedata.com.eoms.ui.check;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import speedata.com.eoms.R;
import speedata.com.eoms.adapter.IconTreeItemHolder;
import speedata.com.eoms.application.MyApplication;
import speedata.com.eoms.bean.Device;
import speedata.com.eoms.bean.DeviceDao;
import speedata.com.eoms.bean.Package;
import speedata.com.eoms.bean.PackageDao;
import speedata.com.eoms.mvp.MVPBaseFragment;
import speedata.com.eoms.ui.groupcheck.GroupCheckActivity;
import speedata.com.eoms.ui.main.MainActivity;
import speedata.com.eoms.ui.onecheck.OnecheckActivity;
import speedata.com.eoms.utils.ScanUtil;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CheckFragment extends MVPBaseFragment<CheckContract.View, CheckPresenter>
        implements CheckContract.View, View.OnClickListener, ScanUtil.OnScanListener {

    private Button btnTopLeft;
    private TextView tv_title;
    private TextView tv_select;
    private ScanUtil scanUtil;
    private AndroidTreeView androidTreeView;
    private AlertDialog dialog;

    @Override
    public int getLayout() {
        return R.layout.fragment_check;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = this.getView();
        initView(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        scanUtil = new ScanUtil(getActivity());
        scanUtil.setOnScanListener(this);

    }

    private void initView(View view) {
        btnTopLeft = (Button) view.findViewById(R.id.btn_top_left);
        btnTopLeft.setVisibility(View.INVISIBLE);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("巡检");
        tv_select = (TextView) view.findViewById(R.id.tv_select);

        tv_select.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_select:
                showLoading(getActivity(),"加载中...");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.getTreeData();
                    }
                }).start();
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        scanUtil.stopReceData();
    }

    @Override
    public void getBarcode(String data) {
        List<Package> packageList = MyApplication.getDaoInstant().getPackageDao().queryBuilder()
                .where(PackageDao.Properties.Barcode.eq(data)).list();
        if (packageList.size()>0){
            MyApplication.soundPool.play(MyApplication.successSound,1, 1, 0, 0, 1);
            Intent intent = new Intent(getActivity(), GroupCheckActivity.class);
            intent.putExtra("groupName", packageList.get(0).getName());
            startActivity(intent);
            return;
        }
        QueryBuilder<Device> where = MyApplication.getDaoInstant().getDeviceDao().queryBuilder()
                .where(DeviceDao.Properties.Barcode.eq(data));
        List<Device> list = where.list();
        if (list.size() > 0) {
            MyApplication.soundPool.play(MyApplication.successSound,1, 1, 0, 0, 1);
            Intent intent = new Intent(getActivity(), OnecheckActivity.class);
            intent.putExtra("deviceName", list.get(0).getName());
            startActivity(intent);
        }else {
            MyApplication.soundPool.play(MyApplication.failedSound,1, 1, 0, 0, 1);
            Toast.makeText(getActivity(),"没有查到相关信息",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void returnTreeNode(final TreeNode root) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder((MainActivity) getActivity());
                builder.setTitle("选择设备");
                builder.setCancelable(false);
                androidTreeView = new AndroidTreeView((MainActivity) getActivity(), root);
                androidTreeView.setDefaultAnimation(false);
                androidTreeView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
                androidTreeView.setDefaultViewHolder(IconTreeItemHolder.class);
                androidTreeView.setDefaultNodeClickListener(nodeClickListener);
                View view = androidTreeView.getView();
                view.setMinimumHeight(1600);
                view.setMinimumWidth(900);
                builder.setView(view);
                builder.setNegativeButton("取消", null);
                dialog = builder.create();
                dialog.show();
                hideLoading();
            }
        });

    }

    @Override
    public void showFailedInfo(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText((MainActivity) getActivity(), msg, Toast.LENGTH_SHORT).show();
                hideLoading();
            }
        });
    }

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {
            IconTreeItemHolder.IconTreeItem item = (IconTreeItemHolder.IconTreeItem) value;
            String path = node.getPath();
            int level = node.getLevel();
            String[] split = path.split(":");
            if (split[split.length-1].equals("2") && level == 3) {
                Intent intent = new Intent(getActivity(), OnecheckActivity.class);
                intent.putExtra("deviceName", item.text);
                startActivity(intent);
                dialog.cancel();
            }
            if (split[split.length-1].equals("1") && level == 2) {
                Intent intent=new Intent(getActivity(), GroupCheckActivity.class);
                intent.putExtra("groupName",item.text);
                startActivity(intent);
                dialog.cancel();
            }

        }
    };
}
