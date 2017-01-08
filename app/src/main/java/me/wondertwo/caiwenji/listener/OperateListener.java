package me.wondertwo.caiwenji.listener;

import android.view.View;

import me.wondertwo.caiwenji.data.LoadViewHolder;

/**
 * Created by wondertwo on 2016/12/25.
 */

public interface OperateListener {
    void onOperation(View view, int position, LoadViewHolder holder);
}
