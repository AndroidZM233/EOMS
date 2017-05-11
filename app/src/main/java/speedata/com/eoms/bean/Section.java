package speedata.com.eoms.bean;

import java.util.List;

import speedata.com.eoms.adapter.PhotoGridAdapter;
import speedata.com.eoms.model.ImageItem;

/**
 * Created by 张明_ on 2017/4/24.
 */

public class Section {
    private List<ImageItem> imageItemList;
    private PhotoGridAdapter adapter;
    private String remark;
    private boolean faultChecked;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isFaultChecked() {
        return faultChecked;
    }

    public void setFaultChecked(boolean faultChecked) {
        this.faultChecked = faultChecked;
    }

    public List<ImageItem> getImageItemList() {
        return imageItemList;
    }

    public void setImageItemList(List<ImageItem> imageItemList) {
        this.imageItemList = imageItemList;
    }

    public PhotoGridAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(PhotoGridAdapter adapter) {
        this.adapter = adapter;
    }

    public Section(List<ImageItem> imageItemList, PhotoGridAdapter adapter) {
        this.imageItemList = imageItemList;
        this.adapter = adapter;
    }

}
