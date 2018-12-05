package mvp.rx.retrofit;

import android.app.Activity;

import java.util.Iterator;
import java.util.Stack;

/**
 * Description:activity管理类
 * Data：2018/10/23-15:09
 * Author: lin
 */
public class ActivityManager {
    private static ActivityManager sInstance;
    private static Stack<Activity> stackActivity;

    private ActivityManager() {
        stackActivity = new Stack<>();
    }

    public static ActivityManager getInstance() {
        if (sInstance == null) {
            synchronized (ActivityManager.class) {
                if (sInstance == null) {
                    sInstance = new ActivityManager();
                }
            }
        }
        return sInstance;
    }

    // ----------------------------activity life method

    public void onCreate(Activity activity) {
        addActivity(activity);
    }

    public void onResume(Activity activity) {
        addActivity(activity);
    }

    /**
     * finish()和onDestroy()都要调用
     *
     * @param activity
     */
    public void onDestroy(Activity activity) {
        removeActivity(activity);
    }

    private void addActivity(Activity activity) {
        if (!stackActivity.contains(activity)) {
            stackActivity.add(activity);
        }
    }

    private void removeActivity(Activity activity) {
        if (activity != null) {
            stackActivity.remove(activity);
        }
    }

    public Activity getActivity(Class<?> cls) {
        Iterator<Activity> it = stackActivity.iterator();
        while (it.hasNext()) {
            Activity act = it.next();
            if (act.getClass() == cls) {
                return act;
            }
        }
        return null;
    }

    public Activity getLastActivity() {
        Activity activity = null;
        try {
            activity = stackActivity.lastElement();
        } catch (Exception e) {
        }
        return activity;
    }

    public boolean isLastActivity(Activity activity) {
        if (activity != null) {
            return getLastActivity() == activity;
        } else {
            return false;
        }
    }

    public boolean isEmpty() {
        return stackActivity.isEmpty();
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        Iterator<Activity> it = stackActivity.iterator();
        while (it.hasNext()) {
            Activity act = it.next();
            if (act.getClass() == cls) {
                it.remove();
                act.finish();
            }
        }
    }

    public boolean containActivity(Class<?> cls) {
        Iterator<Activity> it = stackActivity.iterator();
        while (it.hasNext()) {
            Activity act = it.next();
            if (act.getClass() == cls) {
                return true;
            }
        }
        return false;
    }

    public void finishAllClassActivityExcept(Activity activity) {
        Iterator<Activity> it = stackActivity.iterator();
        while (it.hasNext()) {
            Activity act = it.next();
            if (act.getClass() == activity.getClass() && act != activity) {
                it.remove();
                act.finish();
            }
        }
    }

    public void finishAllActivity() {
        Iterator<Activity> it = stackActivity.iterator();
        while (it.hasNext()) {
            Activity act = it.next();
            it.remove();
            act.finish();
        }
    }

    public void finishAllActivityExcept(Class<?> cls) {
        Iterator<Activity> it = stackActivity.iterator();
        while (it.hasNext()) {
            Activity act = it.next();
            if (act.getClass() != cls) {
                it.remove();
                act.finish();
            }
        }
    }

    public void finishAllActivityExcept(Activity activity) {
        Iterator<Activity> it = stackActivity.iterator();
        while (it.hasNext()) {
            Activity act = it.next();
            if (act != activity) {
                it.remove();
                act.finish();
            }
        }
    }
}
