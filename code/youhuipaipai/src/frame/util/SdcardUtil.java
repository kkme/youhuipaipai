package frame.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

/***********************************
 * SD卡工具类 该类主要功能包括:{}
 * 
 * @author duanxinmeng
 * @version 1.0
 * @date 2013-9-19
 */
public class SdcardUtil {

    /*********************************
     * 判断SDcard是否可以用
     * 
     * @return true 可用 false 不可用
     */
    public static boolean isSdCardExists() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            Log.e("-->Eastedge", "the Sdcard is not exists");
            return false;
        }
    }

    /*********************************
     * 获取SD卡总大小 需加入权限: <uses-permission
     * android:name="android.permission.WRITE_EXTERNAL_STORAGE"
     * ></uses-permission>
     * 
     * @return String 单位M 若为返回null或者空,则sdcard不能使用或无Sdcard
     */
    public static String getSdCardTotalSize() {
        if (!isSdCardExists())
            return "";
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());

        long blockSize = stat.getBlockSize();

        long availableBlocks = stat.getBlockCount();

        return String.valueOf((availableBlocks * blockSize) / 1024 / 1024);
    }

    /*********************************
     * 获取SD卡可用大小 需加入权限:<uses-permission
     * android:name="android.permission.WRITE_EXTERNAL_STORAGE"
     * ></uses-permission>
     * 
     * @return 单位M
     * 
     */
    public static String getSdcardAvailbleSize() {
        if (!isSdCardExists())
            return "";
        File path = Environment.getExternalStorageDirectory(); // 取得sdcard文件路径
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return String.valueOf((availableBlocks * blockSize) / 1024 / 1024);
    }

    /****
     * 创建一个文件夹
     * 
     * @return
     */
    public static boolean createMkdir() {
        return true;
    }

    public static boolean createFile() {
        return true;
    }

    /***
     * 获取根目录路径
     * 
     * @return String
     */
    public static String getRootPath() {
        if (!isSdCardExists())
            return "";
        return Environment.getExternalStorageDirectory().getPath();
    }

    /*******************************
     * 获取根目录文件列表
     * 
     * @return List<File>
     */
    public static File[] getRootFiles() {
        if (getRootPath().equals(""))
            return null;
        List<File> files = new ArrayList<File>();
        File file = new File(getRootPath());
        return file.listRoots();
    }

}
