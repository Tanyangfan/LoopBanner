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
如有3页需要显示的广告，则在ViewPager
