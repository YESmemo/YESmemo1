package com.yuandream.yesmemo.ui.recordings.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuandream.yesmemo.R;

import java.util.List;

public class RecordingsItenAdapter extends BaseAdapter {

    private List<RecordingsItenBean> mlist;
    private LayoutInflater mlayoutInflater;
    private Context mcontext;

    public RecordingsItenAdapter(Context context, List<RecordingsItenBean> list) {
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
            view = mlayoutInflater.inflate(R.layout.iten_recordings,viewGroup,false);
            TextView title = view.findViewById(R.id.item_recordings_title);
            viewHolder.title = title;
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        RecordingsItenBean itenBean = mlist.get(i);
        viewHolder.title.setText(itenBean.getTitle());
        return view;
    }

    class ViewHolder{
        // 标题
        TextView title;
    }
}
