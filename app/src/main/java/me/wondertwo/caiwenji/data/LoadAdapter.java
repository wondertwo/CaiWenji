package me.wondertwo.caiwenji.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import me.wondertwo.caiwenji.R;
import me.wondertwo.caiwenji.listener.OperateListener;

/**
 * Created by wondertwo on 2016/12/24.
 */

public class LoadAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<SourceFile> mDataSet;
    private OperateListener mListener;

    public LoadAdapter(Context context, List<SourceFile> data, OperateListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.mDataSet = data;
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
        LoadViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new LoadViewHolder();
            convertView = mInflater.inflate(R.layout.download_list_item, null);

            viewHolder.mTextViewLeft = (TextView) convertView.findViewById(R.id.item_text_left);
            viewHolder.mTextViewCenter = (TextView) convertView.findViewById(R.id.item_text_center);
            viewHolder.mTextViewRight = (TextView) convertView.findViewById(R.id.item_text_right);
            viewHolder.mSeekBar = (SeekBar) convertView.findViewById(R.id.item_progress);
            viewHolder.mButtonLeft = (Button) convertView.findViewById(R.id.item_button_left);
            viewHolder.mButtonRight = (Button) convertView.findViewById(R.id.item_button_right);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (LoadViewHolder) convertView.getTag();
        }
        final LoadViewHolder holder = viewHolder;

        //--1--
        holder.mTextViewLeft.setText(mDataSet.get(position).name);

        //--2--
        holder.mButtonLeft.setText("开始下载");
        holder.mButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onOperation(view, position, holder);
            }
        });

        //--3--
        holder.mButtonRight.setText("删除任务");
        holder.mButtonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onOperation(view, position, holder);
            }
        });

        return convertView;
    }
}
