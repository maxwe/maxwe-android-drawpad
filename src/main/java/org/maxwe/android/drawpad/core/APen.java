package org.maxwe.android.drawpad.core;

import android.graphics.*;
import android.view.MotionEvent;
import org.maxwe.android.drawpad.core.graphics.AnyLine;
import org.maxwe.json.Json;
import org.maxwe.json.JsonArray;
import org.maxwe.json.JsonObject;
import org.maxwe.android.drawpad.Constants;
import org.maxwe.android.drawpad.core.pens.*;

/**
 * Created by dingpengwei on 10/23/14.
 */
public abstract class APen extends Paint implements IPen{

    protected Path path;
    protected IGraphic iGraphic;
    protected float startX;
    protected float startY;
    protected float endX;
    protected float endY;
    protected static boolean filled = DEFAULT_VALUE_PEN_FILLED;
    private float preX;
    private float preY;
    private static float SIZE = DEFAULT_VALUE_PEN_SIZE;

    protected JsonObject jsonStringPen = Json.createJsonObject();

    protected JsonArray moveXY;

    public APen(){
        this.path = new Path();
        this.setColor(DEFAULT_VALUE_PEN_COLOR);
        this.setStrokeWidth(SIZE);
        this.setStyle(Paint.Style.STROKE);
        this.setAntiAlias(true);
        this.moveXY = Json.createJsonArray();
    }

    @Override
    public void setIGraphic(IGraphic iGraphic) {
        this.iGraphic = iGraphic;
        this.iGraphic.setIPen(this);
    }

    @Override
    public IGraphic getIGraphic() {
        return this.iGraphic;
    }

    @Override
    public Path getPath() {
        return this.path;
    }

    @Override
    public void setColor(int color) {
        super.setColor(color);
    }

    @Override
    public int getColor() {
        return super.getColor();
    }

    @Override
    public void setSize(float size) {
        super.setStrokeWidth(SIZE);
        SIZE = size;
    }

    @Override
    public float getSize() {
        return SIZE;
    }

    @Override
    public float getStartX() {
        return this.startX;
    }

    @Override
    public float getStartY() {
        return this.startY;
    }

    @Override
    public float getEndX() {
        return this.endX;
    }

    @Override
    public float getEndY() {
        return this.endY;
    }

    @Override
    public void setFilled(boolean filled) {
        this.filled = filled;
        if(filled){
            this.setStyle(Style.FILL);
        }else{
            this.setStyle(Style.STROKE);
        }
    }

    @Override
    public boolean getFilled() {
        return this.filled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void onTouchDown(float x, float y) {
        this.startX = x;
        this.startY = y;
        this.endX = x;
        this.endY = y;
        this.path.reset();
        this.path.moveTo(this.startX, this.startY);
        this.preX = this.startX;
        this.preY = this.startY;
    }

    @Override
    public void onTouchMove(float x, float y) {
        if(this.getIGraphic() instanceof AnyLine){
            this.moveXY.push(x).push(y);
        }
        this.path.quadTo(preX,preY,(preX + x) / 2 ,(preY + y) / 2);
        this.preX = x;
        this.preY = y;
        this.endX = x;
        this.endY = y;
    }

    @Override
    public void onTouchUp(float x, float y) {
        this.endX = x;
        this.endY = y;
        this.path.lineTo(this.endX,this.endY);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {


    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        switch (e1.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        switch (e2.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {}

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public void onDraw(Canvas canvas) {
        this.iGraphic.onDraw(canvas);
    }

    @Override
    public IPen clone() {
        IPen iPen = null;
        if(this instanceof DefaultPen){
            iPen = new DefaultPen();
        }else if(this instanceof DottedPen){
            iPen = new DottedPen();
        }else if(this instanceof EmbossPen){
            iPen = new EmbossPen();
        }else if(this instanceof EraserPen){
            iPen = new EraserPen();
        }else if(this instanceof BlurPen){
            iPen = new BlurPen();
        }
        iPen.setColor(this.getColor());
        iPen.setFilled(this.getFilled());
        return iPen;
    }

    @Override
    public String toString() {
        this.jsonStringPen.set(Constants.KEY_PEN_NAME,this.getName())
                .set(Constants.KEY_PEN_START_X,this.getStartX())
                .set(Constants.KEY_PEN_START_Y, this.getStartY())
                .set(Constants.KEY_PEN_MOVE_LOCATIONS, this.moveXY)
                .set(Constants.KEY_PEN_END_X, this.getEndX())
                .set(Constants.KEY_PEN_END_Y, this.getEndY())
                .set(Constants.KEY_PEN_COLOR,this.getColor())
                .set(Constants.KEY_PEN_STROKE_WIDTH,this.getStrokeWidth())
                .set(Constants.KEY_PEN_FILLED,this.getFilled());
        return this.jsonStringPen.toString();
    }
}
