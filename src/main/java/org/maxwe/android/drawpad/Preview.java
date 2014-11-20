package org.maxwe.android.drawpad;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import org.maxwe.android.drawpad.core.IGraphic;
import org.maxwe.android.drawpad.core.IPen;
import org.maxwe.android.drawpad.core.graphics.*;
import org.maxwe.android.drawpad.core.pens.DefaultPen;
import org.maxwe.android.drawpad.core.pens.EraserPen;

/**
 * Created by dingpengwei on 11/11/14.
 */
public class Preview extends View {

    private IPen iPen = new DefaultPen();
    private IGraphic iGraphic = new AnyLine();
    private int viewWidth;
    private int viewHeight;


    public Preview(Context context,IPen iPen,IGraphic iGraphic) {
        super(context);
        this.iPen = iPen.clone();
        this.iGraphic = iGraphic.clone();
        this.iPen.setIGraphic(this.iGraphic);
        this.iGraphic.setIPen(this.iPen);
    }

    public Preview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.iPen.setIGraphic(this.iGraphic);
        this.iGraphic.setIPen(this.iPen);

    }

    public Preview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.initPreview();
        this.iGraphic.getIPen().onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        this.viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        this.viewHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(this.viewWidth, this.viewHeight);

        this.iPen.setIGraphic(this.iGraphic);
        this.iGraphic.setIPen(this.iPen);
        this.iPen.onTouchDown(this.viewWidth / 5 * 1, this.viewHeight / 2 * 1);
        this.iPen.onTouchMove(this.viewWidth / 5 * 1, this.viewHeight / 4 * 1);
        this.iPen.onTouchMove(this.viewWidth / 5 * 2, this.viewHeight / 2 * 1);
        this.iPen.onTouchMove(this.viewWidth / 5 * 3, this.viewHeight / 4 * 2);
        this.iPen.onTouchMove(this.viewWidth / 5 * 3, this.viewHeight / 4 * 3);
        this.iPen.onTouchMove(this.viewWidth / 5 * 4, this.viewHeight / 4 * 2);
        this.iPen.onTouchUp(this.viewWidth / 5 * 4, this.viewHeight / 4 * 2);
    }

    public void setiPen(IPen iPen) {
        this.iPen = iPen.clone();
        this.iPen.setIGraphic(this.iGraphic);
        this.iGraphic.setIPen(this.iPen);
        this.invalidate();
    }

    public void setiGraphic(IGraphic iGraphic) {
        this.iGraphic = iGraphic.clone();
        this.iPen.setIGraphic(this.iGraphic);
        this.iGraphic.setIPen(this.iPen);
        this.invalidate();
    }

    public void setColor(int color){
        this.iPen.setColor(color);
        this.invalidate();
    }

    public int getColor(){
        return this.iPen.getColor();
    }

    public void setFilled(boolean isFilled){
        this.iPen.setFilled(isFilled);
        this.invalidate();
    }

    public boolean getFilled(){
        return this.iPen.getFilled();
    }

    public void setSize(int size){
        this.iPen.setSize(size);
        this.invalidate();
    }

    public int getSize(){
        return (int)this.iPen.getSize();
    }

    private void initPreview(){
        if(this.iGraphic instanceof AnyLine || this.iPen instanceof EraserPen){
            this.iPen.onTouchDown(this.viewWidth / 5 * 1,this.viewHeight / 2 * 1);
            this.iPen.onTouchMove(this.viewWidth / 5 * 1,this.viewHeight / 4 * 1);
            this.iPen.onTouchMove(this.viewWidth / 5 * 2,this.viewHeight / 2 * 1);
            this.iPen.onTouchMove(this.viewWidth / 5 * 3,this.viewHeight / 4 * 2);
            this.iPen.onTouchMove(this.viewWidth / 5 * 3,this.viewHeight / 4 * 3);
            this.iPen.onTouchMove(this.viewWidth / 5 * 4,this.viewHeight / 4 * 2);
            this.iPen.onTouchUp(this.viewWidth / 5 * 4,this.viewHeight / 4 * 2);
        }else if(this.iGraphic instanceof StraightLIne){
            this.iPen.onTouchDown(this.viewWidth / 10,this.viewHeight / 10);
            this.iPen.onTouchUp(this.viewWidth / 10 * 9,this.viewHeight / 10 * 9);
        }else if(this.iGraphic instanceof PerfectCircle){
            this.iPen.onTouchDown(this.viewWidth / 2,this.viewHeight / 2);
            this.iPen.onTouchUp(this.viewWidth / 2 + 100,this.viewHeight / 2 + 100);
        }else if(this.iGraphic instanceof Ellipse){
            this.iPen.onTouchDown(this.viewWidth / 10 * 2,this.viewHeight / 10 * 2);
            this.iPen.onTouchUp(this.viewWidth / 10 * 8,this.viewHeight / 10 * 8);
        }else if(this.iGraphic instanceof RightAngleRectangle){
            this.iPen.onTouchDown(this.viewWidth / 10 * 2,this.viewHeight / 10 * 2);
            this.iPen.onTouchUp(this.viewWidth / 10 * 8,this.viewHeight / 10 * 8);
        }else if(this.iGraphic instanceof RoundedRectangle){
            this.iPen.onTouchDown(this.viewWidth / 10 * 2,this.viewHeight / 10 * 2);
            this.iPen.onTouchUp(this.viewWidth / 10 * 8,this.viewHeight / 10 * 8);
        }

    }
}
