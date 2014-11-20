package org.maxwe.android.drawpad.core;

import android.graphics.Canvas;
import android.graphics.Path;
import android.view.ScaleGestureDetector;
import org.maxwe.json.Json;
import org.maxwe.json.JsonObject;
import org.maxwe.android.drawpad.core.graphics.*;

/**
 * Created by dingpengwei on 10/23/14.
 */
public abstract class  AGraphic implements IGraphic{

    protected Path path;
    protected ValuesOfGraphStatus status;
    protected float scale = 1.0f;
    protected float degree;
    protected IPen iPen;
    protected float startX;
    protected float startY;
    protected float endX;
    protected float endY;

    protected JsonObject jsonStringGraphic = Json.createJsonObject();

    protected AGraphic(){
        this.path = new Path();
    }

    @Override
    public void setIPen(IPen iPen) {
        this.iPen = iPen;
    }

    @Override
    public IPen getIPen() {
        return this.iPen;
    }

    @Override
    public void setStatus(ValuesOfGraphStatus status) {
        this.status = status;
    }

    @Override
    public ValuesOfGraphStatus getStatus() {
        return status;
    }

    @Override
    public void setRotationDegree(float degree) {
        if(Math.abs(degree) > 1){
            this.degree = degree - 90;
        }
    }

    @Override
    public float getRotationDegree() {
        return this.degree;
    }

    @Override
    public float getScale() {
        return this.scale;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        this.scale *= detector.getScaleFactor();
        // Don't let the object get too small or too large.
        this.scale = Math.max(0.5f, Math.min(this.scale, 5.0f));
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public void onDraw(Canvas canvas) {
        this.startX = this.iPen.getStartX();
        this.startY = this.iPen.getStartY();
        this.endX = this.iPen.getEndX();
        this.endY = this.iPen.getEndY();
    }

    @Override
    public String toString() {
        this.jsonStringGraphic.set(KEY_GRAPH_NAME, this.getName())
                .set(KEY_GRAPH_STATUS,this.getStatus())
                .set(KEY_GRAPH_SCALE,this.getScale())
                .set(KEY_GRAPH_DEGREE,this.getRotationDegree())
                .set(KEY_GRAPH_PEN, this.getIPen().toString());
        return this.jsonStringGraphic.toString();
    }

    @Override
    public IGraphic clone() {
        IGraphic iGraphic = null;
        if(this instanceof AnyLine){
            iGraphic = new AnyLine();
        }else if(this instanceof Ellipse){
            iGraphic = new Ellipse();
        }else if(this instanceof PerfectCircle){
            iGraphic = new PerfectCircle();
        }else if(this instanceof RightAngleRectangle){
            iGraphic = new RightAngleRectangle();
        }else if (this instanceof RoundedRectangle){
            iGraphic = new RoundedRectangle();
        }else if(this instanceof StraightLIne){
            iGraphic = new StraightLIne();
        }
        return iGraphic;
    }
}
