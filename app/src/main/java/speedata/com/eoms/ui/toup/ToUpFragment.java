package speedata.com.eoms.ui.toup;


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
 * 邮箱 784787081@qq.com
 */

public class ToUpFragment extends MVPBaseFragment<ToUpContract.View, ToUpPresenter> implements ToUpContract.View {

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
        ToUpRVBean toUpRVBean=new ToUpRVBean();
        toUpRVBean.setDate("2022-11-11");
        toUpRVBean.setInfo1("111111111111111111111.txt");
        toUpRVBean.setInfo2("111111111111111111111111.kk");
        toUpRVBean.setShow(true);
        mList.add(toUpRVBean);
        ToUpRVBean toUpRVBean2=new ToUpRVBean();
        toUpRVBean2.setDate("2022-11-10");
        toUpRVBean2.setInfo2("22222222222222222222222.txt");
        toUpRVBean2.setShow(false);
        mList.add(toUpRVBean2);
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
