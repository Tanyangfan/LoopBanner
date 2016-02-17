package com.mushi.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.mushi.loopbanner.R;

/**
 * 该类是一个示例，在initConfig中需要设置一些属性
 * 
 * @author mushi
 * @date 2016-02-17
 */
public class ExampleBannerView extends BannerView {

    public ExampleBannerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ExampleBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExampleBannerView(Context context) {
        super(context);
    }

    @Override
    protected void initConfig() {
        // 设置总页数，一般为list.size(),此项必须设置
        setPageNumber(3);
        // 设置启动页数，默认为0，不需要设置
        setStartItem(2);
        // 设置是否循环，默认不能循环拖动
        setLoopMode(BannerView.MODE_LOOP);
        // 设置是否需要小白点
        setNeedWhitePoint(true);
        // 设置小白点位置，默认在左边
        setIndicatorGravity(Gravity.CENTER);
        // 设置banner图片缩放方式，默认为FIT_XY
        setItemImageScaleType(ScaleType.CENTER_INSIDE);
    }

    @Override
    protected void setBannerImage(ImageView imageView, int index) { //设置图片资源
        int resId = 0;
        switch (index) {
        case 0:
            resId = R.drawable.banner_0;
            break;
        case 1:
            resId = R.drawable.banner_1;
            break;
        case 2:
            resId = R.drawable.banner_2;
            break;
        default:
            break;
        }
        imageView.setImageResource(resId);
    }

    @Override
    protected void bannerPagerSelected(int currentItem) {

    }

}
