package com.giszone.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

/**
 * Created by giszone on 2017/3/9 0009.
 */

public class FileUtils {

    public static final int CACHE_SIZE = 1024;


    public static void copyFile(String oldPath, String newPath) {
        try {
            File oldFile = new File(oldPath);
            if (oldFile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[CACHE_SIZE];
                int len;
                while ((len = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, len);
                }
                inStream.close();
            } else {
                Log.e(XUtils.TAG, "文件不存在！");
            }
        } catch (Exception e) {
            Log.e(XUtils.TAG, "复制单个文件操作出错 !");
            e.printStackTrace();
        }
    }

    public static void moveFile(String oldPath, String newPath) {
        try {
            File oldFile = new File(oldPath);
            if (oldFile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[CACHE_SIZE];
                int len;
                while ((len = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, len);
                }
                inStream.close();
                deleteFile(oldPath);
            } else {
                Log.e(XUtils.TAG, "文件不存在！");
            }
        } catch (Exception e) {
            Log.e(XUtils.TAG, "复制单个文件操作出错！");
            e.printStackTrace();
        }
    }


    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName 要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            Log.e(XUtils.TAG, "删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                Log.e(XUtils.TAG, "删除单个文件" + fileName + "成功！");
                return true;
            } else {
                Log.e(XUtils.TAG, "删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            Log.e(XUtils.TAG, "删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dir 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            Log.e(XUtils.TAG, "删除目录失败：" + dir + "不存在！");
            return false;
        }
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    // 删除子文件
                    if (!deleteFile(file.getAbsolutePath())) {
                        Log.e(XUtils.TAG, "删除文件" + file.getPath() + "失败！");
                        return false;
                    }
                } else if (file.isDirectory()) {
                    // 删除子目录
                    if (!deleteDirectory(file.getAbsolutePath())) {
                        Log.e(XUtils.TAG, "删除目录" + file.getPath() + "失败！");
                        return false;
                    }
                }
            }
        }
        // 删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            Log.e(XUtils.TAG, "删除目录" + dir + "失败！");
            return false;
        }
    }

    /**
     * 删除该目录下所有数据，但是保留该文件夹
     *
     * @param dir
     * @return
     */
    public static boolean clearDir(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            Log.e(XUtils.TAG, "删除目录失败：" + dir + "不存在！");
            return false;
        }
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    // 删除子文件
                    if (!deleteFile(file.getAbsolutePath())) {
                        Log.e(XUtils.TAG, "删除文件" + file.getPath() + "失败！");
                        return false;
                    }
                } else if (file.isDirectory()) {
                    // 删除子目录
                    if (!deleteDirectory(file.getAbsolutePath())) {
                        Log.e(XUtils.TAG, "删除目录" + file.getPath() + "失败！");
                        return false;
                    }
                }
            }
        }
        Log.e(XUtils.TAG, "删除目录" + dir + "成功！");
        return true;
    }

    /**
     * 向文件中添加内容
     *
     * @param strContent 内容
     * @param filePath   地址
     * @param fileName   文件名
     */
    public static void addStringToFile(String strContent, String filePath, String fileName) {
        try {
            //生成文件夹之后，再生成文件，不然会出错
            // 每次写入时，都换行写
            File subFile = new File(filePath + fileName);
            /**   构造函数 第二个是读写方式    */
            RandomAccessFile raf = new RandomAccessFile(subFile, "rw");
            /**  将记录指针移动到该文件的最后  */
            raf.seek(subFile.length());
            /** 向文件末尾追加内容  */
            raf.write(strContent.getBytes());

            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改文件内容（覆盖或者添加）
     *
     * @param path    文件地址
     * @param content 覆盖内容
     * @param append  指定了写入的方式，是覆盖写还是追加写(true=追加)(false=覆盖)
     */
    public static void modifyFile(String path, String content, boolean append) {
        try {
            FileWriter fileWriter = new FileWriter(path, append);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            writer.append(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件内容
     *
     * @param filePath 地址
     * @return 返回内容
     */
    public static String getStringFromFile(String filePath) {
        StringBuffer sb = new StringBuffer();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File(filePath)), StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 文件改名
     *
     * @param filePath 文件的路径
     * @param newName  新文件名
     * @return 文件的新路径
     */
    public static String renameFile(String filePath, String newName) {
        File file = new File(filePath);
        //前面路径必须一样才能修改成功
        String newPath = filePath.substring(0, filePath.lastIndexOf("/") + 1) + newName + filePath.substring(filePath.lastIndexOf("."), filePath.length());
        File newFile = new File(newPath);
        if (file.renameTo(newFile)) {
            return newPath;
        } else {
            return filePath;
        }
    }

    /**
     * 复制文件
     *
     * @param fromFile 要复制的文件目录
     * @param toFile   要粘贴的文件目录
     * @return 是否复制成功
     */
    public static boolean copy(String fromFile, String toFile) {
        //要复制的文件目录
        File[] currentFiles;
        File root = new File(fromFile);
        //如同判断SD卡是否存在或者文件是否存在
        //如果不存在则 return出去
        if (!root.exists()) {
            return false;
        }
        //如果存在则获取当前目录下的全部文件 填充数组
        currentFiles = root.listFiles();

        //目标目录
        File targetDir = new File(toFile);
        //创建目录
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }
        //遍历要复制该目录下的全部文件
        for (int i = 0; i < currentFiles.length; i++) {
            if (currentFiles[i].isDirectory())//如果当前项为子目录 进行递归
            {
                copy(currentFiles[i].getPath() + "/", toFile + currentFiles[i].getName() + "/");

            } else//如果当前项为文件则进行文件拷贝
            {
                copySdcardFile(currentFiles[i].getPath(), toFile + currentFiles[i].getName());
            }
        }
        return true;
    }

    //文件拷贝
    //要复制的目录下的所有非子目录(文件夹)文件拷贝
    public static boolean copySdcardFile(String fromFile, String toFile) {
        try {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return true;

        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean copyAssetsToFile(Context context, String assetName, String toFile) {
        try {
            File toDir = new File(toFile);
            if (!toDir.exists()) {
                toDir.getParentFile().mkdirs();
            } else {
                return true;
            }
            InputStream isFrom = context.getAssets().open(assetName);
            OutputStream fosTo = new FileOutputStream(toFile);
            byte[] bt = new byte[1024];
            int c;
            while ((c = isFrom.read(bt)) > 0) {
                fosTo.write(bt, 0, c);
            }
            fosTo.flush();
            fosTo.close();
            isFrom.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 将文本写入保存到log文件中
     *
     * @param filePath 文件路径
     * @param content  要写入的内容
     * @return 写入是否成功
     */
    public static boolean writeStringToFile(String filePath, String content) {
        try {
            //[1]新建一个文件对象
            File file = new File(filePath);
            //[2]创建一个文件输出流
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将文本写入保存到log文件中
     *
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @param content  要写入的内容
     * @return 写入是否成功
     */
    public static boolean writeStringToFile(String filePath, String fileName, byte[] content) {
        try {
            //[1]新建一个文件对象
            File file = new File(filePath, fileName);
            //[2]创建一个文件输出流
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content);
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 读取文件中的文本内容
     *
     * @return 返回读取的文本，认为null则读取失败
     */
    public static byte[] readBytes(String path) {
        File file = new File(path);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            int size = fis.available();
            byte[] content = new byte[size];

            fis.read(content);
            fis.close();

            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取文件中的文本内容
     *
     * @param path 文本名称
     * @return 返回读取的文本，认为null则读取失败
     */
    public static String readStringFromFile(String path) {

        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            int size = fis.available();
            byte[] content = new byte[size];

            fis.read(content);
            fis.close();

            return new String(content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void checkDir(String path, boolean isFile) {
        File file = new File(path);
        if (!file.exists()) {
            if (isFile) {
                file.getParentFile().mkdirs();
            } else {
                file.mkdirs();
            }
        }
    }

    /**
     * 通过本地路经 content://得到URI路径
     *
     * @param context
     * @param contentUri
     * @return
     */
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        String locationPath = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        try (Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null)) {
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                locationPath = cursor.getString(column_index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locationPath;
    }

    /**
     * 将文本写入保存到log文件中
     *
     * @param filePath 文件路径
     * @param fileName 文件名称
     * @param content  要写入的内容
     * @return 写入是否成功
     */
    public static boolean writeByteArrayToFile(String filePath, String fileName, byte[] content) {
        try {
            //[1]新建一个文件对象
            File file = new File(filePath, fileName);
            //[2]创建一个文件输出流
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content);
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 读取文件中的文本内容
     *
     * @param path 文本所在路径
     * @return 返回读取的文本，认为null则读取失败
     */
    public static byte[] readByteArrayFromFile(String path) {
        File file = new File(path);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            int size = fis.available();
            byte[] content = new byte[size];

            fis.read(content);
            fis.close();

            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


    /**
     * <p>
     * 文件转换为二进制数组
     * </p>
     *
     * @param filePath 文件路径
     * @return
     * @throws Exception
     */
    public static byte[] readBytesFromFile(String filePath) throws Exception {
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead;
            while ((nRead = in.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }
            out.close();
            in.close();
            return out.toByteArray();
        } else {
            return null;
        }
    }


    /**
     * <p>
     * 二进制数据写文件
     * </p>
     *
     * @param bytes    二进制数据
     * @param filePath 文件生成目录
     */
    public static void writeBytesToFile(byte[] bytes, String filePath) throws Exception {
        File destFile = new File(filePath);
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        InputStream in = new ByteArrayInputStream(bytes);
        FileOutputStream out = new FileOutputStream(destFile);
        byte[] cache = new byte[CACHE_SIZE];
        int nRead;
        while ((nRead = in.read(cache)) != -1) {
            out.write(cache, 0, nRead);
            out.flush();
        }
        out.close();
        in.close();
    }

}

