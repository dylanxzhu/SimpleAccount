package com.yuewawa.simpleaccount.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.yuewawa.simpleaccount.R;
import com.yuewawa.simpleaccount.activity.MainActivity;
import com.yuewawa.simpleaccount.adapter.DashPagerAdapter;
import com.yuewawa.simpleaccount.constant.Constants;
import com.yuewawa.simpleaccount.fragment.base.BaseFragment;
import com.yuewawa.simpleaccount.service.RecordService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuewawa on 2016-06-07.
 */
public class DashFrament extends BaseFragment {

    private View view;
    private ViewPager viewPager;
    private DashPagerAdapter dashPagerAdapter;
    private List<View> list;
    private LayoutInflater pagerInflater;
    private Context context;
    private WebView webViewOne;
    private View viewOne, viewTwo, viewThree;
    private View[] stripView;
    private LinearLayout stripLayout;
    private MyPageChangeListener pageChangeListener;
    private RecordService recordService;
    private ProgressDialog progressDialog;
    private Handler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dash, container, false);
        initViews();
        list = new ArrayList<>();
        context = getActivity();
        pagerInflater = LayoutInflater.from(context);
        pageChangeListener = new MyPageChangeListener();
        recordService = new RecordService(context);

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("页面加载中...");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        viewOne = pagerInflater.inflate(R.layout.web_view_one, null, false);
        webViewOne = (WebView) viewOne.findViewById(R.id.web_view_1);
        WebSettings settings = webViewOne.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);//设定支持viewport
        settings.setLoadWithOverviewMode(true);   //自适应屏幕
        settings.setDefaultTextEncodingName("UTF-8");

        webViewOne.loadUrl("file:///android_asset/www/web_view_one.html");
        webViewOne.addJavascriptInterface(recordService, "recordService");

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0x001:
                        progressDialog.dismiss();
                        break;
                }
            }
        };
        Message msg = new Message();
        msg.what = 0x001;
        mHandler.sendMessageDelayed(msg, 3000);

        viewTwo = pagerInflater.inflate(R.layout.web_view_two, null, false);
        viewThree = pagerInflater.inflate(R.layout.web_view_three, null, false);

        list.add(viewOne);
        list.add(viewTwo);
        list.add(viewThree);

        dashPagerAdapter = new DashPagerAdapter(list);
        viewPager.setAdapter(dashPagerAdapter);
        viewPager.addOnPageChangeListener(pageChangeListener);

        if (list!=null && list.size()>0){
            stripView = new View[list.size()];
            for (int i=0; i<stripView.length; i++){
                stripView[i] = new View(context);
                stripView[i].setLayoutParams(new LinearLayout.LayoutParams(0, 18, 1.0f));

                if (i==0){
                    stripView[i].setBackgroundColor(Color.rgb(139,0,0));
                }
                else {
                    stripView[i].setBackgroundColor(Color.rgb(70,130,180));
                }
                stripLayout.addView(stripView[i]);
            }
        }

        return view;
    }

    private void initViews(){
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        stripLayout = (LinearLayout) view.findViewById(R.id.strip_layout);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.currentTag = Constants.DASH_FRAGMENT;
    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int stripPosition = 0;
            if (position>stripView.length-1){
                stripPosition = position%stripView.length;
            }
            else {
                stripPosition = position;
            }
            for (int i=0; i<stripView.length; i++){
                if (i==stripPosition){
                    stripView[stripPosition].setBackgroundColor(Color.rgb(139,0,0));
                }
                else {
                    stripView[i].setBackgroundColor(Color.rgb(70,130,180));
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
