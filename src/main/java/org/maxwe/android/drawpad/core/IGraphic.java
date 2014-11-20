package org.maxwe.android.drawpad.core;

import android.graphics.Canvas;
import android.view.ScaleGestureDetector;
import org.maxwe.android.drawpad.Constants;

/**
 * Created by dingpengwei on 10/23/14.
 */
public interface IGraphic extends Constants{
    public String getName();
    public void setIPen(IPen iPen);
    public IPen getIPen();
    public void setStatus(ValuesOfGraphStatus status);
    public ValuesOfGraphStatus getStatus();
    public void setRotationDegree(float degree);
    public float getRotationDegree();
    public float getScale();
    public boolean onScaleBegin(ScaleGestureDetector detector);
    public boolean onScale(ScaleGestureDetector detector);
    public void onScaleEnd(ScaleGestureDetector detector);
    public void onDraw(Canvas canvas);
    public IGraphic clone();
}
