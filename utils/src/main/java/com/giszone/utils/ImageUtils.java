package com.giszone.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Lee on 2017/12/12.
 */

public class ImageUtils {

    //将Bitmap转换成字符串
    public static String bitmapToString(Bitmap bitmap) {
        String string = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    // 将字符串转换成Bitmap类型
    public static Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //将Bitmap转换成二进制数组
    public static byte[] bitmapToByte(Bitmap bitmap) {
        byte[] bytes = null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        try {
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = stream.toByteArray();
        return bytes;
    }

    //将二进制数组转换成Bitmap类型
    public static Bitmap byteToBitmap(byte[] bitmapArray) {
        Bitmap bitmap = null;
        if (bitmapArray != null) {
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        }
        return bitmap;
    }

    // 保存图片到本地
    public static void saveImage(String path, Bitmap bitmap) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            //保存图片后发送广播通知更新图库为可见
//            Uri uri = Uri.fromFile(file);
//            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 加载网络上的图片
    public static Bitmap getBitmap(String imageUri) {
        try {
            URL myFileUrl = new URL(imageUri);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //图片质量压缩方法,压缩图片的二进制数组大小，像素和内存大小不变
    public static Bitmap compressQuality(Bitmap image) {
        Bitmap bitmap = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            //尽量压缩到50kb以内
            for (int options = 100; (baos.toByteArray().length / 1024 > 50) && options > 10; options -= 10) {
                baos.reset();//重置baos即清空baos
                image.compress(Bitmap.CompressFormat.PNG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            }
            for (int options = 10; (baos.toByteArray().length / 1024 > 50) && options > 1; options -= 1) {
                baos.reset();//重置baos即清空baos
                image.compress(Bitmap.CompressFormat.PNG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            }
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
            bitmap = BitmapFactory.decodeStream(isBm, null, null);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    //图片比例压缩法，内存大小与尺寸大小成正比，图片大小约为20~40像素
    public static Bitmap compressProportion(Bitmap image) {
        Bitmap bitmap = null;
        Matrix matrix = new Matrix();
        if (image.getWidth() >= 2000) {
            matrix.setScale(0.1f, 0.1f); //图片大小变为原来的0.01倍
        } else if ((image.getWidth() < 2000) && (image.getWidth() >= 1000)) {
            matrix.setScale(0.14f, 0.14f); //图片大小约为原来的0.02倍
        } else if ((image.getWidth() < 1000) && (image.getWidth() >= 800)) {
            matrix.setScale(0.17f, 0.17f); //图片大小变为原来的0.03倍
        } else if ((image.getWidth() < 800) && (image.getWidth() >= 600)) {
            matrix.setScale(0.2f, 0.2f); //图片大小变为原来的0.04倍
        } else if ((image.getWidth() < 600) && (image.getWidth() >= 400)) {
            matrix.setScale(0.22f, 0.22f); //图片大小变为原来的0.05倍
        } else if ((image.getWidth() < 400) && (image.getWidth() >= 200)) {
            matrix.setScale(0.32f, 0.32f); //图片大小变为原来的0.1倍
        } else if ((image.getWidth() < 200) && (image.getWidth() >= 100)) {
            matrix.setScale(0.45f, 0.45f); //图片大小变为原来的0.2倍
        } else if ((image.getWidth() < 100) && (image.getWidth() >= 50)) {
            matrix.setScale(0.63f, 0.63f); //图片大小变为原来的0.4倍
        } else if ((image.getWidth() < 50) && (image.getWidth() >= 30)) {
            matrix.setScale(0.84f, 0.84f); //图片大小变为原来的0.7倍
        } else {
            matrix.setScale(0.95f, 0.95f); //图片大小变为原来的0.9倍
        }
        bitmap = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
        return bitmap;
    }

    //图片比例压缩法，内存大小与尺寸大小成正比，图片大小约为20~40像素
    public static Bitmap compressFeedback(Bitmap image) {
        Bitmap bitmap = null;
        Matrix matrix = new Matrix();
        if (image.getWidth() >= 2000) {
            matrix.setScale(0.32f, 0.32f); //图片大小变为原来的0.1倍
        } else if ((image.getWidth() < 2000) && (image.getWidth() >= 1000)) {
            matrix.setScale(0.45f, 0.45f); //图片大小约为原来的0.2倍
        } else if ((image.getWidth() < 1000) && (image.getWidth() >= 800)) {
            matrix.setScale(0.50f, 0.50f); //图片大小变为原来的0.25倍
        } else if ((image.getWidth() < 800) && (image.getWidth() >= 600)) {
            matrix.setScale(0.57f, 0.57f); //图片大小变为原来的0.33倍
        } else if ((image.getWidth() < 600) && (image.getWidth() >= 400)) {
            matrix.setScale(0.71f, 0.71f); //图片大小变为原来的0.5倍
        } else if ((image.getWidth() < 400) && (image.getWidth() >= 200)) {
            matrix.setScale(0.95f, 0.95f); //图片大小变为原来的0.9倍
        } else {
            matrix.setScale(1.0f, 1.0f); //图片大小变为原来的1倍
        }
        bitmap = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
        return bitmap;
    }
}
