package speedata.com.eoms.ui.repair;


import speedata.com.eoms.R;
import speedata.com.eoms.mvp.MVPBaseFragment;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RepairFragment extends MVPBaseFragment<RepairContract.View, RepairPresenter> implements RepairContract.View {

    @Override
    public int getLayout() {
        return R.layout.fragment_repair;
    }
}
