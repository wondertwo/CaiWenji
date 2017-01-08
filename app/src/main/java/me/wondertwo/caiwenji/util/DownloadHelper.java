package me.wondertwo.caiwenji.util;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import me.wondertwo.caiwenji.util.FileStorageHelper;

/**
 * 文件下载帮助类
 *
 * Created by wondertwo on 2016/12/22.
 */

public class DownloadHelper {

    private final static String TAG = "DownloadHelper";
    private final static int BUFFER_SIZE = 1024 * 2;

    private int/*long*/ fileTotalSize; //文件总大小
    private int/*long*/ fileLoadedSize; //已下载大小
    private int downloadPercent; //下载百分数
    private int fileBlockSize; //每个线程需要下载大小
    private float downloadSpeed; //下载速度
    private long currentTime; //当前时间
    private long downloadTime; //下载用时
    private boolean isFinish; //是否下载完成

    private String sourceUrl; //文件资源URL
    private String fileName; //文件保存名
    private Handler progressHandler;
    private int MSG_INIT = 0; //初始化进度字段
    private int MSG_UPDATE = 1; //更新进度字段

    public DownloadHelper(Handler handler, String sourceUrl, String fileName) {
        this.fileTotalSize = 0;
        this.fileLoadedSize = 0;
        this.downloadPercent = 0;
        this.fileBlockSize = 0;
        this.downloadSpeed = 0F;
        this.currentTime = System.currentTimeMillis();
        this.downloadTime = 0L;

        this.isFinish = false;
        this.progressHandler = handler;
        this.sourceUrl = sourceUrl; //网络资源文件 URL
        this.fileName = fileName; //下载后保存文件名
    }

    //开始下载
    public boolean download() {
        if (sourceUrl == null || fileName == null) {
            return false;
        }

        InputStream is = null;
        OutputStream os = null;
        try {
            URLConnection connection = (new URL(sourceUrl)).openConnection();
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("Accept-Encoding", "identity"); //文件小于2G不使用gzip压缩

            fileTotalSize = connection.getContentLength(); //总大小
            sendMessage(MSG_INIT, fileTotalSize, fileLoadedSize);
            is = new BufferedInputStream(connection.getInputStream());

            File file = FileStorageHelper.getNamedFile(fileName);
            os = new BufferedOutputStream(new FileOutputStream(file));

            byte[] bytes = new byte[BUFFER_SIZE];
            int length = 0;
            while((length = is.read(bytes)) != -1) {
                os.write(bytes);
                fileLoadedSize += length;
                //更新进度
                sendMessage(MSG_UPDATE, fileTotalSize, fileLoadedSize);
            }
            os.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) os.close();
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    //更新下载进度
    private void sendMessage(int what, int totalSize, int currentSize) {
        Message message = progressHandler.obtainMessage(); //new Message();
        message.what = what; //标识信息字段
        message.arg1 = totalSize; //文件大小，单位b
        message.arg2 = currentSize; //已下载大小，单位b
        progressHandler.sendMessage(message);
    }

    private long getContentSize(HttpURLConnection conn){
        long contentSize = 0;
        for(int i = 0; ; i++) {
            String mine = conn.getHeaderFieldKey(i);
            if(mine == null) {
                break;
            }

            if(mine.equals("Content-Length")) {
                contentSize = Long.parseLong(conn.getHeaderField(i));
                break;
            }
        }

        return contentSize;
    }

}
