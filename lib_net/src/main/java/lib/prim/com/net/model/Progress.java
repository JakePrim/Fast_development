package lib.prim.com.net.model;

import android.os.SystemClock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lib.prim.com.net.PrimHttp;

import static lib.prim.com.net.model.Progress.Priority.DEFAULT;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：2/11 0011
 * 描    述：进度类
 * 修订历史：
 * ================================================
 */
public class Progress implements Serializable {
    private static final long serialVersionUID = 6353658567594109891L;

    public static final int NONE = 0;         //无状态
    public static final int WAITING = 1;      //等待
    public static final int LOADING = 2;      //下载中
    public static final int PAUSE = 3;        //暂停
    public static final int ERROR = 4;        //错误
    public static final int FINISH = 5;       //完成

    public String tag;                              //下载的标识键
    public String url;                              //网址
    public String folder;                           //保存文件夹
    public String filePath;                         //保存文件地址
    public String fileName;                         //保存的文件名
    public float fraction;                          //下载的进度，0-1
    public long totalSize;                          //总字节长度, byte
    public long currentSize;                        //本次下载的大小, byte
    public transient long speed;                    //网速，byte/s
    public int status;                              //当前状态
    public int priority;                            //任务优先级
    public long date;                               //创建时间
    public Throwable exception;                     //当前进度出现的异常
    private transient long tempSize;                //每一小段时间间隔的网络流量
    private transient long lastRefreshTime;         //最后一次刷新的时间
    private transient List<Long> speedBuffer;       //网速做平滑的缓存，避免抖动过快

    public interface Priority {
        int UI_TOP = Integer.MAX_VALUE;
        int UI_NORMAL = 1000;
        int UI_LOW = 100;
        int DEFAULT = 0;
        int BG_TOP = -100;
        int BG_NORMAL = -1000;
        int BG_LOW = Integer.MIN_VALUE;
    }

    public Progress() {
        lastRefreshTime = SystemClock.elapsedRealtime();
        totalSize = -1;
        priority = DEFAULT;
        date = System.currentTimeMillis();
        speedBuffer = new ArrayList<>();
    }

    public static Progress changeProgress(Progress progress, long writeSize, Action action) {
        return changeProgress(progress, writeSize, progress.totalSize, action);
    }

    public static Progress changeProgress(final Progress progress, long writeSize, long totalSize, final Action action) {
        progress.totalSize = totalSize;
        progress.currentSize += writeSize;
        progress.tempSize += writeSize;

        long currentTime = SystemClock.elapsedRealtime();
        boolean isNotify = (currentTime - progress.lastRefreshTime) >= PrimHttp.REFRESH_TIME;
        if (isNotify || progress.currentSize == totalSize) {
            long diffTime = currentTime - progress.lastRefreshTime;
            if (diffTime == 0) diffTime = 1;
            progress.fraction = progress.currentSize * 1.0f / totalSize;
            progress.speed = progress.bufferSpeed(progress.tempSize * 1000 / diffTime);
            progress.lastRefreshTime = currentTime;
            progress.tempSize = 0;
            if (action != null) {
                action.call(progress);
            }
        }
        return progress;
    }

    /** 平滑网速，避免抖动过大 */
    private long bufferSpeed(long speed) {
        speedBuffer.add(speed);
        if (speedBuffer.size() > 10) {
            speedBuffer.remove(0);
        }
        long sum = 0;
        for (float speedTemp : speedBuffer) {
            sum += speedTemp;
        }
        return sum / speedBuffer.size();
    }

     public interface Action {
        void call(Progress progress);
    }
}
