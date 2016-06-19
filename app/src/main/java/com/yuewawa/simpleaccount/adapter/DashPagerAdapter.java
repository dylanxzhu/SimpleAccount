package com.yuewawa.simpleaccount.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by yuewawa on 2016-06-18.
 */
public class DashPagerAdapter extends PagerAdapter{

    private List<View> list;

    public DashPagerAdapter(List<View> list){
        this.list = list;
    }
    @Override
    public int getCount() {
        if (list!=null && list.size()>0){
            return list.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

}
