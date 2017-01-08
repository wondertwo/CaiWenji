package me.wondertwo.caiwenji.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 *
 * Created by wondertwo on 2016/12/23.
 */

public class FileStorageHelper {

    private static String TAG = "FileStorageHelper";
    private static String mLocalDir = ""; //应用数据存储文件夹路径

    /**
     * 静态代码块，创建数据存储文件夹
     */
    static {
        String sdCardState = Environment.getExternalStorageState();
        if (sdCardState.equals(Environment.MEDIA_MOUNTED)) {
            mLocalDir = Environment.getExternalStorageDirectory().getPath() +
                    File.separator + "CaiWenJi" + File.separator;
            /*Log.e(TAG, mLocalDir);*/

            File file = new File(mLocalDir);
            if (!file.exists()) {
                file.mkdir();
            }
        }
    }

    /**
     * 获取指定名文件对象
     *
     * @param fileName  文件名
     * @return
     */
    public static File getNamedFile(String fileName) {
        File file = null;
        try {
            file = new File(mLocalDir + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

}
