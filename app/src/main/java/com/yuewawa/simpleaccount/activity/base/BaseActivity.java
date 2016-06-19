package com.yuewawa.simpleaccount.activity.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.TextView;

import com.yuewawa.simpleaccount.R;


/**
 * Created by yuewawa on 2016-06-08.
 */
public class BaseActivity extends Activity implements View.OnClickListener{

    protected TextView icBack, titleTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initHeadBar(@StringRes int resId){
        Typeface tf = Typeface.createFromAsset(getAssets(), "iconfont/iconfont.ttf");
        icBack = (TextView) findViewById(R.id.ic_back);
        icBack.setTypeface(tf);
        icBack.setOnClickListener(this);

        titleTxt = (TextView) findViewById(R.id.title_txt);
        titleTxt.setText(resId);
    }

    protected void goBack(Context context, Class clazz){
        Intent intent = new Intent(context, clazz);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {

    }
}
