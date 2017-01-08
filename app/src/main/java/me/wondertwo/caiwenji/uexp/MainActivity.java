package me.wondertwo.caiwenji.uexp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.wondertwo.caiwenji.R;
import me.wondertwo.caiwenji.data.MainAdapter;
import me.wondertwo.caiwenji.data.Constants;
import me.wondertwo.caiwenji.data.SourceFile;
import me.wondertwo.caiwenji.listener.SelectListener;

public class MainActivity extends BaseActivity implements SelectListener {

    private ListView mListView;
    private List<SourceFile> mDataList;
    private MainAdapter mAdapter;
    private Button mButton;
    private int mSelectedCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.main_list_view);
        mAdapter = null;
        if (Constants.sourceUrl.length == Constants.fileName.length) {
            mDataList = new ArrayList<>(Constants.sourceUrl.length);
            for (int i = 0; i < Constants.sourceUrl.length; i++) {
                SourceFile sourceFile = new SourceFile();
                sourceFile.url = Constants.sourceUrl[i];
                sourceFile.name = Constants.fileName[i];
                mDataList.add(sourceFile);
            }
            mAdapter = new MainAdapter(MainActivity.this, mDataList, MainActivity.this);
        }
        mListView.setAdapter(mAdapter);

        mSelectedCount = 0;
        mButton = (Button) findViewById(R.id.download_list);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoadActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onPostResume();
        mSelectedCount = Constants.getList().size();
        mButton.setText("点击打开下载列表(" + mSelectedCount + ")");
    }

    @Override
    public void onSelected(int position) {
        if (!Constants.getList().contains(mDataList.get(position))) {
            Constants.getList().add(mDataList.get(position));
            Toast.makeText(MainActivity.this,
                    "成功添加 “" + mDataList.get(position).name + "” 下载任务~",
                    Toast.LENGTH_SHORT).show();
            mSelectedCount++;
            mButton.setText("点击打开下载列表(" + mSelectedCount + ")");
        } else {
            Toast.makeText(MainActivity.this, "不要重复添加哦~", Toast.LENGTH_SHORT).show();
        }
    }
}
