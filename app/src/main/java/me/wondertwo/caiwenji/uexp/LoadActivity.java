package me.wondertwo.caiwenji.uexp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

import me.wondertwo.caiwenji.R;
import me.wondertwo.caiwenji.data.Constants;
import me.wondertwo.caiwenji.data.LoadAdapter;
import me.wondertwo.caiwenji.data.LoadViewHolder;
import me.wondertwo.caiwenji.data.SourceFile;
import me.wondertwo.caiwenji.listener.OperateListener;
import me.wondertwo.caiwenji.util.DownloadHelper;
import me.wondertwo.caiwenji.util.ThreadPoolMgr;

/**
 * Created by wondertwo on 2016/12/25.
 */

public class LoadActivity extends BaseActivity implements OperateListener {

    private final int MSG_UPDATE = 1; //更新进度字段
    private final int MSG_INIT = 0; //初始化进度字段
    private DecimalFormat format = new DecimalFormat("#0.0");

    private int mSelectedCount;
    private Button mButton;
    private LoadAdapter mAdapter;
    private List<SourceFile> mDataList;
    private ListView mListView;
    private boolean mIsFirstClick = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        Toolbar mToolBar = (Toolbar) findViewById(R.id.app_bar_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDataList = Constants.getList();
        mSelectedCount = Constants.getList().size();
        mButton = (Button) findViewById(R.id.load_button);
        mButton.setText("点击开始下载全部(" + mSelectedCount + ")");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadAll();
                mButton.setEnabled(false);
                mIsFirstClick = false;
            }
        });

        mListView = (ListView) findViewById(R.id.load_list_view);
        mAdapter = new LoadAdapter(LoadActivity.this, mDataList, LoadActivity.this);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOperation(View view, int position, LoadViewHolder holder) {
        switch (view.getId()) {
            case R.id.item_button_left: //开始下载（暂停，继续）
                downloadSingle(position, holder);
                break;
            case R.id.item_button_right: //删除任务
                deleteTask(position);
                break;
        }
    }

    //单个下载
    private synchronized void downloadSingle(int position, final LoadViewHolder holder) {
        final String sourceUrl = mDataList.get(position).url;
        final String fileName = mDataList.get(position).name;
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_INIT:
                        holder.mSeekBar.setMax(msg.arg1);
                        break;
                    case MSG_UPDATE:
                        holder.mSeekBar.setProgress(msg.arg2);
                        String textCenter = "" + decimal(msg.arg2) + "M/" + decimal(msg.arg1) + "M";
                        String textRight = "已完成" + percent(msg.arg1, msg.arg2) + "%";
                        holder.mTextViewCenter.setText(textCenter);
                        holder.mTextViewRight.setText(textRight);
                        break;
                }
            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                DownloadHelper helper = new DownloadHelper(handler, sourceUrl, fileName);
                helper.download();
            }
        };
        ThreadPoolMgr.getPool().execute(runnable);
    }

    //删除任务
    private void deleteTask(int position) {
        if (Constants.getList().get(position) != null) {
            Toast.makeText(LoadActivity.this,
                    "已成功删除 “" + Constants.getList().get(position).name + "” 下载任务~",
                    Toast.LENGTH_SHORT).show();
            Constants.getList().remove(position);
            mAdapter.notifyDataSetChanged();
            mSelectedCount--;
            mButton.setText("点击开始下载全部(" + mSelectedCount + ")");
        }
    }

    //下载全部
    private void downloadAll() {

    }

    private int percent(int total, int current) {
        return (int) (((float) current / total) * 100);
    }

    private String decimal(int value) {
        return format.format((double) value / (1024 * 1024));
    }

}

//    @Override
//    protected void onResume() {
//        super.onResume();
//        mAdapter.notifyDataSetChanged();
//        if (!mIsFirstEnter && !isCountChanged()) {
//            mButton.setEnabled(false);
//        }
//    }
//
//    private boolean isCountChanged() {
//        if (this.mSelectedCount != Constants.getList().size()) {
//            mSelectedCount = Constants.getList().size();
//            mButton.setText("点击开始下载全部(" + mSelectedCount + ")");
//            mButton.setEnabled(true);
//            return true;
//        } else {
//            return false;
//        }
//    }