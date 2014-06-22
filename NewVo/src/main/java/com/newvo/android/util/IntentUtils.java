package com.newvo.android.util;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
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
    public static final int MICROPHONE_INTENT = 25;

    public static Uri photoFile;

    public static boolean loadingIntent;
    public static boolean loadingCropIntent;

    private IntentUtils() {

    }

    public static void startImageCaptureIntent(Fragment fragment) {
        if(loadingIntent){
            return;
        }
        loadingIntent = true;
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
        if(loadingCropIntent){
            return;
        }
        loadingCropIntent = true;
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        fragment.startActivityForResult(intent, IMAGE_PICK);
    }

    public static void startImageCropIntent(Fragment fragment, String photoPath) {
        if(loadingIntent){
            return;
        }
        loadingIntent = true;
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

    public static void resetIntentLoading(){
        IntentUtils.loadingCropIntent = false;
        IntentUtils.loadingIntent = false;
    }

    public static void startMicrophoneIntent(Fragment fragment){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //... put other settings in the Intent
        fragment.startActivityForResult(intent, MICROPHONE_INTENT);
    }

    public static boolean loadingEitherIntent(){
        return loadingCropIntent || loadingIntent;
    }
}
