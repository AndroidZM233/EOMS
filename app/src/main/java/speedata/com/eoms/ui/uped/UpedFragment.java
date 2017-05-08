package speedata.com.eoms.ui.uped;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import speedata.com.eoms.R;
import speedata.com.eoms.adapter.ToUpRVAdapter;
import speedata.com.eoms.bean.ToUpRVBean;
import speedata.com.eoms.mvp.MVPBaseFragment;
import speedata.com.eoms.ui.record.RecordActivity;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class UpedFragment extends MVPBaseFragment<UpedContract.View, UpedPresenter> implements UpedContract.View {

    private RecyclerView rv_content;
    private ToUpRVAdapter mAdapter;
    private List<ToUpRVBean> mList = new ArrayList<ToUpRVBean>();

    @Override
    public int getLayout() {
        return R.layout.fragment_toup;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = this.getView();
        initView(view);
        initData();
        initRV();
    }

    private void initData() {
        List<ToUpRVBean> logFile = mPresenter.findLogFile();
        mList.addAll(logFile);
    }

    private void initView(View view) {
        rv_content= (RecyclerView) view.findViewById(R.id.rv_content);
    }

    private void initRV() {
        mAdapter = new ToUpRVAdapter((RecordActivity)getActivity(), R.layout.item_record, mList);
        rv_content.setLayoutManager(new LinearLayoutManager((RecordActivity)getActivity()));
        rv_content.setAdapter(mAdapter);
    }
}
