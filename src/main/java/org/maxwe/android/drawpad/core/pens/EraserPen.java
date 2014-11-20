package org.maxwe.android.drawpad.core.pens;

import android.graphics.Color;
import org.maxwe.android.drawpad.core.APen;
import org.maxwe.android.drawpad.Constants;


/**
 * Created by dingpengwei on 11/3/14.
 */
public class EraserPen extends APen {
    public EraserPen() {
        super();
        this.setColor(Color.WHITE);
    }



    @Override
    public String getName() {
        return Constants.ValuesOfPenName.ERASER.name();
    }
}
