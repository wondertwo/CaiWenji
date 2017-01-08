package me.wondertwo.caiwenji.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import me.wondertwo.caiwenji.R;
import me.wondertwo.caiwenji.listener.SelectListener;

/**
 * Created by wondertwo on 2016/12/24.
 */

public class MainAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<SourceFile> mDataSet;
    private SelectListener mListener;

    public MainAdapter(Context context, List<SourceFile> data, SelectListener listener) {
        this.mDataSet = data;
        this.mInflater = LayoutInflater.from(context);
        this.mListener = listener;
    }

    @Override
    public int getCount() {
        return mDataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MainViewHolder holder = null;
        if (convertView == null) {
            holder = new MainViewHolder();
            convertView = mInflater.inflate(R.layout.add_download_item, null);
            holder.mTextView = (TextView) convertView.findViewById(R.id.add_item_name);
            holder.mButton = (Button) convertView.findViewById(R.id.add_item_button);
            convertView.setTag(holder);
        } else {
            holder = (MainViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(mDataSet.get(position).name);
        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onSelected(position);
            }
        });

        return convertView;
    }

}
