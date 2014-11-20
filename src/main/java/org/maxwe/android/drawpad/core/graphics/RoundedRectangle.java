package org.maxwe.android.drawpad.core.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import org.maxwe.android.drawpad.core.AGraphic;
import org.maxwe.android.drawpad.Constants;

/**
 * Created by dingpengwei on 10/23/14.
 */
public class RoundedRectangle extends AGraphic {

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF();
        if(this.startX < endX && this.startY < this.endY){
            rectF.set(this.startX,this.startY,this.endX,this.endY);
        }else if(this.startX >= endX && this.startY < this.endY){
            rectF.set(this.endX,this.startY,this.startX,this.endY);
        }else if (this.startX < endX && this.startY >= this.endY){
            rectF.set(this.startX,this.endY,this.endX,this.startY);
        }else if(this.startX >= endX && this.startY >= this.endY){
            rectF.set(this.endX,this.endY,this.startX,this.startY);
        }
        canvas.drawRoundRect(rectF,20,20,(Paint)this.iPen);
    }


    @Override
    public String getName() {
        return Constants.ValuesOfGraphName.ROUNDED_RECTANGLE.name();
    }
}
