package com.max.framework.util.stream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.jar.JarArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * zip工具类
 * @author 马青松
 */
public class ZipUtil {
    /**
     * 日志打印
     */
    private static final Logger logger = Logger.getLogger(ZipUtil.class);
    
    public static void main(String[] args) {
        unzip("D:\\03eclipseall\\手动测试覆盖率安装使用方法.rar", "D:\\03eclipseall");
    }
    
    /**
     * 解压缩
     * @param zipPath 包地址
     * @param unzipPath 解压后地址
     */
    public static void unzip(String zipPath, String unzipPath) {
        ArchiveInputStream in = null;
        BufferedInputStream bufferedInputStream = null;
        File warFile = new File(zipPath);
        try {
            // 获得输出流
            bufferedInputStream = new BufferedInputStream(new FileInputStream(warFile));
            in = new ArchiveStreamFactory().createArchiveInputStream(ArchiveStreamFactory.JAR, bufferedInputStream);
            JarArchiveEntry entry = null;
            // 循环遍历解压
            while ((entry = (JarArchiveEntry) in.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    new File(unzipPath, entry.getName()).mkdir();
                } else {
                    OutputStream out = FileUtils.openOutputStream(new File(unzipPath, entry.getName()));
                    IOUtils.copy(in, out);
                    out.close();
                }
            }
        } catch (FileNotFoundException ex) {
            logger.error(ex);
        } catch (ArchiveException ex) {
            logger.error(ex);
        } catch (IOException ex) {
            logger.error(ex);
        } finally {
            try {
                bufferedInputStream.close();
                in.close();
            } catch (IOException ex) {
                logger.error(ex);
            }
        }
    }

    /**
     * 压缩
     * @param destFile 创建的地址及名称
     * @param zipDir 要打包的目录
     */
    public static void zip(String destFile, String zipDir) {
        BufferedOutputStream bufferedOutputStream = null;
        ArchiveOutputStream out = null;
        File outFile = new File(destFile);
        try {
            outFile.createNewFile();
            // 创建文件
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outFile));
            out = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.JAR, bufferedOutputStream);
            if (zipDir.charAt(zipDir.length() - 1) != File.separatorChar) {
                zipDir += File.separator;
            }

            Iterator<File> files = FileUtils.iterateFiles(new File(zipDir), null, true);
            while (files.hasNext()) {
                File file = files.next();
                ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file, file.getPath().replace(zipDir.replace("/", "\\"), ""));
                out.putArchiveEntry(zipArchiveEntry);
                IOUtils.copy(new FileInputStream(file), out);
                out.closeArchiveEntry();
            }
            
        } catch (IOException ex) {
            logger.error(ex);
        } catch (ArchiveException ex) {
            logger.error(ex);
        } finally {
            try {
                out.finish();
                out.close();
                bufferedOutputStream.close();
            } catch (IOException ex) {
                logger.error(ex);
            }
        }
    }
}
