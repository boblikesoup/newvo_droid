package com.newvo.android.util;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by David on 5/3/2014.
 */
public class IntentUtils {

    public static final int IMAGE_CAPTURE = 2;
    public static final int IMAGE_PICK = 4;

    public static Uri photoFile;

    private IntentUtils() {

    }

    public static void startImageCaptureIntent(Fragment fragment) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //PhotoFile has to be stored by me because intents/extras don't pass from ACTION_IMAGE_CAPTURE.
        photoFile = null;
        photoFile = createImageFile();

        if (photoFile != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    photoFile);
            fragment.startActivityForResult(intent, IMAGE_CAPTURE);
        }
    }

    public static void startImagePickIntent(Fragment fragment) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        fragment.startActivityForResult(intent, IMAGE_PICK);
    }

    public static void startImageCropIntent(Fragment fragment, String photoPath) {
        Context context = fragment.getActivity();
        Uri croppedFile = createImageFile();
        ImageFileUtils.resizeFile(context, photoPath, croppedFile);
        String resizedPhotoPath = croppedFile.toString();
        new Crop(Uri.parse(resizedPhotoPath)).output(Uri.parse(resizedPhotoPath)).start(context, fragment);
    }

    public static Uri createImageFile() {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        try {
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents
            return Uri.fromFile(image);
        } catch (IOException ex) {
            Log.e("newvo", "Failed to create image file.");
            return null;
        }
    }
}
