package lilin.com.myapplication;

import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ProgressDialogFragment extends BaseDialogFragment {

    private ImageView mLoadingImage;
    private TextView mContentText;
    private AnimationDrawable animationDrawable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.LoadingDialog);
        //初始化数据
//        mCircleAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_round_rotate);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_progress, container, false);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(  //设置DialogFragment的宽高
//                DensityUtil.dip2px(getActivity(),156),DensityUtil.dip2px(getActivity(),123));
//        view.setLayoutParams(params);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoadingImage = view.findViewById(R.id.progress_loading);
        mContentText = view.findViewById(R.id.progress_content);
        animationDrawable = (AnimationDrawable) mLoadingImage.getDrawable();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        //清理缓存
        startAnim();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAnim();
    }

    //开始动画
    private void startAnim() {
        if (animationDrawable != null) {
            animationDrawable.start();
        }
    }

    //停止动画
    private void stopAnim() {
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
    }

}
