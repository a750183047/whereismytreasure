package com.yan.whereismytreasure.UI.Adapter;

import android.content.Context;

import com.yan.whereismytreasure.Modle.Bean.ExpressInfo.Lists;
import com.yan.whereismytreasure.R;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.internal.SuperViewHolder;

import java.util.List;

/**
 * 快递信息列表Adapter
 * Created by a7501 on 2016/5/24.
 */
public class ExpressAdapter extends SuperAdapter<Lists>{
    public ExpressAdapter(Context context, List<Lists> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, Lists item) {
        holder.setText(R.id.text_time,item.getDatetime().substring(5,16));
        holder.setText(R.id.text_info,item.getRemark());

    }
}
