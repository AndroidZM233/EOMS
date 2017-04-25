package speedata.com.eoms.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import speedata.com.eoms.R;
import speedata.com.eoms.application.MyApplication;
import speedata.com.eoms.utils.BimpUtil;

/**
 * Created by 张明_ on 2017/4/21.
 */

public class GridAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int selectedPosition = -1;
    private boolean shape;

    public boolean isShape() {
        return shape;
    }

    public void setShape(boolean shape) {
        this.shape = shape;
    }

    public GridAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void update(Handler handler) {
        loading(handler);
    }

    public int getCount() {
        return (MyApplication.getInstance().selectBitmap.size() + 1);
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

        if (position == MyApplication.getInstance().selectBitmap.size()) {
            holder.image.setImageResource(R.drawable.add);
//            if (position == 9) {
//                holder.image.setVisibility(View.GONE);
//            }
        } else {
            holder.image.setImageBitmap(MyApplication.getInstance().selectBitmap.get(position)
                    .getBitmap());
        }

        return convertView;
    }

    public class ViewHolder {
        public ImageView image;
    }



    public void loading(final Handler handler) {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (BimpUtil.max == MyApplication.getInstance().selectBitmap.size()) {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                        break;
                    } else {
                        BimpUtil.max += 1;
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }
            }
        }).start();
    }

}
