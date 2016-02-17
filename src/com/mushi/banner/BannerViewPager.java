package com.mushi.banner;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.mushi.banner.BannerView.OnBannerClickListener;

public class BannerViewPager extends ViewPager {

    private float mLastMotionX;
    private int mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    private boolean autoPlaySupport = false;
    private boolean hasStart = false;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (getCurrentItem() < getAdapter().getCount() - 1) {
                setCurrentItem(getCurrentItem() + 1);
            } else {
                setCurrentItem(0);
            }
            if (autoPlaySupport) {
                handler.postDelayed(this, 4000L);
            }
        }
    };

    private OnBannerClickListener mListener;

    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAutoPlay();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        stopAutoPlay();
        
        switch (ev.getActionMasked()) {
        case MotionEvent.ACTION_DOWN:
            mLastMotionX = ev.getX();
            break;
        case MotionEvent.ACTION_UP:
            if (!determineDrag(ev)) {
                if (mListener != null) {
                    mListener.onBannerClick(getCurrentItem());
                }
            }
            startAutoPlay();
            break;
        case MotionEvent.ACTION_MOVE:
            break;
        default:
            break;
        }

        return super.onTouchEvent(ev);
    }

    private boolean determineDrag(MotionEvent ev) {
        final float x = ev.getX();
        final float dx = mLastMotionX - x;
        final float xDiff = Math.abs(dx);
        if (xDiff > mTouchSlop / 2) {
            mLastMotionX = x;
            return true;
        }
        return false;
    }

    @Override
    protected void onPageScrolled(int position, float arg1, int arg2) {
        super.onPageScrolled(position, arg1, arg2);
    }

    public void setOnBannerClickListener(OnBannerClickListener listener) {
        mListener = listener;
    }

    public void setAutoPlaySupport(boolean autoPlaySupport) {
        this.autoPlaySupport = autoPlaySupport;
    }

    public void startAutoPlay() {
        if (autoPlaySupport && !hasStart) {
            handler.postDelayed(runnable, 4000L);
            hasStart = true;
        }
    }

    public void stopAutoPlay() {
        handler.removeCallbacks(runnable);
        hasStart = false;
    }

}
