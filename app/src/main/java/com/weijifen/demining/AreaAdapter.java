package com.weijifen.demining;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dell on 2017/8/18.
 */

public class AreaAdapter extends CommonAdapter {
    Context context;
    List<Area> mDatas;

    public AreaAdapter(Context context, List mDatas, int mLayoutId) {
        super(context, mDatas, mLayoutId);
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public void convert(ViewHolder helper, Object item) {
        TextView textView = helper.getView(R.id.adapter_text);
//        HomeSearchUtil bean = (HomeSearchUtil) item;
        Area bean = (Area) item;

        if(bean.isBomb())
        {
            if (bean.isSweep()) {
                textView.setBackgroundResource(R.drawable.landmine);
            } else {
                if (bean.isFlag()) {
                    textView.setBackgroundResource(R.drawable.flag);
                } else {
                    textView.setBackgroundResource(R.drawable.box);
                }

            }

        }else {
//            textView.setBackgroundResource(R.drawable.box);
            if(bean.isSweep()){
                textView.setBackgroundResource(R.drawable.box_null);
            }else {
                if (bean.isFlag()) {
                    textView.setBackgroundResource(R.drawable.flag);
                } else {
                    textView.setBackgroundResource(R.drawable.box);
                }
            }
            if (bean.getNumber() > 0) {
                textView.setText(bean.getNumber()+"");
            }
        }

    }
}
