package com.newvo.android.util;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.*;

/**
 * Created by David on 5/3/2014.
 */
public class ImageFileUtils {

    private ImageFileUtils(){

    }

    public static String getPhotoPath(File photo){
        if(photo == null){
            return null;
        }
        return "file:" + photo.getAbsolutePath();
    }


    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public static byte[] pathToByteArray(ContentResolver contentResolver, String path) {
        Bitmap bitmap = getBitmap(contentResolver, path);
        if (bitmap != null) {
            return bitmapToByteArray(bitmap);
        } else return null;
    }

    public static Bitmap getBitmap(ContentResolver contentResolver, String path) {

        Uri uri = Uri.parse(path);
        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 300000; // 1.2MP
            in = contentResolver.openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();


            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }
            Log.d("NewVo", "scale = " + scale + ", orig-width: " + o.outWidth + ",orig-height: " + o.outHeight);

            Bitmap b = null;
            in = contentResolver.openInputStream(uri);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();
                Log.d("NewVo", "1th scale operation dimenions - width: " + width + ",height: " + height);

                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                        (int) y, true);
                b.recycle();
                b = scaledBitmap;

                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            in.close();

            Log.d("NewVo", "bitmap size - width: " + b.getWidth() + ", height: " +
                    b.getHeight());
            return b;
        } catch (IOException e) {
            Log.e("NewVo", e.getMessage(), e);
            return null;
        }
    }

    public static void resizeFile(Context context, String path, Uri output){
        resizeBitmap(getBitmap(context.getContentResolver(), path), output);
    }

    public static void resizeBitmap(Bitmap bitmap, Uri uri) {
        if (bitmap == null) {
            return;
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(new File(uri.getPath()));
        } catch (FileNotFoundException e) {
            Log.e("NewVo", "file not found exception" + uri.toString());
            return;
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
    }

}
