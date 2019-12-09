package com.max.framework.util.stream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import org.apache.log4j.Logger;

import com.max.framework.core.resource.ResourceUtil;

/**
 * 用于进行指定配置文件加锁,目的防止窗体二次启动
 * @author 马青松
 */
public class FileLockUtil {
    /**
     * 日志打印
     */
    private static final Logger logger = Logger.getLogger(FileLockUtil.class);
    /**
     * 指定加锁文件
     */
    private static String lockFileName = ResourceUtil.getResourceInfo("lock.file.path");
    /**
     * 文件
     */
    private static File lockFile = null;
    /**
     * 文件
     */
    private static RandomAccessFile raf = null;
    /**
     * 指定通道
     */
    private static FileChannel fc = null;
    /**
     * 锁
     */
    private static FileLock fl = null;
    /**
     * 写文件
     */
    private static FileWriter fr = null;

    /**
     * 指定文件加锁
     * @return true,加锁成功,false,不能加锁
     */
    public static boolean getFileLock() {
        boolean rtn = true;
        try {
            lockFile = new File(lockFileName);
            raf = new RandomAccessFile(lockFile, "rw");

            fr = new FileWriter(lockFile);
            fr.write("singleton");
            fr.close();
            fr = null;
            fc = raf.getChannel();
            fl = fc.tryLock();

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
        return rtn;
    }

    /**
     * 加锁后解锁
     */
    public static void releaseFileLock() {
        try {
            if (fr != null)
                fr.close();
            if (fl != null)
                fl.release();
            if (fc != null)
                fc.close();
            if (raf != null)
                raf.close();
            if (lockFile != null)
                lockFile.delete();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

    /**
     * 指定加锁文件
     * @return 指定加锁文件
     */
    public static String getLockFileName() {
        return lockFileName;
    }

    /**
     * 指定加锁文件
     * @param lockFileName 指定加锁文件
     */
    public static void setLockFileName(String lockFileName) {
        FileLockUtil.lockFileName = lockFileName;
    }
}
