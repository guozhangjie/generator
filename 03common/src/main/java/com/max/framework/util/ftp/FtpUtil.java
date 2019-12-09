package com.max.framework.util.ftp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.max.framework.util.string.StringUtil;

/**
 * FTP连接工具类
 * @author 马青松
 */
public class FtpUtil {

    /**
     * 日志
     */
    private static Logger logger = Logger.getLogger(FtpUtil.class);

    /**
     * GBK字符串常量
     */
    private static final String ENCODING_GBK = "GBK";

    /**
     * iso-8859-1字符串常量
     */
    private static final String ENCODING_ISO = "iso-8859-1";

    /**
     * 文件分割符\
     */
    private static final String FILE_SEPARATOR_RIGHT = "\\";

    /**
     * 文件分割符/
     */
    private static final String FILE_SEPARATOR_LEFT = "/";

    /**
     * 获取ftp连接
     * @param ftpBean ftp信息
     * @return FTP连接
     */
    public static FTPClient connectFtp(FtpBean ftpBean) {
        FTPClient ftp = new FTPClient();
        try {
            if (ftpBean.getPort() == null) {
                ftp.connect(ftpBean.getIpAddr(), 21);
            } else {
                ftp.connect(ftpBean.getIpAddr(), ftpBean.getPort());
            }
            ftp.login(ftpBean.getUserName(), ftpBean.getPwd());
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
            }
            ftp.changeWorkingDirectory(ftpBean.getPath());
        } catch (SocketException ex) {
            logger.error(ex);
        } catch (IOException ex) {
            logger.error(ex);
        }
        return ftp;
    }

    /**
     * 关闭ftp连接
     * @param ftp FTP连接
     */
    public static void closeFtp(FTPClient ftp) {
        if (ftp != null && ftp.isConnected()) {
            try {
                ftp.logout();
                ftp.disconnect();
            } catch (IOException ex) {
                logger.info(ex);
            }
        }
    }

    /**
     * 下载链接配置
     * @param ftp FTP连接
     * @param localBaseDir 本地目录
     * @param remoteBaseDir 远程目录
     * @return -2:FTP不存在远程路径;-1:切换FTP路径出现异常;0:FTP未成功连接;1:全部下载成功;2:部分下载成功;
     */
    public static int startDown(FTPClient ftp, String localBaseDir, String remoteBaseDir) {
        if (ftp != null && ftp.isConnected()) {
            File file = new File(localBaseDir);
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                FTPFile[] files = null;
                boolean changedir = ftp.changeWorkingDirectory(remoteBaseDir);
                if (changedir) {
                    ftp.setControlEncoding(ENCODING_GBK);
                    files = ftp.listFiles();
                    boolean state = true;
                    for (int i = 0; i < files.length; i++) {
                        boolean downState = downloadFile(ftp, files[i], localBaseDir, remoteBaseDir, state);
                        if (state && !downState) {
                            state = false;
                        }
                    }
                    if (state) {
                        return 1;
                    } else {
                        return 2;
                    }
                } else {
                    return -2;
                }
            } catch (IOException ex) {
                logger.error(ex);
                return -1;
            }
        } else {
            return 0;
        }
    }

    /**
     * 下载FTP文件 当你需要下载FTP文件的时候，调用此方法 根据<b>获取的文件名，本地地址，远程地址</b>进行下载
     * 如果本地文件存在，则不会从服务器上下载
     * @param ftp FTP连接
     * @param ftpFile FTP文件
     * @param relativeLocalPath 关联本地文件
     * @param relativeRemotePath 关联远程文件
     * @param messages 错误消息
     * @param pathState 是否全部下载成功
     */
    private static boolean downloadFile(FTPClient ftp, FTPFile ftpFile, String relativeLocalPath, String relativeRemotePath, boolean pathState) {
        boolean state = pathState;
        if (ftpFile.isFile()) {
            if (ftpFile.getName().indexOf("?") == -1) {
                OutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(relativeLocalPath + ftpFile.getName());
                    ftp.retrieveFile(ftpFile.getName(), outputStream);
                } catch (FileNotFoundException ex) {
                    logger.error(ex);
                    state = false;
                } catch (IOException ex) {
                    logger.error(ex);
                    state = false;
                } finally {
                    try {
                        if (outputStream != null) {
                            outputStream.flush();
                            outputStream.close();
                        }
                    } catch (IOException ex) {
                        logger.error(ex);
                        try {
                            outputStream.close();
                        } catch (IOException exception) {
                            logger.error(exception);
                        }
                    }
                }
            } else {
                logger.info(relativeRemotePath + " 下的文件：" + ftpFile.getName() + " 文件名存在非法字符");
                state = false;
            }
        } else {
            String newlocalRelatePath = relativeLocalPath + ftpFile.getName();
            String newRemote = relativeRemotePath + ftpFile.getName().toString();
            File fl = new File(newlocalRelatePath);
            if (!fl.exists()) {
                fl.mkdirs();
            }
            try {
                newlocalRelatePath = newlocalRelatePath + File.separator;
                newRemote = newRemote + File.separator;
                String path = new String(newRemote.getBytes(ENCODING_GBK), ENCODING_ISO);
                boolean changedir = ftp.changeWorkingDirectory(path);
                if (changedir) {
                    FTPFile[] files = null;
                    files = ftp.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        downloadFile(ftp, files[i], newlocalRelatePath, newRemote, pathState);
                    }
                    ftp.changeToParentDirectory();
                } else {
                    logger.info("FTP路径：" + newRemote + "不存在");
                    state = false;
                }
            } catch (UnsupportedEncodingException ex) {
                logger.error(ex);
            } catch (IOException ex) {
                logger.error(ex);
                state = false;
            }
        }
        return state;
    }

    /**
     * 获取FTP单个文件二进制流
     * @param ftp FTP连接
     * @param remoteBaseDir FTP路径
     * @param fileName 文件名
     * @return 文件二进制流
     */
    public static byte[] getSingleFileByte(FTPClient ftp, String remoteBaseDir, String fileName) {
        byte[] fileByte = null;
        String filePath = remoteBaseDir + FILE_SEPARATOR_LEFT + fileName;

        if (ftp != null && ftp.isConnected()) {
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            try {
                ftp.enterLocalPassiveMode();
                String path = new String(filePath.getBytes(ENCODING_GBK), ENCODING_ISO);

                ftp.retrieveFile(path, arrayOutputStream);
                fileByte = arrayOutputStream.toByteArray();
                if (fileByte == null || fileByte.length == 0) {
                    return null;
                }
            } catch (UnsupportedEncodingException ex) {
                logger.error(ex);
            } catch (IOException ex) {
                logger.error(ex);
            } finally {
                try {
                    if (arrayOutputStream != null) {
                        arrayOutputStream.close();
                    }
                } catch (IOException ex) {
                    logger.error(ex);
                }
            }
        }
        return fileByte;
    }

    /**
     * 获取FTP单个文件二进制流
     * @param ftp FTP连接
     * @param filePath FTP路径标识 含文件名称
     * @return 文件二进制流
     */
    public static byte[] getSingleFileByte(FTPClient ftp, String filePath) {
        byte[] fileByte = null;

        if (StringUtil.isNullOrEmpty(filePath)) {
            return fileByte;
        }
        filePath = filePath.trim().replace(FILE_SEPARATOR_RIGHT, FILE_SEPARATOR_LEFT);

        if (ftp != null && ftp.isConnected()) {
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            try {
                ftp.enterLocalPassiveMode();
                String path = new String(filePath.getBytes(ENCODING_GBK), ENCODING_ISO);

                ftp.retrieveFile(path, arrayOutputStream);
                fileByte = arrayOutputStream.toByteArray();
                if (fileByte == null || fileByte.length == 0) {
                    return null;
                }
            } catch (UnsupportedEncodingException ex) {
                logger.error(ex);
            } catch (IOException ex) {
                logger.error(ex);
            } finally {
                if (arrayOutputStream != null) {
                    try {
                        arrayOutputStream.close();
                    } catch (IOException ex) {
                        logger.error(ex);
                    }
                }
            }
        }
        return fileByte;
    }

    /**
     * FTP上传文件或文件夹(在FTP根目录创建对应文件夹)
     * @param file 上传文件或文件夹
     * @param ftp FTP连接
     */
    public static void uploadAllFileByDir(File file, FTPClient ftp) {
        if (file.isDirectory()) {
            try {
                String fileName = new String(file.getName().getBytes(ENCODING_GBK), ENCODING_ISO);
                ftp.makeDirectory(fileName);
                ftp.changeWorkingDirectory(fileName);
                File[] files = file.listFiles();
                for (File thisFile : files) {
                    if (thisFile.isDirectory()) {
                        uploadAllFileByDir(thisFile, ftp);
                        try {
                            ftp.changeToParentDirectory();
                        } catch (IOException ex) {
                            logger.error(ex);
                        }
                    } else {
                        uploadFile(thisFile, ftp);
                    }
                }
            } catch (UnsupportedEncodingException ex) {
                logger.error(ex);
            } catch (IOException ex) {
                logger.error(ex);
            }
        } else {
            uploadFile(file, ftp);
        }
    }

    /**
     * 向FTP指定文件夹传单个文件或文件夹下所有文件(如果FTP不存在该路径，则新建)
     * @param file 单个文件或文件夹
     * @param ftp FTP连接
     * @param remoteBaseDir FTP指定路径
     * @return true:上传成功;false:上传失败;(该状态仅对于指定文件适用)
     */
    public static boolean uploadSingleFile(File file, FTPClient ftp, String remoteBaseDir) {
        boolean isSuccess = false;
        boolean changedir = false;
        try {
            ftp.makeDirectory(remoteBaseDir);
            changedir = ftp.changeWorkingDirectory(remoteBaseDir);
        } catch (IOException ex) {
            logger.error(ex);
        }
        if (changedir) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File thisFile : files) {
                    uploadSingleFile(thisFile, ftp, remoteBaseDir);
                }
            } else {
                isSuccess = uploadFile(file, ftp);
            }
        }
        return isSuccess;
    }

    /**
     * 上传文件私有方法
     * @param file 上传的文件
     * @param ftp FTP连接
     * @return true:上传成功;false:上传失败;
     */
    private static boolean uploadFile(File file, FTPClient ftp) {
        FileInputStream input = null;
        boolean state = false;
        try {
            input = new FileInputStream(file);
            state = ftp.storeFile(new String(file.getName().getBytes(ENCODING_GBK), ENCODING_ISO), input);
        } catch (UnsupportedEncodingException ex) {
            logger.error(ex);
        } catch (IOException ex) {
            logger.error(ex);
        } finally {
            logger.info("文件：" + file.getAbsolutePath() + "是否上传成功--" + state);
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    logger.error(ex);
                }
            }
        }
        return state;
    }
}
