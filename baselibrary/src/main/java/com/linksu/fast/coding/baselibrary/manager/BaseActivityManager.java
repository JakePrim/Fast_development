package com.linksu.fast.coding.baselibrary.manager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import java.util.LinkedList;
import java.util.List;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：7/6 0006
 * 描    述：Class Note:
 * use {@link LinkedList} to manage your activity stack
 * 修订历史：
 * ================================================
 */
public class BaseActivityManager {
    private static BaseActivityManager instance = null;
    private static List<Activity> mActivities = new LinkedList<>();

    private BaseActivityManager() {

    }

    public static BaseActivityManager getInstance() {
        if (null == instance) {
            synchronized (BaseActivityManager.class) {
                if (null == instance) {
                    instance = new BaseActivityManager();
                }
            }
        }
        return instance;
    }

    public int size() {
        return mActivities.size();
    }


    public synchronized Activity getForwardActivity() {
        return size() > 0 ? mActivities.get(size() - 1) : null;
    }

    public synchronized void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    public synchronized void removeActivity(Activity activity) {
        if (mActivities.contains(activity)) {
            mActivities.remove(activity);
        }
    }

    public synchronized void clear() {
        for (int i = mActivities.size() - 1; i > -1; i--) {
            Activity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
            i = mActivities.size();
        }
    }

    public synchronized void clearTop() {
        for (int i = mActivities.size() - 2; i > -1; i--) {
            Activity activity = mActivities.get(i);
            removeActivity(activity);
            activity.finish();
            i = mActivities.size() - 1;
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            clear();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }

    public boolean isAppExit() {
        return mActivities == null || mActivities.isEmpty();
    }
}
