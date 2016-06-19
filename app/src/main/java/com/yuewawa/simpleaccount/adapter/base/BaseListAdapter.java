package com.yuewawa.simpleaccount.adapter.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.annotation.StringRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuewawa on 2016-06-15.
 */
public abstract class BaseListAdapter<T> extends BaseAdapter{

    private List<T> list;
    private int layoutId;

    public BaseListAdapter(List<T> list, int layoutId){
        this.list = list;
        this.layoutId = layoutId;
    }
    @Override
    public int getCount() {
        if (list!=null && list.size()>0){
            return list.size();
        }
        return 0;
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.bind(parent.getContext(), convertView, parent, layoutId, position);
        bindView(holder, getItem(position));
        return holder.getItemView();
    }

    public abstract void bindView(ViewHolder holder, T obj);

    public void add(T t){
        if (list==null){
            list = new ArrayList<T>();
        }
        list.add(t);
        notifyDataSetChanged();
    }

    public void add(int position, T t){
        if (list==null){
            list = new ArrayList<T>();
        }
        list.add(position, t);
        notifyDataSetChanged();
    }

    public void remove(T t){
        if (list!=null){
            list.remove(t);
        }
        notifyDataSetChanged();
    }

    public void remove(int position){
        if (list!=null){
            list.remove(position);
        }
        notifyDataSetChanged();
    }

    public void clear(){
        if (list!=null){
            list.clear();
        }
        notifyDataSetChanged();
    }

    public void refresh(){
        notifyDataSetChanged();
    }

    public static class ViewHolder{
        private SparseArray<View> views;
        private View item;
        private int position;
        private Context context;

        private ViewHolder(Context context, ViewGroup parent, int layoutId){
            views = new SparseArray<View>();
            this.context = context;
            View convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            convertView.setTag(this);
            item = convertView;
        }

        public static ViewHolder bind(Context context, View convertView, ViewGroup parent, int layoutId, int position){
            ViewHolder holder;
            if (convertView==null){
                holder = new ViewHolder(context, parent, layoutId);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
                holder.item = convertView;
            }
            holder.position = position;
            return holder;
        }

        public <T extends View> T getView(int id){
            T t = (T) views.get(id);
            if (t==null){
                t = (T) item.findViewById(id);
                views.put(id, t);
            }
            return t;
        }

        public View getItemView(){
            return item;
        }

        public int getPosition(){
            return position;
        }

        public ViewHolder setText(int id, CharSequence text){
            View view = getView(id);
            if (view instanceof TextView){
                ((TextView) view).setText(text);
            }
            return this;
        }

        public ViewHolder setImage(int id, Bitmap bitmap){
            View view = getView(id);
            if (view instanceof ImageView){
                ((ImageView) view).setImageBitmap(bitmap);
            }
            return this;
        }

        public ViewHolder setOnClickListener(int id, View.OnClickListener listener){
            getView(id).setOnClickListener(listener);
            return this;
        }

        public ViewHolder setVisibility(int id, int visible){
            getView(id).setVisibility(visible);
            return this;
        }

        public ViewHolder setTag(int id, Object obj){
            getView(id).setTag(obj);
            return this;
        }

        public ViewHolder setTextColor(int id, int color){
            View view = getView(id);
            if (view instanceof TextView){
                ((TextView)view).setTextColor(color);
            }
            return this;
        }

        public ViewHolder setText(int id, @StringRes int redId){
            View view = getView(id);
            if (view instanceof TextView){
                ((TextView) view).setText(redId);
            }
            return this;
        }

        public ViewHolder setTypeFace(int id, Typeface typeface){
            View view = getView(id);
            if (view instanceof TextView){
                ((TextView)view).setTypeface(typeface);
            }
            return this;
        }
    }
}
