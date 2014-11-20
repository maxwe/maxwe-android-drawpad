package org.maxwe.android.drawpad.core.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;
import org.maxwe.android.drawpad.core.AGraphic;

/**
 * Created by dingpengwei on 10/23/14.
 */
public class AnyLine extends AGraphic {

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(this.getIPen().getPath(),(Paint)this.getIPen());
    }

    @Override
    public String getName() {
        return ValuesOfGraphName.ANY_LINE.name();
    }
}
