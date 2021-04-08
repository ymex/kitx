package cn.ymex.kitx.widget.state;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.collection.ArrayMap;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.Map;

import cn.ymex.kitx.widget.R;

/**
 * Created by ymex on 2019/10/7.
 * About:
 */
public class StateConstraintLayout extends ConstraintLayout implements StateLayout {
    private int holdViewId = -1;
    private Map<Integer, View> holdViews = new ArrayMap<>();
    private FrameLayout stateLayout;
    private int currentLayoutId = StateLayout.CONTENT_VIEW_LAYOUT;


    public StateConstraintLayout(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public StateConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public StateConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }


    public static StateConstraintLayout wrap(View view) {
        StateConstraintLayout layout = new StateConstraintLayout(view.getContext());
        ViewGroup parent = (ViewGroup) view.getParent();
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        int index = parent.indexOfChild(view);
        parent.removeView(view);
        parent.addView(layout, index, lp);
        layout.addView(view);
        return layout;
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StateConstraintLayout, defStyleAttr, 0);
        holdViewId = typedArray.getResourceId(R.styleable.StateConstraintLayout_state_layout_placeholder_view, holdViewId);
        int stateLayoutBackgroundColor = typedArray.getColor(R.styleable.StateConstraintLayout_state_layout_background_color, Color.WHITE);
        boolean clickable = typedArray.getBoolean(R.styleable.StateConstraintLayout_state_layout_clickable, true);
        typedArray.recycle();

        if (stateLayout == null) {
            stateLayout = new FrameLayout(context, attrs, defStyleAttr);
            stateLayout.setBackgroundColor(stateLayoutBackgroundColor);
            stateLayout.setClickable(clickable);
        }
        LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.topToTop = ConstraintSet.PARENT_ID;
        layoutParams.bottomToBottom = ConstraintSet.PARENT_ID;
        layoutParams.startToStart = ConstraintSet.PARENT_ID;
        layoutParams.endToEnd = ConstraintSet.PARENT_ID;
        addView(stateLayout, layoutParams);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (holdViewId != -1) {
            firstInflaterLayout(holdViewId);
            stateLayout.setVisibility(VISIBLE);
            setCurrentLayoutId(holdViewId);
        } else {
            stateLayout.setVisibility(GONE);
            setCurrentLayoutId(StateLayout.CONTENT_VIEW_LAYOUT);
        }
        bringStateLayoutToFront();
    }


    /**
     * 置顶状态布局
     */
    private void bringStateLayoutToFront() {
        if (getChildCount() - 1 != indexOfChild(stateLayout)) {
            stateLayout.bringToFront();
        }
    }

    /**
     * 切换到指定布局
     *
     * @param layout
     */
    @Override
    public void showView(@LayoutRes int layout) {
        showView(layout, null);
    }


    /**
     * 切换到指定布局
     *
     * @param layout
     * @param callBack
     */
    @Override
    public void showView(@LayoutRes final int layout, final StateLayout.Callback callBack) {
        bringStateLayoutToFront();
        stateLayout.removeAllViews();
        stateLayout.setVisibility(VISIBLE);

        View view = null;
        if (holdViews.containsKey(layout)) {
            view = holdViews.get(layout);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            stateLayout.addView(view,layoutParams);
        }

        if (stateLayout.getChildCount() <= 0) {
            view = firstInflaterLayout(layout);
        }


        if (callBack != null) {
            callBack.onCreatedView(view);
        }
        setCurrentLayoutId(layout);
    }


    private View firstInflaterLayout(@LayoutRes int layout) {
        View view = LayoutInflater.from(getContext()).inflate(layout, null);
        holdViews.put(layout, view);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        stateLayout.addView(view,layoutParams);

        Map<Integer, Callback> callbackMap = StateLayout.Config.instanse().getConfig();

        if (callbackMap.containsKey(layout)) {
            StateLayout.Callback back = callbackMap.get(layout);
            if (back != null) {
                back.onCreatedView(view);
            }
        }
        return view;
    }

    @Override
    public void showContentView() {
        stateLayout.setVisibility(GONE);
        setCurrentLayoutId(StateLayout.CONTENT_VIEW_LAYOUT);
    }


    private void setCurrentLayoutId(int layout) {
        currentLayoutId = layout;
    }

    /**
     * 获取当前状态布局
     * 当布局内容为content view 时 layout id  是 StateLayout.CONTENT_VIEW_LAYOUT。此id固定。
     *
     * @return
     */
    @Override
    public int getCurrentLayoutId() {
        return currentLayoutId;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        holdViews.clear();
    }

    @Override
    public FrameLayout getStateLayout() {
        return stateLayout;
    }
}
