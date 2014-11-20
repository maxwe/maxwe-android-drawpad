package org.maxwe.android.drawpad.activities;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import org.maxwe.android.drawpad.Constants;

public class ColorPickerView extends View {
    private Paint paintChoseing = new Paint();
    private Paint paintChosed = new Paint();
    private int initColor = Color.BLACK;
    private final int[] initColors  = new int[]{
            0xFF000000, 0xFF000000,
            0xFFFF00FF, 0xFFFF00FF,
            0xFF0000FF, 0xFF0000FF,
            0xFF00FFFF, 0xFF00FFFF,
            0xFF00FF00, 0xFF00FF00,
            0xFFFFFF00, 0xFFFFFF00,
            0xFFFF0000, 0xFFFF0000,
            0xFFFFFFFF, 0xFFFFFFFF};;
    private int width = -1;
    private int height = -1;
    private boolean isPreview = true;
    private int previewWidth = -1;
    private int previewHeight = -1;
    private float cx, cy, radius;
    private OnColorChangeLintener onColorChangeLintener = new OnColorChangeLintener() {
        @Override
        public void onColorChanged(int color) {
            Toast.makeText(ColorPickerView.this.getContext(),"尚未设置监听",Toast.LENGTH_LONG).show();
        }
    };

    public ColorPickerView(Context context) {
        this(context,null,-1,-1,Color.BLACK,true);
    }

    public ColorPickerView(Context context, AttributeSet attrs) {
        this(context,attrs,-1,-1,Color.BLACK,true);
    }

    public ColorPickerView(Context context, AttributeSet attrs, int defStyle) {
        this(context,attrs,-1,-1,Color.BLACK,true);
    }

    public ColorPickerView(Context context, AttributeSet attrs, int width, int height, int initColor,boolean isPreview) {
        super(context,attrs);
        this.width = width;
        this.height = height;
        this.previewWidth = this.previewHeight = this.height;
        this.initColor = initColor;
        this.paintChosed.setColor(this.initColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(new RectF(0, 0, this.width - this.previewWidth, this.height), 10, 10, paintChoseing);
        this.cx = this.width - this.previewWidth / 2;
        this.cy = this.previewHeight / 2;
        this.radius = this.previewHeight / 2;
        canvas.drawCircle(cx, cy, radius, this.paintChosed);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (this.isInCircle(this.cx, this.cy, this.radius, x, y)) {
                    this.onColorChangeLintener.onColorChanged(this.paintChosed.getColor());
                }
                break;
        }
        this.paintChosed.setColor(this.interpRectColor(this.initColors, x));
        this.onColorChangeLintener.onColorChanged(this.interpRectColor(this.initColors, x));
        this.invalidate();
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if(this.width == -1){
            this.width = widthSize;
        }
        if(this.height == -1){
            this.height = heightSize;
        }

        if(this.isPreview){
            this.previewWidth = this.previewHeight = this.height;
        }else{
            this.previewWidth = this.previewHeight = 0;
        }

        setMeasuredDimension(this.width, this.height);
        Shader shader = new LinearGradient(0, 0, this.width - this.previewWidth, this.height, initColors, null, Shader.TileMode.REPEAT);
        paintChoseing.setShader(shader);
    }

    private boolean isInCircle(float cx, float cy, float radius, float x, float y) {
        boolean result = false;
        float distance = (float) Math.sqrt(Math.pow(x - cx, 2) + Math.pow(y - cy, 2));
        if (distance > radius) {
            return result;
        }
        return true;
    }

    private int interpRectColor(int colors[], float x) {
        int a, r, g, b, c0, c1, index;
        float p;
        if (x < 0) {
            index = 0;
        } else if (x > 0 && x < this.width) {
            index = (int) ((x / this.width) * this.initColors.length) + 1;
        } else if (x > this.width) {
            index = this.initColors.length - 1;
        } else {
            index = (int) (x / this.width);
        }

        c0 = colors[index > this.initColors.length - 1 ? this.initColors.length - 1 : index];
        c1 = colors[index + 1 > this.initColors.length - 1 ? this.initColors.length - 1 : index + 1];
        p = x / this.width;

        a = ave(Color.alpha(c0), Color.alpha(c1), p);
        r = ave(Color.red(c0), Color.red(c1), p);
        g = ave(Color.green(c0), Color.green(c1), p);
        b = ave(Color.blue(c0), Color.blue(c1), p);
        return Color.argb(a, r, g, b);
    }

    private int ave(int s, int d, float p) {
        return s + Math.round(p * (d - s));
    }

    public interface OnColorChangeLintener {
        void onColorChanged(int color);
    }

    public void setOnColorChangeLintener(OnColorChangeLintener onColorChangeLintener){
        this.onColorChangeLintener = onColorChangeLintener;
    }

    public void setIsPreview(boolean isPreview) {
        this.isPreview = isPreview;
    }

    public void reset(){
        this.paintChosed.setColor(Constants.DEFAULT_VALUE_PEN_COLOR);
        this.invalidate();
    }
}
