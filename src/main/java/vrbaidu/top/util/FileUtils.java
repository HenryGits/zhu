package vrbaidu.top.util;

/**
 * Created by Administrator on 2017/3/9.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ResourceBundle;


/**
 * 文件读取工具类
 */
public class FileUtils {
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
    /**
     * 普通io方式读写
     * @param inputFileName
     * @param outPutFileName
     */
    public static void readFileByIO(String inputFileName, String outPutFileName) throws IOException {
        File file = new File(inputFileName);
        int tmp = 0;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        long startTime = System.currentTimeMillis();
        try {
            inputStream = new FileInputStream(file);
            outputStream = new FileOutputStream(outPutFileName, true);
            byte [] bytes=new byte[1024];
            //循环读取到byte数组中
            while ((tmp = inputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0 ,tmp);
            }
            //刷新写入流
            outputStream.flush();
            logger.info("文件写入完毕!" + String.format("用时======>%s毫秒",System.currentTimeMillis()-startTime));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            outputStream.close();
            inputStream.close();
        }
    }

    /**
     * 读取配置文件中的属性(配置文件必须放在classes目录下)
     * @param configName 配置文件的文件名(不带后缀)
     * @param propKey 属性的键
     * @return String
     */
    public String getProperty(String configName, String propKey) {
        return ResourceBundle.getBundle(configName).getString(propKey);
    }

    /**
     * 通道流方式读取(NIO),并发读取
     * @param allocate 缓冲区大小
     * @param fileName
     * @throws IOException
     */
    public static void readByChannel(int allocate, String fileName) throws IOException {
        RandomAccessFile raf = null;
        FileChannel fileChannel = null;
        MappedByteBuffer mappedByteBuffer = null;
        byte[] bytes = new byte[0];
        long size = 0;
        long startTime = System.currentTimeMillis();
        try {
            //创建一个随机访问文件对象,并设置为可读写模式
            raf = new RandomAccessFile(fileName,"rw");
            fileChannel = raf.getChannel();
            size = fileChannel.size();
            //构建一个只读的MappedByteBuffer
            mappedByteBuffer =fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, size);
            if (size < 1024){
                //如果文件不大，直接读取到数组
                bytes = new byte[(int) size];
                mappedByteBuffer.get(bytes, 0 , (int) size);
                logger.info(new String(bytes));
            }else {
                //如果大文件，循环读取
                byte[] bigFileByte = new byte[allocate];
                long cycle = size/allocate;
                int mode = (int) size%allocate;
                for (int i = 0 ; i < cycle ; i++){
                    //每次读取allocate个字节
                    mappedByteBuffer.get(bytes);
                }
                if (mode > 0){
                    bytes = new byte[mode];
                    mappedByteBuffer.get(bytes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            fileChannel.close();
            raf.close();
            logger.info(String.format("文件大小====>%s字节",size));
            logger.info(String.format("消耗时间====>%s毫秒",System.currentTimeMillis()-startTime));
        }
    }

    public static void main(String[] args) throws IOException {
        //readFileByIO("C:\\var\\log\\psbc\\credit\\mgt.log", "f:\\text.txt");
        readByChannel(1023,"C:\\\\var\\\\log\\\\psbc\\\\credit\\\\mgt.log");
    }
}
