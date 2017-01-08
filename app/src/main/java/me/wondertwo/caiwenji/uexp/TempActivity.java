package me.wondertwo.caiwenji.uexp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;

import me.wondertwo.caiwenji.R;
import me.wondertwo.caiwenji.util.DownloadHelper;
import me.wondertwo.caiwenji.util.ThreadPoolMgr;

public class TempActivity extends BaseActivity {

    private String mLocalPath;
    private Button mButtonDownload;
    private TextView mProgressTextLeft;
    private TextView mProgressTextRight;
    private SeekBar mProgressSeekBar;

    private DecimalFormat format = new DecimalFormat("#0.00");
    private Handler mHandler;
    private final int MSG_INIT = 0; //初始化进度字段
    private final int MSG_UPDATE = 1; //更新进度字段

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mLocalPath = Environment.getExternalStorageDirectory() + File.separator;

        initialize();
    }

    private void initialize() {
        mButtonDownload = (Button) findViewById(R.id.button_download);
        mButtonDownload.setOnClickListener(new Listener());

        mProgressTextLeft = (TextView) findViewById(R.id.progress_text_left);
        mProgressTextRight = (TextView) findViewById(R.id.progress_text_right);

        mProgressSeekBar = (SeekBar) findViewById(R.id.progress_seekbar);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) { //msg.arg1 文件大小，msg.arg2 下载大小
                switch (msg.what) {
                    case MSG_INIT:
                        mProgressSeekBar.setMax(msg.arg1);
                        break;
                    case MSG_UPDATE:
                        mProgressSeekBar.setProgress(msg.arg2);
                        String textLeft = "" + decimal(msg.arg2) + "/" + decimal(msg.arg1);
                        String textRight = "" + percent(msg.arg1, msg.arg2) + "%";
                        mProgressTextLeft.setText(textLeft);
                        mProgressTextRight.setText(textRight);
                        break;
                }
            }
        };
    }

    class Listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String url = "http://f3.market.mi-img.com/download/AppStore/06b27a50e2c094df50f3bda97e98f7b46f65815b5/com.tencent.mobileqq.apk";
            download(url, "ali.apk");
        }
    }

    private void download(final String sourceUrl, final String fileName) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                DownloadHelper helper = new DownloadHelper(mHandler, sourceUrl, fileName);
                helper.download();
            }
        };
        ThreadPoolMgr.getPool().execute(runnable); //new Thread(runnable).start();
    }

    private int percent(int total, int current) {
        return (int) (((float) current / total) * 100);
    }

    private String decimal(int value) {
        return format.format((double) value / (1024 * 1024));
    }

}


