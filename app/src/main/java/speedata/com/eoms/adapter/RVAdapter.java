package speedata.com.eoms.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;

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

    public RVAdapter(Context context, int layoutResId, List<RVBean> data) {
        super(context, layoutResId, data);
        this.mContext = context;
        this.rvList = data;
    }

    @Override
    public void convert(BaseAdapterHelper helper, final RVBean item, final int outPosition) {
        final String orderNumber = item.getOrderNumber();
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
        }else {
            helper.setAdapter(R.id.gridview_photo, sections.get(outPosition).getAdapter());
        }
        helper.setOnItemClickListener(R.id.gridview_photo, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectPosition = outPosition;
                if (position == sections.get(outPosition).getImageItemList().size()) {
                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    ((Activity) mContext).startActivityForResult(openCameraIntent, TAKE_PICTURE);
                } else {
                    Intent intent = new Intent(mContext, ShowPhotoActivity.class);
                    intent.putExtra("ID", position);
                    intent.putExtra("orderNumber", orderNumber);

                    MyApplication.getInstance().selectBitmap.clear();
                    MyApplication.getInstance().selectBitmap.addAll(sections
                            .get(outPosition).getImageItemList());
                    ((Activity) mContext).startActivityForResult(intent,DEL_PICTURE);
                }
            }
        });
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

    public boolean onActicityResultInAdapter(Intent data) {
        String fileName = String.valueOf(System.currentTimeMillis());
        Bitmap bm = (Bitmap) data.getExtras().get("data");
        String orderNumber = rvList.get(selectPosition).getOrderNumber();
        if (BimpUtil.saveBitmap(bm, orderNumber, fileName)) {
            ImageItem takePhoto = new ImageItem();
            takePhoto.setBitmap(bm);
            takePhoto.setImagePath(AppConfig.getTempDataPath()
                    + "/" + orderNumber + "/" + fileName + ".png");
            sections.get(selectPosition).getImageItemList().add(takePhoto);
            notifyDataSetChanged();
            return true;
        } else {
            return false;
        }
    }

    public void upDataUi(){
        List<ImageItem> selectBitmap = new ArrayList<>();
        selectBitmap.addAll(MyApplication.getInstance().selectBitmap);
        sections.get(selectPosition).setImageItemList(selectBitmap);
        sections.get(selectPosition).getAdapter().upDataList(selectBitmap);
        notifyDataSetChanged();
    }
}
