package org.maxwe.android.drawpad.core.graphics;

import android.content.Context;
import android.graphics.*;
import android.view.ScaleGestureDetector;
import org.maxwe.android.drawpad.R;
import org.maxwe.android.drawpad.core.AGraphic;
import org.maxwe.json.Json;
import org.maxwe.android.drawpad.Constants;

/**
 * Created by dingpengwei on 10/23/14.
 */
public class Drawable extends AGraphic {

    private Context context;
    private int drawableId;

    public Drawable(Context context,int drawableId){
        this.context = context;
        this.drawableId = drawableId;
    }

    public int getDrawableId(){
        return this.drawableId;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return super.onScaleBegin(detector);
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        return super.onScale(detector);
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        super.onScaleEnd(detector);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmapStamp = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.toolsstamp);
        Bitmap bitmapTrash = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.toolstrash);
        Bitmap bitmapMain = BitmapFactory.decodeResource(this.context.getResources(), this.drawableId);

        float bitmapMainWidth = bitmapMain.getWidth() * this.scale;
        float bitmapMainHeight = bitmapMain.getHeight() * this.scale;

        this.iPen.onTouchUp(this.iPen.getStartX() + bitmapMainWidth, this.iPen.getStartY() + bitmapMainHeight);

        float offsetStartX = ((int)this.iPen.getStartX() - bitmapMainWidth / 2);
        float offsetStartY = ((int)this.iPen.getStartY() - bitmapMainHeight / 2);

        Matrix matrix = new Matrix();

        if(this.status != Constants.ValuesOfGraphStatus.CONFIRMED){
            matrix.postTranslate(offsetStartX ,offsetStartY - Constants.VALUE_GRAPH_DRAWABLE_BITMAP_OFFSET_Y);
            canvas.drawBitmap(bitmapStamp,matrix,(Paint)this.iPen);
            matrix.reset();
            matrix.postTranslate(offsetStartX + Constants.VALUE_GRAPH_DRAWABLE_BITMAP_OFFSET_X,offsetStartY - Constants.VALUE_GRAPH_DRAWABLE_BITMAP_OFFSET_Y);
            canvas.drawBitmap(bitmapTrash,matrix,(Paint)this.iPen);
            matrix.reset();
        }

        matrix.postTranslate(offsetStartX / this.scale, offsetStartY / this.scale);
//        matrix.postRotate(this.degree ,offsetStartX / this.scale + bitmapMainWidth / 2 ,offsetStartY / this.scale + bitmapMainHeight / 2);
        matrix.postScale(this.scale, this.scale);
        canvas.drawBitmap(bitmapMain, matrix, (Paint)this.iPen);
        matrix.reset();
    }

    @Override
    public String getName() {
        return Constants.ValuesOfGraphName.DRAWABLE.name();
    }

    @Override
    public String toString() {
        this.jsonStringGraphic.set(Constants.KEY_GRAPH_NAME,this.getName())
                .set(Constants.KEY_GRAPH_STATUS,this.getStatus())
                .set(Constants.KEY_GRAPH_SCALE,this.getScale())
                .set(Constants.KEY_GRAPH_DEGREE,this.getRotationDegree())
                .set(Constants.KEY_GRAPH_PEN,this.getIPen().toString())
                .set(Constants.KEY_GRAPH_DRAWABLE, Json.createJsonObject().set(Constants.KEY_GRAPH_DRAWABLE_BITMAP,this.getDrawableId()));
        return this.jsonStringGraphic.toString();
    }
}
