package app.myapp.myapplication.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    public static String saveBitmapToStorage(Context context, Bitmap bitmap) throws IOException {
        File file = new File(context.getFilesDir(), "image.png");
        FileOutputStream fos = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.close();
        return file.getAbsolutePath();
    }
}
