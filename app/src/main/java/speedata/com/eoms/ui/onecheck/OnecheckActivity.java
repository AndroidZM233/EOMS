package speedata.com.eoms.ui.onecheck;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import speedata.com.eoms.R;
import speedata.com.eoms.adapter.GridAdapter;
import speedata.com.eoms.application.AppConfig;
import speedata.com.eoms.application.MyApplication;
import speedata.com.eoms.bean.Device;
import speedata.com.eoms.bean.DeviceType;
import speedata.com.eoms.bean.DeviceTypeDao;
import speedata.com.eoms.model.ImageItem;
import speedata.com.eoms.mvp.MVPBaseActivity;
import speedata.com.eoms.ui.showphoto.ShowPhotoActivity;
import speedata.com.eoms.utils.BimpUtil;
import speedata.com.eoms.utils.GetTimeUtils;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class OnecheckActivity extends MVPBaseActivity<OnecheckContract.View, OnecheckPresenter>
        implements OnecheckContract.View, View.OnClickListener, AdapterView.OnItemClickListener {
    private TextView tv_name;
    private TextView tv_type;
    private TextView tv_position;
    private TextView tv_responsible;
    private TextView tv_repair;
    private RadioButton radio_normal;
    private RadioButton radio_fault;
    private RadioGroup radioGroup;
    private EditText et_remark;
    private GridView gridview_photo;
    private GridAdapter adapter;
    private Button btn_top_left, btn_commit;
    private TextView tv_title;
    private static final int TAKE_PICTURE = 0x000001;
    private String orderNumber = "1";
    private String imagePath;
    private String fileName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_onecheck);
        initView();
        String deviceName = getIntent().getStringExtra("deviceName");
        mPresenter.initData(deviceName);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_position = (TextView) findViewById(R.id.tv_position);
        tv_responsible = (TextView) findViewById(R.id.tv_responsible);
        tv_repair = (TextView) findViewById(R.id.tv_repair);
        radio_normal = (RadioButton) findViewById(R.id.radio_normal);
        radio_fault = (RadioButton) findViewById(R.id.radio_fault);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        et_remark = (EditText) findViewById(R.id.et_remark);
        gridview_photo = (GridView) findViewById(R.id.gridview_photo);
        tv_title = (TextView) findViewById(R.id.tv_title);
        btn_top_left = (Button) findViewById(R.id.btn_top_left);
        btn_commit = (Button) findViewById(R.id.btn_commit);

        tv_title.setText("单巡");
        btn_top_left.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
        gridview_photo.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        gridview_photo.setAdapter(adapter);
        gridview_photo.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_top_left:
                OnecheckActivity.this.finish();
                break;
            case R.id.btn_commit:
                showLoading("保存中...");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final boolean inspectionTXT;
                        boolean checked = radio_normal.isChecked();
                        //正常传0 故障传1
                        if (checked) {
                            inspectionTXT = mPresenter.saveInspectionTXT(orderNumber
                                    , 0, et_remark.getText().toString());
                        } else {
                            inspectionTXT = mPresenter.saveInspectionTXT(orderNumber
                                    , 1, et_remark.getText().toString());
                        }
                        hideLoading();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (inspectionTXT) {
                                    Toast.makeText(OnecheckActivity.this, "保存成功！!"
                                            , Toast.LENGTH_SHORT).show();
                                    OnecheckActivity.this.finish();
                                } else {
                                    Toast.makeText(OnecheckActivity.this, "文件保存失败!"
                                            , Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).start();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        fileName = GetTimeUtils.getTimeStyle1() + "_" + MyApplication.deviceId;
        String fileParentStr = AppConfig.getTempDataPath() + "/" + orderNumber + "/";
        imagePath = fileParentStr + fileName + ".png";

        if (position == MyApplication.getInstance().selectBitmap.size()) {
            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 加载路径
            File file = new File(imagePath);
            File fileParent = new File(fileParentStr);
            if (!fileParent.exists()) {
                fileParent.mkdirs();
            }
            Uri uri = Uri.fromFile(file);
            // 指定存储路径，这样就可以保存原图了
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(openCameraIntent, TAKE_PICTURE);
        } else {
            Intent intent = new Intent(OnecheckActivity.this, ShowPhotoActivity.class);
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
                showLoading("保存中...");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bm = BimpUtil.getimage(imagePath, 200f, 120f);
                        if (BimpUtil.saveBitmap(bm, orderNumber, fileName)) {
                            ImageItem takePhoto = new ImageItem();
                            takePhoto.setBitmap(bm);
                            takePhoto.setImagePath(imagePath);
                            MyApplication.getInstance().selectBitmap.add(takePhoto);
                            if (adapter != null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(OnecheckActivity.this,
                                            "图片保存失败！", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        hideLoading();
                    }
                }).start();

                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void backDevice(Device device) {
        tv_name.setText(device.getName());
        String classify = device.getClassify();
        List<DeviceType> deviceTypeList = MyApplication.getDaoInstant().getDeviceTypeDao().queryBuilder()
                .where(DeviceTypeDao.Properties.Id.eq(classify)).list();
        if (deviceTypeList.size()!=0){
            tv_type.setText(deviceTypeList.get(0).getName());
        }
        tv_position.setText(device.getPosition());
        tv_responsible.setText(device.getResponsibilityOffice());
        tv_repair.setText(device.getMaintenanceFactory());
        orderNumber = device.getId();

        BimpUtil.max = 0;
        MyApplication.getInstance().selectBitmap.clear();
        mPresenter.getOrderNumPhoto(orderNumber);//获取当前单号下的照片
        adapter.notifyDataSetChanged();
    }
}
