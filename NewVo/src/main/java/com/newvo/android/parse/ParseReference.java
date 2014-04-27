package com.newvo.android.parse;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.parse.*;

import java.io.ByteArrayOutputStream;

/**
 * Created by David on 4/26/2014.
 */
public class ParseReference {

    public static void initialize(){
        ParseUser.registerSubclass(User.class);
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(Suggestion.class);
        ParseObject.registerSubclass(Vote.class);
        ParseACL acl = new ParseACL();
        acl.setPublicReadAccess(true);
        ParseACL.setDefaultACL(acl, true);
    }

    public static Bitmap parseFileToBitmap(ParseFile parseFile) {
        if (parseFile == null) {
            return null;
        }
        try {
            return byteArrayToBitmap(parseFile.getData());
        } catch (ParseException e) {
            Log.e("NewVo", e.toString());
            return null;
        }
    }

    public static ParseFile bitmapToParseFile(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        return new ParseFile(bitmapToByteArray(bitmap));
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap byteArrayToBitmap(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }
}
