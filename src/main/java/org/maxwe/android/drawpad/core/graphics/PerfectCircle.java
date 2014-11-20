package org.maxwe.android.drawpad.core.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;
import org.maxwe.android.drawpad.core.AGraphic;
import org.maxwe.android.drawpad.Constants;

/**
 * Created by dingpengwei on 10/23/14.
 */
public class PerfectCircle extends AGraphic {

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float radius = (float) Math.sqrt(Math.pow(this.endX - this.startX, 2)+ Math.pow(this.endY - this.startY, 2));
        canvas.drawCircle(this.startX, this.startY, radius, (Paint)this.iPen);
    }

    @Override
    public String getName() {
        return Constants.ValuesOfGraphName.PERFECT_CIRCLE.name();
    }
}
