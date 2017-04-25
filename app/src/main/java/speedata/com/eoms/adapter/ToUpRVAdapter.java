package speedata.com.eoms.adapter;

import android.content.Context;

import java.util.List;

import speedata.com.eoms.R;
import speedata.com.eoms.bean.ToUpRVBean;
import xyz.reginer.baseadapter.BaseAdapterHelper;
import xyz.reginer.baseadapter.CommonRvAdapter;

/**
 * Created by 张明_ on 2017/4/5.
 */

public class ToUpRVAdapter extends CommonRvAdapter<ToUpRVBean> {


    public ToUpRVAdapter(Context context, int layoutResId, List<ToUpRVBean> data) {
        super(context, layoutResId, data);
    }

    @Override
    public void convert(BaseAdapterHelper helper, ToUpRVBean item, int position) {
        helper.setVisible(R.id.linearlayout1,item.isShow());
        helper.setText(R.id.tv_date,item.getDate());
        helper.setText(R.id.tv_info1,item.getInfo1());
        helper.setText(R.id.tv_info2,item.getInfo2());
    }
}
