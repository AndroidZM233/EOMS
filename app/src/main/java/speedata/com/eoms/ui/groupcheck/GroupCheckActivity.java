package speedata.com.eoms.ui.groupcheck;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import speedata.com.eoms.R;
import speedata.com.eoms.adapter.RVAdapter;
import speedata.com.eoms.bean.RVBean;
import speedata.com.eoms.mvp.MVPBaseActivity;
import xyz.reginer.baseadapter.CommonRvAdapter;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class GroupCheckActivity extends MVPBaseActivity<GroupCheckContract.View, GroupCheckPresenter>
        implements GroupCheckContract.View, View.OnClickListener, CommonRvAdapter.OnItemClickListener {

    private Button btn_top_left;
    private TextView tv_title;
    private RecyclerView rv_content;
    private Button btn_commit;
    private RVAdapter mAdapter;
    private List<RVBean> mList = new ArrayList<RVBean>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_groupcheck);
        initView();
        initData();
        initRV();
    }

    private void initData() {
        RVBean rvBean = new RVBean();
        rvBean.setName("1");
        rvBean.setPosition("1");
        rvBean.setType("1");
        rvBean.setOrderNumber("1");
        mList.add(rvBean);
        RVBean rvBean2 = new RVBean();
        rvBean2.setName("2");
        rvBean2.setPosition("2");
        rvBean2.setType("2");
        rvBean2.setOrderNumber("2");
        mList.add(rvBean2);
    }

    private void initView() {
        btn_top_left = (Button) findViewById(R.id.btn_top_left);
        tv_title = (TextView) findViewById(R.id.tv_title);
        rv_content = (RecyclerView) findViewById(R.id.rv_content);
        btn_commit = (Button) findViewById(R.id.btn_commit);

        btn_top_left.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
        tv_title.setText("组巡");
    }

    private void initRV() {
        mAdapter = new RVAdapter(GroupCheckActivity.this, R.layout.item_info, mList);
        rv_content.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rv_content.setLayoutManager(new LinearLayoutManager(this));
        rv_content.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_top_left:
                GroupCheckActivity.this.finish();
                break;
            case R.id.btn_commit:

                break;
        }
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, View view, int position) {
    }

    private static final int TAKE_PICTURE = 0x000001;
    private static final int DEL_PICTURE = 0x000002;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode != GroupCheckActivity.RESULT_OK) {
                    return;
                }
                boolean result = mAdapter.onActicityResultInAdapter(data);
                if (!result){
                    showToast("图片保存失败！");
                }
                break;
            case DEL_PICTURE:
                mAdapter.upDataUi();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
