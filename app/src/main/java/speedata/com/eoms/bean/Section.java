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
