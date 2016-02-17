package com.mushi.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mushi.banner.BannerView;
import com.mushi.banner.ExampleBannerView;
import com.mushi.loopbanner.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final ExampleBannerView bannerView = (ExampleBannerView) findViewById(R.id.id_banner);
        
        // 设置自动滚动
        bannerView.setAutoPlaySupport(true);
        bannerView.startAutoPlay();
        
        // 设置点击响应事件
        bannerView.setOnBannerClickListener(new BannerView.OnBannerClickListener() {
            @Override
            public void onBannerClick(int position) {
                System.out.println("You click, position = " + position);
            }
        });
        
        final EditText bannerNumberEdt = (EditText) findViewById(R.id.id_edt_banner_number);
        Button refreshBannerBtn = (Button) findViewById(R.id.id_btn_refresh_banner);
        refreshBannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pageNumebr = Integer.valueOf(bannerNumberEdt.getText().toString());
                if (pageNumebr > 0) {
                    // 刷新banner，必须在初始化后面使用
                    bannerView.refreshBanner(pageNumebr);
                }
            }
        });
    }

}
