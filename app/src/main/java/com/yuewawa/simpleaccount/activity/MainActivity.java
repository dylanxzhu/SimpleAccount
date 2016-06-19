package com.yuewawa.simpleaccount.activity;

import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuewawa.simpleaccount.R;
import com.yuewawa.simpleaccount.constant.Constants;
import com.yuewawa.simpleaccount.fragment.base.BaseFragment;

/**
 * MainActivity
 *
 * @author yuewawa 2016-06-07
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    private Toolbar toolbar;

    private TextView icDash, icRecords, icProfile;
    private TextView dashTxt, recordsTxt, profileTxt;
    private RelativeLayout dashRL, recordsRL, profileRL;
    private FragmentManager fm = null;
    private String tag = "";
    private BaseFragment baseFragment;
    public static String currentTag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initViews();
        initIconFont();

        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(Color.rgb(255, 255, 255));
        setSupportActionBar(toolbar);

        setViewListener();
        setDefaultTabMenu();

        baseFragment = new BaseFragment();
        fm = getFragmentManager();
        baseFragment.setDefaultFragment(R.id.main_fragment_layout, Constants.DASH_FRAGMENT, currentTag, fm);
    }

    @Override
    protected void onStop() {
        super.onStop();
        currentTag = "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initIconFont() {
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "iconfont/iconfont.ttf");
        icDash = (TextView) findViewById(R.id.ic_dash);
        icRecords = (TextView) findViewById(R.id.ic_records);
        icProfile = (TextView) findViewById(R.id.ic_profile);

        icDash.setTypeface(iconfont);
        icRecords.setTypeface(iconfont);
        icProfile.setTypeface(iconfont);
    }

    //初始化组件
    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        dashTxt = (TextView) findViewById(R.id.dash_txt);
        recordsTxt = (TextView) findViewById(R.id.records_txt);
        profileTxt = (TextView) findViewById(R.id.profile_txt);

        dashRL = (RelativeLayout) findViewById(R.id.dash_rl);
        recordsRL = (RelativeLayout) findViewById(R.id.records_rl);
        profileRL = (RelativeLayout) findViewById(R.id.profile_rl);
    }

    private void setDefaultTabMenu() {
        icDash.setSelected(true);
        dashTxt.setSelected(true);
        dashRL.setSelected(true);
    }

    private void resetTabMenu() {
        icDash.setSelected(false);
        icRecords.setSelected(false);
        icProfile.setSelected(false);

        dashTxt.setSelected(false);
        recordsTxt.setSelected(false);
        profileTxt.setSelected(false);

        dashRL.setSelected(false);
        recordsRL.setSelected(false);
        profileRL.setSelected(false);
    }

    private void setViewListener() {
        dashRL.setOnClickListener(this);
        recordsRL.setOnClickListener(this);
        profileRL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dash_rl:
                resetTabMenu();
                icDash.setSelected(true);
                dashTxt.setSelected(true);
                dashRL.setSelected(true);

                tag = Constants.DASH_FRAGMENT;
                baseFragment.changeFragment(R.id.main_fragment_layout, tag, currentTag, fm);
                break;
            case R.id.records_rl:
                resetTabMenu();
                icRecords.setSelected(true);
                recordsTxt.setSelected(true);
                recordsRL.setSelected(true);

                tag = Constants.RECORDS_FRAGMENT;
                baseFragment.changeFragment(R.id.main_fragment_layout, tag, currentTag, fm);
                break;
            case R.id.profile_rl:
                resetTabMenu();
                icProfile.setSelected(true);
                profileTxt.setSelected(true);
                profileRL.setSelected(true);

                tag = Constants.PROFILE_FRAGMENT;
                baseFragment.changeFragment(R.id.main_fragment_layout, tag, currentTag, fm);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
