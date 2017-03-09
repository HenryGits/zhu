package vrbaidu.top.util;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by Administrator on 2017/3/1.
 */
public class ZipUtils {
    /**
     * 压缩单个文件
     * @param filePath 文件路径
     * @param zipPath  解压路径
     * @throws IOException
     */
    public static void ZipFile(String filePath , String zipPath) throws IOException {
        InputStream input = null;
        ZipOutputStream zipOut = null;
        try {
            File file = new File(filePath);
            File zipFile = new File(zipPath);
            input = new FileInputStream(file);
            zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            zipOut.putNextEntry(new ZipEntry(file.getName()));
            int temp = 0;
            while((temp = input.read()) != -1){
                zipOut.write(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            input.close();
            zipOut.close();
        }
    }

    /**
     * 一次性压缩多个文件，文件存放至一个文件夹中
     * File.pathSeparator指的是分隔连续多个路径字符串的分隔符，例如:
     * Java   -cp   test.jar;abc.jar   HelloWorld 就是指“;”
     * File.separator才是用来分隔同一个路径字符串中的目录的，例如：
     * C:\Program Files\Common Files 就是指“\”
     * @param filePath 含有压缩包的文件夹
     * @param zipPath 指定压缩包名称
     * @throws IOException
     */
    public static void ZipMultiFile(String filePath ,String zipPath) throws IOException {
        InputStream input = null;
        ZipOutputStream zipOut = null;
        try {
            File file = new File(filePath);// 要被压缩的文件夹
            File zipFile = new File(zipPath);
            zipOut= new ZipOutputStream(new FileOutputStream(zipFile));
            //如果是一个文件夹
            if(file.isDirectory()){
                File[] files = file.listFiles();
                for(int i = 0; i < files.length; ++i){
                    input = new FileInputStream(files[i]);
                    zipOut.putNextEntry(new ZipEntry(file.getName() + file.getPath() + files[i].getName()));
                    int temp = 0;
                    while((temp = input.read()) != -1){
                        zipOut.write(temp);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            input.close();
            zipOut.close();
        }
    }

    /**
     * 解压缩（解压缩单个文件）
     * @param zipPath
     * @param outFilePath
     * @param fileName
     * @throws IOException
     */
    public static void ZipContraFile(String zipPath ,String outFilePath ,String fileName) throws IOException {
        InputStream input = null;
        OutputStream output = null;
        try {
            File file = new File(zipPath);//压缩文件路径和文件名
            File outFile = new File(outFilePath);//解压后路径和文件名
            ZipFile zipFile = new ZipFile(file);
            ZipEntry entry = zipFile.getEntry(fileName);//所解压的文件名
            input = zipFile.getInputStream(entry);
            output = new FileOutputStream(outFile);
            int temp = 0;
            while((temp = input.read()) != -1){
                output.write(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            input.close();
            output.close();
        }
    }

    /**  解压缩（压缩文件中包含多个文件）可代替上面的方法使用。
     * ZipInputStream类
     * 当我们需要解压缩多个文件的时候，ZipEntry就无法使用了，
     * 如果想操作更加复杂的压缩文件，我们就必须使用ZipInputStream类
     * @param zipPath
     * @param outZipPath
     * @throws IOException
     * */
    public static void ZipContraMultiFile(String zipPath ,String outZipPath) throws IOException {
        File outFile = null;
        ZipEntry entry = null;
        InputStream input = null;
        OutputStream output = null;
        try {
            File file = new File(zipPath);
            ZipFile zipFile = new ZipFile(file);
            ZipInputStream zipInput = new ZipInputStream(new FileInputStream(file));
            while((entry = zipInput.getNextEntry()) != null){
                System.out.println("解压缩" + entry.getName() + "文件");
                outFile = new File(outZipPath + File.separator + entry.getName());
                if(!outFile.getParentFile().exists()){
                    outFile.getParentFile().mkdir();
                }
                if(!outFile.exists()){
                    outFile.createNewFile();
                }
                input = zipFile.getInputStream(entry);
                output = new FileOutputStream(outFile);
                int temp = 0;
                while((temp = input.read()) != -1){
                    output.write(temp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            input.close();
            output.close();
        }
    }


//    public static void extract(File zipFile,File destDir) throws Exception{
//        ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
//        ZipEntry zie;
//
//        while((zie = zis.getNextEntry()) != null){
//            String entryName = zie.getName();
//            File newFile = new File(destDir,entryName);
//            int count;
//            byte[] data = new byte[8192];
//            if(zie.isDirectory()){
//                newFile.mkdirs();
//            }else{
//                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile));
//                while((count = zis.read(data)) != -1){
//                    bos.write(data,0,count);
//                }
//                bos.flush();
//                bos.close();
//            }
//        }
//        zis.close();
//    }
//    public static void extractBuf(File zipFile, File destDir) throws Exception {
//
//        BufferedInputStream bi = new BufferedInputStream(new FileInputStream(zipFile));
//        ZipInputStream zis = new ZipInputStream(bi);
//        ZipEntry zie;
//        int BUFFER_SIZE = 8192;
//
//        while ((zie = zis.getNextEntry()) != null) {
//            String entryName = zie.getName();
//// System.out.println("Extracting: " + zipentry.getName());
//            File newFile = new File(destDir , entryName);
//            byte[] data = new byte[BUFFER_SIZE];
//            int count;
//            if (zie.isDirectory()) {
//                newFile.mkdirs();
//            } else {
//                FileOutputStream fos = new FileOutputStream(newFile);
//                BufferedOutputStream bos = new BufferedOutputStream(fos,BUFFER_SIZE);
//                while((count = zis.read(data,0,BUFFER_SIZE)) != -1){
//                    bos.write(data,0,count);
//                }
//                bos.flush();
//                bos.close();
//            }
//        }
//
//        zis.close();
//    }

    /**
     * NIO方式解压缩
     * @param zipFile 压缩包所在文件夹
     * @param destDir 解压的文件夹
     * @throws Exception
     */
    public static void extractNIOzf(File zipFile,File destDir) throws Exception{
        ZipEntry zie = null;
        FileOutputStream fos = null;
        ZipFile zf = new ZipFile(zipFile);
        Enumeration et = zf.entries();
        while(et.hasMoreElements()){
            zie = (ZipEntry)et.nextElement();
            File newFile = new File(destDir,zie.getName());
            ReadableByteChannel rc = Channels.newChannel(zf.getInputStream(zie));
            if(zie.isDirectory()){
                newFile.mkdirs();
            }else{
                FileChannel fc = fos.getChannel();
                fc.transferFrom(rc, 0, zie.getSize());
                fos.close();
            }
        }
        zf.close();
    }

    /**
     *
     * @param zipFile
     * @param destDir
     * @throws Exception
     */
    public static void extractNIO(File zipFile,File destDir)throws Exception{
        ZipEntry zie = null;
        FileOutputStream fos = null;
        BufferedInputStream bi = new BufferedInputStream(new FileInputStream(zipFile));
        ZipInputStream zin = new ZipInputStream(bi);
        ReadableByteChannel rc = Channels.newChannel(zin);
        while((zie = zin.getNextEntry()) != null){
            String entryName = zie.getName();
            File newFile = new File(destDir,entryName);
            if(zie.isDirectory()){
                newFile.mkdirs();
            }else{
                fos = new FileOutputStream(newFile);
                FileChannel fc = fos.getChannel();
                long count = 0;
                long size = zie.getSize();
//                while(true){
//                long written = fc.transferFrom(rc, count, size);
//                count += written;
//                 }
                fc.transferFrom(rc,0,zie.getSize());
                fos.close();
            }
        }
        zin.close();
    }

    public static void main(String[] args) {
        try {
            System.out.println(System.currentTimeMillis());
            extractNIO(new File("F:\\eclipse.zip"),new File("f:\\test"));
            System.out.println(System.currentTimeMillis());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
