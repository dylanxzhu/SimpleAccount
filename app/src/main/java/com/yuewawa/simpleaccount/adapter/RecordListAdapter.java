package com.yuewawa.simpleaccount.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuewawa.simpleaccount.R;
import com.yuewawa.simpleaccount.entity.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuewawa on 2016-06-16.
 */
public class RecordListAdapter extends BaseAdapter{

    private static final String TYPE_IN = "收入";
    private static final String TYPE_OUT = "支出";

    private List<Record> list;
    private LayoutInflater inflater;
    private Context context;

    public RecordListAdapter(List<Record> list, Context context){
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        if (list!=null  && list.size()>0){
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolder holder = null;
        if (convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.records_list_item, parent, false);
            holder.nameTxt = (TextView) convertView.findViewById(R.id.name_txt);
            holder.typeTxt = (TextView) convertView.findViewById(R.id.type_txt);
            holder.amountTxt = (TextView) convertView.findViewById(R.id.amount_txt);
            holder.dateTxt = (TextView) convertView.findViewById(R.id.date_txt);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        Record record = list.get(position);
        holder.setTypeFace();
        switch (type){
            case 0:
                holder.typeTxt.setTextColor(Color.rgb(85,107,47));
                holder.typeTxt.setText(R.string.ic_type_in);
                break;
            case 1:
                holder.typeTxt.setTextColor(Color.rgb(139,0,0));
                holder.typeTxt.setText(R.string.ic_type_out);
                break;
        }
        holder.nameTxt.setText(record.getName());
        holder.amountTxt.setText("￥"+String.valueOf(record.getAmount()));
        holder.dateTxt.setText(record.getDate());
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if (list!=null && list.size()>0) {
            if (list.get(position).getType().equals(TYPE_IN)) {
                return 0;
            } else if (list.get(position).getType().equals(TYPE_OUT)) {
                return 1;
            }
        }
        return super.getItemViewType(position);
    }

    private class ViewHolder {
        private TextView nameTxt, typeTxt, amountTxt, dateTxt;

        private void setTypeFace(){
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "iconfont/iconfont.ttf");
            typeTxt.setTypeface(typeface);
        }
    }

    public void add(Record record){
        if (list==null){
            list = new ArrayList<>();
        }
        list.add(record);
        notifyDataSetChanged();
    }
}
