package speedata.com.eoms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import speedata.com.eoms.R;
import speedata.com.eoms.model.ImageItem;

/**
 * Created by 张明_ on 2017/4/21.
 */

public class PhotoGridAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int selectedPosition = -1;
    private boolean shape;
    private List<ImageItem> imageItemList = new ArrayList<ImageItem>();

    public boolean isShape() {
        return shape;
    }

    public void setShape(boolean shape) {
        this.shape = shape;
    }

    public PhotoGridAdapter(Context context,List<ImageItem> imageItemList) {
        inflater = LayoutInflater.from(context);
        this.imageItemList=imageItemList;
    }

    public int getCount() {
        return (imageItemList.size() + 1);
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.photo_grid_item,
                    parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.photo_grid_item_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == imageItemList.size()) {
            holder.image.setImageResource(R.drawable.add);
        } else {
            holder.image.setImageBitmap(imageItemList.get(position)
                    .getBitmap());
        }

        return convertView;
    }

    public class ViewHolder {
        public ImageView image;
    }

    public void upDataList(List<ImageItem> imageItemList){
        this.imageItemList=imageItemList;
    }
}
