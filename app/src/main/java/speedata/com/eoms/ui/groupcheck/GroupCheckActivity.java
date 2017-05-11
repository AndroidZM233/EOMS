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
import android.widget.Toast;

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
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_groupcheck);
        initView();
        List<RVBean> rvBeanList = mPresenter.initData(getIntent().getStringExtra("groupName"));
        mList.addAll(rvBeanList);
        initRV();
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
        layoutManager = new LinearLayoutManager(this);
        rv_content.setLayoutManager(layoutManager);
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
                showLoading("提交中...");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean inspectionTXT;
                        for (int i = 0; i < mList.size(); i++) {
                            String etStr = mList.get(i).getRemark();
                            boolean checked = mList.get(i).isNo1_check();
                            if (!checked) {
                                inspectionTXT = mPresenter.saveInspectionTXT(mList.get(i)
                                        .getOrderNumber(), 0, etStr);
                            } else {
                                inspectionTXT = mPresenter.saveInspectionTXT(mList.get(i)
                                        .getOrderNumber(), 1, etStr);
                            }
                            if (!inspectionTXT) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(GroupCheckActivity.this,
                                                "提交失败，文件保存失败!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                });
                            }
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(GroupCheckActivity.this, "提交成功！!"
                                        , Toast.LENGTH_SHORT).show();
                                GroupCheckActivity.this.finish();
                            }
                        });
                    }
                }).start();
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
                showLoading("保存中...");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean result = mAdapter.onActicityResultInAdapter(GroupCheckActivity.this);
                        if (!result) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showToast("图片保存失败！");
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                        hideLoading();
                    }
                }).start();

                break;
            case DEL_PICTURE:
                mAdapter.upDataUi();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
