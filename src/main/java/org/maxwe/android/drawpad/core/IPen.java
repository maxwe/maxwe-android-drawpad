package org.maxwe.android.drawpad.core;

import android.graphics.Canvas;
import android.graphics.Path;
import android.view.MotionEvent;
import org.maxwe.android.drawpad.Constants;


/**
 * Created by dingpengwei on 10/23/14.
 */
public interface IPen extends Constants{
    public String getName();
    public Path getPath();
    public void setIGraphic(IGraphic iGraphic);
    public IGraphic getIGraphic();
    public void setColor(int color);
    public int getColor();
    public void setSize(float size);
    public float getSize();
    public float getStartX();
    public float getStartY();
    public float getEndX();
    public float getEndY();
    public void setFilled(boolean isFilled);
    public boolean getFilled();
    public boolean onTouchEvent(MotionEvent event);
    public void onTouchMove(float x,float y);
    public void onTouchDown(float x,float y);
    public void onTouchUp(float x,float y);
    public boolean onDown(MotionEvent e);
    public void onShowPress(MotionEvent e);
    public boolean onSingleTapUp(MotionEvent e);
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);
    public void onLongPress(MotionEvent e);
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);
    public boolean onSingleTapConfirmed(MotionEvent e);
    public boolean onDoubleTap(MotionEvent e);
    public boolean onDoubleTapEvent(MotionEvent e);
    public void onDraw(Canvas canvas);
    public IPen clone();
}
