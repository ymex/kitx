package cn.ymex.kitx.widget.banner.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;


public class BannerPager extends ViewPager {
    private boolean canScroll = true;
    public static final int OFF_SCREEN_PAGE_LIMIT = 5;
    private boolean isVertical = false; //垂直

    private static final Interpolator sInterpolator = new DecelerateInterpolator();//减速，使用滑动更自然

    public BannerPager(Context context) {
        super(context);
        replaceViewPagerScroll();
    }

    public BannerPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        replaceViewPagerScroll();
    }


    private void replaceViewPagerScroll() {
        setOffscreenPageLimit(OFF_SCREEN_PAGE_LIMIT);
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            BannerScroller mScroller = new BannerScroller(getContext(), sInterpolator);
            mScroller.setDuration(mScroller.mDuration);
            field.set(this, mScroller);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.err.println(e.getLocalizedMessage());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            System.err.println(e.getLocalizedMessage());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isVertical) {
            return this.canScroll && super.onTouchEvent(swapEventCoordinate(ev));
        }
        return this.canScroll && super.onTouchEvent(ev);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isVertical) {
            getParent().requestDisallowInterceptTouchEvent(true);
            boolean intercepted = super.onInterceptTouchEvent(swapEventCoordinate(ev));
            swapEventCoordinate(ev);
            return this.canScroll && intercepted;
        }
        return this.canScroll && super.onInterceptTouchEvent(ev);
    }


    private MotionEvent swapEventCoordinate(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();
        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;
        ev.setLocation(newX, newY);
        return ev;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    public void setVertical(boolean vertical) {
        isVertical = vertical;
        if (isVertical) {
            setOverScrollMode(OVER_SCROLL_NEVER);
            setPageTransformer(true, new VerticalPageTransformer());
        } else {
            setPageTransformer(true, null);
        }
    }


    private class BannerScroller extends Scroller {
        private int mDuration = 520;


        public BannerScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        public void setDuration(int time) {
            mDuration = time;
        }
    }

    private class VerticalPageTransformer implements PageTransformer {

        @Override
        public void transformPage(View view, float position) {

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                view.setAlpha(1);

                // Counteract the default slide transition
                view.setTranslationX(view.getWidth() * -position);

                //set Y position to swipe in from top
                float yPosition = position * view.getHeight();
                view.setTranslationY(yPosition);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
