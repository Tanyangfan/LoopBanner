# LoopBanner
A banner widget, which can atuo scroll and loop slide.<br/>
一个支持自动播放和循环滚动的banner控件。<br/>

### 实现功能<br/>

1.  自动播放功能<br/>
2.  循环滚动功能<br/>
3.  更新banner<br/>
4.  支持和ListView嵌套使用<br/>

### 控件原理<br/>

使用ViewPager进行banner的显示

#### 循环滚动原理<br/>

在真实需要显示的banner前后各增加一个页<br/>
如有3页需要显示的广告，则在ViewPager创建5个页面，page1,page2,page3显示对应的广告页，page0显示page3的页面，page4显示page1的页面。<br/>
当滚动到page4时，通过ViewPager.setCurrentItem(1,false)切换到page1(对page0的处理类似),达到循环滚动的目的。<br/>

### 使用方法

####  继承 BannerView

使用前需构造自己的控件类，该类继承自BannerView，实现BannerView中的抽象方法<br/>
```java
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
 * 选中banner时调用
 * 
 * @param currentItem
 */
protected abstract void bannerPagerSelected(int currentItem);
```

#####  初始化

在initConfig()中进行BannerView的初始化设定，其中setPageNumber(int)必须进行设置，其余根据需要配置<br/>
```java
@Override
protected void initConfig() {
    // 设置总页数，一般为list.size(),此项必须设置，如未设置，请在需要的地方调用refreshBanner
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
```

##### 设置Banner图片

```java
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
```

####  使用你实现的ExampleBannerView

##### 布局文件
```xml
<com.mushi.banner.ExampleBannerView
    android:id="@+id/id_banner"
    android:layout_width="match_parent"
    android:layout_height="300dp" >
</com.mushi.banner.ExampleBannerView>
```

##### 设置自动播放
```java
// 设置自动滚动
bannerView.setAutoPlaySupport(true);
bannerView.startAutoPlay();
```

##### 设置banner的点击事件
```java
// 设置点击响应事件
bannerView.setOnBannerClickListener(new BannerView.OnBannerClickListener() {
    @Override
    public void onBannerClick(int position) {
        System.out.println("You click, position = " + position);
    }
});
```

##### 刷新banner
```java
// 在数据更新后刷新banner，必须在初始化后面使用
bannerView.refreshBanner(pageNumebr);
```

### License<br/>
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
