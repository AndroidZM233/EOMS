package speedata.com.eoms.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import speedata.com.eoms.R;
import speedata.com.eoms.application.AppConfig;
import speedata.com.eoms.application.MyApplication;
import speedata.com.eoms.bean.RVBean;
import speedata.com.eoms.bean.Section;
import speedata.com.eoms.model.ImageItem;
import speedata.com.eoms.ui.showphoto.ShowPhotoActivity;
import speedata.com.eoms.utils.BimpUtil;
import speedata.com.eoms.utils.FileUtil;
import speedata.com.eoms.utils.GetTimeUtils;
import xyz.reginer.baseadapter.BaseAdapterHelper;
import xyz.reginer.baseadapter.CommonRvAdapter;

/**
 * Created by 张明_ on 2017/4/5.
 */

public class RVAdapter extends CommonRvAdapter<RVBean> {
    private Context mContext;
    private static final int TAKE_PICTURE = 0x000001;
    private static final int DEL_PICTURE = 0x000002;
    private List<Section> sections = new ArrayList<Section>();
    private List<RVBean> rvList = new ArrayList<RVBean>();
    private int selectPosition;
    private String imagePath;
    private String fileName;
    private int etPosition;

    public RVAdapter(Context context, int layoutResId, List<RVBean> data) {
        super(context, layoutResId, data);
        this.mContext = context;
        this.rvList = data;
    }

    @Override
    public void convert(BaseAdapterHelper helper, final RVBean item, final int outPosition) {
        final String orderNumber = item.getOrderNumber();
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_type, item.getType());
        helper.setText(R.id.tv_position, item.getPosition());
        helper.setText(R.id.tv_responsible, item.getResponsible());
        helper.setText(R.id.tv_repair, item.getRepair());

        if (sections.size() < rvList.size()) {
            MyApplication.getInstance().selectBitmap.clear();
            getOrderNumPhoto(orderNumber);
            List<ImageItem> selectBitmap = new ArrayList<>();
            selectBitmap.addAll(MyApplication.getInstance()
                    .selectBitmap);
            PhotoGridAdapter adapter = new PhotoGridAdapter(mContext, selectBitmap);
            Section section = new Section(selectBitmap, adapter);
            sections.add(section);
            helper.setAdapter(R.id.gridview_photo, adapter);
        } else {
            helper.setAdapter(R.id.gridview_photo, sections.get(outPosition).getAdapter());
        }

//        helper.setText(R.id.et_remark, "");
        helper.setText(R.id.et_remark, rvList.get(outPosition).getRemark());
        boolean itemNo1_check = rvList.get(outPosition).isNo1_check();
        if (itemNo1_check) {
            RadioGroup radioGroup = helper.getView(R.id.radioGroup);
            radioGroup.clearCheck();
            radioGroup.check(R.id.radio_fault);
        } else {
            RadioGroup radioGroup = helper.getView(R.id.radioGroup);
            radioGroup.clearCheck();
            radioGroup.check(R.id.radio_normal);
        }


        helper.setOnItemClickListener(R.id.gridview_photo, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectPosition = outPosition;

                fileName = GetTimeUtils.getTimeStyle1() + "_" + MyApplication.deviceId;
                String fileParentStr = AppConfig.getTempDataPath() + "/" + orderNumber + "/";
                imagePath = fileParentStr + fileName + ".png";


                if (position == sections.get(outPosition).getImageItemList().size()) {
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
                    ((Activity) mContext).startActivityForResult(openCameraIntent, TAKE_PICTURE);
                } else {
                    Intent intent = new Intent(mContext, ShowPhotoActivity.class);
                    intent.putExtra("ID", position);
                    intent.putExtra("orderNumber", orderNumber);

                    MyApplication.getInstance().selectBitmap.clear();
                    MyApplication.getInstance().selectBitmap.addAll(sections
                            .get(outPosition).getImageItemList());
                    ((Activity) mContext).startActivityForResult(intent, DEL_PICTURE);
                }
            }
        });

        helper.setOnClickListener(R.id.radio_normal, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvList.get(outPosition).setNo1_check(false);
            }
        });
        helper.setOnClickListener(R.id.radio_fault, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvList.get(outPosition).setNo1_check(true);
            }
        });

        final EditText view = helper.getView(R.id.et_remark);
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    view.addTextChangedListener(new TextSwitcher(outPosition));
                    etPosition = outPosition;
                } else {
                    etPosition = -1;
                }
            }
        });

    }

    class TextSwitcher implements TextWatcher {

        private int outPosition;

        public TextSwitcher(int outPosition) {
//            this.item = item;
            this.outPosition = outPosition;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (etPosition == outPosition && etPosition >= 0 ) {
                rvList.get(etPosition).setRemark(s.toString());
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
//            item.setRemark(s.toString());
//            if (etPosition >= 0 ) {
//                rvList.get(etPosition).setRemark(s.toString());
//                etPosition=-1;
//            }
        }
    }

    private void getOrderNumPhoto(String orderNumber) {
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

    public boolean onActicityResultInAdapter(Context context) {
        String orderNumber = rvList.get(selectPosition).getOrderNumber();
        Bitmap bm = BimpUtil.getimage(imagePath, 400f, 240f);
        if (BimpUtil.saveBitmap(bm, orderNumber, fileName)) {
            ImageItem takePhoto = new ImageItem();
            takePhoto.setBitmap(bm);
            takePhoto.setImagePath(imagePath);
            sections.get(selectPosition).getImageItemList().add(takePhoto);
            return true;
        } else {
            return false;
        }

//        String fileName = GetTimeUtils.getTimeStyle1() + "_" + MyApplication.deviceId;
//        Bitmap bm = (Bitmap) data.getExtras().get("data");
//
//        if (BimpUtil.saveBitmap(bm, orderNumber, fileName)) {
//            ImageItem takePhoto = new ImageItem();
//            takePhoto.setBitmap(bm);
//            takePhoto.setImagePath(AppConfig.getTempDataPath()
//                    + "/" + orderNumber + "/" + fileName + ".png");
//            sections.get(selectPosition).getImageItemList().add(takePhoto);
//            notifyDataSetChanged();
//            return true;
//        } else {
//            return false;
//        }
    }

    public void upDataUi() {
        List<ImageItem> selectBitmap = new ArrayList<>();
        selectBitmap.addAll(MyApplication.getInstance().selectBitmap);
        sections.get(selectPosition).setImageItemList(selectBitmap);
        sections.get(selectPosition).getAdapter().upDataList(selectBitmap);
        notifyDataSetChanged();
    }
}
