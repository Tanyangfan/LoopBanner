package com.mushi.banner;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mushi.loopbanner.R;
import com.mushi.util.UiUtils;

/**
 * banner控件，需要继承该类，具体看{@link ExampleBannerView}
 * 
 * @author mushi
 * @date 2016-02-17
 */
public abstract class BannerView extends RelativeLayout implements OnPageChangeListener {

    private BannerViewPager viewPager;
    private BannerPagerAdapter pagerAdapter;
    private LinearLayout indicatorContainer;

    private int indicatorGravity = Gravity.RIGHT;
    private ScaleType itemScaleType = ScaleType.FIT_XY;

    // 当前显示的位置
    private int currentItem = 0;
    private int preItem = 0;
    private int realItem = 0;
    private int startItem = 0;

    // 存储banner每一页的view
    private ArrayList<View> viewList = new ArrayList<View>();

    // 设置模式为循环滚动还是正常滑动
    private int mode = 0;
    protected final static int MODE_NORMAL = 0;
    protected final static int MODE_LOOP = 1;

    // banner的总页数
    private int pageNumber = 0;

    // 是否需要小点
    private boolean isNeedWhitePoint = true;

    public BannerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BannerView(Context context) {
        super(context);
        init(context);
    }

    private void init(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_view_layout, this);

        // 初始化配置
        initConfig();

        // 初始化Banner每一个控件
        initBanneritems();

        viewPager = (BannerViewPager) view.findViewById(R.id.id_vp_banner);
        viewPager.setOnPageChangeListener(this);
        pagerAdapter = new BannerPagerAdapter();
        viewPager.setAdapter(pagerAdapter);

        if (isNeedWhitePoint) {
            indicatorContainer = (LinearLayout) view.findViewById(R.id.id_layout_bottom_point_container);
            initIndicatorContainer();
        }

        setCurrentItem(startItem);
    }

    /**
     * 初始化banner内容
     */
    private void initBanneritems() {
        viewList.clear();
        if (mode == MODE_LOOP && pageNumber > 1) {
            viewList.add(initPagerItem(pageNumber - 1));
        }
        for (int i = 0; i < pageNumber; i++) {
            viewList.add(initPagerItem(i));
        }
        if (mode == MODE_LOOP && pageNumber > 1) {
            viewList.add(initPagerItem(0));
        }
    }

    /**
     * 初始化每一页内容
     * 
     * @param index
     * @return
     */
    private View initPagerItem(int index) {

        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(lp);
        ImageView.ScaleType localScaleType = itemScaleType;
        imageView.setScaleType(localScaleType);
        setBannerImage(imageView, index);

        return imageView;
    }

    /**
     * 初始化指示器容器
     */
    private void initIndicatorContainer() {

        // 重置Item
        currentItem = 0;
        preItem = 0;
        indicatorContainer.removeAllViews();
        indicatorContainer.setGravity(indicatorGravity);

        Context context = getContext();

        for (int i = 0; i < pageNumber; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setId(i);
            ImageView.ScaleType scaleType = ImageView.ScaleType.FIT_XY;
            imageView.setScaleType(scaleType);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(UiUtils.dpToPixel(context, 20), UiUtils.dpToPixel(context, 20));
            imageView.setLayoutParams(lp);
            imageView.setPadding(UiUtils.dpToPixel(context, 6), UiUtils.dpToPixel(context, 6), UiUtils.dpToPixel(context, 6), UiUtils.dpToPixel(context, 6));
            if (i == currentItem) {
                imageView.setImageResource(R.drawable.ic_focus_select);
            } else {
                imageView.setImageResource(R.drawable.ic_focus);
            }

            indicatorContainer.addView(imageView);
        }
    }
    
    /**
     * 刷新banner
     * @param pageNumber
     */
    public void refreshBanner(int pageNumber) {
        this.pageNumber = pageNumber;
        
        initBanneritems();
        
        if (isNeedWhitePoint) {
            initIndicatorContainer();
        }
        
        pagerAdapter.notifyDataSetChanged();
        
        setCurrentItem(0);
    }

    /**
     * 计算现在的Item
     * 
     * @param realItem
     * @return
     */
    private int calculateCurrentItem(int realItem) {
        int currentItem = realItem;

        if (mode == MODE_LOOP && pageNumber > 1) {
            currentItem = (realItem - 1) % pageNumber;

            if (currentItem == -1) {
                currentItem = pageNumber - 1;
            }
        }

        return currentItem;
    }

    /**
     * 设置总共有几页
     * 
     * @param pageAccount
     */
    protected void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * 设置启动项
     * 
     * @param startItem
     */
    protected void setStartItem(int startItem) {
        this.startItem = startItem;
    }

    /**
     * 设置当前是第几页，0开始
     * 
     * @param currentItem
     */
    protected void setCurrentItem(int currentItem) {
        if (pageNumber != 0 && currentItem < pageNumber) {
            this.currentItem = currentItem;
        }

        if (mode == MODE_LOOP && pageNumber > 1) {
            viewPager.setCurrentItem(currentItem + 1);
        } else {
            viewPager.setCurrentItem(currentItem);
        }
    }

    /**
     * 设置循环模式
     * 
     * @param mode
     */
    protected void setLoopMode(int mode) {
        this.mode = mode;
    }

    /**
     * 设置是否需要在下面显示第几页的点
     * 
     * @param isNeed
     */
    protected void setNeedWhitePoint(boolean isNeed) {
        this.isNeedWhitePoint = isNeed;
    }

    /**
     * 设置小白点位置，默认在右边
     * 
     * @param gravity
     */
    protected void setIndicatorGravity(int gravity) {
        this.indicatorGravity = gravity;
    }

    /**
     * 设置banner图片缩放方式
     * 
     * @param itemScaleType
     */
    protected void setItemImageScaleType(ScaleType itemScaleType) {
        this.itemScaleType = itemScaleType;
    }

    public void setOnBannerClickListener(final OnBannerClickListener listener) {
        if (listener != null) {
            viewPager.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void onBannerClick(int position) {
                    listener.onBannerClick(calculateCurrentItem(position));
                }
            });
        }
    }

    public void setAutoPlaySupport(boolean autoPlaySupport) {
        viewPager.setAutoPlaySupport(autoPlaySupport);
    }

    public void startAutoPlay() {
        viewPager.startAutoPlay();
    }

    public void stopAutoPlay() {
        viewPager.stopAutoPlay();
    }

    /**
     * 初始化banner配置 会在初始化viewPager之前调用，如，是否循环播放，banner页面数目等
     */
    protected abstract void initConfig();

    /**
     * 设置图片
     * 
     * @param imageView
     * @param index
     */
    protected abstract void setBannerImage(ImageView imageView, int index);

    /**
     * 选中banner
     * 
     * @param currentItem
     */
    protected abstract void bannerPagerSelected(int currentItem);

    /**
     * 点击事件回调接口
     */
    public interface OnBannerClickListener {
        void onBannerClick(int position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (ViewPager.SCROLL_STATE_IDLE == state) {
            if (mode == MODE_LOOP && pageNumber > 1) {
                if (realItem == viewList.size() - 1) {
                    viewPager.setCurrentItem(1, false);
                } else if (realItem == 0) {
                    viewPager.setCurrentItem(viewList.size() - 2, false);
                }
            }
        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int index) {

        realItem = index;

        currentItem = calculateCurrentItem(index);

        if (indicatorContainer != null) {
            // 修改前一项背景
            ImageView preSelImg = (ImageView) indicatorContainer.findViewById(preItem);
            if (preSelImg != null) {
                preSelImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_focus));
            }

            // 修改当前选中项的背景
            ImageView curSelImg = (ImageView) indicatorContainer.findViewById(currentItem);
            if (curSelImg != null) {
                curSelImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_focus_select));
            }
        }

        preItem = currentItem;

        bannerPagerSelected(currentItem);
    }

    class BannerPagerAdapter extends PagerAdapter {

        public BannerPagerAdapter() {

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            final View view = viewList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            object = null;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
        }

    }
}
