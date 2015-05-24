package com.github.muxmax.juggrnotesapp.presentation.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;

import com.github.muxmax.juggrnotesapp.presentation.view.NoteDetailActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class that provides navigation between different app parts. This decouples all views
 * (activities and fragments) from each other.
 */
public class NavigationUtils {

    public static final int RESULT_LOAD_IMAGE = 1;
    private static final String APP_DIR = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            .concat("/Android/data/com.github.muxmax.juggrnotesapp/images/");

    /**
     * Navigates to an appropriate activity that provides image resources. So a chooser intent will
     * be started that shows gallery and photo apps.
     *
     * @param activity The current activity. Needed for starting further intents.
     */
    public static Uri goToGalleryOrPhotoApp(Activity activity) {

        final File root = new File(
                APP_DIR);
        Uri capturedImageUri = null;

        if (!root.mkdirs() && !root.isDirectory()) {
            Log.e(NavigationUtils.class.getName(),
                    "Could not create image file path for storing image from photo intent.");
        } else {
            final File newImageFilePath =
                    new File(root, "juggr_note_app_img_" + System.currentTimeMillis() + ".jpg");
            capturedImageUri = Uri.fromFile(newImageFilePath);

            final List<Intent> cameraIntents = new ArrayList<>();
            final Intent captureIntent =
                    new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            final PackageManager packageManager = activity.getPackageManager();
            final List<ResolveInfo> listCam =
                    packageManager.queryIntentActivities(captureIntent, 0);
            for (ResolveInfo res : listCam) {
                final String packageName = res.activityInfo.packageName;
                final Intent intent = new Intent(captureIntent);
                intent.setComponent(
                        new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                intent.setPackage(packageName);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
                cameraIntents.add(intent);
            }

            // Filesystem.
            final Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

            // Chooser of filesystem options.
            final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

            // Add the camera options.
            chooserIntent
                    .putExtra(Intent.EXTRA_INITIAL_INTENTS,
                            cameraIntents.toArray(new Parcelable[0]));
            activity.startActivityForResult(chooserIntent, RESULT_LOAD_IMAGE);
        }
        return capturedImageUri;
    }

    /**
     * Tells whether the given intent data comes from a camera activity or not.
     *
     * @param data An intent possibly containing result data.
     * @return {@code true} when the intent comes from a camera activity, {@code false} otherwise.
     */
    public static boolean isFromCamera(Intent data) {
        if (data == null) {
            return true;
        } else {
            final String action = data.getAction();
            return action != null &&
                    action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        }
    }

    /**
     * Get the file path for the given uri.
     *
     * @param uri      An uri containing a file path to a media resource.
     * @param activity The current activity.
     * @return A string containing the path.
     */
    public static String getPath(Uri uri, Activity activity) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.getContentResolver().query(uri, projection, null, null, null);

        if (cursor == null) { // local file paths like Dropbox etc.
            return uri.getPath();
        }

        cursor.moveToFirst();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        return cursor.getString(column_index);
    }

    /**
     * Navigate to the {@link com.github.muxmax.juggrnotesapp.presentation.view.NoteDetailActivity}.
     *
     * @param context The context the new activity is started from.
     * @param noteId  The id of the note to be viewed.
     */
    public static void goToNoteDetailActivity(Context context, Long noteId) {
        Intent intent = new Intent(context, NoteDetailActivity.class);
        intent.putExtra(BundleArguments.NOTE_ID, noteId);
        context.startActivity(intent);
    }

}
