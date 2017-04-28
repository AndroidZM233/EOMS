package speedata.com.eoms.ui.repair;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import speedata.com.eoms.R;
import speedata.com.eoms.adapter.GridAdapter;
import speedata.com.eoms.application.AppConfig;
import speedata.com.eoms.application.MyApplication;
import speedata.com.eoms.bean.Device;
import speedata.com.eoms.bean.DeviceDao;
import speedata.com.eoms.model.ImageItem;
import speedata.com.eoms.mvp.MVPBaseFragment;
import speedata.com.eoms.ui.main.MainActivity;
import speedata.com.eoms.ui.onecheck.OnecheckActivity;
import speedata.com.eoms.ui.showphoto.ShowPhotoActivity;
import speedata.com.eoms.utils.BimpUtil;
import speedata.com.eoms.utils.GetTimeUtils;
import speedata.com.eoms.utils.ScanUtil;
import speedata.com.eoms.view.MyGridView;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RepairFragment extends MVPBaseFragment<RepairContract.View, RepairPresenter>
        implements RepairContract.View, View.OnClickListener, AdapterView.OnItemClickListener {

    private Button btn_top_left;
    private TextView tv_title;
    private TextView tv_name;
    private TextView tv_type;
    private TextView tv_position;
    private TextView tv_responsible;
    private TextView tv_repair;
    private TextView tv_select;
    private RadioGroup radioGroup;
    private EditText et_remark;
    private MyGridView gridview_photo;
    private Button btn_commit;
    private GridAdapter adapter;
    private String orderNumber;
    private static final int TAKE_PICTURE = 0x000001;
    private ScanUtil scanUtil;


    @Override
    public int getLayout() {
        return R.layout.fragment_repair;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = this.getView();
        initView(view);
    }

    private void initView(View view) {
        btn_top_left = (Button) view.findViewById(R.id.btn_top_left);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_type = (TextView) view.findViewById(R.id.tv_type);
        tv_position = (TextView) view.findViewById(R.id.tv_position);
        tv_responsible = (TextView) view.findViewById(R.id.tv_responsible);
        tv_repair = (TextView) view.findViewById(R.id.tv_repair);
        tv_select = (TextView) view.findViewById(R.id.tv_select);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        et_remark = (EditText) view.findViewById(R.id.et_remark);
        gridview_photo = (MyGridView) view.findViewById(R.id.gridview_photo);
        btn_commit = (Button) view.findViewById(R.id.btn_commit);

        MyApplication.getInstance().selectBitmap.clear();
        tv_select.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
        btn_top_left.setVisibility(View.INVISIBLE);
        tv_title.setText("报修");
        radioGroup.setVisibility(View.GONE);
        if (adapter==null){
            adapter = new GridAdapter((MainActivity) getActivity());
        }
        gridview_photo.setAdapter(adapter);
        gridview_photo.setOnItemClickListener(this);
        scanUtil = new ScanUtil((MainActivity) getActivity());
        scanUtil.setOnScanListener(new ScanUtil.OnScanListener() {
            @Override
            public void getBarcode(String data) {
                QueryBuilder<Device> where = MyApplication.getDaoInstant().getDeviceDao().queryBuilder()
                        .where(DeviceDao.Properties.Barcode.eq(data));
                List<Device> list = where.list();
                if (list.size() > 0) {
                    tv_name.setText(list.get(0).getName());
                    tv_type.setText(list.get(0).getType());
                    tv_position.setText(list.get(0).getPosition());
                    tv_responsible.setText(list.get(0).getResponsibilityOffice());
                    tv_repair.setText(list.get(0).getMaintenanceFactory());
                    orderNumber=list.get(0).getId();
//                    PlaySoundPool.getPlaySoundPool((MainActivity) getActivity()).playLaser();

                    BimpUtil.max = 0;
                    MyApplication.getInstance().selectBitmap.clear();
                    mPresenter.getOrderNumPhoto(orderNumber);//获取当前单号下的照片
                    adapter.update(handler);

                } else {
//                    PlaySoundPool.getPlaySoundPool((MainActivity) getActivity()).playError();
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        scanUtil.stopReceData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_commit:
                if (TextUtils.isEmpty(orderNumber)){
                    Toast.makeText((MainActivity) getActivity(),"请先扫描或者选择!"
                            ,Toast.LENGTH_SHORT).show();
                    return;
                }
                final String repairContent = et_remark.getText().toString();
                if (TextUtils.isEmpty(repairContent)){
                    Toast.makeText((MainActivity) getActivity(),"请填写备注（故障内容）!"
                            ,Toast.LENGTH_SHORT).show();
                    return;
                }
                showLoading((MainActivity) getActivity(),"提交中...");
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        final boolean repairTXT = mPresenter.saveRepairTXT(orderNumber,
                                repairContent);
                        hideLoading();
                        ((MainActivity) getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (repairTXT){
                                    Toast.makeText((MainActivity) getActivity(),"提交成功！!"
                                            ,Toast.LENGTH_SHORT).show();
//                                    closeFragment();
                                    openFragment(new RepairFragment());
                                }else {
                                    Toast.makeText((MainActivity) getActivity(),"提交失败，文件保存失败!"
                                            ,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).start();

                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.update(handler);
        }
    }


    Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    adapter.notifyDataSetChanged();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (TextUtils.isEmpty(orderNumber)){
            Toast.makeText((MainActivity) getActivity(),"请先扫描或者选择!",Toast.LENGTH_SHORT).show();
            return;
        }
        if (position == MyApplication.getInstance().selectBitmap.size()) {
            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(openCameraIntent, TAKE_PICTURE);
        } else {
            Intent intent = new Intent((MainActivity) getActivity(), ShowPhotoActivity.class);
            intent.putExtra("ID", position);
            intent.putExtra("orderNumber", orderNumber);
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode != OnecheckActivity.RESULT_OK) {
                    return;
                }
                String fileName = GetTimeUtils.getTimeStyle1()+"_"+MyApplication.deviceId;
                Bitmap bm = (Bitmap) data.getExtras().get("data");
                if (BimpUtil.saveBitmap(bm, orderNumber, fileName)) {
                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setBitmap(bm);
                    takePhoto.setImagePath(AppConfig.getTempDataPath() + "/" + orderNumber + "/" + fileName + ".bmp");
                    MyApplication.getInstance().selectBitmap.add(takePhoto);
                    if (adapter != null) {
                        adapter.update(handler);
                    }
                } else {
                    Toast.makeText((MainActivity) getActivity(),"图片保存失败！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
