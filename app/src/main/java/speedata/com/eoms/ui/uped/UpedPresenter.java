package speedata.com.eoms.ui.uped;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import speedata.com.eoms.bean.ToUpRVBean;
import speedata.com.eoms.mvp.BasePresenterImpl;
import speedata.com.eoms.utils.GetTimeUtils;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class UpedPresenter extends BasePresenterImpl<UpedContract.View> implements UpedContract.Presenter{
    @Override
    public List<ToUpRVBean> findLogFile() {
        File file = new File("/storage/emulated/0/data/HTYL/Out");
        String[] list = file.list();
        List<ToUpRVBean> timeList = new ArrayList<>();
        for (int i = 0; i < list.length; i++) {
            boolean contains1 = list[i].contains("_inspection_ok.log");
            boolean contains2 = list[i].contains("_repair_ok.log");
            if (contains1 || contains2) {
                String[] split = list[i].split("_");
                String time = GetTimeUtils.getStrinTimeToAnotherStringTime(split[0]
                        , "yyyyMMddHHmmss", "yyyy-MM-dd");
                boolean isHave = false;
                for (int j = 0; j < timeList.size(); j++) {
                    String date = timeList.get(j).getDate();
                    if (date.equals(time)) {
                        timeList.get(j).setInfo1(list[i]);
                        timeList.get(j).setShow(true);
                        isHave = true;
                        break;
                    }
                }
                if (!isHave) {
                    ToUpRVBean toUpRVBean = new ToUpRVBean();
                    toUpRVBean.setDate(time);
                    toUpRVBean.setInfo2(list[i]);
                    timeList.add(toUpRVBean);
                }

            }
        }
        return timeList;
    }
}
