package lib.linksu.fast.coding.home;

import android.os.Bundle;

import lib.linksu.fast.coding.R;
import lib.linksu.fast.coding.baselibrary.base.activity.LFragmentActivity;
import lib.linksu.fast.coding.baselibrary.base.fragment.BaseFragment;
import lib.linksu.fast.coding.baselibrary.enetity.BaseEventBusBean;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：9/13 0013
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class HomeActivity extends LFragmentActivity {

    @Override
    protected int getFragmentContentId() {
        return 0;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }

    @Override
    protected void operateArgs() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    protected boolean openEventBus() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void getEventBean(BaseEventBusBean event) {

    }

    @Override
    protected void onRetryClick() {

    }

    @Override
    protected void initViewsAndEvent(Bundle savedInstanceState) {

    }

    @Override
    protected int getContentViewById() {
        return R.layout.activity_home;
    }
}
