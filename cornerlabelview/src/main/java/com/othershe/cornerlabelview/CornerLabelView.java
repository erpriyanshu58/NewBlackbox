package com.othershe.cornerlabelview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * A corner label view that draws a colored triangle with text in a corner.
 * Supports custom XML attributes: position, text, text_color, bg_color, text_size, side_length.
 */
public class CornerLabelView extends View {

    public static final int LEFT_TOP = 0;
    public static final int RIGHT_TOP = 1;
    public static final int LEFT_BOTTOM = 2;
    public static final int RIGHT_BOTTOM = 3;

    private Paint paint;
    private Paint textPaint;
    private Path path;
    private int bgColor = Color.TRANSPARENT;
    private int textColor = Color.WHITE;
    private float textSize = 28f;
    private String text = "";
    private float sideLength = 0;
    private int position = LEFT_TOP;

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
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CornerLabelView);
            try {
                position = ta.getInt(R.styleable.CornerLabelView_position, LEFT_TOP);
                text = ta.getString(R.styleable.CornerLabelView_text, "");
                textColor = ta.getColor(R.styleable.CornerLabelView_text_color, Color.WHITE);
                bgColor = ta.getColor(R.styleable.CornerLabelView_bg_color, Color.TRANSPARENT);
                textSize = ta.getDimension(R.styleable.CornerLabelView_text_size, 28f);
                sideLength = ta.getDimension(R.styleable.CornerLabelView_side_length, 0f);
            } finally {
                ta.recycle();
            }
        }

        if (text == null) {
            text = "";
        }

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(bgColor);

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

        float size = sideLength > 0 ? sideLength : Math.min(width, height);

        paint.setColor(bgColor);
        path.reset();

        switch (position) {
            case LEFT_TOP:
                path.moveTo(0, 0);
                path.lineTo(size, 0);
                path.lineTo(0, size);
                break;
            case RIGHT_TOP:
                path.moveTo(width, 0);
                path.lineTo(width - size, 0);
                path.lineTo(width, size);
                break;
            case LEFT_BOTTOM:
                path.moveTo(0, height);
                path.lineTo(size, height);
                path.lineTo(0, height - size);
                break;
            case RIGHT_BOTTOM:
                path.moveTo(width, height);
                path.lineTo(width - size, height);
                path.lineTo(width, height - size);
                break;
        }
        path.close();
        canvas.drawPath(path, paint);

        // Draw text if present
        if (text != null && !text.isEmpty()) {
            textPaint.setColor(textColor);
            textPaint.setTextSize(textSize);
            float textWidth = textPaint.measureText(text);
            canvas.save();

            float cx, cy;
            switch (position) {
                case LEFT_TOP:
                    cx = size / 4f;
                    cy = size / 4f;
                    canvas.rotate(-45, cx, cy);
                    break;
                case RIGHT_TOP:
                    cx = width - size / 4f;
                    cy = size / 4f;
                    canvas.rotate(45, cx, cy);
                    break;
                case LEFT_BOTTOM:
                    cx = size / 4f;
                    cy = height - size / 4f;
                    canvas.rotate(45, cx, cy);
                    break;
                case RIGHT_BOTTOM:
                    cx = width - size / 4f;
                    cy = height - size / 4f;
                    canvas.rotate(-45, cx, cy);
                    break;
                default:
                    cx = size / 4f;
                    cy = size / 4f;
                    canvas.rotate(-45, cx, cy);
                    break;
            }
            canvas.drawText(text, cx - textWidth / 2, cy + textSize / 3, textPaint);
            canvas.restore();
        }
    }

    public void setFillColor(int color) {
        this.bgColor = color;
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

    public void setPosition(int position) {
        this.position = position;
        invalidate();
    }

    public void setSideLength(float sideLength) {
        this.sideLength = sideLength;
        invalidate();
    }
}
