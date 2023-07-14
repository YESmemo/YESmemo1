package com.yuandream.yesmemo.ui.plans.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.yuandream.yesmemo.R;

import java.util.List;

public class PlansItenAdapter extends BaseAdapter {

    private List<PalnsItenBean> mlist;
    private LayoutInflater mlayoutInflater;
    private Context mcontext;

    public PlansItenAdapter(Context context, List<PalnsItenBean> list) {
        this.mcontext = context;
        this.mlist = list;
        this.mlayoutInflater = LayoutInflater.from(mcontext);
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int i) {
        return mlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder();
        if (view == null) {
            view = mlayoutInflater.inflate(R.layout.iten_plans,viewGroup,false);
            TextView title = view.findViewById(R.id.item_plans_title);
            viewHolder.title = title;
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        PalnsItenBean itenBean = mlist.get(i);
        viewHolder.title.setText(itenBean.getTitle());
        return view;
    }

    class ViewHolder{
        // 标题
        TextView title;
    }
}
