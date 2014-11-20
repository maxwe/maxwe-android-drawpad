package org.maxwe.android.drawpad.activities;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import org.maxwe.android.drawpad.Constants;
import org.maxwe.android.drawpad.DrawpadParser;
import org.maxwe.android.drawpad.core.IGraphic;
import org.maxwe.android.drawpad.core.IPen;
import org.maxwe.android.drawpad.core.graphics.AnyLine;
import org.maxwe.android.drawpad.core.graphics.Drawable;
import org.maxwe.android.drawpad.core.pens.DefaultPen;
import org.maxwe.android.drawpad.core.pens.EraserPen;
import org.maxwe.json.Json;
import org.maxwe.json.JsonArray;
import org.maxwe.json.JsonObject;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by dingpengwei on 10/23/14.
 */
public class MainView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, ScaleGestureDetector.OnScaleGestureListener, Constants {

    private JsonObject drawpad = Json.createJsonObject(); // 历史记录：记录整个画板对象
    private JsonArray unredo = Json.createJsonArray(); // 历史记录：记录撤销和反撤销操作

    private static final int MAX_UNREDO_NUM = 10; // 允许最大的撤销个数
    private static final int MIN_UNREDO_NUM = 0; // 允许最小的撤销个数

    private JsonArray confirmingDrawables = Json.createJsonArray(); // 待确认绘制的图片素材对象的集合
    private Drawable confirmingDrawable = null; // 当前待确认绘制的图片素材对象

    private int screenWidth;
    private int screenHeight;
    private Bitmap bitmap; // 系统背景图 使用该bitmap是以提高绘制效率
    private Canvas canvas; // 系统背景图的绘制者
    private IGraphic iGraphic = new AnyLine();
    private IGraphic iGraphicClone;// 当前绘制的对象
    private IPen iPen = new DefaultPen();
    private IPen iPenClone;// 当前的画笔对象

    private GestureDetector gestureDetector;
    private ScaleGestureDetector scaleGestureDetector;

    private int currentDrawStatus = MotionEvent.ACTION_OUTSIDE; //初始化状态为光标未来获取

    private int backgroundColor = Color.WHITE;

    public MainView(Context context) {
        super(context);
        //允许手势操作
        this.setLongClickable(true);
        this.gestureDetector = new GestureDetector(this.getContext(), this);
        this.scaleGestureDetector = new ScaleGestureDetector(this.getContext(), this);
        //默认背景颜色
        this.drawpad.set(KEY_BACKGROUND_FLAG, ValuesOfBackgroundFlag.COLOR).set(KEY_BACKGROUND, this.backgroundColor);
        this.drawpad.set(KEY_GRAPHICS, Json.createJsonArray());
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画背景（以前的笔迹）
        canvas.drawColor(this.backgroundColor);
        canvas.drawBitmap(this.bitmap, 0, 0, new Paint());
        // 画当前（当前的笔迹）
        if (this.currentDrawStatus == MotionEvent.ACTION_MOVE) {
            if(this.iGraphicClone != null){
                this.iGraphicClone.getIPen().onDraw(canvas);
            }
        }

        int lenght = this.confirmingDrawables.getLenght();
        for(int i=0;i<lenght;i++){
            IGraphic iGraphic = this.confirmingDrawables.get(i);
            iGraphic.onDraw(canvas);
        }
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointCount = event.getPointerCount();
        if (pointCount == 2) {
            /**
             * 缩放或旋转
             */
            return this.scaleGestureDetector.onTouchEvent(event);
        } else if (pointCount == 1) {
            boolean touchEvent = this.gestureDetector.onTouchEvent(event);
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    this.currentDrawStatus = MotionEvent.ACTION_DOWN;
                    this.iPenClone = this.iPen.clone();
                    /**
                     * 判断触点是否在图片区域中
                     */
                    int[] inGraphicYIndex = this.isInGraphic(this.confirmingDrawables, x, y);
                    if (inGraphicYIndex[0] != -1) {
                        /**
                         * 触点在drawable区域中
                         */
                        this.confirmingDrawable = this.confirmingDrawables.get(inGraphicYIndex[0]);
                        if(inGraphicYIndex[1] == 1){
                            /**
                             * 触点在确定按钮上
                             */
                            this.confirmingDrawables.remove(inGraphicYIndex[0]);
                            this.confirmingDrawable.setStatus(ValuesOfGraphStatus.CONFIRMED);
                            this.drawpad.getJsonArray(KEY_GRAPHICS).push(this.confirmingDrawable);//存入历史
                            this.confirmingDrawable.getIPen().onDraw(this.canvas);
                        }else if(inGraphicYIndex[1] == 2){
                            /**
                             * 触点在删除按钮上
                             */
                            this.confirmingDrawables.remove(inGraphicYIndex[0]);
                        }else {
                            /**
                             * 触点在图片区域中
                             */
                            this.iPenClone.setIGraphic(this.confirmingDrawable);
                            this.onDrawableTouchDown(x,y);
                        }
                    }else if(this.confirmingDrawable != null){
                        /**
                         * 第一个图片
                         */
                        this.iPenClone.setIGraphic(this.confirmingDrawable);
                        this.confirmingDrawable.setIPen(this.iPenClone);

                        this.confirmingDrawables.push(this.confirmingDrawable);
                        this.onDrawableTouchDown(x,y);
                    }else{
                        /**
                         * 普通图形
                         */
                        this.unredo.clear();
                        this.onTouchDown(x,y);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    this.currentDrawStatus = MotionEvent.ACTION_MOVE;
                    if(this.confirmingDrawable == null){
                        /**
                         * 普通图形
                         */
                        this.onTouchMove(x, y);
                    }else{
                        /**
                         * 图片图形
                         */
                        this.onDrawableTouchMove(x,y);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    this.currentDrawStatus = MotionEvent.ACTION_UP;
                    if(this.confirmingDrawable == null){
                        /**
                         * 普通图形
                         */
                        this.onTouchUp(x, y);
                    }else{
                        /**
                         * 图片图形
                         */
                        this.onDrawableTouchUp(x,y);
                    }

                    break;
            }
            return touchEvent;
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.screenWidth = w;
        this.screenHeight = h;
        this.createBitmap(this.screenWidth,this.screenHeight);
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
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        this.invalidate();
        if(this.confirmingDrawable != null){
            return this.confirmingDrawable.onScale(detector);
        }
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        this.invalidate();
        if(this.confirmingDrawable != null){
            return this.confirmingDrawable.onScaleBegin(detector);
        }
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        if(this.confirmingDrawable != null){
            this.confirmingDrawable.onScaleEnd(detector);
        }
    }

    /**
     * 普通图形的按下事件
     * @param x
     * @param y
     */
    private void onTouchDown(float x, float y) {
        if(this.iPen instanceof EraserPen){
            this.iGraphicClone = new AnyLine();
        }else{
            this.iGraphicClone = this.iGraphic.clone();
        }
        this.iPenClone.setIGraphic(this.iGraphicClone);
        this.iGraphicClone.setIPen(this.iPenClone);
        this.drawpad.getJsonArray(KEY_GRAPHICS).push(this.iGraphicClone);
        this.iGraphicClone.getIPen().onTouchDown(x, y);
    }

    /**
     * 普通图形的移动事件
     * @param x
     * @param y
     */
    private void onTouchMove(float x, float y) {
        this.iGraphicClone.getIPen().onTouchMove(x, y);
        this.invalidate();
    }

    /**
     * 普通图形的抬起事件
     * @param x
     * @param y
     */
    private void onTouchUp(float x, float y) {
        this.iGraphicClone.getIPen().onTouchUp(x,y);
        this.iGraphicClone.getIPen().onDraw(this.canvas);
        this.invalidate();
    }

    /**
     * 图片图形的按下事件
     * @param x
     * @param y
     */
    private void onDrawableTouchDown(float x,float y){
        this.confirmingDrawable.getIPen().onTouchDown(x, y);
        this.invalidate();
    }

    /**
     * 图片图形的移动事件
     * @param x
     * @param y
     */
    private void onDrawableTouchMove(float x,float y){
        this.confirmingDrawable.getIPen().onTouchDown(x, y);
        this.invalidate();
    }

    /**
     * 图片图形的离开事件
     * @param x
     * @param y
     */
    private void onDrawableTouchUp(float x,float y){
        this.confirmingDrawable.getIPen().onTouchUp(x,y);
        this.invalidate();
        this.confirmingDrawable = null;
    }

    /**
     * 创建一张空白的位图
     */
    private void createBitmap(int screenWidth,int screenHeight) {
        this.bitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(this.bitmap);
        this.canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
    }

    /**
     * 公开设置画笔颜色的方法
     * @param color
     */
    public void setPenColor(int color) {
        if(this.iPen instanceof EraserPen){

        }else{
            this.iPen.setColor(color);
        }
    }

    /**
     * 公开获取画笔颜色的方法
     * @return
     */
    public int getPenColor() {
        return this.iPen.getColor();
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        this.invalidate();
        this.drawpad.set(KEY_BACKGROUND_FLAG, ValuesOfBackgroundFlag.COLOR).set(KEY_BACKGROUND, this.backgroundColor);
    }

    public int getBackgroundColor(){
        return this.backgroundColor;
    }




    /**
     * 公开设置画笔大小的方法
     * @param size
     */
    public void setPenSize(float size){
        this.iPen.setSize(size);
    }

    /**
     * 公开获取画笔大小的方法
     * @return
     */
    public float getPenSize(){
        return this.iPen.getSize();
    }

    /**
     * 公开设置画笔填充的方法
     * @return
     */
    public void setPenFilled(boolean filled){
        this.iPen.setFilled(filled);
    }

    /**
     * 公开获取画笔是否填充的方法
     * @return
     */
    public boolean getPenFilled(){
        return this.iPen.getFilled();
    }

    /**
     * 公开设置图形的方法
     * @param iGraphic
     */
    public void setIGraphic(IGraphic iGraphic) {
        if (iGraphic instanceof Drawable) {
            this.confirmingDrawable = (Drawable)iGraphic;
        } else {
            this.iGraphic = iGraphic;
        }
    }

    /**
     * 公开获取图形的方法
     * @return
     */
    public IGraphic getIGraphic() {
        return this.iGraphic;
    }

    /**
     * 公开设置画笔类型的方法
     * @param iPen
     */
    public void setIPen(IPen iPen){
        this.iPen = iPen;
    }

    /**
     * 公开获取画笔类型的方法
     * @return
     */
    public IPen getIPen(){
        return this.iPen;
    }

    /**
     *
     * @return
     */
    public boolean isCanUndo() {
        if (this.unredo.getLenght() < MAX_UNREDO_NUM && this.drawpad.getJsonArray(KEY_GRAPHICS) != null && this.drawpad.getJsonArray(KEY_GRAPHICS).getLenght() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 公开判定是否可以反撤销的方法
     * @return
     */
    public boolean isCanRedo() {
        if (this.unredo.getLenght() > MIN_UNREDO_NUM) {
            return true;
        }
        return false;
    }

    /**
     * 公开撤销操作的方法
     */
    public void undo() {
        if (this.isCanUndo()) {
            this.iGraphicClone = null;
            this.createBitmap(this.screenWidth,this.screenHeight);
            JsonArray graphics = this.drawpad.getJsonArray(KEY_GRAPHICS);
            int lenght = graphics.getLenght();
            IGraphic last = graphics.get(lenght - 1);
            this.unredo.push(last);
            graphics.remove(graphics.getLenght() - 1);
            DrawpadParser.parserLocal(this.getContext(), this.canvas, drawpad);
            this.invalidate();
        }
    }

    /**
     * 公开反撤销操作的方法
     */
    public void redo() {
        if (this.isCanRedo()) {
            this.iGraphicClone = null;
            this.createBitmap(this.screenWidth,this.screenHeight);
            int lenght = this.unredo.getLenght();
            IGraphic last = this.unredo.get(lenght - 1);
            JsonArray graphics = this.drawpad.getJsonArray(KEY_GRAPHICS);
            graphics.push(last);
            this.unredo.remove(lenght - 1);
            DrawpadParser.parserLocal(this.getContext(), this.canvas, drawpad);
            this.invalidate();
        }
    }

    public void clear(){
        this.createBitmap(this.screenWidth, this.screenHeight);
        this.invalidate();
    }

    /**
     * 获取双指旋转的角度
     * @param event
     * @return
     */
    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.abs(Math.atan2(delta_y, delta_x));

        float degrees = (float) Math.toDegrees(radians);
        return degrees;
    }


    /**
     * 判断当前的点击是在那个图片中
     * @param graphics
     * @param x
     * @param y
     * @return
     */
    private int[] isInGraphic(JsonArray graphics, float x, float y) {
        int[] indies = new int[]{-1,0};//数组下标为0的数字表示当前数据所在集合的下标，数组下表为1的数字表示对当前数据进行的操作，0：移动&缩放&旋转，1：确定，2：删除
        int length = graphics.getLenght();
        for (int i = length -1 ; i >= 0; i--) {
            Drawable graph = graphics.get(i);
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), graph.getDrawableId());
            float startX = graph.getIPen().getStartX();
            float startY = graph.getIPen().getStartY();
            float endX = graph.getIPen().getEndX();
            float endY = graph.getIPen().getEndY();

            float width = bitmap.getWidth() * graph.getScale();
            float height = bitmap.getHeight() * graph.getScale();

            float offsetStartX = startX - width / 2;
            float offsetStartY = startY - height / 2 - this.VALUE_GRAPH_DRAWABLE_BITMAP_OFFSET_Y;
            float offsetEndX = endX - width / 2;
            float offsetEndY = endY - height / 2;

            RectF rect = new RectF(offsetStartX, offsetStartY, offsetEndX, offsetEndY);
            if (rect.contains(x, y)) {
                indies[0] = i;
                if(new RectF(offsetStartX, offsetStartY, offsetEndX - width + this.VALUE_GRAPH_DRAWABLE_BITMAP_OFFSET_Y , offsetEndY - height).contains(x,y)){
                    indies[1] = 1;
                }else if(new RectF(offsetStartX + VALUE_GRAPH_DRAWABLE_BITMAP_OFFSET_X, offsetStartY, offsetEndX , offsetEndY - height).contains(x,y)){
                    indies[1] = 2;
                }else{
                    indies[1] = 0;
                }
                return indies;
            }
        }
        return indies;
    }

    /**
     * 公开保存当前图片的方法
     * @param path
     * @return
     * @throws IOException
     */
    public boolean saveBitmap(String path) throws IOException{
        return this.saveBitmap(Bitmap.CompressFormat.JPEG, 100, path);
    }

    /**
     * 公开保存当前图片的方法
     * @param format
     * @param quality
     * @param path
     * @return
     * @throws IOException
     */
    public boolean saveBitmap(Bitmap.CompressFormat format,int quality,String path) throws IOException{
        Bitmap saveBitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(saveBitmap);
        canvas.drawColor(this.backgroundColor);
        canvas.drawBitmap(this.bitmap, 0, 0, null);
        return saveBitmap.compress(format, quality, new FileOutputStream(path));
    }
}
