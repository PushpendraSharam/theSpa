package app.myapp.myapplication.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SignatureView extends View {
    private Path path;
    private Paint paint;
    private float lastX, lastY;

    public SignatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xFF000000); // Black color
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                // Stop drawing the signature
                break;
            default:
                return false;
        }

        // Redraw the view
        invalidate();
        return true;
    }

    public void clear() {
        path.reset();
        invalidate();
    }

    public Bitmap getSignatureBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        draw(canvas);
        return bitmap;
    }
    public boolean isEmpty() {
        return path.isEmpty();
    }
}
