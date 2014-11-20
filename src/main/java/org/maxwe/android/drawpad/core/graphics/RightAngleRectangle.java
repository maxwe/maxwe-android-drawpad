package org.maxwe.android.drawpad.core.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;
import org.maxwe.android.drawpad.core.AGraphic;
import org.maxwe.android.drawpad.Constants;

/**
 * Created by dingpengwei on 10/23/14.
 */
public class RightAngleRectangle extends AGraphic {

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.startX < endX && this.startY < this.endY) {
            canvas.drawRect(this.startX, this.startY, this.endX, this.endY, (Paint)this.iPen);
        } else if (this.startX >= endX && this.startY < this.endY) {
            canvas.drawRect(this.endX, this.startY, this.startX, this.endY, (Paint)this.iPen);
        } else if (this.startX < endX && this.startY >= this.endY) {
            canvas.drawRect(this.startX, this.endY, this.endX, this.startY, (Paint)this.iPen);
        } else if (this.startX >= endX && this.startY >= this.endY) {
            canvas.drawRect(this.endX, this.endY, this.startX, this.startY, (Paint)this.iPen);
        }

    }

    @Override
    public String getName() {
        return Constants.ValuesOfGraphName.RIGHT_ANGLE_RECTANGLE.name();
    }
}
