package lilin.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Map;

/**
 * 简单封装的BaseDialogFragment基类
 *
 * @author DrkCore
 * @since 2015年10月17日12:48:35
 */
public abstract class BaseDialogFragment extends DialogFragment {

	/* 继承 */

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //将回调位置信息保存到Bundle中
        outState.putSerializable(KEY_LISTENER_HOLDER_BUNDLE, listenerLocations);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //Dlg被show了，是时候将临时存储起来的Listener倒出来保存其位置信息了
        dumpTmpListener();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //恢复回调位置信息
        ArrayList<ListenerLocation> holders = savedInstanceState != null ?
                (ArrayList<ListenerLocation>) savedInstanceState.getSerializable(KEY_LISTENER_HOLDER_BUNDLE) : null;
        if (holders != null && !holders.isEmpty()) {
            if (listenerLocations != null) {
                listenerLocations.addAll(holders);
            } else {
                listenerLocations = holders;
            }
        }
    }

    /*回调辅助*/

    private static class ListenerLocation implements Serializable {

        public Class listenerClz;
        public int from;
        public String fieldName;

        public ListenerLocation() {
        }

        public ListenerLocation(Class listenerClz, int from, String fieldName) {
            this.listenerClz = listenerClz;
            this.from = from;
            this.fieldName = fieldName;
        }
    }

    private static final String KEY_LISTENER_HOLDER_BUNDLE = "KEY_LISTENER_HOLDER_BUNDLE";

    /**
     * 通常Dlg是现setListener之后再被show的，在此之前是无法获得parentFrag或者context等。
     * 这里用一个Map先将之保存下来，当{@link #onAttach(Context)}时再重新{@link #saveListener(Class, Object)}。
     */
    private ArrayMap<Class, Object> tmpListeners;
    /**
     * 所哟的回调位置信息
     */
    private ArrayList<ListenerLocation> listenerLocations;

    /**
     * 存储回调对象的位置信息以便在Activity重建之后找到对应的实例。
     * 使用该方法你必须将回调实现定义在非静态且被final修饰的成员变量内，比如：
     * <p/>
     * <code>
     * class TmpFrag extends Fragment{ <br/><br/>
     * //theListener就是会被保存下的位置。<br/>
     * 任意权限修饰符 final XXXListener  theListener = new XXXListener(){};
     * <br/><br/>
     * }
     * </code>
     * <br/>
     *
     * @param listenerType
     * @param listener
     */
    protected final void saveListener(Class listenerType, Object listener) {
        if (listener == null || listener instanceof Activity || listener instanceof Fragment) {
            //为null直接退出不解释
            //如果是activity或者fragment的话不保存回调，用getListenerFromParent就行了
            return;
        }

        if (!isAdded()) {
            /**还没添加到界面时无法获得context和parentFrag所以这里先添加到临时的栈中
             * 当{@link #onAttach(Context)}时再重新save*/
            if (tmpListeners == null) {
                tmpListeners = new ArrayMap<>();
            }
            tmpListeners.put(listenerType, listener);
            return;
        }

        //搜索回调对象的位置
        ListenerLocation listenerLocation = findListenerLocation(listenerType, listener);
        if (listenerLocation == null) {
            return;//搜不到，直接退出
        }

        //执行到这里，说明已经搜索到了所在的类和Field名
        if (listenerLocations == null) {
            listenerLocations = new ArrayList<>();
        }
        listenerLocations.add(listenerLocation);
    }

    /**
     * 将临时保存的Listener重新save一下。
     */
    private void dumpTmpListener() {
        if (tmpListeners == null) {
            return;
        }
        for (Map.Entry<Class, Object> entry : tmpListeners.entrySet()) {
            saveListener(entry.getKey(), entry.getValue());
        }
        //清空数据
        tmpListeners.clear();
        tmpListeners = null;
    }

    /**
     * 按照{@link #getTargetFragment()}、{@link #getParentFragment()}和{@link #getContext()}的顺序依次
     * 查找listener是否是其某一成员变量。如果是则返回该成员变量的位置信息，否则为null。
     *
     * @param listenerType
     * @param listener
     * @return
     */
    @Nullable
    private ListenerLocation findListenerLocation(@NonNull Class listenerType, @NonNull Object listener) {
        Object[] from = {getTargetFragment(), getParentFragment(), getContext()};
        for (int i = 0, len = from.length; i < len; i++) {
            String fieldName = from[i] != null ? checkListenerBelongs(from[i], listener) : null;
            if (fieldName != null) {
                return new ListenerLocation(listenerType, i, fieldName);
            }
        }
        return null;
    }

    /**
     * 检查listener是否被toCheck对象的某一个成员所引用。
     * 如果是则返回该成员域的名字，否则为null。
     *
     * @param toCheck
     * @param listener
     * @return
     */
    @Nullable
    private static String checkListenerBelongs(@NonNull Object toCheck, @NonNull Object listener) {
        Class toCheckClz = toCheck.getClass();
        Field[] fields = toCheckClz.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);//打开限制
                //如果要保存的接口是toCheck的非静态的且被final修饰的成员则返回域名称，表明查找成功
                if (!Modifier.isStatic(field.getModifiers())
                        && Modifier.isFinal(field.getModifiers())
                        && field.get(toCheck) == listener) {
                    return field.getName();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    protected final <T> T getListenerFromSavedLocation(Class<T> listener) {
        if (listenerLocations == null) {
            return null;
        }

        //查找ListenerHolders中是否存在接口对应的位置信息
        ListenerLocation location = null;
        ListenerLocation tmp;
        for (int i = 0, size = listenerLocations.size(); i < size; i++) {
            tmp = listenerLocations.get(i);
            if (tmp.listenerClz == listener) {
                location = tmp;
                break;
            }
        }
        if (location == null) {
            return null;
        }

        //执行到这说明location已经是要的那个接口的类，让我们开始反射一下
        Object from = new Object[]{getTargetFragment(), getParentFragment(), getContext()}[location.from];
        try {
            Field field = from != null ? from.getClass().getDeclaredField(location.fieldName) : null;
            if (field != null) {
                field.setAccessible(true);
                return (T) field.get(from);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从父容器中获取回调。
     *
     * @param listener
     * @param <T>
     * @return
     */
    protected final <T> T getListenerFromParent(Class<T> listener) {
        if (!listener.isInterface()) {
            throw new IllegalArgumentException("getListenerFromParent()方法只允许获取接口");
        }
        Object obj;
        if (listener.isInstance(obj = getTargetFragment())/*targetFragment为最优先级*/
                || listener.isInstance(obj = getParentFragment()/*父fragment为次*/)
                || listener.isInstance(obj = getContext())/*Activity最次*/) {
            return (T) obj;
        }
        return null;
    }

    /**
     * 按照{@link #getListenerFromParent(Class)}、{@link #getListenerFromSavedLocation(Class)}的顺序获取指定的回调实例。
     *
     * @param listener
     * @param <T>
     * @return
     */
    protected final <T> T getListenerEx(Class<T> listener) {
        T t = getListenerFromParent(listener);
        if (t == null) {//父容器不是接口则检查是否有保存下接口位置
            t = getListenerFromSavedLocation(listener);
        }
        return t;
    }

    public void show(FragmentManager fragmentManager) {
        Log.e("Wislie","tag:"+getTag());
//        show(fragmentManager, getTag());

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(this, getTag());
        ft.commitAllowingStateLoss();
    }
}

