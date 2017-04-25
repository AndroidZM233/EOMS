package speedata.com.eoms.ui.check;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import speedata.com.eoms.R;
import speedata.com.eoms.mvp.MVPBaseFragment;
import speedata.com.eoms.ui.main.MainActivity;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CheckFragment extends MVPBaseFragment<CheckContract.View, CheckPresenter>
        implements CheckContract.View,View.OnClickListener {

    private Button btn_top_left;
    private TextView tv_title;
    private TextView tv_select;

    @Override
    public int getLayout() {
        return R.layout.fragment_check;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = this.getView();
        initView(view);
    }

    private void initView(View view) {
        btn_top_left= (Button) view.findViewById(R.id.btn_top_left);
        btn_top_left.setVisibility(View.INVISIBLE);
        tv_title= (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("巡检");
        tv_select= (TextView) view.findViewById(R.id.tv_select);

        tv_select.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_select:
                Toast.makeText((MainActivity) getActivity(),"222",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
