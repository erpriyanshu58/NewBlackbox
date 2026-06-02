package com.othershe.cornerlabelview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * A simple corner label view that draws a colored triangle in the top-left corner with text.
 * Recreated from the original com.github.othershe:CornerLabelView library
 * which is no longer available on JitPack.
 */
public class CornerLabelView extends View {

    private Paint paint;
    private Paint textPaint;
    private Path path;
    private int backgroundColor = Color.TRANSPARENT;
    private int textColor = Color.WHITE;
    private float textSize = 28f;
    private String text = "";
    private float cornerSize = 24f;

    public CornerLabelView(Context context) {
        this(context, null);
    }

    public CornerLabelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CornerLabelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, new int[]{
                    android.R.attr.background,
                    android.R.attr.text,
                    android.R.attr.textSize,
                    android.R.attr.textColor
            });
            ta.recycle();
        }

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(backgroundColor);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.LEFT);

        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        if (width == 0 || height == 0) return;

        // Draw a triangle in the top-left corner
        float size = Math.min(width, height);

        paint.setColor(backgroundColor);
        path.reset();
        path.moveTo(0, 0);
        path.lineTo(size, 0);
        path.lineTo(0, size);
        path.close();
        canvas.drawPath(path, paint);

        // Draw text if present
        if (text != null && !text.isEmpty()) {
            textPaint.setColor(textColor);
            textPaint.setTextSize(textSize);
            float textWidth = textPaint.measureText(text);
            canvas.save();
            canvas.rotate(-45, size / 4f, size / 4f);
            canvas.drawText(text, size / 4f - textWidth / 2, size / 4f + textSize / 3, textPaint);
            canvas.restore();
        }
    }

    public void setFillColor(int color) {
        this.backgroundColor = color;
        paint.setColor(color);
        invalidate();
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    public void setTextColor(int color) {
        this.textColor = color;
        textPaint.setColor(color);
        invalidate();
    }

    public void setTextSize(float size) {
        this.textSize = size;
        textPaint.setTextSize(size);
        invalidate();
    }
}
