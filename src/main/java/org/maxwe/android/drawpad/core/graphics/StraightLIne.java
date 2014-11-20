package org.maxwe.android.drawpad.core.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;
import org.maxwe.android.drawpad.core.AGraphic;

/**
 * Created by dingpengwei on 10/23/14.
 */
public class StraightLIne extends AGraphic {

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(this.startX, this.startY, this.endX, this.endY,(Paint)this.iPen);
    }

    @Override
    public String getName() {
        return ValuesOfGraphName.STRAIGHT_LINE.name();
    }
}
