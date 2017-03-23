package vrbaidu.top.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by Administrator on 2017/3/11.
 * FTP文件上传工具类
 */
public class FTPUtils {
    private static Logger logger = LoggerFactory.getLogger(FTPUtils.class);
    private static FTPClient ftp = null;
    private String IPAddress;   //IP地址
    private String userName;    //用户名
    private String passWord;    //密码
    private String path;        //路径
    private Integer port;        //端口号

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * FTP连接
     * @param ftpUtils
     * @return
     * @throws IOException
     */
    public static boolean connectFTP(FTPUtils ftpUtils) throws IOException {
        ftp = new FTPClient();
        boolean flag = false;
        int reply;
        //连接到IP地址和端口
        if (ftpUtils.getPort() == null) {
            ftp.connect(ftpUtils.getIPAddress(), 21);
        }else {
            ftp.connect(ftpUtils.getIPAddress(), ftpUtils.getPort());
        }
        //登录
        ftp.login(ftpUtils.getUserName(), ftpUtils.getPassWord());
        logger.info("登陆成功!!");
        //大文件类型
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        //返回码
        reply = ftp.getReplyCode();
        //如果返回码是异常情况--->关闭连接
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            logger.info("系统异常!!");
            return flag;
        }
        //工作路径切换
        ftp.changeWorkingDirectory(ftpUtils.getPath());
        logger.info("工作路径切换为：" + ftpUtils.getPath());
        flag = true;
        return  flag;
    }

    /**
     * 关闭FTP连接
     */
    public static void closeFTP() {
        try {
            if (ftp != null && ftp.isConnected()){
                ftp.logout();
                ftp.disconnect();
                logger.info("ftp连接关闭成功!!");
            }
        }catch (Exception e) {
            logger.error("ftp连接关闭失败!!");
        }
    }

    /**
     * 文件上传
     * @param fileName
     * @throws IOException
     */
    public static void upload(String fileName) throws IOException {
        File file = new File(fileName);
        File childFile = null;
        File twoChildFile = null;
        FileInputStream input = null;
        try {
            //如果是一个文件夹
            if (file.isDirectory()) {
                ftp.makeDirectory(new String(file.getName().getBytes("UTF-8"),"ISO-8859-1"));
                ftp.changeWorkingDirectory(new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"));
                //获取到文件夹下所有子文件
                String[] files = file.list();
                logger.info("文件夹下文件数量："+files.length);
                for (String fstr: files) {
                    childFile = new File(file.getPath()+ "/" + fstr );
                    if (childFile.isDirectory()) {
                        upload(childFile.toString());
                        ftp.changeToParentDirectory();
                    }else {
                        twoChildFile = new File(file.getPath() +"/" +fstr);
                        input = new FileInputStream(twoChildFile);
                        ftp.storeFile(new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"), input);
                    }
                }
            }else {
                twoChildFile = new File(file.getPath());
                input = new FileInputStream(twoChildFile);
                ftp.storeFile(twoChildFile.getName(), input);
            }
            logger.info("上传文件成功!!");
        }catch (Exception e) {
            logger.error("上传文件失败!!原因："+e.getMessage());
        }finally {
            input.close();
            closeFTP();
        }
    }

    /**
     * 下载链接配置
     * @param f
     * @param localBaseir 本地目录
     * @param remoteBaseDir 远程目录
     */
    public static void startDown(FTPUtils f, String localBaseir, String remoteBaseDir) {
        FTPFile[] files = null;
        boolean flag = false;
        try {
            if (connectFTP(f)) {
                flag = ftp.changeWorkingDirectory(remoteBaseDir);
                if (flag) {
                    ftp.setControlEncoding("UTF-8");
                    files = ftp.listFiles();
                    for (int i = 0; i<files.length; i++) {
                        downLoadFile(files[i], localBaseir, remoteBaseDir);
                    }
                }
            }
            logger.info("下载成功!");
        } catch (IOException e) {
            logger.error("下载过程中出现异常,原因是" + e.getMessage());
        }
    }
    /**
     * 下载文件方法
     * @param ftpFile
     * @param relativeLocalPath
     * @param relativeRemotePath
     */
    private static void downLoadFile(FTPFile ftpFile, String relativeLocalPath, String relativeRemotePath) {
        OutputStream output = null;
        File localFile = null;
        try {
            //如果下载内容只是单文件
            if (ftpFile.isFile()) {
                logger.info("单文件下载！！");
                if (ftpFile.getName().indexOf("?") == -1) {
                    localFile = new File(relativeLocalPath+ ftpFile.getName());
                    //判断文件是否存在，存在则返回
                    if (localFile.exists()) {
                        return;
                    }else {
                        output = new FileOutputStream(relativeLocalPath+ ftpFile.getName());
                        ftp.retrieveFile(ftpFile.getName(), output);
                        output.flush();
                    }
                }
                return;
            }
            logger.info("文件夹下载！！");
            //如果下载内容包含在文件夹下
            String newLocalRelatePath = relativeLocalPath + ftpFile.getName();
            String newRemote = relativeRemotePath + ftpFile.getName();
            File file = new File(newLocalRelatePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            newLocalRelatePath = newLocalRelatePath + "/";
            newRemote = newRemote +"/";
            String currentWordDir = ftpFile.getName();
            boolean changeDir = ftp.changeWorkingDirectory(currentWordDir);
            if (changeDir) {
                FTPFile[] files = ftp.listFiles();
                for (int i = 0; i<files.length; i++) {
                    downLoadFile(files[i], newLocalRelatePath, newRemote);
                }
                return;
            }
            ftp.changeToParentDirectory();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }finally {
            try {
                output.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
